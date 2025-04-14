package com.example;

import java.util.ArrayList;

public class ServerInfo {
    public ArrayList<UserInfo> users = new ArrayList<>();
    public ArrayList<UserInDB> leaders = new ArrayList<>();
    public GameStatus status;
    public int clientNumberWin;
    public double y1, y2, y3;
    public double x1, x2, x3;
    public double r1, r2, r3;
    public double[] arrow_x_coords = {101.0, 101.0};
    public double[] arrow_y_coords = {30.0, 100.0};

    public ServerInfo() {
        status = GameStatus.STOP;
        clientNumberWin = 0;
        for (int i = 0; i < users.size(); i++) {
            users.get(i).score = 0;
            users.get(i).shots = 0;
        }
    }
    
    public void init() {
        status = GameStatus.PLAY;
        clientNumberWin = 0;
        for (int i = 0; i < users.size(); i++) {
            users.get(i).score = 0;
            users.get(i).shots = 0;
        }
    }
}
