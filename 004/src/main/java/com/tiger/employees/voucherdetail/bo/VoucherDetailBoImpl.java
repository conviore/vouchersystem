package com.tiger.employees.voucherdetail.bo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.Region;
import org.springframework.beans.factory.annotation.Autowired;

import com.tiger.employees.balance.po.Balance;
import com.tiger.employees.subject.dao.SubjectDao;
import com.tiger.employees.subject.po.Subject;
import com.tiger.employees.voucher.dao.VoucherDao;
import com.tiger.employees.voucher.po.Voucher;
import com.tiger.employees.voucherdetail.dao.VoucherDetailDao;
import com.tiger.employees.voucherdetail.po.VoucherDetail;
import com.tiger.employees.voucherdetail.vo.VoucherDetailVo;
import com.tiger.utilities.ExcelUtil;
import com.tiger.utilities.StringUtil;

public class VoucherDetailBoImpl implements VoucherDetailBo {

	@Autowired
	private VoucherDetailDao voucherDetailDao;
	
	@Autowired
	private VoucherDao voucherDao;
	
	@Autowired
	private SubjectDao subjectDao;

	public List<VoucherDetail> getDetailList(Map param) {
		// TODO Auto-generated method stub
		List<VoucherDetail> resultList=this.voucherDetailDao.getVoucherDetails(param);
		return resultList;
	}

	public VoucherDetail getDetailById(String id) {
		// TODO Auto-generated method stub
		if(!StringUtil.isEmpty(id)){
			VoucherDetail po=this.voucherDetailDao.getVoucherDetailById(id);
            return po;
		}
		else{
			
			return null;
		}
	}

	public void insertVoucherDetail(VoucherDetail vd) {
		// TODO Auto-generated method stub
		this.voucherDetailDao.addVoucherDetail(vd);
		
	}

	public void modifyVoucherDetail(VoucherDetail vd) {
		// TODO Auto-generated method stub
		this.voucherDetailDao.updateVoucherDetail(vd);
	}

	public void insertDetailList(List<VoucherDetail> vdl) {
		// TODO Auto-generated method stub
		for(VoucherDetail vd:vdl){
			String subjectid=vd.getSubjectId();
			Subject sub=this.subjectDao.findById(subjectid);
			if(sub!=null){
				if(sub.getCode()!=null){
					vd.setSubjectRootCode(sub.getCode().substring(0, 4));
				}
			}
		}
		this.voucherDetailDao.addVoucherDetailList(vdl);
		
	}

	public void modifyDetailList(List<VoucherDetail> vdl) {
		// TODO Auto-generated method stub
		for(VoucherDetail vd:vdl){
			String subjectid=vd.getSubjectId();
			Subject sub=this.subjectDao.findById(subjectid);
			if(sub!=null){
				if(sub.getCode()!=null){
					vd.setSubjectRootCode(sub.getCode().substring(0, 4));
				}
			}
		}
		this.voucherDetailDao.updateVoucherDetailList(vdl);
	}

	public List<VoucherDetailVo> getDetailListVo(Map param) {
		// TODO Auto-generated method stub
		List<VoucherDetail> resultList=this.voucherDetailDao.getVoucherDetails(param);
		List<VoucherDetailVo> resultListVo=new ArrayList();
		for(VoucherDetail po:resultList){
			VoucherDetailVo vo=new VoucherDetailVo(po);
			if(!StringUtil.isEmpty(vo.getSubjectId())){
				Subject subjectPo=this.subjectDao.querySubjectById(vo.getSubjectId());
				vo.setSubjectCode(subjectPo.getCode());
			}
			resultListVo.add(vo);
		}
		return resultListVo;
	}

