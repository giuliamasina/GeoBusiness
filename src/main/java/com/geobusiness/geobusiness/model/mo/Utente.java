package com.geobusiness.geobusiness.model.mo;



public class Utente {

    private Integer id;
    private String username;

    private  String email;
    private String password;
    private boolean deleted;

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public boolean isDeleted() { return deleted; }

    public void setDeleted(boolean deleted) { this.deleted = deleted; }
}
