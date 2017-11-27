package com.tiger.employees.monsettleprog.bo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javassist.compiler.Javac;

import com.tiger.employees.profititem.dao.ProfitItemDao;
import com.tiger.employees.profititem.po.ProfitItem;
import com.tiger.employees.profititem.vo.ProfitItemVo;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.springframework.beans.factory.annotation.Autowired;

import com.tiger.employees.balanceitem.dao.BalanceItemDao;
import com.tiger.employees.balanceitem.po.BalanceItem;
import com.tiger.employees.balanceitem.vo.BalanceItemVo;
import com.tiger.employees.company.dao.CompanyDao;
import com.tiger.employees.company.po.Company;
import com.tiger.employees.monsettleprog.dao.MonthSettleProgressDao;
import com.tiger.employees.monsettleprog.po.MonthSettleProgress;
import com.tiger.employees.voucherdetail.vo.VoucherDetailVo;
import com.tiger.employees.vouchersummary.vo.VSReportVo;
import com.tiger.utilities.ExcelUtil;
import com.tiger.utilities.SettleConfig;
import com.tiger.utilities.StringUtil;
import com.tiger.utilities.TimeUtil;

public class MonthSettleProgressBoImpl implements MonthSettleProgressBo {

	private final static String ITEM_TYPE_SYMBOL_ASSET="zichan";
	private final static String ITEM_TYPE_SYMBOL_LIBILITIES="fuzhai";
	
	@Autowired
	private MonthSettleProgressDao monthSettleProgressDao;
	
	@Autowired
	private BalanceItemDao balanceItemDao;

	@Autowired
	private ProfitItemDao profitItemDao;
	
	@Autowired
	private CompanyDao companyDao;
	
	
	DecimalFormat myformat=new java.text.DecimalFormat("0.00");
	
	

	public List<MonthSettleProgress> getMSPs(Map param) {
		// TODO Auto-generated method stub
		List<MonthSettleProgress> resultList=null;
		resultList=this.monthSettleProgressDao.getMSPs(param);
		return resultList;
	}

	public MonthSettleProgress getMSPById(String id) {
		// TODO Auto-generated method stub
		MonthSettleProgress po=null;
		po=this.monthSettleProgressDao.getMSPById(id);
		return po;
	}

	public void addMSP(MonthSettleProgress msp) {
		// TODO Auto-generated method stub
		String mspId=UUID.randomUUID().toString();
		if(msp!=null){
			msp.setId(mspId);
			msp.setCreateTime(TimeUtil.getCurrentTime17ByMillis());
			this.monthSettleProgressDao.addMSP(msp);
		}
		
	}

	public void updateMSP(MonthSettleProgress msp) {
		// TODO Auto-generated method stub
		this.monthSettleProgressDao.updateMSP(msp);
		
	}

	/**
	 * 月结后开始新的月份结帐
	 */
	public void addMonSettleProg(Map inputParam) {
		// TODO Auto-generated method stub
		
		//查看是否有未月结的记录
//		Map param=new HashMap();
//		param.put("status", SettleConfig.MON_SETTLE_INCOMPLETE);
//		List<MonthSettleProgress> mspList=this.monthSettleProgressDao.getMSPs(param);
//		if(mspList==null){
//			
//		}
		String companyId=inputParam.get("companyId").toString();
		String sql="select max(concat(settle_year,settle_month)) as aname from month_settle_progress where company_id='"+companyId+"'";
		List<Object> maxMonthObjectList=this.monthSettleProgressDao.execSql(sql);
		String currentSettleDate=maxMonthObjectList.get(0).toString();
		String currentSettleMonth=currentSettleDate.substring(4, 6);
		String currentSettleYear=currentSettleDate.substring(0, 4);
		String newSettleMonth=null;
		String newSettleYear=null;
		int currentSettleMonthInt=Integer.valueOf(currentSettleMonth);
		int currentSettleYearInt=Integer.valueOf(currentSettleYear);
		if(currentSettleMonthInt<12){
			
			newSettleMonth=String.valueOf(currentSettleMonthInt+1);
			if(currentSettleMonthInt<9){
				newSettleMonth="0"+""+newSettleMonth;
			}
			
			newSettleYear=currentSettleYear;
		}
		else{
			newSettleMonth="01";
			newSettleYear=String.valueOf(currentSettleYearInt+1);
		}
		
		MonthSettleProgress msp=new MonthSettleProgress();
		msp.setCompanyId(companyId);
		msp.setId(UUID.randomUUID().toString());
		msp.setCreateTime(TimeUtil.getCurrentTime17ByMillis());
		msp.setUpdateTime(TimeUtil.getCurrentTime17ByMillis());
		msp.setSettleMonth(newSettleMonth);
		msp.setSettleYear(newSettleYear);
		msp.setStatus(SettleConfig.MON_SETTLE_INCOMPLETE);
		this.monthSettleProgressDao.addMSP(msp);
		
	}

