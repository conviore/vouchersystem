package com.tiger.employees.balance.bo;

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
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import com.tiger.employees.balance.dao.BalanceDao;
import com.tiger.employees.balance.po.Balance;
import com.tiger.employees.monsettleprog.dao.MonthSettleProgressDao;
import com.tiger.employees.monsettleprog.po.MonthSettleProgress;
import com.tiger.employees.subject.dao.SubjectDao;
import com.tiger.employees.subject.po.Subject;
import com.tiger.utilities.SettleConfig;
import com.tiger.utilities.TimeUtil;

public class BalanceBoImpl implements BalanceBo{
	
	@Autowired
	BalanceDao balanceDao;
	
	@Autowired
	SubjectDao subjectDao;
	
	@Autowired
	MonthSettleProgressDao monthSettleProgressDao;
	
	private final static String subject_root_id="1";

	public void saveBalance(Balance balance) {
		// TODO Auto-generated method stub
		this.balanceDao.addBalance(balance);
		
	}

	public Balance getBalanceById(String id) {
		// TODO Auto-generated method stub
		Balance balance=this.balanceDao.getBalance(id);
		return balance;
		
	}

	public List<Balance> getBalances(Map param) {
		// TODO Auto-generated method stub
		List resultList=null;
		resultList=this.balanceDao.getListBalance(param);
		return resultList;
	}

	public void saveListBalances(List<Balance> balance) {
		// TODO Auto-generated method stub
		if(balance!=null){
			this.balanceDao.addListBalance(balance);
		}
	}

	public List<Balance> generateSubjectSummary(Map param) {
		// TODO Auto-generated method stub
		List resultList=new ArrayList();
		String settleTime=(String) param.get("settleTime");
		String dateYear=settleTime.substring(0,4);
         String dateMonth=settleTime.substring(4, 6);
         String dateDay=null;
         if(dateMonth.equals("01")||dateMonth.equals("03")||dateMonth.equals("05")||dateMonth.equals("07")||dateMonth.equals("08")||dateMonth.equals("10")||dateMonth.equals("12")){
        	 dateDay="31";
         }else if(dateMonth.equals("02")){
        	 dateDay="28";
         }else{
        	 dateDay="30";
         }
 		System.out.println("");
 		System.out.println("month is "+dateMonth);
 		System.out.println("year is "+dateYear);
 		
 		String oldSettleDateMonth=null;
 		String oldSettleDateYear=dateYear;
 		String oldDateDay=null;
 		Integer monthInt=null;
 		
 		settleTime=settleTime+dateDay;
 		
 		if(dateMonth.equals("01")){
 			oldSettleDateMonth="12";
 			oldSettleDateYear=String.valueOf(Integer.valueOf(dateYear)-1);
 		}
 		else{
 			monthInt=Integer.valueOf(dateMonth)-1;
 			oldSettleDateMonth=String.valueOf(monthInt);
 			if(monthInt<10){
 				oldSettleDateMonth="0"+String.valueOf(monthInt);
 			} 			
 		}
 		
 		String oldSettleDay=null;
        if(oldSettleDateMonth.equals("01")||oldSettleDateMonth.equals("03")||oldSettleDateMonth.equals("05")||oldSettleDateMonth.equals("07")||oldSettleDateMonth.equals("08")||oldSettleDateMonth.equals("10")||oldSettleDateMonth.equals("12")){
        	oldSettleDay="31";
        }else if(oldSettleDateMonth.equals("02")){
        	oldSettleDay="28";
        }else{
        	oldSettleDay="30";
        }
		
 		String oldSettleDate=oldSettleDateYear+oldSettleDateMonth+oldSettleDay;
		
		String companyId=(String)param.get("companyId");
		resultList=this.generateSummary(companyId,settleTime,oldSettleDate, subject_root_id,resultList);
		return resultList;
	}
	
