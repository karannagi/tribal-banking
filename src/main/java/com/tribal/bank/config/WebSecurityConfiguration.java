package com.tribal.bank.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.AllArgsConstructor;

@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	private final UserDetailsService tribalUserDetailsService;
    @Override
    protected void configure(HttpSecurity http) throws Exception 
    {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http
         .csrf().disable()
         .authorizeRequests()
         .antMatchers( "/h2/**").permitAll()
         .antMatchers("/actuator/**").permitAll()
         .antMatchers("/swagger-resources/**",
                 "/swagger-ui.html",
                 "/v2/api-docs",
                 "/webjars/**").permitAll()
         .anyRequest().authenticated()
         .and()
         .httpBasic();
        
        http.headers().frameOptions().sameOrigin();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) 
            throws Exception 
    {
        auth.userDetailsService(tribalUserDetailsService).passwordEncoder(encoder());
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
