package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
//import javax.validation.constraints.NotBlank;
import javax.persistence.UniqueConstraint;

import com.sun.istack.NotNull;

@Entity
@Table(name = "accounts", uniqueConstraints=
@UniqueConstraint(columnNames={"account"}))
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String name;
	
	private Long account;
	
	private String type;
	
	private Double balance;

	public Account() {
		super();
	}

	public Account(String name, Double balance, Long account) {
		super();
		this.setName(name);
		this.setBalance(balance);
		this.setAccountNumber(account);
	}

	public Account(Long id, String name, Double balance, Long account) {
		super();
		this.setId(id);
		this.setName(name);
		this.setBalance(balance);
		this.setAccountNumber(account);
	}

	public Long getId() {
		return id;
	}

	public void setAccountNumber(Long account) {
		this.account = account;
	}
	
	public String getType() {
		return type;
	}
	
	public void setTupe(String type) {
		this.type = type;
	}
	
	public Long getAccountNumber() {
		return account;
	}


	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "Account{" + "id=" + id + ", name='" + name + '\'' +  "account number=" + account + ", balance='" + balance + '\'' + '}';
	}
}
