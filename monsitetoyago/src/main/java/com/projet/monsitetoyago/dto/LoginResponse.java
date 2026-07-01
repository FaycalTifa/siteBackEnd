package com.projet.monsitetoyago.dto;

public class LoginResponse {

    private String token;
    private String username;
    private String role;
    private String message;
    private Boolean success;

    public LoginResponse() {}
    public LoginResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }
    public LoginResponse(Boolean success, String token, String username, String role) {
        this.success = success;
        this.token = token;
        this.username = username;
        this.role = role;
        this.message = "Connexion réussie";
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public Boolean getSuccess() { return success; }
    public void setSuccess(Boolean success) { this.success = success; }

}
