package com.example;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
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
    private Label score;

    @FXML
    private Label shots;

    @FXML
    private Line lin;

    @FXML
    void clickOnMainPane(MouseEvent event) {
        lin.setLayoutX(0 - lin.getStartX());
        lin.setLayoutY(event.getY());
    }


    private int score_num = 0;
    private int shots_num = 0;


    Thread t;
    Thread shot_t;
    boolean play = false;
    boolean paused = false;
    boolean shoted = false;
    
    void next() {
        double height = mainPane.getHeight();
        double y1 = circle1.getLayoutY();
        double y2 = circle2.getLayoutY();
        double y3 = circle3.getLayoutY();
        y1 += 10;
        y2 += 20;
        y3 += 30;
        if(y1 > height) {
            y1 = 0;
        }

        if(y2 > height) {
            y2 = 0;
        }

        if(y3 > height) {
            y3 = 0;
        }
        circle1.setLayoutY(y1);
        circle2.setLayoutY(y2);
        circle3.setLayoutY(y3);

    }

    boolean was_hit(Circle circ, Line line) {
        double rad = circ.getRadius();
        double x = circ.getLayoutX();
        double y = circ.getLayoutY();
        double linn = line.getEndX();
        System.out.println("Line: " + (line.getLayoutX() + line.getEndX()) + " Krug: " + x + " " + y);
        double res = ((line.getLayoutX() + line.getEndX()) - x) * ((line.getLayoutX() + line.getEndX()) - x) + (line.getLayoutY() - y) * (line.getLayoutY() - y);
        if (res <= rad * rad) {
            return true;
        }
        return false;
    }

    void move_line() {
        double widht = mainPane.getWidth();
        double x = lin.getLayoutX();
        x += 10;
        if(x > widht) {
            x = 0 - lin.getStartX();
            shoted = false;
            shot_t = null;
        }
        if(was_hit(circle1, lin)) {
            System.out.println("Circle1");
            x = 0;
            score_num += 1;
            score.setText(Integer.toString(score_num));
            shoted = false;
            shot_t = null;
        }
        if(was_hit(circle2, lin)) {
            System.out.println("Circle2");
            x = 0;
            score_num += 2;
            score.setText(Integer.toString(score_num));
            shoted = false;
            shot_t = null;
        }
        if(was_hit(circle3, lin)) {
            System.out.println("Circle3");
            x = 0;
            score_num += 4;
            score.setText(Integer.toString(score_num));
            shoted = false;
            shot_t = null;
        }
        lin.setLayoutX(x);
    }

    @FXML
    void clickOnShot(ActionEvent event) {
        if(shot_t == null) {
            shots_num += 1;
            shots.setText(Integer.toString(shots_num));
            shot_t = new Thread(
                ()->{
                    shoted = true;
                    while(shoted) {
                        Platform.runLater(this::move_line);
                        try {
                            if(paused) {
                                synchronized (this) {
                                    this.wait();
                                }
                                paused = false;
                            }
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            shoted = false;
                            shot_t = null;
                        }
                    }
                }
            );
            shot_t.start();
        }

    }

    @FXML
    void clickOnContinue(ActionEvent event) {
        synchronized (this) {
            if (t != null || shot_t != null)
                this.notifyAll();
        }
        paused = false;
    }

    @FXML
    void clickOnPause(ActionEvent event) {
        if (t != null || shot_t != null) {
            paused = true;
        }
    }

    @FXML
    void clickOnStart(ActionEvent event) {
        if (t == null) {
            score.setText(Integer.toString(score_num));
            shots.setText(Integer.toString(shots_num));
            t = new Thread(
                ()-> {
                    play = true;
                    while(play) {
                        Platform.runLater(this::next);
                        try {
                            if(paused) {
                                synchronized (this) {
                                    this.wait();
                                }
                                paused = false;
                            }
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            play = false;
                            t = null;
                        }
                    }
                }
            );
            t.start();
        }
    }

    @FXML
    void clickOnStop(ActionEvent event) {
        if (t != null) {
            t.interrupt();
        }
        if (shot_t != null) {
            shot_t.interrupt();
        }
    }
}
