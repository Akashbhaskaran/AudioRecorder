package com.dimowner.audiorecorder.app.model;

public class LoginResponse {
    String access_token;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public LoginResponse(String access_token) {
        this.access_token = access_token;
    }
}
