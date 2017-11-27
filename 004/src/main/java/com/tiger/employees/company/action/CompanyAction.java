package com.tiger.employees.company.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Action;
import com.tiger.BaseAction;
import com.tiger.employees.balance.bo.BalanceBo;
import com.tiger.employees.balance.po.Balance;
import com.tiger.employees.company.bo.CompanyBo;
import com.tiger.employees.company.po.Company;
import com.tiger.employees.monsettleprog.bo.MonthSettleProgressBo;
import com.tiger.employees.monsettleprog.po.MonthSettleProgress;
import com.tiger.employees.subject.bo.SubjectBo;
import com.tiger.employees.subject.po.Subject;
import com.tiger.utilities.SettleConfig;
import com.tiger.utilities.TimeUtil;

public class CompanyAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7867263545898958363L;
	
	@Autowired
	private SubjectBo subjectBo;
	
	@Autowired
	private CompanyBo companyBo;
	
	@Autowired
	private BalanceBo balanceBo;
	
	@Autowired
	private MonthSettleProgressBo monthSettleProgressBo;
	
	private List<Company> companies=null;
	private Map dataMap;
	private List dataMapList=null;
	
    private File file;
    private String contentType;
    private String filename;
    
    private File uploadFile;  
    private String uploadFileFileName;  
	
	public String saveCompany(){
		String companyName=request.getParameter("companyName");
		String comment=request.getParameter("comment");
		String settleBeginDate=request.getParameter("settleBeginDate");
		System.out.println(companyName);
		System.out.println(comment);
		System.out.println(settleBeginDate);
		String[] dateSegs=settleBeginDate.split("-");
		String dateYear=dateSegs[0];
		String dateMonth=dateSegs[1];
		String dateDay=dateSegs[2];
		System.out.println("");
		System.out.println("month is "+dateMonth);
		System.out.println("year is "+dateYear);
		
		settleBeginDate=dateYear+dateMonth+dateDay;
		
		Company comp= new Company();
		comp.setCompanyName(companyName);
		comp.setSettleBeginDate(settleBeginDate);
		comp.setComment(comment);
		comp.setStatus("0");
		dataMap=new HashMap();
		this.companyBo.saveCompany(comp);
		this.dataMap.put("status", true);
		this.dataMap.put("info", "保存成功");
		return Action.SUCCESS;
		
	}

	/**
	 * @return the dataMap
	 */
	public Map getDataMap() {
		return dataMap;
	}

	/**
	 * @param dataMap the dataMap to set
	 */
	public void setDataMap(Map dataMap) {
		this.dataMap = dataMap;
	}

	/**
	 * @return the companies
	 */
	public String getCompanies() {
		dataMapList=new ArrayList();
		companies=new ArrayList();
		companies=this.companyBo.getListCompany();
		for(Company comp:companies){
			Map m=new HashMap();
			m.put("id", comp.getId());
			m.put("companyName", comp.getCompanyName());
			dataMapList.add(m);
		}
		return Action.SUCCESS;
	}
	
	public String doUpload(){
		this.dataMap=new HashMap();
		 String directory = "/upload/tmp";  
	        String targetDirectory = ServletActionContext.getServletContext().getRealPath(directory);  
	        //生成上传的文件对象  
	        File target = new File(targetDirectory,uploadFileFileName);  
	        //如果文件已经存在，则删除原有文件  
	        if(target.exists()){  
	            target.delete();  
	        }  
	        //复制file对象，实现上传  
	        try {  
	            FileUtils.copyFile(uploadFile, target);  
	              
	            //out = response.getWriter();  
	            //out.print("文件上传成功！");  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	          
	        loadRoleInfo(uploadFileFileName);  
	        this.dataMap.put("status", true);
			this.dataMap.put("info", "初始化数据成功");
			return Action.SUCCESS;
	}

	 /** 
     * 把Excele表读出的数据，组装成一个List,统一导入数据库 
     * @param uploadFileFileName 
     */  
    public void loadRoleInfo(String uploadFileFileName){  
          
        String directory = "/upload/tmp";   
        String targetDirectory = ServletActionContext.getServletContext().getRealPath(directory);  
        String companyId = (String) ServletActionContext.getServletContext().getAttribute("companyId");  
        Company comp=this.companyBo.getCompanyById(companyId);
        File target = new File(targetDirectory,uploadFileFileName);  
          
        List roleList = new ArrayList();  
        List initBlanaceList = new ArrayList();  
        try{  
            FileInputStream fi = new FileInputStream(target);  
            Workbook wb = new XSSFWorkbook(fi);  
            Sheet sheet = wb.getSheetAt(0);  
            String settleBeginDate=comp.getSettleBeginDate();
//    		String[] dateSegs=settleBeginDate.split("-");
//    		String dateYear=dateSegs[0];
//    		String dateMonth=dateSegs[1];
            String dateYear=settleBeginDate.substring(0,4);
            String dateMonth=settleBeginDate.substring(4, 6);
    		System.out.println("");
    		System.out.println("month is "+dateMonth);
    		System.out.println("year is "+dateYear);
    		
    		String oldDateMonth=null;
    		String oldDateYear=dateYear;
    		String oldDateDay=null;
    		if(dateMonth.equals("01")){
    			oldDateMonth="12";
    			oldDateYear=String.valueOf(Integer.valueOf(dateYear)-1);
    			oldDateDay="31";
    		}
    		else if(dateMonth.equals("03")||dateMonth.equals("05")||dateMonth.equals("07")||dateMonth.equals("08")||dateMonth.equals("10")||dateMonth.equals("12")){
    			if(dateMonth.equals("03")){
    				oldDateDay="28";
    			}else{
    				oldDateDay="30";
    			}
    			oldDateMonth=String.valueOf(Integer.valueOf(dateMonth)-1);
    			
    		}else{
    			oldDateDay="31";
    			oldDateMonth=String.valueOf(Integer.valueOf(dateMonth)-1);
    		}
    		if(oldDateMonth.length()==1){
				oldDateMonth="0"+oldDateMonth;
			}
    		String oldSettleDate=oldDateYear+""+oldDateMonth+""+oldDateDay;
    		
            int rowNum = sheet.getLastRowNum()+1;  
            for(int i=0;i<rowNum;i++){  
//                PtRoleInfo ptRoleInfo = new PtRoleInfo();
            	Balance balance=new Balance();
            	balance.setOrgId(companyId);
            	balance.setUpdateTime(TimeUtil.getCurrentDateTime14());
            	balance.setSettleTime(oldSettleDate);
                Row row = sheet.getRow(i);  
                int cellNum = row.getLastCellNum();
                BigDecimal cashnum=new BigDecimal('0');
                Map m=new HashMap();
                for(int j=0;j<cellNum;j++){  
                    Cell cell = row.getCell(j);  
                    String cellValue = null;
                    
                    switch(cell.getCellType()){ //判断excel单元格内容的格式，并对其进行转换，以便插入数据库  
                        case 0 : cellValue = String.valueOf(cell.getNumericCellValue()); break;  
                        case 1 : cellValue = cell.getStringCellValue(); break;  
                        case 2 : cellValue = String.valueOf(cell.getDateCellValue()); break;  
                        case 3 : cellValue = ""; break;  
                        case 4 : cellValue = String.valueOf(cell.getBooleanCellValue()); break;  
                        case 5 : cellValue = String.valueOf(cell.getErrorCellValue()); break;  
                    }
                    
                    System.out.println(cellValue);
                    if(j==0){
                    	cellValue=cellValue.substring(0, cellValue.length()-2);
                    }
                    
                    
                    
                    
                    switch(j){//通过列数来判断对应插如的字段
                    case 0: balance.setSubjectCode(cellValue);break;
                    case 2: cashnum=new BigDecimal(cellValue);balance.setFinalMonDebit(cashnum);break;
                    case 3: cashnum=new BigDecimal(cellValue);balance.setFinalMonCredit(cashnum);break;
//                        case 0 : ptRoleInfo.setRoleId(cellValue);break;  
//                        case 1 : ptRoleInfo.setRoleName(cellValue);break;  
//                        case 2 : ptRoleInfo.setDeil(cellValue);break;  
                    }  
                }
                
                if(balance.getSubjectCode()!=null){
                	m.put("code", balance.getSubjectCode());
                	m.put("orgId", companyId);
                	List<Subject> subjectList=this.subjectBo.getSubjects(m);
                	if(!subjectList.isEmpty()){
                		balance.setSubjectId(subjectList.get(0).getId());
                	}
                	else{
                		Map addSubParam=new HashMap();
                		addSubParam.put("subjectCode", balance.getSubjectCode());
                		addSubParam.put("orgId", companyId);
                		addSubParam.put("subjectName", row.getCell(1).getStringCellValue());
                		Subject newSub=this.subjectBo.addMissSubject(addSubParam);
                		balance.setSubjectId(newSub.getId());
                	}
                }
                initBlanaceList.add(balance);
//                roleList.add(ptRoleInfo);  
            } 
            this.balanceBo.saveListBalances(initBlanaceList);
            
            MonthSettleProgress initMsp=new MonthSettleProgress();
            initMsp.setCompanyId(companyId);
            initMsp.setComment("initComplete");
            initMsp.setSettleMonth(oldDateMonth);
            initMsp.setSettleYear(oldDateYear);
            initMsp.setStatus(SettleConfig.MON_SETTLE_COMPLETE);
            this.monthSettleProgressBo.addMSP(initMsp);
            
            MonthSettleProgress beginMsp=new MonthSettleProgress();
            beginMsp.setCompanyId(companyId);
            beginMsp.setComment("To Settle");
            beginMsp.setSettleMonth(dateMonth);
            beginMsp.setSettleYear(dateYear);
            beginMsp.setStatus(SettleConfig.MON_SETTLE_INCOMPLETE);
            this.monthSettleProgressBo.addMSP(beginMsp);
            
            //再次初始化记账方向
            this.monthSettleProgressBo.initAccountingDirection();
            
//            fileLoadDao.roleInfotoDB(roleList);  
        }catch(Exception e){  
            e.printStackTrace();  
        }  
    }  
	
	/**
	 * @param companies the companies to set
	 */
	public void setCompanies(List<Company> companies) {
		this.companies = companies;
	}

	/**
	 * @return the dataMapList
	 */
	public List getDataMapList() {
		return dataMapList;
	}

	/**
	 * @param dataMapList the dataMapList to set
	 */
	public void setDataMapList(List dataMapList) {
		this.dataMapList = dataMapList;
	}

	/**
	 * @return the file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * @param file the file to set
	 */
	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * @return the contentType
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * @param contentType the contentType to set
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	/**
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * @param filename the filename to set
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

	/**
	 * @return the uploadFile
	 */
	public File getUploadFile() {
		return uploadFile;
	}

	/**
	 * @param uploadFile the uploadFile to set
	 */
	public void setUploadFile(File uploadFile) {
		this.uploadFile = uploadFile;
	}

	/**
	 * @return the uploadFileFileName
	 */
	public String getUploadFileFileName() {
		return uploadFileFileName;
	}

	/**
	 * @param uploadFileFileName the uploadFileFileName to set
	 */
	public void setUploadFileFileName(String uploadFileFileName) {
		this.uploadFileFileName = uploadFileFileName;
	}
	

	
}
