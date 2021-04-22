package com.microservices.photoappapiusers.Service;

import com.microservices.photoappapiusers.Shared.UserDto;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    
    UserDto createUser(UserDto userDetails);
    UserDto getUserDetailsByEmail(String Email);
}
