package com.tribal.bank.repo;

import java.math.BigDecimal;
import java.util.Optional;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.tribal.bank.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long>{

	@Transactional
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	Optional<Account> findByAccountNumber(String accountNumber);
	
	@Modifying
	@Query("update Account set balance=:balance where accountNumber=:accountNumber")
	public void updateAccountBalance(BigDecimal balance, String accountNumber);
}
