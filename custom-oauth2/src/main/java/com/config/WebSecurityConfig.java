package com.config;

import com.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@ComponentScan({"com.service"})
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    CustomUserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().withUser("roman").password("qwe123").roles("ADMIN");
//        auth.inMemoryAuthentication().withUser("vova").password("1111").roles("USER");
        auth.userDetailsService(userDetailsService);
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .cors()
//                .and()
//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/welcome", "/home", "/", "/error").permitAll()
//                .antMatchers("/admin").hasRole("ADMIN")
//                .anyRequest().authenticated();
//    }

    //redundant => default is used
//    @Override
//    @Bean
//    protected AuthenticationManager authenticationManager() throws Exception {
//        return super.authenticationManager();
//    }
}
