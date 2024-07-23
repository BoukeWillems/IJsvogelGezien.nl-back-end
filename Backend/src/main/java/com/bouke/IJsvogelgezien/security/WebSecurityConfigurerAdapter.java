package com.bouke.IJsvogelgezien.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

public abstract class WebSecurityConfigurerAdapter {
    public abstract void configure(AuthenticationManagerBuilder builder) throws Exception;

    @Bean
    public abstract AuthenticationManager authenticationManagerBean() throws Exception;

    protected abstract void configure(HttpSecurity http) throws Exception;
}
