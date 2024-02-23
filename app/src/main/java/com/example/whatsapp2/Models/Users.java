package com.example.whatsapp2.Models;

public class Users {
    String userId;
    String profilePic;
    String userName;
    String status;
    String email;
    String password;
    String lastMessage;
    public Users(){}

    public Users(String userId, String profilePic,String userName, String status, String email, String password, String lastMessage) {
        this.userId = userId;
        this.profilePic = profilePic;
        this.userName = userName;
        this.status = status;
        this.email = email;
        this.password = password;
        this.lastMessage = lastMessage;
    }

    public Users(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}