	public InputStream getVoucherSummaryExcel(Map param) {
		if(param!=null){
			//获得报表数据
			String companyName=(String) param.get("companyName");
			param.remove("companyName");
			Voucher v=this.voucherDao.getVoucherById(param.get("voucherId").toString());
			String no="";
			if(!StringUtil.isEmpty(v.getVoucherNo())){
				no=v.getVoucherNo();
			}
			List<VoucherDetailVo> vdList=this.getVoucherSummary(param);
			String settleDate=(String) param.get("settleTime");
			
			/**
			 * 制作报表
			 */
			HSSFWorkbook workbook = new HSSFWorkbook();  
	        HSSFSheet sheet = workbook.createSheet("sheet1"); 
	        //set column width
	        sheet.setColumnWidth(0, 5742);
	        sheet.setColumnWidth(1, 5742);
	        sheet.setColumnWidth(2, 5742);
	        sheet.setColumnWidth(3, 5742);
	        
	        {  
	            // 创建表头
	        	HSSFFont topFont = workbook.createFont();
	        	topFont.setFontName("宋体");
	        	topFont.setFontHeightInPoints((short) 16);
	        	topFont.setUnderline(HSSFFont.U_DOUBLE);
	        	topFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//加粗 
	        	
	        	HSSFFont topFont2 = workbook.createFont();
	        	topFont2.setFontName("宋体");
	        	topFont2.setFontHeightInPoints((short) 12);
	        	topFont2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
	        	
	        	HSSFFont colFont1 = workbook.createFont();
	        	colFont1.setFontName("宋体");
	        	colFont1.setFontHeightInPoints((short) 12);
	        	colFont1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//bold;
	        	
	        	HSSFFont colFont2 = workbook.createFont();
	        	colFont2.setFontName("宋体");
	        	colFont2.setFontHeightInPoints((short) 11);
	        	colFont2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
	        	
	             
	        	HSSFCellStyle topStyle=ExcelUtil.getColContentStyle(workbook);
	        	HSSFCellStyle topStyle2=ExcelUtil.getColContentStyle(workbook);
	        	HSSFCellStyle footerStyle=ExcelUtil.getColContentStyle(workbook);


	        	topStyle.setFont(topFont);
	        	topStyle.setBorderTop(HSSFCellStyle.BORDER_NONE);
	        	topStyle.setBorderLeft(HSSFCellStyle.BORDER_NONE);
	        	topStyle.setBorderRight(HSSFCellStyle.BORDER_NONE);
	        	topStyle.setBorderBottom(HSSFCellStyle.BORDER_NONE);
	        	
	        	footerStyle.setFont(topFont);
	        	footerStyle.setBorderTop(HSSFCellStyle.BORDER_NONE);
	        	footerStyle.setBorderLeft(HSSFCellStyle.BORDER_NONE);
	        	footerStyle.setBorderRight(HSSFCellStyle.BORDER_NONE);
	        	footerStyle.setBorderBottom(HSSFCellStyle.BORDER_NONE);
	        	footerStyle.setFont(topFont2);
	        	footerStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);

				HSSFCellStyle colTopStyle1=ExcelUtil.getColHeaderStyle(workbook);
				colTopStyle1.setBorderTop(HSSFCellStyle.BORDER_NONE);
				colTopStyle1.setBorderLeft(HSSFCellStyle.BORDER_NONE);
				colTopStyle1.setBorderRight(HSSFCellStyle.BORDER_NONE);
				colTopStyle1.setFont(topFont2);
				
				
				HSSFCellStyle colTopStyleCom=ExcelUtil.getColHeaderStyle(workbook);
				colTopStyleCom.setBorderTop(HSSFCellStyle.BORDER_NONE);
				colTopStyleCom.setBorderLeft(HSSFCellStyle.BORDER_NONE);
				colTopStyleCom.setBorderRight(HSSFCellStyle.BORDER_NONE);
				colTopStyleCom.setBorderBottom(HSSFCellStyle.BORDER_NONE);
				colTopStyleCom.setFont(topFont2);



				HSSFRow row = sheet.createRow(0);
	            row.setHeight((short)600);
	            HSSFCell cell = row.createCell((short) 0);  
	            cell.setCellValue("记账凭证");
	            cell.setCellStyle(topStyle);
	            cell = row.createCell((short) 3);  
	            //cell.setCellValue("字第   号");
	            //topStyle2.setFont(topFont2);
	            //cell.setCellStyle(topStyle2);
	            cell = row.createCell((short) 4);

	            row = sheet.createRow(1);
	            row.setHeight((short)450);
				cell = row.createCell((short) 0);
				cell.setCellStyle(colTopStyleCom);
				cell.setCellValue("企业名称："+companyName);
				cell = row.createCell((short) 1);
	            cell.setCellValue(settleDate.substring(0, 4)+"年"+settleDate.substring(4,6)+"月"+settleDate.substring(6, 8)+"日"); 
	            cell.setCellStyle(topStyle2);
	            cell.setCellStyle(colTopStyle1);
				cell = row.createCell((short) 3);
				cell.setCellValue("记账编号："+no);
				cell.setCellStyle(topStyle2);
				cell.setCellStyle(colTopStyle1);

	            //创建列头
	            HSSFFont colHeadFont = workbook.createFont();
	            colHeadFont.setFontName("宋体");
	            colHeadFont.setFontHeightInPoints((short) 12);
	            HSSFCellStyle colHeadStyle=ExcelUtil.getColHeaderStyle(workbook);
	            colHeadStyle.setFont(colHeadFont);
	            row = sheet.createRow(2);
	            row.setHeight((short)450);
	            cell = row.createCell((short) 0);  
	            cell.setCellValue("摘要");
	            HSSFCellStyle colHeadStyle2=ExcelUtil.getColHeaderStyle(workbook);
	            colHeadStyle2.setBorderLeft(HSSFCellStyle.BORDER_DOUBLE);
	            colHeadStyle2.setBorderTop(HSSFCellStyle.BORDER_DOUBLE);
	            colHeadStyle2.setFont(colFont1);
	            cell.setCellStyle(colHeadStyle2);
	            cell = row.createCell((short) 1);  
	            cell.setCellValue("科目");
	            HSSFCellStyle colHeadStyle3=ExcelUtil.getColHeaderStyle(workbook);
	            colHeadStyle3.setBorderTop(HSSFCellStyle.BORDER_DOUBLE);
	            colHeadStyle3.setFont(colFont1);
	            cell.setCellStyle(colHeadStyle3);
	            cell = row.createCell((short) 2);  
	            cell.setCellValue("借方金额");
	            HSSFCellStyle colHeadStyle4=ExcelUtil.getColHeaderStyle(workbook);
	            colHeadStyle4.setBorderTop(HSSFCellStyle.BORDER_DOUBLE);
	            colHeadStyle4.setFont(colFont1);
	            cell.setCellStyle(colHeadStyle4);
	            cell = row.createCell((short) 3);
	            cell.setCellValue("贷方金额");
	            HSSFCellStyle colHeadStyle5=ExcelUtil.getColHeaderStyle(workbook);
	            colHeadStyle5.setBorderRight(HSSFCellStyle.BORDER_DOUBLE);
	            colHeadStyle5.setBorderTop(HSSFCellStyle.BORDER_DOUBLE);
	            colHeadStyle5.setFont(colFont1);
	            cell.setCellStyle(colHeadStyle5);
	            

	            // 创建数据  
	            // 第一行  公司 会计期间
	            VoucherDetailVo vdv=null;
	            HSSFCellStyle colContentStyle=ExcelUtil.getColContentStyle(workbook);
	            colContentStyle.setFont(colFont2);
	            HSSFCellStyle colContentStyleL=ExcelUtil.getColContentStyle(workbook);
	            colContentStyleL.setFont(colFont2);
	            HSSFCellStyle colContentStyleR=ExcelUtil.getColContentStyle(workbook);
	            colContentStyleR.setFont(colFont2);
	            HSSFCellStyle colContentStyleAlignLeft=ExcelUtil.getColContentStyle(workbook);
	            colContentStyleAlignLeft.setAlignment(HSSFCellStyle.ALIGN_LEFT);
	            colContentStyleAlignLeft.setFont(colFont2);
	            for(int i=0;i<vdList.size();i++){
	            	row = sheet.createRow(i+3);
	            	row.setHeight((short)450);
	            	vdv=vdList.get(i);
	            	cell = row.createCell((short) 0);  
//		            cell.setCellValue("摘要"); 
	            	cell.setCellValue(vdv.getComment());
	            	cell.setCellStyle(colContentStyleL);
		            cell = row.createCell((short) 1);  
//		            cell.setCellValue("科目");
		            cell.setCellValue(vdv.getSubjectCode());
		            cell.setCellStyle(colContentStyleAlignLeft);
		            cell = row.createCell((short) 2);  
//		            cell.setCellValue("借方金额");
		            if(vdv.getDebit()==null){
		            }
		            else{
		            	cell.setCellValue(vdv.getDebit().doubleValue());
		            }
		            cell.setCellStyle(colContentStyle);
		            cell = row.createCell((short) 3);  
//		            cell.setCellValue("贷方金额");
		            if(vdv.getCredit()==null){
		            }
		            else{
		            	cell.setCellValue(vdv.getCredit().doubleValue());
		            }
		            cell.setCellStyle(colContentStyleR);
		            
	            }
	            
	            
	            //创建页脚
	            
	            row = sheet.createRow(3+vdList.size()+1);
	            cell = row.createCell((short) 0);  
	            cell.setCellValue("会计主管");
	            cell.setCellStyle(footerStyle);
	            cell = row.createCell((short) 1);  
	            cell.setCellValue("记账");
	            cell.setCellStyle(footerStyle);
	            cell = row.createCell((short) 2);  
	            cell.setCellValue("出纳");
	            cell.setCellStyle(footerStyle);
	            cell = row.createCell((short) 3);
	            cell.setCellValue("复核");
	            cell.setCellStyle(footerStyle);
	            cell = row.createCell((short) 3);
	            cell.setCellValue("制单");
	            cell.setCellStyle(footerStyle);
	            
	            //合并单元格
	            Region topRegion1 = new Region(0, (short) 0, 0, (short) 2);
	            sheet.addMergedRegion(topRegion1);
	            //Region topRegion2 = new Region(1, (short) 0, 1, (short) 2);
	            //sheet.addMergedRegion(topRegion2);
	            
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

	public List<VoucherDetailVo> getVoucherSummary(Map param) {
		// TODO Auto-generated method stub
	    String voucherId=param.get("voucherId").toString();
	    String companyId=param.get("companyId").toString();
//	    String sql="select vd.subject_root_code,sum(debit),sum(credit) from voucher_detail vd where vd.voucher_id='"+voucherId+"' group by vd.subject_root_code";
//	    String sql=" select vd.subject_root_code,sum(debit),sum(credit),s.comment from voucher_detail vd,subject s where vd.voucher_id='"+voucherId+"' and s.code=vd.subject_root_code and s.org_id='"+companyId+"' group by vd.subject_root_code";
		String sql=" select concat(vd.subject_root_code,'-',s.comment),sum(debit),sum(credit),'' as comment from voucher_detail vd,subject s where vd.voucher_id='"+voucherId+"' and s.code=vd.subject_root_code and s.org_id='"+companyId+"' group by  vd.subject_root_code order by sum(credit),sum(debit),vd.subject_root_code";
		List<Object> sqlResult=this.voucherDetailDao.execSql(sql);
	    VoucherDetailVo vdv=null;
	    List<VoucherDetailVo> resultList=new ArrayList();
	    for(Object o: sqlResult){
	    	Object[] oa=(Object[])o;
	    	String subjectCode=oa[0].toString();
	    	BigDecimal sumCredit=(BigDecimal) oa[2];
			BigDecimal sumDebit=(BigDecimal) oa[1];
			String subjectName=oa[3].toString();
			
			vdv=new VoucherDetailVo();
			vdv.setComment("");
			vdv.setSubjectCode(subjectCode+"/"+subjectName);
			vdv.setDebit(sumDebit);
			vdv.setCredit(sumCredit);
			resultList.add(vdv);
	    }
	    
	    if(resultList.size()<5){
	    	int diff=5-resultList.size();
	    	
	    	for(int i=1;i<diff+1;i++){
	    		resultList.add(new VoucherDetailVo());
	    	}
	    }
	    
		return resultList;
	}

	public void deleteVoucherDetail(VoucherDetail vd) {
		// TODO Auto-generated method stub
		this.voucherDetailDao.deleteVoucherDetail(vd);
		
	}

	public void deleteDetailList(List<VoucherDetail> vdl) {
		// TODO Auto-generated method stub
		if(vdl==null||vdl.isEmpty()){
			return;
		}
		this.voucherDetailDao.deleteVoucherDetailList(vdl);
		
		String id=vdl.get(0).getVoucherId();
		
		if(StringUtil.isEmpty(id)){
			return;
		}
		
		//重新计算总和
		Voucher po=null;
		if(!StringUtil.isEmpty(id)){
			po=this.voucherDao.getVoucherById(id);
            String sql="select ifnull(sum(debit),0),ifnull(sum(credit),0) from voucher_detail where voucher_id='"+id+"'";
            List sqlResultL=this.voucherDao.execSql(sql);
            try{
            	BigDecimal debitSum=(BigDecimal)((Object[])sqlResultL.get(0))[0];
            	BigDecimal creditSum=(BigDecimal)((Object[])sqlResultL.get(0))[1];
            po.setDebitSum(debitSum);
            po.setCreditSum(creditSum);
            this.voucherDao.updateVoucher(po);
            }catch(Exception e){
            	e.printStackTrace();
            }
		}
	}
}
