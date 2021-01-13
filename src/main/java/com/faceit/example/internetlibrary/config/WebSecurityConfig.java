package com.faceit.example.internetlibrary.config;

import com.faceit.example.internetlibrary.service.impl.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
/*        http.authorizeRequests()
                .antMatchers("/**").permitAll()
                .and()
                .csrf().disable();*/
/*        http.csrf().disable().cors().and().authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll()
                .and()
                .logout().permitAll();*/

        http.csrf().disable().cors().and().authorizeRequests()
                .antMatchers("/registration").permitAll()
                .antMatchers("/api/registration").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                //.failureUrl("/regist")
                .permitAll().and()
                .logout()
                .permitAll();
        //.logoutSuccessHandler(logoutSuccessHandler());

        /*http.csrf().disable().cors().and().authorizeRequests()
                .antMatchers("/").hasAnyRole("EMPLOYEE", "HR", "IT")
                .antMatchers("/book").hasAnyRole("EMPLOYEE", "IT")
                .antMatchers("/reader").hasAnyRole("EMPLOYEE", "IT")
                .antMatchers("/api/book/**").hasAnyRole("EMPLOYEE", "HR", "IT")
                .antMatchers("/api/reader/**").hasAnyRole("EMPLOYEE", "HR", "IT")
                .antMatchers("/api/orderbook/**").hasAnyRole("EMPLOYEE", "HR", "IT")
                .and().formLogin().permitAll()
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/");*/
    }
}
