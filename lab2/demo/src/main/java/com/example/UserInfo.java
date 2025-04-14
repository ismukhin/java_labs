package com.example;


public class UserInfo {
    public String username;
    public int score, shots;
    public double x_a, y_a;
    public boolean active;
    
    UserInfo(String username, boolean active) {
        this.x_a = -1.0;
        this.y_a = -1.0;
        this.score = this.shots = 0;
        this.active = active;
        this.username = username;
    }
}
