package com.faceit.example.internetlibrary.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
        // временно отключаем безопаcность, чтобы не требовал ввода логина и пароля на страницах
        http.authorizeRequests()
                .antMatchers("/**").permitAll()
                .and()
                .csrf().disable();

/*        http.csrf().disable().cors().and().authorizeRequests()
                .antMatchers("/api/book/**").hasAnyRole("EMPLOYEE", "HR", "IT")
*//*                .antMatchers(HttpMethod.DELETE, "/api/book/**").hasAnyRole("EMPLOYEE", "HR", "IT")
                .antMatchers(HttpMethod.POST, "/api/book/**").hasAnyRole("EMPLOYEE", "HR", "IT")*//*
                .antMatchers("/api/reader/**").hasAnyRole("EMPLOYEE", "HR", "IT")
                .antMatchers("/api/orderbook").hasRole("EMPLOYEE")
                //.antMatchers("/api/orderbook/reader/**").hasRole("EMPLOYEE")
                .and().formLogin().permitAll();*/
    }

    /*
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // настройка ограничений доступа к страницам
        http.authorizeRequests()
                .antMatchers("/**").permitAll()
                .antMatchers("/pages/spr.xhtml").hasRole("ADMIN")// здесь автоматически будет добавлен префикс ROLE_,  поэтому указываем название роли без него
                .antMatchers("/pages/books.xhtml").hasAnyRole("ADMIN", "USER")// здесь автоматически будет добавлен префикс ROLE_,  поэтому указываем название роли без него
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().accessDeniedPage("/index.xhtml")// при ошибке доступа - будет перенправляться на страницу с книгами
                .and()
                .csrf().disable()
                // окно аутентификации
                .formLogin()
                .loginPage("/index.xhtml")
                .failureHandler(authHandler)
                .defaultSuccessUrl("/pages/books.xhtml")
                .loginProcessingUrl("/login")
                .passwordParameter("password")
                .usernameParameter("username")
                .and()
                // настройка выхода пользователя из системы
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/index.xhtml")
                .deleteCookies("JSESSIONID", "SPRING_SECURITY_REMEMBER_ME_COOKIE")
                .invalidateHttpSession(true);
    }*/
}
