package com.config;

import com.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@Import(WebSecurityConfig.class)
public class OAuth2Config {
    private static final String RESOURCE_ID = "resource";

    @Configuration
    @EnableAuthorizationServer
    @ComponentScan({"com.service"})
    public class OAuth2Server extends AuthorizationServerConfigurerAdapter {

        //redundant -> used by default
//        private TokenStore tokenStore = new InMemoryTokenStore();

        @Autowired
        AuthenticationManager authenticationManager;

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            endpoints.authenticationManager(authenticationManager);
            //redundant -> used by default
//                    .userDetailsService(userDetailsService)
//                    .tokenStore(tokenStore);
        }

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients
                    .inMemory()
                    .withClient("client")
                    .authorizedGrantTypes("password", "authorization_code", "refresh_token")
                    .authorities("ROLE_USER")
                    .scopes("read")
                    .resourceIds(RESOURCE_ID)
                    .secret("qwe123")
                    .accessTokenValiditySeconds(60);
        }

        //redundant -> used by default
//        @Bean
//        @Primary
//        public DefaultTokenServices tokenServices() {
//            DefaultTokenServices tokenServices = new DefaultTokenServices();
//            tokenServices.setTokenStore(this.tokenStore);
//            tokenServices.setSupportRefreshToken(true);
//            return tokenServices;
//        }
    }

    @Configuration
    @EnableResourceServer
    public class ResourceServer extends ResourceServerConfigurerAdapter {

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
            resources.resourceId(RESOURCE_ID);
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            // @formatter:off
            http
                    .cors()
                    .and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).and()
                    .csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/welcome", "/home", "/").permitAll()
                    .antMatchers("/admin").hasRole("ADMIN")
                    .anyRequest().authenticated();
//                    .and().formLogin()
//                    .loginPage("/login").successForwardUrl("/admin");
            // @formatter:on
        }
    }
}
