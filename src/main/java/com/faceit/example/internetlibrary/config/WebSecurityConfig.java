package com.faceit.example.internetlibrary.config;

import com.faceit.example.internetlibrary.service.impl.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

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
/*        http.csrf().disable().cors().and().authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll()
                .and()
                .logout().permitAll();*/

/*        http.csrf().disable().cors().and().authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/index")
                //.failureUrl("/registration")
                .permitAll().and()
                .logout()
                .permitAll();*/

        http
                //.antMatcher("/registration").antMatcher("/api/registration")
                .authorizeRequests()
                .antMatchers("/", "/login", "/registration/**", "/api/registration/**").permitAll()
                .antMatchers("/book/**", "/user/**", "/orderbook/**").hasAnyRole("EMPLOYEE", "USER")
                .antMatchers("/api/**").hasAnyRole("EMPLOYEE", "USER")
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable().cors()
                .and()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login?error=true")
                .defaultSuccessUrl("/orderbook")
                .usernameParameter("username")
                .passwordParameter("password")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login").and().exceptionHandling()
                .accessDeniedPage("/access-denied");
    }

    @Override
    public void configure(WebSecurity web) {
        web
                .ignoring()
                //.antMatchers("/js/registration.js");
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
    }
}
