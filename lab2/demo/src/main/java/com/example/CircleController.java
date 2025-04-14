package com.example;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import javafx.application.Platform;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

public class CircleController {
    
    @FXML
    private Circle circle1;

    @FXML
    private Circle circle2;

    @FXML
    private Circle circle3;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private TextField iname_field;

    @FXML
    private Label player1;

    @FXML
    private Label player2;

    @FXML
    private Label score1;
    
    @FXML
    private Button readyButton;

    @FXML
    private Label score2;

    @FXML
    private Label shots;

    @FXML
    private Line lin1;

    @FXML
    private Line lin2;

    @FXML
    private Label status_label;

    @FXML
    private Button table;

    @FXML
    void clickOnMainPane(MouseEvent event) {
        clientInfo.ya = event.getY();
    }

    private Stage leadersStage;
    private String user_name = "";
    private int score_num = 0;
    private int shots_num = 0;
    private final Gson json = new Gson();;

    boolean win = false;

    boolean connect = false;
    int clientNumber;
    ServerInfo serverInfo = new ServerInfo();
    clientInfo clientInfo = new clientInfo();

    int port = 3124;

    InetAddress ip = null;

    Socket cs;
    InputStream is;
    OutputStream os;
    DataInputStream dis;
    DataOutputStream dos;

    void next() {
        //double height = mainPane.getHeight();
        //double y1 = circle1.getLayoutY();
        //double y2 = circle2.getLayoutY();
        //double y3 = circle3.getLayoutY();
        //y1 += 10;
        //y2 += 20;
        //y3 += 30;
        //if(y1 > height) {
        //    y1 = 0;
        //}
//
        //if(y2 > height) {
        //    y2 = 0;
        //}
//
        //if(y3 > height) {
        //    y3 = 0;
        //}
        //circle1.setLayoutY(y1);
        //circle2.setLayoutY(y2);
        //circle3.setLayoutY(y3);

    }

    boolean was_hit(Circle circ, Line line) {
        //double rad = circ.getRadius();
        //double x = circ.getLayoutX();
        //double y = circ.getLayoutY();
        //double linn = line.getEndX();
        //System.out.println("Line: " + (line.getLayoutX() + line.getEndX()) + " Krug: " + x + " " + y);
        //double res = ((line.getLayoutX() + line.getEndX()) - x) * ((line.getLayoutX() + line.getEndX()) - x) + (line.getLayoutY() - y) * (line.getLayoutY() - y);
        //if (res <= rad * rad) {
        //    return true;
        //}
        return false;
    }

    void move_line() {
        //double widht = mainPane.getWidth();
        //double x = lin.getLayoutX();
        //x += 10;
        //if(x > widht) {
        //    x = 0 - lin.getStartX();
        //    shoted = false;
        //    shot_t = null;
        //}
        //if(was_hit(circle1, lin)) {
        //    System.out.println("Circle1");
        //    x = 0;
        //    score_num += 1;
        //    score1.setText(Integer.toString(score_num));
        //    shoted = false;
        //    shot_t = null;
        //}
        //if(was_hit(circle2, lin)) {
        //    System.out.println("Circle2");
        //    x = 0;
        //    score_num += 2;
        //    score1.setText(Integer.toString(score_num));
        //    shoted = false;
        //    shot_t = null;
        //}
        //if(was_hit(circle3, lin)) {
        //    System.out.println("Circle3");
        //    x = 0;
        //    score_num += 4;
        //    score1.setText(Integer.toString(score_num));
        //    shoted = false;
        //    shot_t = null;
        //}
        //lin.setLayoutX(x);
    }

