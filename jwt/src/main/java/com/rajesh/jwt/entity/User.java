package com.rajesh.jwt.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    private String userName;

    private String password;

    private String email;

    private boolean sentimentAnalysis;

    private List<String> roles;

    public User() {
    }

    public User(int id, String userName, String password, String email, boolean sentimentAnalysis, List<String> roles) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.sentimentAnalysis = sentimentAnalysis;
        this.roles = roles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isSentimentAnalysis() {
        return sentimentAnalysis;
    }

    public void setSentimentAnalysis(boolean sentimentAnalysis) {
        this.sentimentAnalysis = sentimentAnalysis;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", sentimentAnalysis=" + sentimentAnalysis +
                ", roles=" + roles +
                '}';
    }
}
