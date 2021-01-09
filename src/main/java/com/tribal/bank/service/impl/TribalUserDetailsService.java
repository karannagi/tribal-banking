package com.tribal.bank.service.impl;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tribal.bank.entity.Customer;
import com.tribal.bank.repo.CustomerRepository;
import com.tribal.bank.service.model.MyUserPrincipal;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TribalUserDetailsService implements UserDetailsService{

	private final CustomerRepository customerRepo;
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Optional<Customer> customer = customerRepo.findByUserName(userName);
		if(customer.isPresent()) {
			return new MyUserPrincipal(customer.get());
		}
		throw new UsernameNotFoundException(userName);
	}

}