	/**
	 * 制造资产负债表
	 * @param param
	 * @return
	 */
	public InputStream getBalanceSheetExcel(Map param) {
		if(param!=null){
//			//获得报表数据
			//获得company name
			Company org=this.companyDao.getCompanyById(param.get("orgId").toString());
			String companyName="";
			if(org!=null){
			companyName=org.getCompanyName();
			}
//			param.remove("companyName");
//			List<VoucherDetailVo> vdList=this.getVoucherSummary(param);
			String settleDate=(String) param.get("settleTime");
        List<BalanceItemVo> assetList=this.getBalanceAssetsData(param);
        List<BalanceItemVo> liabList=this.getBalanceLiabilitiesData(param);




			/**
			 * 制作报表
			 */
			HSSFWorkbook workbook = new HSSFWorkbook();  
	        HSSFSheet sheet = workbook.createSheet("sheet1");  
	        sheet.setColumnWidth(0, 5100);
	        sheet.setColumnWidth(1, 800);
	        sheet.setColumnWidth(2, 3300);
	        sheet.setColumnWidth(3, 3300);
	        sheet.setColumnWidth(4, 5100);
	        sheet.setColumnWidth(5, 800);
	        sheet.setColumnWidth(6, 3300);
	        sheet.setColumnWidth(7, 3300);

			/**
			 * set red font style
			 */
			CellStyle style = workbook.createCellStyle();
			Font font = workbook.createFont();
			font.setColor(HSSFColor.RED.index);
			style.setFont(font);

	        {  
	            // 创建表头
	        	HSSFFont topFont = workbook.createFont();
	        	topFont.setFontName("宋体");
	        	topFont.setFontHeightInPoints((short) 16);
	        	topFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//加粗 
	        	
	        	HSSFFont topFont2 = workbook.createFont();
	        	topFont2.setFontName("宋体");
	        	topFont2.setFontHeightInPoints((short) 10);
	        	topFont2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);//加粗 
	        	
	             
	        	HSSFCellStyle topStyle=ExcelUtil.getColContentStyle(workbook);
				topStyle.setBorderBottom(HSSFCellStyle.BORDER_NONE);
				topStyle.setBorderLeft(HSSFCellStyle.BORDER_NONE);
				topStyle.setBorderRight(HSSFCellStyle.BORDER_NONE);
				topStyle.setBorderTop(HSSFCellStyle.BORDER_NONE);

				
	        	HSSFCellStyle topStyle2=ExcelUtil.getColContentStyle(workbook);
				topStyle2.setBorderBottom(HSSFCellStyle.BORDER_NONE);
				topStyle2.setBorderLeft(HSSFCellStyle.BORDER_NONE);
				topStyle2.setBorderRight(HSSFCellStyle.BORDER_NONE);
				topStyle2.setBorderTop(HSSFCellStyle.BORDER_NONE);

	        	topStyle.setFont(topFont);
	            HSSFRow row = sheet.createRow(0);
	            row.setHeight((short)600);
	            HSSFCell cell = row.createCell((short) 0);  
	            cell.setCellValue("资产负债表");
	            cell.setCellStyle(topStyle);
	            cell = row.createCell((short) 3);  
	            cell.setCellValue("字第   号");  
	            topStyle2.setFont(topFont2);
	            cell.setCellStyle(topStyle2);
	            cell = row.createCell((short) 4);

	            row = sheet.createRow(1);
	            row.setHeight((short)450);
	            
	            cell = row.createCell((short) 0);  
	            cell.setCellValue("编制单位： "+companyName); 
	            cell.setCellStyle(topStyle2);
	            
	            cell = row.createCell((short) 3);  
	            cell.setCellValue(settleDate.substring(0, 4)+"年"+settleDate.substring(4,6)+"月"); 
	            cell.setCellStyle(topStyle2);
	            
	            cell = row.createCell((short) 7);  
	            cell.setCellValue("单位：元"); 
	            cell.setCellStyle(topStyle2);
	            
	            //创建列头
	            HSSFFont colHeadFont = workbook.createFont();
	            colHeadFont.setFontName("宋体");
	            colHeadFont.setFontHeightInPoints((short) 10);
	            HSSFCellStyle colHeadStyle=ExcelUtil.getColHeaderStyle(workbook);
	            colHeadStyle.setFont(colHeadFont);
	            row = sheet.createRow(2);
	            row.setHeight((short)450);
	            cell = row.createCell((short) 0);  
	            cell.setCellValue("资产");
	            HSSFCellStyle colHeadStyle2=ExcelUtil.getColHeaderStyle(workbook);
//	            colHeadStyle2.setBorderLeft(HSSFCellStyle.BORDER_DOUBLE);
//	            colHeadStyle2.setBorderTop(HSSFCellStyle.BORDER_DOUBLE);
	            cell.setCellStyle(colHeadStyle2);
	            cell = row.createCell((short) 1);  
	            cell.setCellValue("批次");
	            HSSFCellStyle colHeadStyle3=ExcelUtil.getColHeaderStyle(workbook);
//	            colHeadStyle3.setBorderTop(HSSFCellStyle.BORDER_DOUBLE);
	            cell.setCellStyle(colHeadStyle3);
	            cell = row.createCell((short) 2);  
	            cell.setCellValue("期末余额");
	            HSSFCellStyle colHeadStyle4=ExcelUtil.getColHeaderStyle(workbook);
//	            colHeadStyle4.setBorderTop(HSSFCellStyle.BORDER_DOUBLE);
	            cell.setCellStyle(colHeadStyle4);
	            cell = row.createCell((short) 3);
	            cell.setCellValue("年初余额");
	            HSSFCellStyle colHeadStyle5=ExcelUtil.getColHeaderStyle(workbook);
	            cell.setCellStyle(colHeadStyle5);
	            
	            cell = row.createCell((short) 4);  
	            cell.setCellValue("负债与所有着权益");
//	            colHeadStyle2.setBorderLeft(HSSFCellStyle.BORDER_DOUBLE);
//	            colHeadStyle2.setBorderTop(HSSFCellStyle.BORDER_DOUBLE);
	            cell.setCellStyle(colHeadStyle2);
	            cell = row.createCell((short) 5);  
	            cell.setCellValue("批次");
//	            colHeadStyle3.setBorderTop(HSSFCellStyle.BORDER_DOUBLE);
	            cell.setCellStyle(colHeadStyle3);
	            cell = row.createCell((short) 6);  
	            cell.setCellValue("期末余额");
//	            colHeadStyle4.setBorderTop(HSSFCellStyle.BORDER_DOUBLE);
	            cell.setCellStyle(colHeadStyle4);
	            cell = row.createCell((short) 7);
	            cell.setCellValue("年初余额");
	            cell.setCellStyle(colHeadStyle5);
	            

	            
	            HSSFCellStyle colContentStyle=ExcelUtil.getColContentStyle(workbook);
	            HSSFCellStyle colContentStyleL=ExcelUtil.getColContentStyle(workbook);
	            HSSFCellStyle colContentStyleR=ExcelUtil.getColContentStyle(workbook);
				HSSFCellStyle colContentStyleRed=ExcelUtil.getColContentStyle(workbook);
				colContentStyleRed.setFont(font);

	            row = sheet.createRow(3);
	            cell = row.createCell((short) 0); 
	            cell.setCellValue("流动资产");
	            cell.setCellStyle(colContentStyle);
	            cell = row.createCell((short) 4); 
	            cell.setCellValue("流动负债");
	            cell.setCellStyle(colContentStyle);
	            int redflag=0;
	            // 创建数据  
	            // 左面 资产类

	            BalanceItemVo asset=null;
	            for(int i=0;i<assetList.size();i++){
	            	row = sheet.createRow(i+4);
	            	row.setHeight((short)450);
	            	asset=assetList.get(i);
	            	cell = row.createCell((short) 0);  
//		            cell.setCellValue("项目表"); 
	            	cell.setCellValue(asset.getItemName());
	            	cell.setCellStyle(colContentStyleL);
		            cell = row.createCell((short) 1);  
//		            cell.setCellValue("行次");
		            cell.setCellValue(asset.getItemNo());
		            cell.setCellStyle(colContentStyle);
		            cell = row.createCell((short) 2);  
//		            cell.setCellValue("期末余额");
		            if(asset.getEndBalance()==null){
		            	if(!StringUtil.isEmpty(asset.getFomula())){
		            		cell.setCellFormula(asset.getFomula());
		            	}
		            }
		            else{
		            	cell.setCellValue(this.myformat.format(asset.getEndBalance().doubleValue()));
						if(asset.getEndBalance().doubleValue()<0){
							redflag=1;
						}
		            }

		            cell.setCellStyle(colContentStyle);
		            if(redflag>0){
		            	cell.setCellStyle(colContentStyleRed);
		            	redflag=0;
					}

		            cell = row.createCell((short) 3);
//		            cell.setCellValue("年初余额");
		            if(asset.getInitYearBalance()==null){
		            	if(!StringUtil.isEmpty(asset.getFomula())){
		            		cell.setCellFormula(asset.getFomula().replace("C", "D"));
		            	}
		            }
		            else{
		            	cell.setCellValue(this.myformat.format(asset.getInitYearBalance().doubleValue()));
						if(asset.getInitYearBalance().doubleValue()<0){
							redflag=1;
						}
		            }
		            cell.setCellStyle(colContentStyleR);
					if(redflag>0){
						cell.setCellStyle(colContentStyleRed);
						redflag=0;
					}
	            }
	            
	         // 右面 负债类
	            
	            BalanceItemVo liab=null;
	            for(int i=0;i<liabList.size();i++){
	            	row = sheet.getRow(i+4);
	            	row.setHeight((short)450);
	            	liab=liabList.get(i);
	            	cell = row.createCell((short) 4);  
//		            cell.setCellValue("项目表"); 
	            	cell.setCellValue(liab.getItemName());
	            	cell.setCellStyle(colContentStyleL);
		            cell = row.createCell((short) 5);  
//		            cell.setCellValue("行次");
		            cell.setCellValue(liab.getItemNo());
		            cell.setCellStyle(colContentStyle);
		            cell = row.createCell((short) 6);  
//		            cell.setCellValue("期末余额");
		            if(liab.getEndBalance()==null){
		            	if(!StringUtil.isEmpty(liab.getFomula())){
		            		cell.setCellFormula(liab.getFomula());
		            	}
		            	else{
		            		cell.setCellValue("0");
		            	}
		            }
		            else{
		            	if(StringUtil.isEmpty(liab.getItemNo())){
		            		cell.setCellValue("");
		            	}
		            	else{
		            		cell.setCellValue(liab.getEndBalance().doubleValue());
		            	}
		            }
		            cell.setCellStyle(colContentStyle);
		            cell = row.createCell((short) 7);  
//		            cell.setCellValue("年初余额");
		            if(liab.getInitYearBalance()==null){
		            	if(!StringUtil.isEmpty(liab.getFomula())){
		            		String newFormula=liab.getFomula().replace('G', 'H');
		            		cell.setCellFormula(newFormula);
		            	}
		            	else{
		            		cell.setCellValue("0");
		            	}
		            }
		            else{
		            	if(StringUtil.isEmpty(liab.getItemNo())){
		            		cell.setCellValue("");
		            	}
		            	else{
		            	cell.setCellValue(liab.getInitYearBalance().doubleValue());
		            	}
		            }
		            cell.setCellStyle(colContentStyleR);
		            
	            }
	            
	            
	            //合并单元格
	            Region topRegion1 = new Region(0, (short) 0, 0, (short) 7);
	            sheet.addMergedRegion(topRegion1);
//	            Region topRegion2 = new Region(1, (short) 0, 1, (short) 2);
//	            sheet.addMergedRegion(topRegion2);
	            
	        }  
	 
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
	        try {  
	            workbook.write(baos);  
	            baos.flush();
	            baos.close();
	            byte[] ba = baos.toByteArray();  
	            ByteArrayInputStream bais = new ByteArrayInputStream(ba);  
	            return bais;  
	        } catch (IOException e) {  
	            // TODO Auto-generated catch block  
	            e.printStackTrace();  
	            return null;
	        }  
		}
		return null;}
	
	/**
	 * 准备资产类类数据
	 * @param param
	 * @return
	 */
	private List<BalanceItemVo> getBalanceAssetsData(Map param){
		List<BalanceItemVo> resultList=new ArrayList();
		String orgId=(String) param.get("orgId");
		String settleTime=(String) param.get("settleTime");
		Map queryItemMap=new HashMap();
		queryItemMap.put("itemType", ITEM_TYPE_SYMBOL_ASSET);
		List<BalanceItem> itemList=this.balanceItemDao.getBalanceItems(queryItemMap);
		if(itemList!=null){
			for(BalanceItem po:itemList){
				BalanceItemVo vo= new BalanceItemVo(po);
				if(po.getId().equals("item016")){
					System.out.print("The item is item016");
				}
				if(vo.getIfFomula().equals(SettleConfig.if_fomula_false)){
					if(!StringUtil.isEmpty(vo.getRelatedCode())){
						String querySql="select ifnull(sum(init_year_balance),0),ifnull(sum(end_balance),0) from balance where subject_code in ("+vo.getRelatedCode()+") and org_id='"+orgId+"' and settle_time like '"+settleTime+"%'";
					    List<Object> sumList=this.monthSettleProgressDao.execSql(querySql);
						Object[] sumArray=(Object[]) sumList.get(0);
						BigDecimal initYearValue=(BigDecimal) sumArray[0];
						BigDecimal finalValue=(BigDecimal) sumArray[1];
						vo.setInitYearBalance(initYearValue);
						vo.setEndBalance(finalValue);
					}
				}else{
					if(vo.getItemNo().equals("15")){
						vo.setFomula("SUM(C5:C9)+C18");
					}
					else if(vo.getItemNo().equals("29")){
						//vo.setFomula("SUM(C20:C21)+SUM(C24:C32)");
						vo.setFomula("SUM(C17:C27)");
					}
					else if(vo.getItemNo().equals("30")){
						vo.setFomula("C19+C33");
					}
				}
				
				resultList.add(vo);
			}
			return resultList;
		}
		return null;
	}
	
	/**
	 * 准备负债类数据
	 * @param param
	 * @return
	 */
	private List<BalanceItemVo> getBalanceLiabilitiesData(Map param){
		List<BalanceItemVo> resultList=new ArrayList();
		String orgId=(String) param.get("orgId");
		String settleTime=(String) param.get("settleTime");
		Map queryItemMap=new HashMap();
		queryItemMap.put("itemType", ITEM_TYPE_SYMBOL_LIBILITIES);
		List<BalanceItem> itemList=this.balanceItemDao.getBalanceItems(queryItemMap);
		if(itemList!=null){
			for(BalanceItem po:itemList){
				BalanceItemVo vo= new BalanceItemVo(po);

				if(vo.getIfFomula().equals(SettleConfig.if_fomula_false)){
					if(!StringUtil.isEmpty(vo.getRelatedCode())){
						String querySql="select ifnull(sum(init_year_balance),0),ifnull(sum(end_balance),0) " +
										"from balance " +
								"		where subject_code in ("+vo.getRelatedCode()+") and org_id='"+orgId
										+"' and settle_time like '"+settleTime+"%'";
					    List<Object> sumList=this.monthSettleProgressDao.execSql(querySql);
						Object[] sumArray=(Object[]) sumList.get(0);
						BigDecimal initYearValue=(BigDecimal) sumArray[0];
						BigDecimal finalValue=(BigDecimal) sumArray[1];
						vo.setInitYearBalance(initYearValue);
						vo.setEndBalance(finalValue);
					}
				}else{
					if(vo.getItemNo().equals("42")){
						vo.setFomula("SUM(G5:G14)");
					}
					else if(vo.getItemNo().equals("46")){
						vo.setFomula("SUM(G17:G20)");
					}
					else if(vo.getItemNo().equals("47")){
						vo.setFomula("G15+G21");
					}
					else if(vo.getItemNo().equals("54")){
						vo.setFomula("SUM(G24:G27)");
					}
					else if(vo.getItemNo().equals("55")){
						vo.setFomula("G22+G33");
					}
					
				}

				resultList.add(vo);
				//aling the two sides
				if(vo.getItemNo().equals("53")){
					for (int i = 0; i < 5; i++) {
						BalanceItemVo vvo= new BalanceItemVo(po);
						vvo.setInitYearBalance(new BigDecimal('0'));
						vvo.setItemNo("");
						vvo.setEndBalance(new BigDecimal('0'));
						vvo.setItemName("");
						resultList.add(vvo);
					}

				}
			}
			return resultList;
		}
		return null;
	}

	public void initAccountingDirection() {
		// TODO Auto-generated method stub
		this.monthSettleProgressDao.execDmlSql("update subject set accounting_direction='-1' where code like '2%'");
		this.monthSettleProgressDao.execDmlSql("update subject set accounting_direction='-1' where code like '3%'");
	}

	/**
	 * 制作利润表
	 * @param param
	 * @return
	 */
	public InputStream getProfitSheetExcel(Map param) {
		if(param!=null){
//			//获得报表数据
			//获得company name
			Company org=this.companyDao.getCompanyById(param.get("orgId").toString());
			String companyName="";
			if(org!=null){
			companyName=org.getCompanyName();
			}
			//			param.remove("companyName");
//			List<VoucherDetailVo> vdList=this.getVoucherSummary(param);
			String settleDate=(String) param.get("settleTime");

			/**
			 *  获取利润表数据
			 */
			List<ProfitItemVo> profitDataList=this.getProfitData(param);



			/**
			 * 制作报表
			 */
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("sheet1");
			sheet.setColumnWidth(0, 6100);
			sheet.setColumnWidth(1, 800);
			sheet.setColumnWidth(2, 3300);
			sheet.setColumnWidth(3, 3300);

			/**
			 * set red font style
			 */
			CellStyle style = workbook.createCellStyle();
			Font font = workbook.createFont();
			font.setColor(HSSFColor.RED.index);
			style.setFont(font);

			{
				// 创建表头
				HSSFFont topFont = workbook.createFont();
				topFont.setFontName("宋体");
				topFont.setFontHeightInPoints((short) 16);
				topFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//加粗

				HSSFFont topFont2 = workbook.createFont();
				topFont2.setFontName("宋体");
				topFont2.setFontHeightInPoints((short) 10);
				topFont2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);//加粗


				HSSFCellStyle topStyle=ExcelUtil.getColContentStyle(workbook);
				HSSFCellStyle topStyle2=ExcelUtil.getColContentStyle(workbook);
				topStyle.setFont(topFont);
				topStyle.setBorderBottom(HSSFCellStyle.BORDER_NONE);
				topStyle.setBorderLeft(HSSFCellStyle.BORDER_NONE);
				topStyle.setBorderRight(HSSFCellStyle.BORDER_NONE);
				topStyle.setBorderTop(HSSFCellStyle.BORDER_NONE);
				topStyle2.setBorderBottom(HSSFCellStyle.BORDER_NONE);
				topStyle2.setBorderLeft(HSSFCellStyle.BORDER_NONE);
				topStyle2.setBorderRight(HSSFCellStyle.BORDER_NONE);
				topStyle2.setBorderTop(HSSFCellStyle.BORDER_NONE);		
				
				HSSFRow row = sheet.createRow(0);
				row.setHeight((short)600);
				HSSFCell cell = row.createCell((short) 0);
				cell.setCellValue("利润表");
				cell.setCellStyle(topStyle);
				cell = row.createCell((short) 3);
				//利润表日期
				cell.setCellValue(settleDate);
				topStyle2.setFont(topFont2);
				cell.setCellStyle(topStyle2);
				cell = row.createCell((short) 4);

				row = sheet.createRow(1);
				row.setHeight((short)450);

				cell = row.createCell((short) 0);
				cell.setCellValue("编制单位： "+companyName);
				cell.setCellStyle(topStyle2);

				cell = row.createCell((short) 2);
	            cell.setCellValue(settleDate.substring(0, 4)+"年"+settleDate.substring(4,6)+"月");
				cell.setCellStyle(topStyle2);

				cell = row.createCell((short) 3);
				cell.setCellValue("单位：元");
				cell.setCellStyle(topStyle2);

				//创建列头
				HSSFFont colHeadFont = workbook.createFont();
				colHeadFont.setFontName("宋体");
				colHeadFont.setFontHeightInPoints((short) 10);
				HSSFCellStyle colHeadStyle=ExcelUtil.getColHeaderStyle(workbook);
				colHeadStyle.setFont(colHeadFont);
				row = sheet.createRow(2);
				row.setHeight((short)450);
				cell = row.createCell((short) 0);
				cell.setCellValue("项目");
				HSSFCellStyle colHeadStyle2=ExcelUtil.getColHeaderStyle(workbook);
//	            colHeadStyle2.setBorderLeft(HSSFCellStyle.BORDER_DOUBLE);
//	            colHeadStyle2.setBorderTop(HSSFCellStyle.BORDER_DOUBLE);
				cell.setCellStyle(colHeadStyle2);
				cell = row.createCell((short) 1);
				cell.setCellValue("行次");
				HSSFCellStyle colHeadStyle3=ExcelUtil.getColHeaderStyle(workbook);
//	            colHeadStyle3.setBorderTop(HSSFCellStyle.BORDER_DOUBLE);
				cell.setCellStyle(colHeadStyle3);
				cell = row.createCell((short) 2);
				cell.setCellValue("本月金额");
				HSSFCellStyle colHeadStyle4=ExcelUtil.getColHeaderStyle(workbook);
//	            colHeadStyle4.setBorderTop(HSSFCellStyle.BORDER_DOUBLE);
				cell.setCellStyle(colHeadStyle4);
				cell = row.createCell((short) 3);
				cell.setCellValue("本年累计");
				HSSFCellStyle colHeadStyle5=ExcelUtil.getColHeaderStyle(workbook);
				cell.setCellStyle(colHeadStyle5);

				HSSFCellStyle colContentStyle=ExcelUtil.getColContentStyle(workbook);
				HSSFCellStyle colContentStyleL=ExcelUtil.getColContentStyle(workbook);
				HSSFCellStyle colContentStyleR=ExcelUtil.getColContentStyle(workbook);
				HSSFCellStyle colContentStyleRed=ExcelUtil.getColContentStyle(workbook);
				colContentStyleRed.setFont(font);


				int redflag=0;

				/**
				 *  填充数据
				 */

				ProfitItemVo profitItem=null;
				for(int i=0;i<profitDataList.size();i++){
					row = sheet.createRow(i+3);
					row.setHeight((short)450);
					profitItem=profitDataList.get(i);
					cell = row.createCell((short) 0);
//		            cell.setCellValue("项目表");
					cell.setCellValue(profitItem.getItemName());
					cell.setCellStyle(colContentStyleL);
					cell = row.createCell((short) 1);
//		            cell.setCellValue("行次");
					cell.setCellValue(profitItem.getItemNo());
					cell.setCellStyle(colContentStyle);
					cell = row.createCell((short) 2);
//		            cell.setCellValue("本月金额");
					if(profitItem.getIfFomula().equals("true")){
						if(!StringUtil.isEmpty(profitItem.getFormula())){
							cell.setCellFormula(profitItem.getFormula());
						}
					}
					else{
						cell.setCellValue(myformat.format(profitItem.getAmountIn().doubleValue()));
						if(profitItem.getAmountIn().doubleValue()<0){
							redflag=1;
						}
					}

					cell.setCellStyle(colContentStyle);
					if(redflag>0){
						cell.setCellStyle(colContentStyleRed);
						redflag=0;
					}

					cell = row.createCell((short) 3);
//		            cell.setCellValue("本年累计");
					if(profitItem.getIfFomula().equals("true")){
						if(!StringUtil.isEmpty(profitItem.getFormula())){
							String formula=profitItem.getFormula().replace('C', 'D');
							cell.setCellFormula(formula);
						}
					}
					else{
						cell.setCellValue(myformat.format(profitItem.getAmountTotal().doubleValue()));
						if(profitItem.getAmountTotal().doubleValue()<0){
							redflag=1;
						}
					}
					cell.setCellStyle(colContentStyleR);
					if(redflag>0){
						cell.setCellStyle(colContentStyleRed);
						redflag=0;
					}
				}

				//合并单元格
				Region topRegion1 = new Region(0, (short) 0, 0, (short) 3);
				sheet.addMergedRegion(topRegion1);
//	            Region topRegion2 = new Region(1, (short) 0, 1, (short) 3);
//	            sheet.addMergedRegion(topRegion2);
//				Region topRegion3 = new Region(2, (short) 0, 1, (short) 2);
//				sheet.addMergedRegion(topRegion3);
			}

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			try {
				workbook.write(baos);
				baos.flush();
				baos.close();
				byte[] ba = baos.toByteArray();
				ByteArrayInputStream bais = new ByteArrayInputStream(ba);
				return bais;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
		return null;}

	/**
	 * prepoare the profit sheet's data
	 * @param param
	 * @return
	 */
	private List<ProfitItemVo> getProfitData(Map param){
		List<ProfitItem> rawProfitDataList=null;
		List<ProfitItemVo> ProfitDataVolist=new ArrayList<ProfitItemVo>();
		String orgId=(String) param.get("orgId");
		
		
		
		String settleTime=(String) param.get("settleTime");
		param.remove("orgId");
		param.remove("settleTime");
		rawProfitDataList = this.profitItemDao.getProfitItems(param);
		ProfitItem po=null;
		ProfitItemVo vo=null;


		for (ProfitItem po2:rawProfitDataList){
			vo=new ProfitItemVo(po2);
			
			if(!StringUtil.isEmpty(vo.getRelatedCode())){
			
			String querySqlDebit="select ifnull(sum(current_mon_debit),0),ifnull(sum(cumulative_debit),0) " +
					"from balance " +
					"		where subject_code in ("+vo.getRelatedCode()+") and org_id='"+orgId
					+"' and settle_time like '"+settleTime+"%'";
			String querySqlCredit="select ifnull(sum(current_mon_credit),0),ifnull(sum(cumulative_credit),0) " +
					"from balance " +
					"		where subject_code in ("+vo.getRelatedCode()+") and org_id='"+orgId
					+"' and settle_time like '"+settleTime+"%'";
			List<Object> sumList=null;
			if(vo.getItemType().equals("debit")){
				sumList=this.monthSettleProgressDao.execSql(querySqlDebit);
			}
			else if (vo.getItemType().equals("credit")){
				sumList=this.monthSettleProgressDao.execSql(querySqlCredit);
			}
			Object[] sumArray=(Object[]) sumList.get(0);
			BigDecimal month_accural=(BigDecimal) sumArray[0];
			BigDecimal year_accural=(BigDecimal) sumArray[1];
			vo.setAmountIn(month_accural);
			vo.setAmountTotal(year_accural);
			}
			
			ProfitDataVolist.add(vo);

		}


			return ProfitDataVolist;
	}

	/**
	 * 记账凭证汇总表制作
	 */
	public InputStream getVoucherSummaryExcel(Map param) {
		// TODO Auto-generated method stub
		if(param!=null){
//			//获得company name
			Company org=this.companyDao.getCompanyById(param.get("orgId").toString());
			String companyName="";
			if(org!=null){
			companyName=org.getCompanyName();
			}
//			param.remove("companyName");
//			List<VoucherDetailVo> vdList=this.getVoucherSummary(param);
			String settleDate=(String) param.get("settleTime");

			/**
			 *  get表数据
			 */
			List<VSReportVo> vsrList=this.getVsData(param);

			/**
			 * 制作报表
			 */
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("sheet1");
			sheet.setColumnWidth(0, 6100);
			sheet.setColumnWidth(1, 3300);
			sheet.setColumnWidth(2, 3300);
			sheet.setColumnWidth(3, 3300);

			/**
			 * set red font style
			 */
			CellStyle style = workbook.createCellStyle();
			Font font = workbook.createFont();
			font.setColor(HSSFColor.RED.index);
			style.setFont(font);

			{
				// 创建表头
				HSSFFont topFont = workbook.createFont();
				topFont.setFontName("宋体");
				topFont.setFontHeightInPoints((short) 16);
				topFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//加粗

				HSSFFont topFont2 = workbook.createFont();
				topFont2.setFontName("宋体");
				topFont2.setFontHeightInPoints((short) 10);
				topFont2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);//加粗


				HSSFCellStyle topStyle=ExcelUtil.getColContentStyle(workbook);
				topStyle.setBorderBottom(HSSFCellStyle.BORDER_NONE);
				topStyle.setBorderLeft(HSSFCellStyle.BORDER_NONE);
				topStyle.setBorderRight(HSSFCellStyle.BORDER_NONE);
				topStyle.setBorderTop(HSSFCellStyle.BORDER_NONE);

				
				HSSFCellStyle topStyle2=ExcelUtil.getColContentStyle(workbook);
				topStyle2.setBorderTop(HSSFCellStyle.BORDER_NONE);
				topStyle2.setBorderBottom(HSSFCellStyle.BORDER_NONE);
				topStyle2.setBorderLeft(HSSFCellStyle.BORDER_NONE);
				topStyle2.setBorderRight(HSSFCellStyle.BORDER_NONE);

				topStyle.setFont(topFont);
				HSSFRow row = sheet.createRow(0);
				row.setHeight((short)600);
				HSSFCell cell = row.createCell((short) 0);
				cell.setCellValue("记账凭证汇总表");
				cell.setCellStyle(topStyle);
//				cell = row.createCell((short) 1);
				//利润表日期
//				cell.setCellValue(settleDate);
//				topStyle2.setFont(topFont2);
//				cell.setCellStyle(topStyle2);
//				cell = row.createCell((short) 3);

				row = sheet.createRow(1);
				row.setHeight((short)450);

				cell = row.createCell((short) 0);
				cell.setCellValue("编制单位： "+companyName);
//				cell.setCellStyle(topStyle2);

				cell = row.createCell((short) 1);
	            cell.setCellValue(settleDate.substring(0, 4)+"年"+settleDate.substring(4,6)+"月");
//				cell.setCellStyle(topStyle2);

				cell = row.createCell((short) 2);
				cell.setCellValue("号凭证");
//				cell.setCellStyle(topStyle2);

				//创建列头
				HSSFFont colHeadFont = workbook.createFont();
				colHeadFont.setFontName("宋体");
				colHeadFont.setFontHeightInPoints((short) 10);
				HSSFCellStyle colHeadStyle=ExcelUtil.getColHeaderStyle(workbook);
				colHeadStyle.setFont(colHeadFont);
				row = sheet.createRow(2);
				row.setHeight((short)450);
				cell = row.createCell((short) 0);
				cell.setCellValue("会计科目");
				HSSFCellStyle colHeadStyle2=ExcelUtil.getColHeaderStyle(workbook);
//	            colHeadStyle2.setBorderLeft(HSSFCellStyle.BORDER_DOUBLE);
//	            colHeadStyle2.setBorderTop(HSSFCellStyle.BORDER_DOUBLE);
				cell.setCellStyle(colHeadStyle2);
				cell = row.createCell((short) 1);
				cell.setCellValue("借方金额");
				HSSFCellStyle colHeadStyle3=ExcelUtil.getColHeaderStyle(workbook);
//	            colHeadStyle3.setBorderTop(HSSFCellStyle.BORDER_DOUBLE);
				cell.setCellStyle(colHeadStyle3);
				cell = row.createCell((short) 2);
				cell.setCellValue("贷方金额");
				HSSFCellStyle colHeadStyle4=ExcelUtil.getColHeaderStyle(workbook);
//	            colHeadStyle4.setBorderTop(HSSFCellStyle.BORDER_DOUBLE);
				cell.setCellStyle(colHeadStyle4);


				HSSFCellStyle colContentStyle=ExcelUtil.getColContentStyle(workbook);
				HSSFCellStyle colContentStyleL=ExcelUtil.getColContentStyle(workbook);
				HSSFCellStyle colContentStyleR=ExcelUtil.getColContentStyle(workbook);
				HSSFCellStyle colContentStyleRed=ExcelUtil.getColContentStyle(workbook);
				colContentStyleRed.setFont(font);


				int redflag=0;

				/**
				 *  填充数据
				 */
				int rowNum=0;
				
				VSReportVo vsItem=null;
				for(int i=0;i<vsrList.size();i++){
					row = sheet.createRow(i+3);
					rowNum=i+3;
					row.setHeight((short)450);
					vsItem=vsrList.get(i);
					cell = row.createCell((short) 0);
//		            cell.setCellValue("会计科目");
					cell.setCellValue(vsItem.getSubjectCode()+"-"+vsItem.getSubjectName());
					cell.setCellStyle(colContentStyleL);
					
					cell = row.createCell((short) 1);
//		            cell.setCellValue("借方金额");
//					cell.setCellValue(myformat.format(vsItem.getCurrentDebit()).toString());
					cell.setCellValue(new Double(myformat.format(vsItem.getCurrentDebit())));
					cell.setCellStyle(colContentStyle);
					
					cell = row.createCell((short) 2);
//		            cell.setCellValue("贷方金额");
					cell.setCellValue(new Double(myformat.format(vsItem.getCurrentCredit())));
//					cell.setCellValue(myformat.format(vsItem.getCurrentCredit()).toString());
					cell.setCellStyle(colContentStyle);
					
				}
				
//				rowNum=rowNum+1;
				row = sheet.createRow(rowNum+1);
				cell = row.createCell((short) 0);
//	            cell.setCellValue("会计科目");
				cell.setCellValue("总计");
				cell.setCellStyle(colContentStyleL);
				
				cell = row.createCell((short) 1);
//	            cell.setCellValue("借方金额");
				cell.setCellFormula("SUM(B4:B"+rowNum+")");;
				cell.setCellStyle(colContentStyle);
				
				cell = row.createCell((short) 2);
//	            cell.setCellValue("贷方金额");
				cell.setCellFormula("SUM(C4:C"+rowNum+")");
				cell.setCellStyle(colContentStyle);




				//合并单元格
				Region topRegion1 = new Region(0, (short) 0, 0, (short) 2);
				sheet.addMergedRegion(topRegion1);
//	            Region topRegion2 = new Region(1, (short) 0, 1, (short) 3);
//	            sheet.addMergedRegion(topRegion2);
//				Region topRegion3 = new Region(2, (short) 0, 1, (short) 2);
//				sheet.addMergedRegion(topRegion3);
			}

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			try {
				workbook.write(baos);
				baos.flush();
				baos.close();
				byte[] ba = baos.toByteArray();
				ByteArrayInputStream bais = new ByteArrayInputStream(ba);
				return bais;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}
	
	private List<VSReportVo>getVsData(Map param){
		List<Object> rawDataList=null;
		List<VSReportVo> vsrVoList=new ArrayList();
		String orgId=(String) param.get("orgId");
		String settleTime=(String) param.get("settleTime");
		String sql="select subject_code,subject.comment,current_mon_debit,current_mon_credit "
				+ "from balance,subject where settle_time like '"+settleTime+"%' "
				+ "and length(subject_code)<5 "
				+ "and balance.subject_code=subject.code "
				+ "and balance.org_id='"+orgId+"' "
				+ "and subject.org_id='"+orgId+"' "
				+ "order by subject_code";
		rawDataList=this.monthSettleProgressDao.execSql(sql);
		Object o=null;
		VSReportVo vo=null;
		for (int i=0;i<rawDataList.size();i++){
			Object[] rawArray=(Object[]) rawDataList.get(i);
			String subjectCode=(String)rawArray[0];
			String subjectName=(String)rawArray[1];
			BigDecimal currCredit=(BigDecimal) rawArray[3];
			BigDecimal currDebit=(BigDecimal) rawArray[2];
			vo=new VSReportVo();
			vo.setCompanyId(orgId);
			vo.setCurrentCredit(currCredit);
			vo.setCurrentDebit(currDebit);
			vo.setSubjectName(subjectName);
			vo.setSubjectCode(subjectCode);
			vo.setSettleDate(settleTime);
			vsrVoList.add(vo);
		}
		return vsrVoList;
		
	}

	
}
