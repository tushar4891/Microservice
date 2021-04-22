package com.microservices.photoappapiusers.Model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateUserRequestModel {
   
    @NotNull(message="First name can not be null")
    @Size(min=2, message="First name must not be less than 2 characters")
    private String firstname;

    @NotNull(message="Last name can not be null")
    @Size(min=2, message="Last name must not be less than 2 characters")
    private String lastname;

    @NotNull(message="Email can not be null")
    @Email
    private String email;

    @NotNull(message="Password can not be null")
    @Size(min=5, max=8, message="Password must be equal or greater than 5 char and  less than 8 chars" )
    private String password;

    public CreateUserRequestModel(String firstname, String lastname, String email, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }

    public CreateUserRequestModel() {
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    
    
}
