package com.mypersonal.tutor.Models;

import java.io.Serializable;

public class User implements Serializable {
    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private int validity;
    private String token;
    private String image;
    private String picture;
    private String name;
    private String subject;
    private int tutorId;

    public User(int validity, String name, String picture, String subject) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
        this.validity = validity;
        this.token = token;
        this.image = image;
        this.name = name;
        this.picture = picture;
        this.subject= subject;
        this.tutorId = tutorId;
    }

    public User(Integer name, String firstName, String lastName, Integer userId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.image = image;
    }

    public int getTutorId() {
        return tutorId;
    }

    public void setTutorId(int tutorId) {
        this.tutorId = tutorId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getValidity() {
        return validity;
    }

    public void setValidity(int validity) {
        this.validity = validity;
    }

    public String getmage() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
