package com.docsamples.ojai.samples;

/**
 * Created by khahn on 10/11/15.
 */


import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


public class User {

    private String id;
    private String firstName;
    private String lastName;
    private Date dob;
    private List<String> interests;

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public List<String> getInterests() {
        return interests;
    }

    public void setInterests(List<String> interests) {
        this.interests = interests;
    }

    public void addInterest(String interest) {
        if (interests == null) {
            interests = new ArrayList<String>();
        }
        interests.add(interest);
    }

    @Override
    public String toString() {
        return "User{" +
                "interests=" + interests +
                ", id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dob=" + dob +
                '}';
    }
}
