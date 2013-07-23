package com.cutty.bravo.components.demo.domain;


public class Studentsa {

	private static final long serialVersionUID = -3008347822214286987L;

	private String name;
	private String gender;
	private Teacher headTeacher;// 注意命名规则
	private String age;


	public Teacher getHeadTeacher() {
		return headTeacher;
	}

	public void setHeadTeacher(Teacher headTeacher) {
		this.headTeacher = headTeacher;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}
}
