package com.rajesh.practice.identify_service.response;

public class JwtResponse {

    private String accessToken;

    private String token;


    public JwtResponse() {
    }

    public JwtResponse(String accessToken, String token) {
        this.accessToken = accessToken;
        this.token = token;
    }


    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "JwtResponse{" +
                "accessToken='" + accessToken + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
