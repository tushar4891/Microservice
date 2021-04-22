package com.microservices.photoappapiusers.Security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.photoappapiusers.Model.LoginRequestModel;
import com.microservices.photoappapiusers.Service.UserService;
import com.microservices.photoappapiusers.Shared.UserDto;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

// import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.security.core.userdetails.*;

// This method is called each time when user tries to login.
// This filter takes input from request and fetch username from database and tries to authenticate
// username
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter
{
    private UserService userService;
    private Environment environment;


    public AuthenticationFilter(UserService userService,
                                 Environment environment,
                                 AuthenticationManager authenticationManager)
                                
    {
        this.userService = userService;
        this.environment = environment;
        super.setAuthenticationManager(authenticationManager);
    }
   

    // this tries to attempt authetication by fetching username and password from request
    @Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException 
    {
        
        try {
                LoginRequestModel cred = new ObjectMapper().readValue(request.getInputStream(),
                                             LoginRequestModel.class);
            
                // It takes in a clear text password. Spring Framework takes over from
                // there and calls a method to loadUserByUsername() from which it gets 
                // an encrypted value of a password stored in a database. Then Spring 
                // Security framework uses the BCryptPasswordEncoder which we asked it
                //  to use in WebSecurity java file to encode the plain text password and 
                //  compare it with the value it read from a database.  
                //  So this functionality of encoding a plain text password and comparing 
                //  it with the value returned from a loadUserByUsername() is taking place 
                //  behind the scenes by Spring Framework. 
                return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(cred.getEmail(),
                                                        cred.getPassword(),
                                                        new ArrayList<>())
            );
        } 
        catch (IOException e) 
        {
            throw new RuntimeException(e);
            
        }
        
     
	}

    // If authetication is successful, this method is used to create JWT token and then add
    // that token in Http response method and return it back to http response method.

    // Authetication auth--> gives details of currently logged in user details.
    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException,ServletException
    {
        String  username = ((User) auth.getPrincipal()).getUsername();
       UserDto userDetails =  userService.getUserDetailsByEmail(username);
       

       //generating JWT token
        String token = Jwts.builder()
                       .setSubject(userDetails.getUserId())
                       .setExpiration(new Date(System.currentTimeMillis() 
                        + Long.parseLong(environment.getProperty("token.expiration_time"))))
                       .signWith(SignatureAlgorithm.HS512,environment.getProperty("token.secret"))
                       .compact();
    
        // now generating token is sent back to clent through response header
        res.addHeader("token", token);
        res.addHeader("userId", userDetails.getUserId());
    }  
}
