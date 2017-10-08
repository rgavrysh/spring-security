package com.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import javax.servlet.Filter;

@Configuration
@EnableOAuth2Client
@ComponentScan("com.ctrlers")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    OAuth2ClientContext oAuth2ClientContext;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/", "/login**", "/webjars/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .logout().logoutSuccessUrl("/").permitAll()
                .and()
                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and()
                .addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class);
    }

    private Filter ssoFilter() {
        OAuth2ClientAuthenticationProcessingFilter gitHubFilter = new OAuth2ClientAuthenticationProcessingFilter("/login/github");
        OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(gitHub(), oAuth2ClientContext);
        gitHubFilter.setRestTemplate(oAuth2RestTemplate);
        UserInfoTokenServices gitHubTokenServices = new UserInfoTokenServices(gitHubResource().getUserInfoUri(), gitHub().getClientId());
        gitHubTokenServices.setRestTemplate(oAuth2RestTemplate);
        gitHubFilter.setTokenServices(gitHubTokenServices);
        return gitHubFilter;
    }

    @Bean
    @ConfigurationProperties("gitHub.resource")
    public ResourceServerProperties gitHubResource() {
        return new ResourceServerProperties();
    }

    @Bean
    @ConfigurationProperties("gitHub.client")
    public AuthorizationCodeResourceDetails gitHub() {
        return new AuthorizationCodeResourceDetails();
    }

    @Bean
    public FilterRegistrationBean oauth2ClientFilterRegistration(
            OAuth2ClientContextFilter filter) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(filter);
        registration.setOrder(-100);
        return registration;
    }

}