    @FXML
    void clickOnShot(ActionEvent event) {
        //if(shot_t == null) {
        //    shots_num += 1;
        //    shots.setText(Integer.toString(shots_num));
        //    shot_t = new Thread(
        //        ()->{
        //            shoted = true;
        //            while(shoted) {
        //                Platform.runLater(this::move_line);
        //                try {
        //                    if(paused) {
        //                        synchronized (this) {
        //                            this.wait();
        //                        }
        //                        paused = false;
        //                    }
        //                    Thread.sleep(100);
        //                } catch (InterruptedException e) {
        //                    shoted = false;
        //                    shot_t = null;
        //                }
        //            }
        //        }
        //    );
        //    shot_t.start();
        //}
        if (clientInfo.ready) {
            clientInfo.shot = true;
        }

    }

    @FXML
    void clickOnReady(ActionEvent event) {
        //if (t == null) {
        //    score1.setText(Integer.toString(score_num));
        //    shots.setText(Integer.toString(shots_num));
        //    t = new Thread(
        //        ()-> {
        //            play = true;
        //            while(play) {
        //                Platform.runLater(this::next);
        //                try {
        //                    if(paused) {
        //                        synchronized (this) {
        //                            this.wait();
        //                        }
        //                        paused = false;
        //                    }
        //                    Thread.sleep(100);
        //                } catch (InterruptedException e) {
        //                    play = false;
        //                    t = null;
        //                }
        //            }
        //        }
        //    );
        //    t.start();
        //}
        if (connect) {
            if (clientInfo.ready) {
                readyButton.setText("Готов");
            } else {
                if (serverInfo.status == GameStatus.WIN) {
                    clientInfo.new_game = true;
                }
                readyButton.setText("Пауза");
            }
            clientInfo.ready = !clientInfo.ready;
        }
    }

    @FXML
    void clickOnStop(ActionEvent event) {
        //if (t != null) {
        //    t.interrupt();
        //}
        //if (shot_t != null) {
        //    shot_t.interrupt();
        //}
    }

    @FXML
    void enterPlayerName(ActionEvent event) {
        user_name = iname_field.getText();
        player1.setText(user_name);
    }

    void set_data(ServerInfo serv) {
        this.serverInfo = serv;
    }

