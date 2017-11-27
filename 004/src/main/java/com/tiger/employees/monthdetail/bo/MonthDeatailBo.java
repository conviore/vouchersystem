package com.tiger.employees.monthdetail.bo;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.tiger.employees.monthdetail.vo.MonthDetailVo;
import com.tiger.employees.subject.po.Subject;

public interface MonthDeatailBo {

	List<MonthDetailVo> getMonthDetailList(Map param);
	
	InputStream getMonthDetailExcel(Map param);
	
	List<Subject> getSubject4Combolbox(Map param);
	
	String getSubjectName(String subjectId);
	
	List<MonthDetailVo> getMonDetailList(Map param);
	
}
