package com.cutty.bravo.components.demo.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cutty.bravo.core.domain.BaseDomain;

@Entity
@Table(name = "bravo_student")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Student extends BaseDomain {

	private static final long serialVersionUID = -3008347822214286987L;

	private String name;
	private String gender;
	private Teacher headTeacher;// 注意命名规则
	private String age;
	private Set<Teacher> teachers;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinTable(name = "bravo_teacher_student", joinColumns = { @JoinColumn(name = "student_id") }, 
										inverseJoinColumns = { @JoinColumn(name = "teacher_id") })
	public Set<Teacher> getTeachers() {
		return teachers;
	}

	public void setTeachers(Set<Teacher> teachers) {
		this.teachers = teachers;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "head_teacher", referencedColumnName = "id")
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