	private List<Balance> generateSummary(String companyId,String settleTime,String oldSettleDate,String parentId,List resultList){
		Map param=new HashMap();
		Map balanceParam=new HashMap();
		List<Balance> balanceList=new ArrayList();
		param.put("parentId", parentId);
		param.put("orgId", companyId);
		List<Subject> subjectList=this.subjectDao.querySubjects(param);
		String subId=null;
		String subCode=null;
		Integer accountDirection=1;
		if(subjectList!=null){
			for(Subject sub:subjectList){
				subId=sub.getId();
				subCode=sub.getCode();
				accountDirection=sub.getAccountDirection();
				balanceParam.put("subjectId", subId);
				balanceParam.put("orgId", companyId);
				balanceParam.put("settleTime", oldSettleDate);
				balanceList=this.balanceDao.getListBalance(balanceParam);
				Balance oldPo=null;
				//if this is a new subject, then the old po must be null and we insert into all zero
				if(balanceList.isEmpty()||balanceList==null||balanceList.get(0)==null){
					 oldPo=new Balance();
					 oldPo.setFinalMonCredit(new BigDecimal("0"));
					 oldPo.setFinalMonDebit(new BigDecimal("0"));
					 oldPo.setCumulativeDebit(new BigDecimal("0"));
					 oldPo.setCumulativeCredit(new BigDecimal("0"));
					 oldPo.setSettleTime(oldSettleDate);
				}
				else{
					 oldPo=balanceList.get(0);					
				}
				
				BigDecimal initMonCredit=oldPo.getFinalMonCredit();
				BigDecimal initMonDebit=oldPo.getFinalMonDebit();
				BigDecimal cumulativeCredit=oldPo.getCumulativeCredit();
				BigDecimal cumulativeDebit=oldPo.getCumulativeDebit();
				//年初 本年发生额重置
				if(oldPo.getSettleTime().substring(4, 6).equals("12")){
					cumulativeCredit=new BigDecimal("0");
					cumulativeDebit=new BigDecimal("0");
				}
				Balance newBalance=new Balance();
				newBalance.setUpdateTime(TimeUtil.getCurrentTime17ByMillis());
				newBalance.setSubjectId(subId);
				newBalance.setSubjectName(sub.getComment());
				newBalance.setSubjectCode(sub.getCode());
				newBalance.setInitMonCredit(initMonCredit);
				newBalance.setInitMonDebit(initMonDebit);
				newBalance.setSettleTime(settleTime);
				newBalance.setOrgId(companyId);
				
				if(sub.getIsLeaf().equals("0")){
					String sql="select ifnull(sum(credit),0),ifnull(sum(debit),0) from voucher_detail vt,subject s where vt.subject_id=s.id and vt.settle_time like '"+settleTime.substring(0,6)+"%' and vt.org_id='"+companyId+"' and s.code like '"+subCode+"%'";
					List sumResultL=this.balanceDao.execSql(sql);
					Object[] sumAarry=(Object[]) sumResultL.get(0);
					BigDecimal sumCredit=(BigDecimal) sumAarry[0];
					BigDecimal sumDebit=(BigDecimal) sumAarry[1];
					BigDecimal newCumulativeCredit=cumulativeCredit.add(sumCredit);
					BigDecimal newCumulativeDebit=cumulativeDebit.add(sumDebit);
					BigDecimal finalMonCredit=initMonCredit.add(sumCredit);
					BigDecimal finalMonDebit=initMonDebit.add(sumDebit);
					
					newBalance.setCumulativeCredit(newCumulativeCredit);
					newBalance.setCumulativeDebit(newCumulativeDebit);
					newBalance.setCurrentMonCredit(sumCredit);
					newBalance.setCurrentMonDebit(sumDebit);
					newBalance.setFinalMonCredit(finalMonCredit);
					newBalance.setFinalMonDebit(finalMonDebit);
					
					newBalance.setInitBalance(initMonDebit.subtract(initMonCredit).multiply(new BigDecimal(accountDirection)));
					newBalance.setEndBalance(finalMonDebit.subtract(finalMonCredit).multiply(new BigDecimal(accountDirection)));
					newBalance.setAccountDirection(sub.getAccountDirection());
					
					//查出年初余额
					if(!settleTime.substring(4, 6).equals("01")){
						String queryInitYearSql="select ifnull(init_balance,0) from balance where org_id='"+companyId+"' and settle_time='"+settleTime.substring(0,4)+""+"0131' and subject_id='"+sub.getId()+"'";
						List initBalanceResultL=this.balanceDao.execSql(queryInitYearSql);
						if(initBalanceResultL.isEmpty()){
							newBalance.setInitYearBalance(new BigDecimal("0"));
						}
						else{
//							Object[] initAarry=(Object[]) initBalanceResultL.get(0);
//							BigDecimal initValue=(BigDecimal) initAarry[0];
							BigDecimal initValue=(BigDecimal) initBalanceResultL.get(0);
							newBalance.setInitYearBalance(initValue);
						}
					}
					else{
						newBalance.setInitYearBalance(newBalance.getInitBalance());
					}
					
					resultList.add(newBalance);
					resultList=this.generateSummary(companyId, settleTime, oldSettleDate, subId, resultList);
				}
				else{
					String sql="select ifnull(sum(credit),0),ifnull(sum(debit),0) from voucher_detail vt where vt.settle_time like '"+settleTime.substring(0,6)+"%'  and vt.org_id='"+companyId+"' and vt.subject_id='"+subId+"'";
					List sumResultL=this.balanceDao.execSql(sql);
					Object[] sumAarry=(Object[]) sumResultL.get(0);
					BigDecimal sumCredit=(BigDecimal) sumAarry[0];
					BigDecimal sumDebit=(BigDecimal) sumAarry[1];
					BigDecimal newCumulativeCredit=cumulativeCredit.add(sumCredit);
					BigDecimal newCumulativeDebit=cumulativeDebit.add(sumDebit);
					BigDecimal finalMonCredit=initMonCredit.add(sumCredit);
					BigDecimal finalMonDebit=initMonDebit.add(sumDebit);
					
					newBalance.setCumulativeCredit(newCumulativeCredit);
					newBalance.setCumulativeDebit(newCumulativeDebit);
					newBalance.setCurrentMonCredit(sumCredit);
					newBalance.setCurrentMonDebit(sumDebit);
					newBalance.setFinalMonCredit(finalMonCredit);
					newBalance.setFinalMonDebit(finalMonDebit);
					newBalance.setInitBalance(initMonDebit.subtract(initMonCredit).multiply(new BigDecimal(accountDirection)));
					newBalance.setEndBalance(finalMonDebit.subtract(finalMonCredit).multiply(new BigDecimal(accountDirection)));
					newBalance.setAccountDirection(sub.getAccountDirection());
					//查出年初余额
					if(!settleTime.substring(4, 6).equals("01")){
						String queryInitYearSql="select ifnull(init_balance,0) from balance where org_id='"+companyId+"' and settle_time='"+settleTime.substring(0,4)+""+"0131' and subject_id='"+sub.getId()+"'";
						List initBalanceResultL=this.balanceDao.execSql(queryInitYearSql);
						if(initBalanceResultL.isEmpty()){
							newBalance.setInitYearBalance(new BigDecimal("0"));
						}
						else{
//							Object[] initAarry=(Object[]) initBalanceResultL.get(0);
//							BigDecimal initValue=(BigDecimal) initAarry[0];
							BigDecimal initValue=(BigDecimal) initBalanceResultL.get(0);
							newBalance.setInitYearBalance(initValue);
						}
					}
					else{
						newBalance.setInitYearBalance(newBalance.getInitBalance());
					}
					
					resultList.add(newBalance);
				}
				
				
				
				
				
			}
		}
		
		
		
		return resultList;
	}

