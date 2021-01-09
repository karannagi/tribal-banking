package com.tribal.bank.service;

import static org.junit.Assert.assertNotNull;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.tribal.bank.entity.Customer;
import com.tribal.bank.repo.CustomerRepository;
import com.tribal.bank.service.impl.TribalUserDetailsService;

@ExtendWith(MockitoExtension.class)
public class TribalUserDetailsServiceTest {

	@Mock
	private CustomerRepository customerRepo;
	
	@InjectMocks
	private TribalUserDetailsService tribalUserDetailsService;
	
	@Test
	public void loadUserByUsernameSuccess() {
		
		Mockito.when(customerRepo.findByUserName(Mockito.anyString())).thenReturn(Optional.of(new Customer()));
		UserDetails user = tribalUserDetailsService.loadUserByUsername("testUser");
		assertNotNull(user);
		
	}
	
	@Test
	public void loadUserByUsernameFailure() {
		
		Mockito.when(customerRepo.findByUserName(Mockito.anyString())).thenReturn(Optional.ofNullable(null));
		UsernameNotFoundException response = null;
		try {
		tribalUserDetailsService.loadUserByUsername("testUser");
		}
		catch(UsernameNotFoundException e) {
			response = e;
		}
		assertNotNull(response);
	}
}
