package com.microservices.photoappapiusers.Security;

import com.microservices.photoappapiusers.Service.UserService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter
{       

        private UserService userService;
        private BCryptPasswordEncoder bCryptPasswordEncoder;
        private Environment environment;

        @Autowired
        public WebSecurity(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder, Environment environment) {
            this.userService = userService;
            this.bCryptPasswordEncoder = bCryptPasswordEncoder;
            this.environment = environment;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            
            http.csrf().disable(); 
            http.authorizeRequests().antMatchers("/**").permitAll()
            .and()
            .addFilter(getAuthenticationFilter());
            http.headers().frameOptions().disable();
        }

        private AuthenticationFilter getAuthenticationFilter() throws Exception
        {
            AuthenticationFilter authenticationFilter = new AuthenticationFilter(userService, environment,authenticationManager());
            //authenticationFilter.setAuthenticationManager(authenticationManager());
            authenticationFilter.setFilterProcessesUrl(environment.getProperty("login.url.path"));
            return authenticationFilter;
        }

        // this method lets spring know which service is gong to be used to load user details
        // and which password encoder is going to be used
        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception
        {
            auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
        } 
        
        
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
