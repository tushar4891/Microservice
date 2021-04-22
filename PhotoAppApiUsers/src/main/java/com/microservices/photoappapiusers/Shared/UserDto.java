package com.microservices.photoappapiusers.Shared;

import java.io.Serializable;

public class UserDto implements Serializable {
    
 
    private static final long serialVersionUID = 1L;
    
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String userId;
    private String encryptedPassword;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    // public UserDto(String firstname, String lastname, String email, String password, String userId,
    //         String encryptedPassword) {
    //     this.firstname = firstname;
    //     this.lastname = lastname;
    //     this.email = email;
    //     this.password = password;
    //     this.userId = userId;
    //     this.encryptedPassword = encryptedPassword;
    // }

    // public UserDto() {
    // }

}

    