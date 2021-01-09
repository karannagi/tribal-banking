package com.tribal.bank.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@OneToOne
	@JoinColumn(name = "account_id", referencedColumnName = "id")
	private AccountType accountTypeId;
	private BigDecimal balance;
	private Boolean active;
	@OneToOne
	@JoinColumn(name = "branch_id", referencedColumnName = "id")
	private Branch branch;
	private String accountNumber;
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
