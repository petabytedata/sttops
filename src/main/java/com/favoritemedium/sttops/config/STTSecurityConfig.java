package com.favoritemedium.sttops.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * Basic security context definition for APIs of the application.
 */
@Configuration
public class STTSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     *Configuration of used detail service with hardcoded username/password
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
        //TODO: Hardcoded user name<amit> and password<aaaa>, this should have come users table
        UserDetails userDetails = User.withUsername("amit").password(passwordEncoder.encode("aaaa"))
                .authorities("read", "write").build();
        userDetailsManager.createUser(userDetails);

        auth.userDetailsService(userDetailsManager).passwordEncoder(passwordEncoder);
    }

    /**
     * Basic security for endpoints is defined here in this method.
     * Right now its only getting utilized for fetching all users endpoint.
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();
        //TODO: This endpoint needs to be uncommented to place basic authentication for all APIs.
//     http.authorizeRequests().anyRequest().authenticated();
        http.csrf().disable();
        //TODO: matcher only allowing basic authentication for fetching all users
        http.authorizeRequests().mvcMatchers(HttpMethod.GET, "/users").authenticated();
    }

    /**
     *
     * @return
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
