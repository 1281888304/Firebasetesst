package com.example.firebasetesst;

public class User {

    public String fullName, phone,email;


    public User(){

    }

    public String getFullName() {
        return fullName;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public User(String fullName, String phone, String email){
        this.fullName=fullName;
        this.phone=phone;
        this.email=email;

    }

}
