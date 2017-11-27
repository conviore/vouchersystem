package com.tiger.employees.subject.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Action;
import com.tiger.employees.subject.dao.SubjectDao;
import com.tiger.employees.subject.po.Subject;
import com.tiger.utilities.StringUtil;

public class SubjectBoImpl implements SubjectBo {

	@Autowired
	private SubjectDao subjectDao;
	
	private final static String subject_root_id="1";
	
	private final static String not_leaf_flag="0";
	
	private final static String leaf_flag="1";
	
	
	public List<Subject> getSubjects(Map param) {
		// TODO Auto-generated method stub
		List<Subject> resultList=new ArrayList();
		
		resultList=this.subjectDao.querySubjects(param);
		return resultList;
	}

	public void addSubject(Subject subject) {
		// TODO Auto-generated method stub
		
		//更新父节点为非业界点
		if(!subject.getParentId().equals(subject_root_id)){
			Subject parentSub=this.getSubjectById(subject.getParentId());
			if(parentSub!=null){
				parentSub.setIsLeaf(not_leaf_flag);
				this.updateSubject(parentSub);
			}
		}
		
		//设置记账方向
		if(subject.getCode().substring(0,1).equals("2")||subject.getCode().substring(0,1).equals("3")){
			subject.setAccountDirection(-1);
		}else{
			subject.setAccountDirection(1);
		}
		
		this.subjectDao.addSubject(subject);

	}

	public void updateSubject(Subject subject) {
		// TODO Auto-generated method stub
		this.subjectDao.attachDirty(subject);

	}

	public Subject getSubjectById(String id) {
		// TODO Auto-generated method stub
		Subject sub=this.subjectDao.findById(id);
		return sub;
	}

	public String getTotalDirectChildsNumber(String parentId) {
		// TODO Auto-generated method stub
		String result=this.subjectDao.getTotalDirectChildsNumber(parentId);
		return result;
	}

	public String initCompanySubject(String companyId) {
		// TODO Auto-generated method stub
		if(StringUtil.isEmpty(companyId)){
			return Action.ERROR;
		}
		this.subjectDao.insertInitSubjectData(companyId);
		
		return Action.SUCCESS;
	}
	
	public Subject addMissSubject(Map param){
		String subjectCode=(String) param.get("subjectCode");
		String orgId=(String) param.get("orgId");
		String subjectName=(String) param.get("subjectName");
		Subject po=new Subject();
		if(!StringUtil.isEmpty(subjectCode)){
			String parentCode=subjectCode.substring(0, 4);
			Map queryParam=new HashMap();
			queryParam.put("code", parentCode);
			queryParam.put("orgId", orgId);
			List subjectList=this.getSubjects(queryParam);
			if(subjectList.size()>0){
				Subject parentSub=(Subject) subjectList.get(0);
				po.setOrgId(orgId);
				po.setParentCode(parentCode);
				po.setCode(subjectCode);
				po.setParentId(parentSub.getId());
				po.setCreateTime(new Date());
				po.setId(UUID.randomUUID().toString());
				po.setIsLeaf(leaf_flag);
				po.setComment(subjectName);
				this.addSubject(po);
				return po;
			}
			else{
				po.setParentCode(null);
				po.setCode(subjectCode);
				po.setParentId("1");
				po.setCreateTime(new Date());
				po.setId(UUID.randomUUID().toString());
				po.setIsLeaf(not_leaf_flag);
				po.setComment(subjectName);
				po.setOrgId(orgId);
				this.addSubject(po);
				return po;
			}
			
		}
		return null;
		
	}

}
