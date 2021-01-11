package com.faceit.example.internetlibrary.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final DataSource dataSource;

    @Autowired
    public WebSecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*http.authorizeRequests()
                .antMatchers("/**").permitAll()
                .and()
                .csrf().disable();*/

        http.csrf().disable().cors().and().authorizeRequests()
                .antMatchers("/").hasAnyRole("EMPLOYEE", "HR", "IT")
                .antMatchers("/book").hasAnyRole("EMPLOYEE", "IT")
                .antMatchers("/reader").hasAnyRole("EMPLOYEE", "IT")
                .antMatchers("/api/book/**").hasAnyRole("EMPLOYEE", "HR", "IT")
                .antMatchers("/api/reader/**").hasAnyRole("EMPLOYEE", "HR", "IT")
                .antMatchers("/api/orderbook/**").hasAnyRole("EMPLOYEE", "HR", "IT")
                .and().formLogin().permitAll()
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/");
    }


}
