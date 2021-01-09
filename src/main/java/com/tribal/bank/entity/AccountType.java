package com.tribal.bank.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class AccountType {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String accountName;
	private BigDecimal interest;
	private BigDecimal minBalance;
	private Date createdOn;
	private Date updatedOn;
	
	  @PrePersist
	  protected void onCreate() {
		  createdOn = new Date();
	  }

	  @PreUpdate
	  protected void onUpdate() {
		  updatedOn = new Date();
	  }
}