    @FXML
    void clickOnConnect(ActionEvent event) {
        if(!connect) {
            try {
                ip = InetAddress.getLocalHost();

                cs = new Socket(ip, port);

                //cc = new clientConnect(cs, false);

                os = cs.getOutputStream();
                dos = new DataOutputStream(os);
                is = cs.getInputStream();
                dis = new DataInputStream(is);

            } catch (IOException e) {
                e.printStackTrace();
            }
            String username = iname_field.getText();
            System.out.println(username);
            if ("".equals(username)) {
                System.out.println("Client disconnected. Port " + cs.getPort() + "\n");
                try {
                    cs.close();
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                status_label.setText("Статус: Нет подключения (Имя отсутствует)");

            }
            try {
                dos.writeUTF(username);
                clientNumber = dis.readInt();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (clientNumber == 0) {
                try {
                    System.out.println("Client disconnected. Port " + cs.getPort() + "\n");
                    cs.close();
                    status_label.setText("Статус: Нет подключения(Имя уже существует)");
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                dos.writeDouble(circle1.getLayoutY());
                dos.writeDouble(circle2.getLayoutY());
                dos.writeDouble(circle3.getLayoutY());
                dos.writeDouble(circle1.getLayoutX());
                dos.writeDouble(circle2.getLayoutX());
                dos.writeDouble(circle3.getLayoutX());
                dos.writeDouble(circle1.getRadius());
                dos.writeDouble(circle2.getRadius());
                dos.writeDouble(circle3.getRadius());
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("HERE1");
            status_label.setText("Статус: Подключен ("+username+")");
            System.out.println("HERE2");
            connect = true;
            new Thread( () -> {
                try {
                    while (true) {
                        String strData = dis.readUTF();
                        //System.out.println("Data read");
                        
                        serverInfo = json.fromJson(strData, ServerInfo.class);
                        set_data(serverInfo);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } ).start();
            new Thread( () -> {                 
                while (true) {
                    
                    Platform.runLater(this::change_label);
                    System.out.println("Ready: " + clientInfo.ready);
                    System.out.println("lin1x: " + lin1.getLayoutX() + " lin2x: " + lin2.getLayoutX());
                    System.out.println("lin1y: " + lin1.getLayoutY() + " lin2y: " + lin2.getLayoutY());
                    //Platform.runLater(this::updateStatusGame);
                    //scores[i].setText("Счет: " + String.valueOf(serverInfo.users.get(i).score));
                    //System.out.println(player1.getText());
                    //System.out.println(player2.getText());
                    //shotsArray[i].setText("Выстрелов: " + String.valueOf(serverData.users.get(i).shots));
                    //updateStatusGame();
                    //setWins();
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            } ).start();
            new Thread( () -> {
                while (true) {
                    String strData = json.toJson(clientInfo);

                    try {
                        // System.out.println(clientData.ready);
                        dos.writeUTF(strData);
                        System.out.println("Data write");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    
                    clientInfo.shot = false;
                    
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            } ).start();

        }
    }

    public void change_label() {
        if(serverInfo.users.size() == 1) {
            player1.setText(serverInfo.users.get(0).username);
            score1.setText(String.valueOf(serverInfo.users.get(0).score));
            lin1.setLayoutX(serverInfo.arrow_x_coords[0]);
            lin1.setLayoutY(serverInfo.arrow_y_coords[0]);
        }
        else if (serverInfo.users.size() == 2) {
            player1.setText(serverInfo.users.get(0).username);
            score1.setText(String.valueOf(serverInfo.users.get(0).score));
            lin1.setLayoutX(serverInfo.arrow_x_coords[0]);
            lin1.setLayoutY(serverInfo.arrow_y_coords[0]);
            player2.setText(serverInfo.users.get(1).username);
            score2.setText(String.valueOf(serverInfo.users.get(1).score));
            lin2.setLayoutX(serverInfo.arrow_x_coords[1]);
            lin2.setLayoutY(serverInfo.arrow_y_coords[1]);
        }
        circle1.setLayoutY(serverInfo.y1);
        circle2.setLayoutY(serverInfo.y2);
        circle3.setLayoutY(serverInfo.y3);
        updateStatusGame();
    }

    private void updateStatusGame() {
        if (serverInfo.status == GameStatus.WIN) {
            if (!win) {
                clientInfo.ready = false;
                status_label.setText("Статус: Игра окончена");
                win = true;   
            }
        } else {
            if (serverInfo.status == GameStatus.PLAY) {
                status_label.setText("Статус: Игра запущена");
                clientInfo.new_game = false;
                win = false;
            } else if (serverInfo.status == GameStatus.PAUSE) {
                status_label.setText("Статус: Пауза");
            }
        }
        if (!serverInfo.users.isEmpty()) {
            if (!serverInfo.users.get(clientNumber - 1).active) {
                clientInfo.ready = true;
            }
        }
    }


    @FXML
    void clickOnTable(ActionEvent event) {
        if (leadersStage != null) {
            leadersStage.close();
            leadersStage = null;
        } else {
            List<UserInDB> leaders = serverInfo.leaders;
            leadersStage = createLeadersTable(leaders);
            leadersStage.show();
        }
    }

    private Stage createLeadersTable(List<UserInDB> leaders) {
        TableView<UserInDB> table = new TableView<>();
        table.getItems().clear();

        TableColumn<UserInDB, String> nameColumn = new TableColumn<>("Player");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<UserInDB, Integer> victories_num = new TableColumn<>("Victories_num");
        victories_num.setCellValueFactory(new PropertyValueFactory<>("wins"));

        table.getColumns().add(nameColumn);
        table.getColumns().add(victories_num);

        table.getItems().addAll(leaders);

        Scene scene = new Scene(table);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Leaders");

        return stage;
    }

}
