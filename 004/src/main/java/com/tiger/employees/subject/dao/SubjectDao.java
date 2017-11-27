package com.tiger.employees.subject.dao;

import java.util.List;
import java.util.Map;

import com.tiger.employees.subject.po.Subject;

public interface SubjectDao {

	void addSubject(Subject subject);

	void updateSubject(Subject subject);

	Subject querySubjectById(String id);
	
	List<Subject> querySubjects(Map param);
	
	void attachDirty(Subject instance);
	
	void delete(Subject persistentInstance);
	
	Subject findById(java.lang.String id);
	
	String getTotalDirectChildsNumber(String parentId);
	
	String insertInitSubjectData(String companyId);
	


}
