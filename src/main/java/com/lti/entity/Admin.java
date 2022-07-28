package com.lti.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "table_admin")
public class Admin {

	@Id
	@SequenceGenerator(name = "adm_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(generator = "adm_seq", strategy = GenerationType.SEQUENCE)
	@Column(name = "admin_login_id")
	private int id;

	@Column(name = "admin_password")
	private String password;

	@Column(name = "admin_name")
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
