package com.tiger.employees.employees.po;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

public class Employees {

  private long empNo;

  private Date birthDate;

  private String firstName;

  private String lastName;

 

private String gender;

  private Date hireDate;

/**
 * @return the empNo
 */
public long getEmpNo() {
	return empNo;
}

/**
 * @return the birthDate
 */
public Date getBirthDate() {
	return birthDate;
}



/**
 * @return the lastName
 */
public String getLastName() {
	return lastName;
}

/**
 * @return the gender
 */
public String getGender() {
	return gender;
}

/**
 * @return the hireDate
 */
public Date getHireDate() {
	return hireDate;
}

/**
 * @param empNo the empNo to set
 */
public void setEmpNo(long empNo) {
	this.empNo = empNo;
}

/**
 * @param birthDate the birthDate to set
 */
public void setBirthDate(Date birthDate) {
	this.birthDate = birthDate;
}



/**
 * @param lastName the lastName to set
 */
public void setLastName(String lastName) {
	this.lastName = lastName;
}

/**
 * @param gender the gender to set
 */
public void setGender(String gender) {
	this.gender = gender;
}

/**
 * @param hireDate the hireDate to set
 */
public void setHireDate(Date hireDate) {
	this.hireDate = hireDate;
}

/**
 * @return the firstName
 */
public String getFirstName() {
	return firstName;
}

/**
 * @param firstName the firstName to set
 */
public void setFirstName(String firstName) {
	this.firstName = firstName;
}
  
}
