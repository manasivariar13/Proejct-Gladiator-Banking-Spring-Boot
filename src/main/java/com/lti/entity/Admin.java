package com.lti.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope(scopeName = "prototype")
@Entity
@Table(name = "table_admin")
public class Admin {

	@Id
//	@SequenceGenerator(name = "adm_seq", initialValue = 1, allocationSize = 1)
//	@GeneratedValue(generator = "adm_seq", strategy = GenerationType.SEQUENCE)
	@Column(name = "admin_login_id")
	private String adminId;

	@Column(name = "admin_name")
	private String adminName;
	@Column(name = "admin_password")
	private String adminPassword;


	public String getAdminId() {
		return adminId;
	}
	public Admin(String adminId, String adminPassword,String adminName) {
		super();
		this.adminId = adminId;
		this.adminPassword = adminPassword;
		this.adminName=adminName;
	}


	public void setId(String adminId) {
		this.adminId = adminId;
	}
	
	
	public Admin() {
		super();
		
	}
	public String getAdminPassword() {
		return adminPassword;
	}

	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setName(String adminName) {
		this.adminName = adminName;
	}
}
