package com.example;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.gson.Gson;

public class mainServer {
    int port = 3124;
    ServerInfo info = new ServerInfo();
    ExecutorService service = Executors.newCachedThreadPool();
    InetAddress ip = null;

    ServerSocket ss;
    boolean[] ready = {false, false};
    boolean[] new_game = {false, false};
    //double[] arrow_y_coords = {101.0, 101.0};

    ArrayList<clientConnect> allConnection = new ArrayList<>();
    int numberClients = 0;
    int numberActive;

    double height = 400.0;
    double widht = 759.0;
    protected final Object lockStatus = new Object();
    protected final Object lockTargets = new Object();
    protected final Object lockActive = new Object();
    protected final Object lockDB = new Object();

    public mainServer() {
        try {
            ip = InetAddress.getLocalHost();

            ss = new ServerSocket(port, 0, ip);

            System.out.append("Server start\n");

            new Thread( () -> {
                while (true) {
                    synchronized(lockActive) {
                        numberActive = 0;
                        for (int i = 0; i < info.users.size(); i++) {
                            if (info.users.get(i).active) numberActive++;
                        }        
                    }

                    int numberReady = 0;
                    int numberNewGame = 0;
                    for (int i = 0; i < info.users.size(); i++) {
                        if (ready[i]) numberReady++;
                        if (new_game[i]) numberNewGame++;
                    }
                        
                    //System.out.println("Ready: " + numberReady);
                    //System.out.println("NewGame: " + numberNewGame);
                    //System.out.println("Active: " + numberActive);
                        
                    synchronized (lockStatus) {
                        if (info.status == GameStatus.PLAY) {
                            if (numberReady != info.users.size()) {
                                info.status = GameStatus.PAUSE;
                            }   
                        }
                        else if (info.status == GameStatus.PAUSE) {
                            if (numberReady == info.users.size() && numberReady != 0) {
                                info.status = GameStatus.PLAY;
                            }
                        }
                        else if (info.status == GameStatus.STOP) {
                            if (numberReady == info.users.size() && numberReady != 0) {
                                System.out.println("INIT");
                                info.init();
                            }
                        }
                    }
                    synchronized (lockTargets) {
                        if (info.status == GameStatus.PLAY) {
                            //System.out.println("change layout");
                            double y1 = info.y1 + 0.1;
                            double y2 = info.y2 + 0.2;
                            double y3 = info.y3 + 0.25;
                            if (y1 > height)
                                y1 = 0;
                            if (y2 > height)
                                y2 = 0;
                            if (y3 > height)
                                y3 = 0;
                            info.y1 = y1;
                            info.y2 = y2;
                            info.y3 = y3;
                        }
                    }
                    bcastServerData();
                    
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            } ).start();



        } catch (IOException e) {
            e.printStackTrace();
        }

        while(true) {
            if (numberClients < 2) {
                try {
                    Socket cs = ss.accept();
                    OutputStream os = cs.getOutputStream();
                    DataOutputStream dos = new DataOutputStream(os);
                    InputStream is = cs.getInputStream();
                    DataInputStream dis = new DataInputStream(is);
    
                    int port = cs.getPort();
    
                    System.out.println("Connect (" + port + ")");
                    String username = dis.readUTF();
                    boolean duplicate_name = false;
                
                    for (int i = 0; i < info.users.size(); i++) {
                        if (info.users.get(i).username.equals(username)) {
                            duplicate_name = true;
                            break;
                        }
                    }
                    System.out.println("Duplicate: "+ duplicate_name);
                    if (duplicate_name) {
                        dos.writeInt(0);
                        System.out.println("Client disconnect. Port " + cs.getPort());
                        cs.close();
                        continue;
                    }
                    numberClients++;
                    dos.writeInt(numberClients);
                    info.y1 = dis.readDouble();
                    info.y2 = dis.readDouble();
                    info.y3 = dis.readDouble();
                    info.x1 = dis.readDouble();
                    info.x2 = dis.readDouble();
                    info.x3 = dis.readDouble();
                    info.r1 = dis.readDouble();
                    info.r2 = dis.readDouble();
                    info.r3 = dis.readDouble();
                    System.out.println(username);
                    if (info.status == GameStatus.PLAY) {
                        info.users.add(new UserInfo(username, false));
                    } else {
                        info.users.add(new UserInfo(username, true));
                    }
                    clientConnect cc = new clientConnect(cs, this, numberClients);
                    System.out.println("Size of Users: " + info.users.size());
                    System.out.println("y1: " + info.y1);
                    System.out.println("y2: " + info.y2);
                    System.out.println("y3: " + info.y3);
                    allConnection.add(cc);
                    //service.submit(cc);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    
    public void bcastServerData(){
        for (clientConnect client : allConnection) {
            client.sendToClient();
        }
    }


    public static void main(String[] args) {
        new mainServer();
    }

}
