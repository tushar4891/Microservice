package com.microservices.photoappapiusers.Controllers;

import javax.validation.Valid;

import com.microservices.photoappapiusers.Model.CreateUserRequestModel;
import com.microservices.photoappapiusers.Model.CreateUserResponseModel;
import com.microservices.photoappapiusers.Service.UserService;
import com.microservices.photoappapiusers.Shared.UserDto;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UsersController {
    
    @Autowired
	private Environment env;

    @Autowired
    private UserService userService;
	
    @GetMapping("/status/check")
    public String status()
    {
        return "Working on port no ...."+ env.getProperty("local.server.port");
    }

    @PostMapping
    public ResponseEntity<CreateUserResponseModel> createUser(@Valid @RequestBody CreateUserRequestModel userDetials)
    {
        System.out.println("user is being created");
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDto userDto = modelMapper.map(userDetials, UserDto.class);

        UserDto createdUser = userService.createUser(userDto);

        CreateUserResponseModel returnValue =  modelMapper.map(createdUser, CreateUserResponseModel.class);

        return  ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
    }

    /*
    @PostMapping("/login")
    public void login(@Valid @RequestBody CreateUserRequestModel userD){

        System.out.println("Hi how r u ");
        authenticate(userD.getEmail(), userD.getPassword());

        UserDto userDetails =  userService.getUserDetailsByEmail(userD.getEmail());

        if(userDetails != null)
            System.out.println("User logged in successfully");
        else
            System.out.println("Logging unsuccessful");
       
    }

    
    private void authenticate(String username, String password) {

        System.out.println("authentication is going on ");
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
    */
}
