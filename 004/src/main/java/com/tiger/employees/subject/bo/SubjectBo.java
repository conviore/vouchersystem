package com.tiger.employees.subject.bo;

import java.util.List;
import java.util.Map;

import com.tiger.employees.subject.po.Subject;

public interface SubjectBo {

	List<Subject> getSubjects(Map param);
	
	void addSubject(Subject subject);
	
	void updateSubject(Subject subject);
	
	Subject getSubjectById(String id);
	
	String getTotalDirectChildsNumber(String parentId);
	
	String initCompanySubject(String companyId);
	
	Subject addMissSubject(Map param);
	
	
}
