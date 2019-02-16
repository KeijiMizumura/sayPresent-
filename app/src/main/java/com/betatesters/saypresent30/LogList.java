package com.betatesters.saypresent30;

public class LogList {

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    private String firstName;
    private String lastName;
    int selectedId=-1;

    public LogList(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }

}
