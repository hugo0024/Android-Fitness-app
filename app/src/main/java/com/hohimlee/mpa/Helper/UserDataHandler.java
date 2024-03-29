package com.hohimlee.mpa.Helper;

public class UserDataHandler {

    String firstName, lastName, email, gender, dateOfBirth, phoneNumber, signUpDate;

    public UserDataHandler(){}

    public UserDataHandler(String firstName, String lastName, String email, String gender, String dateOfBirth, String phoneNumber, String signUpDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.signUpDate = signUpDate;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumber(){return phoneNumber;};

    public void setPhoneNumber(String phoneNumber){this.phoneNumber = phoneNumber;}

    public String getSignUpDate(){return signUpDate;};

    public void setSignUpDate(String signUpDate){this.signUpDate = signUpDate;}

}
