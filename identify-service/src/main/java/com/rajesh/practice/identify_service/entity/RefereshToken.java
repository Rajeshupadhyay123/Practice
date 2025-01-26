package com.rajesh.practice.identify_service.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
@Table(name = "referesh_token")
public class RefereshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String token;

    private Instant expiryDate;

    @OneToOne
    @JoinColumn(name = "user_id",referencedColumnName = "user_id")
    private UserCredential userCredential;


    public RefereshToken() {
    }

    // Private constructor to enforce the use of the builder
    private RefereshToken(String token, Instant expiryDate, UserCredential userCredential) {
        this.token = token;
        this.expiryDate = expiryDate;
        this.userCredential=userCredential;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserCredential getUserCredential() {
        return userCredential;
    }

    public void setUserCredential(UserCredential userCredential) {
        this.userCredential = userCredential;
    }

    public Instant getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public String toString() {
        return "RefereshToken{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", userCredential=" + userCredential +
                '}';
    }

    // Static inner Builder class
    public static class Builder {
        private String token;
        private Instant expiryDate;
        private UserCredential userCredential;

        // Builder methods for setting fields
        public Builder token(String token) {
            this.token = token;
            return this;
        }

        public Builder expiryDate(Instant expiryDate) {
            this.expiryDate = expiryDate;
            return this;
        }

        public Builder userCredential(UserCredential userCredential){
            this.userCredential=userCredential;
            return this;
        }

        // Build method to create a RefreshToken instance
        public RefereshToken build() {
            return new RefereshToken(token, expiryDate,userCredential);
        }
    }

    // Static method to get the builder
    public static Builder builder() {
        return new Builder();
    }




}
