package com.etms.worldline.payload.response;

import java.util.List;

public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private Boolean success=false;
    private String message;
    private String username;
    private String email;



    private List<String> roles;

    public JwtResponse(String accessToken, Long id, String username, String email,Boolean success, List<String> roles,String message) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.success=success;
        this.roles = roles;
        this.message=message;
    }
    public JwtResponse(String message,Boolean success){
        this.message=message;
        this.success=success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
