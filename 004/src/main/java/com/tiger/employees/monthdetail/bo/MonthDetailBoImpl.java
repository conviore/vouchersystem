package com.tiger.employees.monthdetail.bo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.springframework.beans.factory.annotation.Autowired;

import com.tiger.employees.balance.dao.BalanceDao;
import com.tiger.employees.balance.po.Balance;
import com.tiger.employees.monthdetail.vo.MonthDetailVo;
import com.tiger.employees.subject.dao.SubjectDao;
import com.tiger.employees.subject.po.Subject;
import com.tiger.employees.voucherdetail.dao.VoucherDetailDao;
import com.tiger.utilities.ExcelUtil;
import com.tiger.utilities.StringUtil;

public class MonthDetailBoImpl implements MonthDeatailBo {
	
	@Autowired
	private BalanceDao balanceDao;
	@Autowired
	private VoucherDetailDao voucherDetailDao;
	@Autowired
	private SubjectDao subjectDao;

	public List<MonthDetailVo> getMonthDetailList(Map param) {
		// TODO Auto-generated method stub
		
		//获得旧的月份l
		List<MonthDetailVo> resultList=new ArrayList<MonthDetailVo>();
		String settleTime=(String) param.get("settleTime");
//		String dateYear=settleTime.substring(0,4);
//      String dateMonth=settleTime.substring(4, 6);
 		
// 		String oldSettleDateMonth=null;
// 		String oldSettleDateYear=dateYear;
// 		Integer monthInt=null;
// 		
// 		if(dateMonth.equals("01")){
// 			oldSettleDateMonth="12";
// 			oldSettleDateYear=String.valueOf(Integer.valueOf(dateYear)-1);
// 		}
// 		else{
// 			monthInt=Integer.valueOf(dateMonth)-1;
// 			oldSettleDateMonth=String.valueOf(monthInt);
// 			if(monthInt<10){
// 				oldSettleDateMonth="0"+String.valueOf(monthInt);
// 			} 			
// 		}
// 		
// 		String oldSettleDay=null;
//        if(oldSettleDateMonth.equals("01")||oldSettleDateMonth.equals("03")||oldSettleDateMonth.equals("05")||oldSettleDateMonth.equals("07")||oldSettleDateMonth.equals("08")||oldSettleDateMonth.equals("10")||oldSettleDateMonth.equals("12")){
//        	oldSettleDay="31";
//        }else if(oldSettleDateMonth.equals("02")){
//        	oldSettleDay="28";
//        }else{
//        	oldSettleDay="30";
//        }
//		
// 		String oldSettleDate=oldSettleDateYear+oldSettleDateMonth+oldSettleDay;
// 		
//		String companyId=(String)param.get("companyId");
//		
//		String subjectId=(String)param.get("subjectId");
//		
//		Subject sub=this.subjectDao.querySubjectById(subjectId);
//		String subjectName=sub.getComment();
//		String subjectCode=sub.getCode();
//		
//		String initValueSQL="select final_mon_debit-final_mon_credit from balance t where t.subject_id='"+subjectId+"' and t.settle_time='"+oldSettleDate+"'";
//		
//		List<Object> initValueList=this.balanceDao.execSql(initValueSQL);
//		
//		BigDecimal initValue=(BigDecimal)initValueList.get(0);
//		BigDecimal initCalculateValue=initValue;
//		BigDecimal creditDetail=null;
//		BigDecimal debitDetail=null;
//		BigDecimal differenceDetail=initValue;
//		BigDecimal sumDebit=new BigDecimal("0");
//		BigDecimal sumCredit=new BigDecimal("0");
//		String queryDetailValueSQL="SELECT v.voucher_no,s.`comment` as subjectname,vd.`comment`,vd.debit,vd.credit "
//				+ "FROM voucher_detail vd, voucher v, subject s "
//				+ "where "
//				+ "vd.voucher_id=v.id "
////				+ "and s.id='"+subjectId+"' "
//				+ "and s.id=vd.subject_id "
//				+ "and s.code like '" +subjectCode+ "%' "
//				+ "and v.org_id='"+companyId+"' "
//				+ "and v.settle_time like '%" +settleTime+ "%' "
//				
//				+ "order by v.voucher_no,vd.update_time";
        
		try{
		
		String settleYear=settleTime.substring(0,4);
        String settleMonth=settleTime.substring(4, 6);
	    String oldSettleDateYear=String.valueOf(Integer.valueOf(settleYear)-1);

		String companyId=(String)param.get("companyId");
		
		String subjectId=(String)param.get("subjectId");
		
		Subject sub=this.subjectDao.querySubjectById(subjectId);
		String subjectName=sub.getComment();
		String subjectCode=sub.getCode();
		Integer accountDirection=sub.getAccountDirection();
        
        
        //获得年初数据：
		String initValueSQL="select (final_mon_debit-final_mon_credit)*'"+new BigDecimal(accountDirection)+"' from balance t where t.subject_id='"+subjectId+"' and t.settle_time='"+oldSettleDateYear+"1231"+"'";
		
		List<Object> initValueList=this.balanceDao.execSql(initValueSQL);
		
		BigDecimal initValue=new BigDecimal((Double)initValueList.get(0));
		initValue=initValue.setScale(2,BigDecimal.ROUND_HALF_UP);
		
		if(initValue==null){
			initValue=new BigDecimal("0");
		}
		BigDecimal initCalculateValue=initValue;
		BigDecimal creditDetail=null;
		BigDecimal debitDetail=null;
		BigDecimal differenceDetail=initValue;
		BigDecimal sumDebit=new BigDecimal("0");
		BigDecimal sumCredit=new BigDecimal("0");
        
		String queryDetailValueSQL="SELECT v.voucher_no,s.`comment` as subjectname,vd.`comment`,ifnull(vd.debit,0),ifnull(vd.credit,0),vd.settle_time "
		+ "FROM voucher_detail vd, voucher v, subject s "
		+ "where "
		+ "vd.voucher_id=v.id "
//		+ "and s.id='"+subjectId+"' "
		+ "and s.id=vd.subject_id "
		+ "and s.code like '" +subjectCode+ "%' "
		+ "and v.org_id='"+companyId+"' "
		+ "and v.settle_time like '" +settleYear+ "%'"
		
		+ "order by v.settle_time,v.voucher_no,vd.update_time";
		
		List<Object> detailValueList=this.voucherDetailDao.execSql(queryDetailValueSQL);
		MonthDetailVo vo=null;
		//计算每一条明细
		for(Object rawResult:detailValueList){
			Object[] rawResultArray=(Object[]) rawResult;
			vo=new MonthDetailVo();
			vo.setSettleTime(rawResultArray[5].toString());
			vo.setVoucherNumber(rawResultArray[0].toString());
			vo.setSubjectName(rawResultArray[1].toString());
			vo.setComment(rawResultArray[2].toString());
//			debitDetail=new BigDecimal(rawResultArray[2]);
			debitDetail=(BigDecimal)rawResultArray[3];
			vo.setDebit(debitDetail);
			sumDebit=sumDebit.add(debitDetail);
//			creditDetail=new BigDecimal(rawResultArray[3].toString());
			creditDetail=(BigDecimal)rawResultArray[4];
			vo.setCredit(creditDetail);
			differenceDetail=differenceDetail.add(debitDetail.multiply(new BigDecimal(accountDirection))).add(creditDetail.negate().multiply(new BigDecimal(accountDirection)));
			differenceDetail=differenceDetail.setScale(2,BigDecimal.ROUND_HALF_UP );
			vo.setDifference(differenceDetail);
			vo.setSubjectCode(subjectCode);
			vo.setSubjectName(subjectName);
			sumCredit=sumCredit.add(creditDetail);
			resultList.add(vo);			
		}
		
		//总和数据
		vo=new MonthDetailVo();
		vo.setSettleTime(settleTime);
		vo.setDebit(sumDebit);
		vo.setCredit(sumCredit);
		vo.setComment("总和");
		vo.setDifference(initValue.add(sumDebit.multiply(new BigDecimal(accountDirection))).add(sumCredit.negate().multiply(new BigDecimal(accountDirection))));
		resultList.add(vo);
		
		
		return resultList;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	public InputStream getMonthDetailExcel(Map param) {
		// TODO Auto-generated method stub
		if(param!=null){
			//获得报表数据
			String companyName=(String) param.get("companyName");
			param.remove("companyName");
			String subjectId=(String) param.get("subjectId");
			Subject sub=this.subjectDao.querySubjectById(subjectId);
			String subjectName=sub.getComment();
//			List<MonthDetailVo> detailList=this.getMonthDetailList(param);
			List<MonthDetailVo> detailList=this.getMonDetailList(param);
			String settleDate=(String) param.get("settleTime");
			
			/**
			 * 制作报表
			 */
			HSSFWorkbook workbook = new HSSFWorkbook();  
			
	        HSSFSheet sheet = workbook.createSheet("sheet1");  
	        
	        sheet.setColumnWidth(0, 3300);
	        sheet.setColumnWidth(1, 3300);
	        sheet.setColumnWidth(2, 3300);
	        sheet.setColumnWidth(3, 3300);
	        sheet.setColumnWidth(4, 6600);
	        sheet.setColumnWidth(5, 3300);
	        sheet.setColumnWidth(6, 3300);
	        sheet.setColumnWidth(7, 3300);
	        {  
	            // 创建表头  
	            HSSFRow row = sheet.createRow(0);  
	            HSSFCell cell = row.createCell((short) 0);  
	            cell.setCellValue(companyName+subjectName+"科目明细");  
	            cell = row.createCell((short) 1);  
	            cell.setCellValue(subjectName+"科目明细");  
	            cell = row.createCell((short) 6);  
	            cell.setCellValue("会计期间");  
	            cell = row.createCell((short) 7);  
	            cell.setCellValue(settleDate);  
	            
	            //创建列头
	            HSSFFont colHeadFont = workbook.createFont();
	            colHeadFont.setFontName("宋体");
	            colHeadFont.setFontHeightInPoints((short) 10);
	            HSSFCellStyle colHeadStyle=ExcelUtil.getColHeaderStyle(workbook);
	            colHeadStyle.setFont(colHeadFont);
	            row = sheet.createRow(1);  
	            cell = row.createCell(0, HSSFCell.CELL_TYPE_STRING);
	            cell.setCellValue("会计期间");
	            cell.setCellStyle(colHeadStyle);
	            cell = row.createCell(1, HSSFCell.CELL_TYPE_STRING);
	            cell.setCellValue("科目编码");
	            cell.setCellStyle(colHeadStyle);
	            cell = row.createCell(2, HSSFCell.CELL_TYPE_STRING);
	            cell.setCellValue("科目名称");
	            cell.setCellStyle(colHeadStyle);
	            cell = row.createCell(3, HSSFCell.CELL_TYPE_NUMERIC);
	            cell.setCellValue("凭证编号");
	            cell.setCellStyle(colHeadStyle);
	            cell = row.createCell(4, HSSFCell.CELL_TYPE_NUMERIC);
	            cell.setCellValue("摘要");
	            cell.setCellStyle(colHeadStyle);
	            cell = row.createCell(5, HSSFCell.CELL_TYPE_NUMERIC);
	            cell.setCellValue("借方");
	            cell.setCellStyle(colHeadStyle);
	            cell = row.createCell(6, HSSFCell.CELL_TYPE_NUMERIC);
	            cell.setCellValue("贷方");
	            cell.setCellStyle(colHeadStyle);
	            cell = row.createCell(7, HSSFCell.CELL_TYPE_NUMERIC);
	            cell.setCellValue("余额");
	            cell.setCellStyle(colHeadStyle);
	            cell = row.createCell(8, HSSFCell.CELL_TYPE_STRING);
	            cell.setCellValue("记账方向");
	            cell.setCellStyle(colHeadStyle);

	            
	            
	            // 创建数据  
	            HSSFCellStyle colContentStyle=ExcelUtil.getColContentStyle(workbook);
	            HSSFCellStyle colContentMoneyStyle=ExcelUtil.getColContentMonStyle(workbook);
	            // 第一行  公司 会计期间
	            MonthDetailVo detailVo=null;
	            for(int i=0;i<detailList.size();i++){
	            	row = sheet.createRow(i+2);
	            	detailVo=detailList.get(i);
	            	cell = row.createCell(0, HSSFCell.CELL_TYPE_STRING);
//		            cell.setCellValue("会计期间"); 
	            	cell.setCellValue(detailVo.getSettleTime());
	            	cell.setCellStyle(colContentStyle);
		            cell = row.createCell(1, HSSFCell.CELL_TYPE_STRING);
//		            cell.setCellValue("科目编码");
		            cell.setCellValue(detailVo.getSubjectCode());
		            cell.setCellStyle(colContentStyle);
		            cell = row.createCell(2, HSSFCell.CELL_TYPE_STRING);
//		            cell.setCellValue("科目名称");  
		            cell.setCellValue(detailVo.getSubjectName());
		            cell.setCellStyle(colContentStyle);
		            cell = row.createCell(3, HSSFCell.CELL_TYPE_STRING);
//		            cell.setCellValue("凭证编号");  
		            cell.setCellValue(detailVo.getVoucherNumber());
		            cell.setCellStyle(colContentStyle);
		            cell = row.createCell(4, HSSFCell.CELL_TYPE_STRING);
//		            cell.setCellValue("摘要");  
		            cell.setCellValue(detailVo.getComment());
		            cell.setCellStyle(colContentStyle);
		            cell = row.createCell(5, HSSFCell.CELL_TYPE_NUMERIC);
//		            cell.setCellValue("借方");
		            cell.setCellValue(detailVo.getDebit().doubleValue());
		            cell.setCellStyle(colContentMoneyStyle);
		            cell = row.createCell(6, HSSFCell.CELL_TYPE_NUMERIC);
//		            cell.setCellValue("贷方");
		            cell.setCellValue(detailVo.getCredit().doubleValue());
		            cell.setCellStyle(colContentMoneyStyle);
		            cell = row.createCell(7, HSSFCell.CELL_TYPE_NUMERIC);
//		            cell.setCellValue("余额");
		            cell.setCellValue(detailVo.getDifference().doubleValue());
		            cell.setCellStyle(colContentMoneyStyle);
		            cell = row.createCell(8, HSSFCell.CELL_TYPE_STRING);
//		            cell.setCellValue("记账方向");
		            cell.setCellValue(detailVo.getSettleWay());
		            cell.setCellStyle(colContentStyle);
	            }
	        }  
	 
	      //合并单元格
            Region topRegion1 = new Region(0, (short) 0, 0, (short) 5);
            sheet.addMergedRegion(topRegion1);
	        
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

	public List<Subject> getSubject4Combolbox(Map param) {
		// TODO Auto-generated method stub
		List<Subject> resultList=null;
		if(param!=null){
			resultList=this.subjectDao.querySubjects(param);
		}
		return resultList;
	}

	public String getSubjectName(String subjectId) {
		// TODO Auto-generated method stub
		if(!StringUtil.isEmpty(subjectId)){
			Subject subject=this.subjectDao.querySubjectById(subjectId);
			if(subject!=null){
				return subject.getComment();
			}
		}
		return null;
	}

	public List<MonthDetailVo> getMonDetailList(Map param) {
		// TODO Auto-generated method stub
		//获得旧的月份
				Map balanceParam=new HashMap();
				List<Balance> balanceList=null;
				Balance lastMonSubBalancePo=null;
				
				List<MonthDetailVo> resultList=new ArrayList<MonthDetailVo>();
				String settleTime=(String) param.get("settleTime");
		        
				try{
				
				String settleYear=settleTime.substring(0,4);
		        String settleMonth=settleTime.substring(4, 6);
		        
		        String oldSettleYear="";
		        String oldSettleMonth="";
		        
			    String oldSettleDateYear=String.valueOf(Integer.valueOf(settleYear)-1);

				String companyId=(String)param.get("companyId");
				
				String subjectId=(String)param.get("subjectId");
				
				Subject sub=this.subjectDao.querySubjectById(subjectId);
				String subjectName=sub.getComment();
				String subjectCode=sub.getCode();
				Integer accountDirection=sub.getAccountDirection();
		        Integer monthInt=null;
		        Map tempMap=new HashMap();
		        tempMap.putAll(param);
		        
		        if(!settleMonth.equals("12")&&(!settleMonth.equals("11"))&&(!settleMonth.equals("10"))){
		        	int settleMonthInt=Integer.parseInt(settleMonth.substring(1));
		        	for (int i=1;i<settleMonthInt+1;i++){
		        		String calculMon=settleYear+"0"+String.valueOf(i);
		        		tempMap.clear();
		        		tempMap.putAll(param);
		        		tempMap.remove("settleTime");
		        		tempMap.put("settleTime", calculMon);
		        		resultList.addAll(this.getAMonDetailList(tempMap));
		        	}
		        }
		        else if (settleMonth.equals("01")){
		        	resultList=this.getAMonDetailList(param);
		        }
		        else{
		        	int settleMonthInt=Integer.parseInt(settleMonth);
		        	String calculMon=null;
		        	for (int i=1;i<settleMonthInt+1;i++){
		        		if(i<10){
		        			calculMon=settleYear+"0"+String.valueOf(i);
		        		}else{
		        			calculMon=settleYear+String.valueOf(i);
		        		}
		        		
		        		tempMap.clear();
		        		tempMap.putAll(param);
		        		tempMap.remove("settleTime");
		        		tempMap.put("settleTime", calculMon);
		        		resultList.addAll(this.getAMonDetailList(tempMap));
		        	}
		        }
				return resultList;
				}catch(Exception e){
					e.printStackTrace();
					return null;
				}
	}		

	private List<MonthDetailVo> getAMonDetailList(Map param){
		// TODO Auto-generated method stub
				//获得旧的月份
						Map balanceParam=new HashMap();
						List<Balance> balanceList=null;
						Balance lastMonSubBalancePo=null;
						
						List<MonthDetailVo> resultList=new ArrayList<MonthDetailVo>();
						String settleTime=(String) param.get("settleTime");
				        
						try{
						
						String settleYear=settleTime.substring(0,4);
				        String settleMonth=settleTime.substring(4, 6);
				        
				        String oldSettleYear="";
				        String oldSettleMonth="";
				        
					    String oldSettleDateYear=String.valueOf(Integer.valueOf(settleYear)-1);

						String companyId=(String)param.get("companyId");
						
						String subjectId=(String)param.get("subjectId");
						
						Subject sub=this.subjectDao.querySubjectById(subjectId);
						String subjectName=sub.getComment();
						String subjectCode=sub.getCode();
						Integer accountDirection=sub.getAccountDirection();
				        Integer monthInt=null;
				        
				    //获得年初数据：
						
					    //获得上月日期     	
						/**
						 * 如果这个月是一月份
						 */
						if(settleMonth.equals("01")){
							oldSettleMonth="12";
				 			oldSettleYear=String.valueOf(Integer.valueOf(settleYear)-1);
				 		}
						/**
						 * 否则 取上个月的
						 */
				 		else{
				 			monthInt=Integer.valueOf(settleMonth)-1;
				 			oldSettleMonth=String.valueOf(monthInt);
				 			oldSettleYear=settleYear;
				 			if(monthInt<10){
				 				oldSettleMonth="0"+String.valueOf(monthInt);
				 			} 			
				 		}
						
						String oldSettleDay=null;
				        if(oldSettleMonth.equals("01")||oldSettleMonth.equals("03")||oldSettleMonth.equals("05")||oldSettleMonth.equals("07")||oldSettleMonth.equals("08")||oldSettleMonth.equals("10")||oldSettleMonth.equals("12")){
				        	oldSettleDay="31";
				        }else if(oldSettleMonth.equals("02")){
				        	oldSettleDay="28";
				        }else{
				        	oldSettleDay="30";
				        }
						
						String oldSettleDate=oldSettleYear+oldSettleMonth+oldSettleDay;
						
						String initValueSQL="select (final_mon_debit-final_mon_credit)*'"+new BigDecimal(accountDirection)+"' from balance t where t.subject_id='"+subjectId+"' and t.settle_time='"+oldSettleDate+"'";
						
						//get last month's cumulative debit and credit value
						balanceParam.put("subjectId", subjectId);
						balanceParam.put("orgId", companyId);
						balanceParam.put("settleTime", oldSettleDate);
						balanceList=this.balanceDao.getListBalance(balanceParam);
						if(balanceList!=null&&balanceList.size()>0){
							lastMonSubBalancePo=balanceList.get(0);
							if(lastMonSubBalancePo.getCumulativeCredit()==null){
								lastMonSubBalancePo.setCumulativeCredit(new BigDecimal("0"));
							}
							if(lastMonSubBalancePo.getCumulativeDebit()==null){
								lastMonSubBalancePo.setCumulativeDebit(new BigDecimal("0"));
							}
						}else{
							lastMonSubBalancePo=new Balance();
							lastMonSubBalancePo.setCumulativeCredit(new BigDecimal("0"));
							lastMonSubBalancePo.setCumulativeDebit(new BigDecimal("0"));
							
						}
						
						
						
						List<Object> initValueList=this.balanceDao.execSql(initValueSQL);
						
						if((initValueList==null)||(initValueList.size()==0)){
							String initValueSQL2="select (final_mon_debit-final_mon_credit)*'"+new BigDecimal(accountDirection)+"' from balance t where t.subject_id='"+subjectId+"' order by t.settle_time";
							initValueList=this.balanceDao.execSql(initValueSQL2);
						}else{
							
						}
						
						
						BigDecimal initValue=new BigDecimal((Double)initValueList.get(0));
						
						
						initValue=initValue.setScale(2,BigDecimal.ROUND_HALF_UP);
						
						if(initValue==null){
							initValue=new BigDecimal("0");
						}
						
						BigDecimal initCalculateValue=initValue;
						BigDecimal creditDetail=null;
						BigDecimal debitDetail=null;
						BigDecimal differenceDetail=initValue;
						BigDecimal sumDebit=new BigDecimal("0");
						BigDecimal sumCredit=new BigDecimal("0");
				        
						String queryDetailValueSQL="SELECT v.voucher_no,s.`comment` as subjectname,vd.`comment`,ifnull(vd.debit,0),ifnull(vd.credit,0),vd.settle_time,vd.settle_way "
						+ "FROM voucher_detail vd, voucher v, subject s "
						+ "where "
						+ "vd.voucher_id=v.id "
//						+ "and s.id='"+subjectId+"' "
						+ "and s.id=vd.subject_id "
						+ "and s.code like '" +subjectCode+ "%' "
						+ "and v.org_id='"+companyId+"' "
						+ "and v.settle_time like '" +settleYear+settleMonth+ "%'"
						
						+ "order by v.settle_time,v.voucher_no,vd.update_time";
						
						List<Object> detailValueList=this.voucherDetailDao.execSql(queryDetailValueSQL);
						MonthDetailVo vo=null;
						//计算每一条明细
						for(Object rawResult:detailValueList){
							Object[] rawResultArray=(Object[]) rawResult;
							vo=new MonthDetailVo();
							vo.setSettleTime(rawResultArray[5].toString());
							vo.setVoucherNumber(rawResultArray[0].toString());
							vo.setSubjectName(rawResultArray[1].toString());
							vo.setComment(rawResultArray[2].toString());
//							debitDetail=new BigDecimal(rawResultArray[2]);
							debitDetail=(BigDecimal)rawResultArray[3];
							vo.setDebit(debitDetail);
							sumDebit=sumDebit.add(debitDetail);
//							creditDetail=new BigDecimal(rawResultArray[3].toString());
							creditDetail=(BigDecimal)rawResultArray[4];
							vo.setCredit(creditDetail);
							differenceDetail=differenceDetail.add(debitDetail.multiply(new BigDecimal(accountDirection))).add(creditDetail.negate().multiply(new BigDecimal(accountDirection)));
							differenceDetail=differenceDetail.setScale(2,BigDecimal.ROUND_HALF_UP );
							vo.setDifference(differenceDetail);
							vo.setSubjectCode(subjectCode);
							vo.setSubjectName(subjectName);
							vo.setSettleWay(rawResultArray[6].toString());
							sumCredit=sumCredit.add(creditDetail);
							resultList.add(vo);			
						}
						
						//本月总和数据 本月发生额 the current month's debit and credit sum value
						vo=new MonthDetailVo();
						vo.setSettleTime(settleTime);
						vo.setDebit(sumDebit);
						vo.setCredit(sumCredit);
						vo.setComment("月发生额");
						vo.setDifference(initValue.add(sumDebit.multiply(new BigDecimal(accountDirection))).add(sumCredit.negate().multiply(new BigDecimal(accountDirection))));
						resultList.add(vo);
						
						//本年leiji发生额
						BigDecimal currentMonCumulativeCredit=lastMonSubBalancePo.getCumulativeCredit().add(sumCredit);
						BigDecimal currentMonCumulativeDebit=lastMonSubBalancePo.getCumulativeDebit().add(sumDebit);
						
						vo=new MonthDetailVo();
						vo.setSettleTime(settleTime);
						vo.setDebit(currentMonCumulativeDebit);
						vo.setCredit(currentMonCumulativeCredit);
						vo.setComment("年发生额");
						vo.setDifference(initValue.add(sumDebit.multiply(new BigDecimal(accountDirection))).add(sumCredit.negate().multiply(new BigDecimal(accountDirection))));
						resultList.add(vo);
						
						
						return resultList;
						}catch(Exception e){
							e.printStackTrace();
							return null;
						}
		
	}
	
}
