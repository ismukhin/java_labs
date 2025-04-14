package com.example;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.google.gson.Gson;


public class clientConnect {
    Socket cs;
    mainServer server;

    InputStream is;
    OutputStream os;
    DataInputStream dis;
    DataOutputStream dos;

    Gson json = new Gson();

    clientInfo status;

    int clientnum;
    boolean shot = false;
    boolean arrow = false;

    public clientConnect(Socket cs, mainServer server, int clientnumber) {
        this.cs = cs;
        this.server = server;
        this.clientnum = clientnumber;
        try {

            os = cs.getOutputStream();

            dos = new DataOutputStream(os);

            new Thread(this::run).start();

        } catch (IOException e) {

        throw new RuntimeException(e);

        }
    }

    private void moveArrow(double x, double y) {
        server.info.arrow_x_coords[clientnum - 1] = x;
        server.info.arrow_y_coords[clientnum - 1] = y;
    }

    public void update() {
        if (!arrow && shot) {
            arrow = true;
            shot = false;
            server.info.users.get(clientnum - 1).shots++;
            //synchronized(server.lockActive) {
            //    moveArrow(130, server.height * 1.0 /
            //              (server.numberActive + 1) * clientnum);
            //}
        }
        if (arrow) shot = false;
        
        if (arrow) {
            double new_x = server.info.arrow_x_coords[clientnum - 1] + 0.5;
            moveArrow(new_x, server.info.arrow_y_coords[clientnum - 1]);
            if(new_x > server.widht) {
                arrow = false;
                server.info.arrow_x_coords[clientnum - 1] = 101.0;
            }
            
            synchronized (server.lockTargets) {
                double x_a = server.info.arrow_x_coords[clientnum - 1];
                double y_a = server.info.arrow_y_coords[clientnum - 1];
                double x_1 = server.info.x1, x_2 = server.info.x2, x_3 = server.info.x3;
                double y_1 = server.info.y1, y_2 = server.info.y2, y_3 = server.info.y3;
                double r_1 = server.info.r1, r_2 = server.info.r2, r_3 = server.info.r3;
            
                if ((x_a - x_1) * (x_a - x_1) + (y_a - y_1) * (y_a - y_1) < r_1*r_1) {
                    server.info.users.get(clientnum - 1).score++;
                    server.info.arrow_x_coords[clientnum - 1] = 101.0;
                    //server.info.users.get(clientnum - 1).y_a = -1.0;
                    arrow = false;            
                }

                if ((x_a - x_2) * (x_a - x_2) + (y_a - y_2) * (y_a - y_2) < r_2*r_2) {
                    server.info.users.get(clientnum - 1).score += 2;
                    server.info.arrow_x_coords[clientnum - 1] = 101.0;
                    //server.info.users.get(clientnum - 1).y_a = -1.0;
                    arrow = false;   
                }
                if ((x_a - x_3) * (x_a - x_3) + (y_a - y_3) * (y_a - y_3) < r_3*r_3) {
                    server.info.users.get(clientnum - 1).score += 5;
                    server.info.arrow_x_coords[clientnum - 1] = 101.0;
                    //server.info.users.get(clientnum - 1).y_a = -1.0;
                    arrow = false;   
                }
            }
            
            if (server.info.users.get(clientnum - 1).score >= 10) {
                synchronized(server.lockDB) {
                    server.db.incrWins(server.info.users.get(clientnum - 1).username);          
                }
                synchronized (server.lockStatus) {
                    server.info.status = GameStatus.WIN;
                }
                server.info.clientNumberWin = clientnum;
            }
        }
    }

    public void run() {
        try {

            is = cs.getInputStream();
            dis = new DataInputStream(is);
            System.out.println("Client thread started");

            while(true) {
                System.out.println("wait dis");
                String strData = dis.readUTF();
                System.out.println("after dis");
                System.out.println("Data read " + clientnum);
                System.out.println("wait status json");
                status = json.fromJson(strData, clientInfo.class);
                System.out.println("after status json");
                server.info.arrow_y_coords[clientnum - 1] = status.ya;
                server.ready[clientnum - 1] = status.ready;
                System.out.append("Updater: " + clientnum + " " + status.ready);
                server.new_game[clientnum - 1] = status.new_game;
                if (!shot) shot = status.shot;

                // System.out.println("Update " + clientNumber);

                if (server.info.status == GameStatus.PLAY &&
                    server.info.users.get(clientnum - 1).active) {
                    System.out.println("Update in " + clientnum);
                    update();
                }

                // System.out.println("Update out " + clientNumber);
            }
            
        } catch (IOException e) {
            
            e.getStackTrace();
            
        }
    }

    public void sendToClient(){
        try {
            String strData = json.toJson(server.info);
            dos.writeUTF(strData);
            //System.out.println("Data write");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


}
