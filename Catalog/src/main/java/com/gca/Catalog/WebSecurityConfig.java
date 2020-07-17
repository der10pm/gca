package com.gca.Catalog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;


@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${DOCKER_USER:catalog}")
    private String DOCKER_USER;

    @Value("${DOCKER_PW:catalog}")
    private String DOCKER_PW;

    @Override
    protected void configure(HttpSecurity http) throws Exception 
    {
        http.addFilterBefore(new CorsFilter(), ChannelProcessingFilter.class);
        http
                .authorizeRequests()
                .antMatchers("/health")
                .permitAll()
                .anyRequest()
                .fullyAuthenticated()
                .and()
                .httpBasic()
                .and().csrf().disable();
    }
  
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) 
            throws Exception 
    {
        auth.inMemoryAuthentication()
            .withUser(this.DOCKER_USER)
            .password("{noop}"+this.DOCKER_PW)
            .roles("USER");
    }

}