	public InputStream getBalanceExcel(Map param) {
		// TODO Auto-generated method stub
		if(param!=null){
			//获得报表数据
			String companyName=(String) param.get("companyName");
			param.remove("companyName");
			List<Balance> balanceList=this.generateSubjectSummary(param);
			String settleDate=(String) param.get("settleTime");
			
			/**
			 * 制作报表
			 */
			HSSFWorkbook workbook = new HSSFWorkbook();  
	        HSSFSheet sheet = workbook.createSheet("sheet1");  
	        {  
	            // 创建表头  
	            HSSFRow row = sheet.createRow(0);  
	            HSSFCell cell = row.createCell((short) 0);  
	            cell.setCellValue(companyName);  
	            cell = row.createCell((short) 1);  
	            cell.setCellValue("科目余额汇总");  
	            cell = row.createCell((short) 2);  
	            cell.setCellValue("会计期间");  
	            cell = row.createCell((short) 3);  
	            cell.setCellValue(settleDate);  
	            
	            //创建列头
	            row = sheet.createRow(1);  
	            cell = row.createCell((short) 0);  
	            cell.setCellValue("会计期间");  
	            cell = row.createCell((short) 1);  
	            cell.setCellValue("科目编码");  
	            cell = row.createCell((short) 2);  
	            cell.setCellValue("科目名称");  
	            cell = row.createCell((short) 3);
	            cell.setCellValue("年初余额");
	            cell = row.createCell((short) 4);
	            cell.setCellValue("月初余额");
	            cell = row.createCell((short) 5);
	            cell.setCellValue("期初余额");
	            cell = row.createCell((short) 6);
	            cell.setCellValue("期初借方");
	            cell = row.createCell((short) 7);  
	            cell.setCellValue("期初贷方");
	            cell = row.createCell((short) 8);  
	            cell.setCellValue("本期发生借方");
	            cell = row.createCell((short) 9);
	            cell.setCellValue("本期发生贷方");
	            cell = row.createCell((short) 10);
	            cell.setCellValue("累计发生借方");
	            cell = row.createCell((short) 11);
	            cell.setCellValue("累计发生贷方");
	            cell = row.createCell((short) 12);
	            cell.setCellValue("期末借方");
	            cell = row.createCell((short) 13);
	            cell.setCellValue("期末贷方");
	            cell = row.createCell((short) 14);
	            cell.setCellValue("期末余额");

	            // 创建数据  
	            // 第一行  公司 会计期间
	            Balance balance=null;
	            for(int i=0;i<balanceList.size();i++){
	            	row = sheet.createRow(i+2);
	            	balance=balanceList.get(i);
	            	cell = row.createCell((short) 0);  
//		            cell.setCellValue("会计期间"); 
	            	cell.setCellValue(balance.getSettleTime());
		            cell = row.createCell((short) 1);  
//		            cell.setCellValue("科目编码");
		            cell.setCellValue(balance.getSubjectCode());
		            cell = row.createCell((short) 2);  
//		            cell.setCellValue("科目名称");  
		            cell.setCellValue(balance.getSubjectName());
		            cell = row.createCell((short) 3);  
//		            cell.setCellValue("年初余额");
		           
		            cell.setCellValue(balance.getInitYearBalance().doubleValue());
		            cell = row.createCell((short) 4);  
//		            cell.setCellValue("月初余额");
		           
		            cell.setCellValue(balance.getInitBalance().doubleValue());
		            cell = row.createCell((short) 5);  
//		            cell.setCellValue("期初余额");
		           
		            cell.setCellValue(balance.getInitBalance().doubleValue());
		            cell = row.createCell((short) 6);
//		            cell.setCellValue("期初借方");
		            cell.setCellValue(balance.getInitMonDebit().doubleValue());
		            cell = row.createCell((short) 7);  
//		            cell.setCellValue("期初贷方");
		            cell.setCellValue(balance.getInitMonCredit().doubleValue());
		            cell = row.createCell((short) 8);  
//		            cell.setCellValue("本期发生借方");
		            cell.setCellValue(balance.getCurrentMonDebit().doubleValue());
		            cell = row.createCell((short) 9);
//		            cell.setCellValue("本期发生贷方");
		            cell.setCellValue(balance.getCurrentMonCredit().doubleValue());
		            cell = row.createCell((short) 10);
//		            cell.setCellValue("累计发生借方");
		            cell.setCellValue(balance.getCumulativeDebit().doubleValue());
		            cell = row.createCell((short) 11);
//		            cell.setCellValue("累计发生贷方");
		            cell.setCellValue(balance.getCumulativeCredit().doubleValue());
		            cell = row.createCell((short) 12);
//		            cell.setCellValue("期末借方");
		            cell.setCellValue(balance.getFinalMonDebit().doubleValue());
		            cell = row.createCell((short) 13);
//		            cell.setCellValue("期末贷方");
		            cell.setCellValue(balance.getFinalMonCredit().doubleValue());
		            cell = row.createCell((short) 14);
//		            cell.setCellValue("期末余额");
		            cell.setCellValue(balance.getEndBalance().doubleValue());
	            }
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

	public void endMonSettle(Map param) {
		// TODO Auto-generated method stub
		
		String settleTime=(String) param.get("settleTime");
		String companyId=(String)param.get("companyId");
		String dateYear=settleTime.substring(0,4);
        String dateMonth=settleTime.substring(4, 6);
        String monProgTime=dateYear+""+dateMonth;
		
        //删除旧数据
        String delSql="delete from balance where settle_time >'"+settleTime+"00' and settle_time<'"+settleTime+"99' and org_id='"+companyId+"'";
        this.monthSettleProgressDao.execDmlSql(delSql);
        
		//生成保存数据
		List<Balance> monEndBalanceList=this.generateSubjectSummary(param);
		
		//保存l
		this.saveListBalances(monEndBalanceList);
		
		//更新月结状态表
		Map mspMap=new HashMap();
		mspMap.put("settleYear", dateYear);
		mspMap.put("settleMonth", dateMonth);
		mspMap.put("companyId", companyId);
		List<MonthSettleProgress> mspList=this.monthSettleProgressDao.getMSPs(mspMap);
		MonthSettleProgress msp=mspList.get(0);
		if(msp!=null){
			msp.setStatus(SettleConfig.MON_SETTLE_COMPLETE);
			msp.setComment("月结完成");
			msp.setUpdateTime(TimeUtil.getCurrentTime17ByMillis());
		}
		
		this.monthSettleProgressDao.updateMSP(msp);
		
		String createTime=msp.getCreateTime();
		String updatesql="update month_settle_progress set comment='未完成',status='"+SettleConfig.MON_SETTLE_INCOMPLETE+"' where company_id='"+companyId+"' and create_time > '"+createTime+"'";
		this.monthSettleProgressDao.execDmlSql(updatesql);
		
		System.out.println("end msp finished");
		
		
		
		
		
	}
	
	
	
}
