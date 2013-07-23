package com.cutty.bravo.components.demo.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cutty.bravo.core.domain.BaseDomain;

@Entity
@Table(name = "bravo_teacher")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Teacher extends BaseDomain {

	private static final long serialVersionUID = -7266860448222135360L;
	private String name;
	private String gender;
	private String age;
	private String workAge;
	private Set<Student> students;//一对多关系中存放数据所需的set

	@Column(name = "work_age")
	public String getWorkAge() {
		return workAge;
	}
	public void setWorkAge(String workAge) {
		this.workAge = workAge;
	}
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinTable(name = "bravo_teacher_student", joinColumns = { @JoinColumn(name = "teacher_id") }, 
										inverseJoinColumns = { @JoinColumn(name = "student_id") })
	public Set<Student> getStudents() {
		return students;
	}
	public void setStudents(Set<Student> students) {
		this.students = students;
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
	public static void main(String[] args){
		System.out.println(System.getProperty("sun.cpu.isalist"));
	}
}
