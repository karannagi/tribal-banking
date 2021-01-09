package com.tribal.bank.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String firstName;
	private String lastName;
	@OneToOne
	@JoinColumn(name = "segment_id", referencedColumnName = "id")
	private Segment segment;
	@OneToMany
	//@JoinColumn(name = "account_id", referencedColumnName = "id")
	List<Account> accounts;
	private String mobileNumber;
	private String email;
	private Boolean active;
	private String userName;
	private String password;
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
