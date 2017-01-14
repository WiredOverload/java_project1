/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import java.net.URL;
import java.io.File;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.*;
import java.util.ArrayList;
import javafx.scene.Scene;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.animation.AnimationTimer;
import java.util.*;

/**
 *
 * @author Michael Hodges
 */
public class JavaFXApplication1 extends Application {

    @Override
    public void start(Stage primaryStage) {

        Boots boot = new Boots(0, 0, 256, 256);
        List<Laser> lasers = new ArrayList<>();
        List<Robot> robots = new ArrayList<>();
        int[] robotXs = new int[64];
        long start = 0; //enter current system time here

        //initializa array to all zeros. There's an easier way but I forgot
        for (int i = 0; i < 64; i++) {
            robotXs[i] = 0;
        }

        //essential graphical setup
        primaryStage.setTitle("Game prototype");
        Group root = new Group();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        Canvas canvas = new Canvas(1024, 512);
        root.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        //logic to add and remove keys when keys are pressed or released
        ArrayList<String> input = new ArrayList<String>();

        scene.setOnKeyPressed(
                new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e) {
                String code = e.getCode().toString();

                // only add once... prevent duplicates
                if (!input.contains(code)) {
                    input.add(code);
                }
            }
        });

        scene.setOnKeyReleased(
                new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e) {
                String code = e.getCode().toString();
                input.remove(code);
            }
        });

        Image ship = new Image("laserShip.png");
        Image space = new Image("space1024.png");
        Image fire = new Image("laser.png");
        Image spider = new Image("spider.png");

        Media music = new Media(new File("scarab_curse.mp3").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(music);
        mediaPlayer.play();

        //try to pull as much away from this class as possible
        //possibly create a second one for a menu
        new AnimationTimer() {
            public void handle(long currentNanoTime) {

                if(boot.getStartT() == 0)
                    boot.setStartT(currentNanoTime);
                
                if (input.contains("A")) {
                    boot.setxVelocity(boot.getxVelocity() - 1);
                }
                if (input.contains("D")) {
                    boot.setxVelocity(boot.getxVelocity() + 1);
                }
                if (input.contains("W")) {
                    boot.setyVelocity(boot.getyVelocity() + 2);
                    lasers.add(new Laser(boot.getX(), boot.getY()));
                }
                if (input.contains("S")) {
                    boot.setyVelocity(boot.getyVelocity() - 1);
                }
                //reset key
                if (input.contains("R")) {
                    //boot.setX(512);
                    //boot.setY(256);
                    boot.setxVelocity(0);
                    boot.setyVelocity(0);
                }
                //mute key
                //add some sort of delay, key is down for multiple updates
                if (input.contains("M")) {
                    if (mediaPlayer.isMute()) {
                        mediaPlayer.setMute(false);
                    } else {
                        mediaPlayer.setMute(true);
                    }
                }
                /*if (input.contains("Q"))
                put closing code here*/

                //updates
                boot.update();
                robots.add(new Robot(robotXs));
                for (int i = 0; i < lasers.size(); i++) {
                    (lasers.get(i)).update();
                }

                //removing lasers that hit bottom
                for (int i = 0; i < lasers.size(); i++) {
                    if ((lasers.get(i)).getY() > 510) {
                        lasers.remove(i);
                    }
                }
                //inefficient collison detection, O(n^2)
                //need to remove constants
                //move to robot update
                //just redo the whole thing, I mean look at it
                for (int i = 0; i < robots.size(); i++) {
                    //add collision detection between laser and robot here
                    for (int j = 0; j < lasers.size(); j++) {
                        if (((robots.get(i)).getX() <= (lasers.get(j)).getX() + 14)
                                && (((robots.get(i)).getX() + 16 >= (lasers.get(j)).getX() - 14))
                                && ((((robots.get(i)).getY() - 8 <= (lasers.get(j)).getY() + 28)
                                && (((robots.get(i)).getY() >= (lasers.get(j)).getY() - 28))))) {
                            robotXs[(robots.get(i)).getX() / 16] -= 1;
                            robots.remove(i);
                        }
                    }
                }

                for (int i = 0; i < 64; i++) {
                    if (robotXs[i] >= 16) {
                        gc.setFill(Color.RED);
                        gc.setStroke(Color.BLACK);
                        gc.setLineWidth(2);
                        Font theFont = Font.font("Times New Roman", FontWeight.BOLD, 48);
                        gc.setFont(theFont);
                        gc.fillText("You survived: " + (currentNanoTime - boot.getStartT()) / 1000000000 + " seconds!", 60, 50);
                        gc.strokeText("You survived: " + (currentNanoTime - boot.getStartT()) / 1000000000 + " seconds!", 60, 50);
                        stop();
                        return;
                        //break;
                        //print current time - start time
                        //enter actual lose logic here
                        //this includes a menu
                    }
                }
                //currently unimplemented health system
                /*
                if (boot.getHealth() < 1) {
                    System.out.println("You Lose");
                    System.out.println("You survived: " + (currentNanoTime) + " seconds!");
                    //same losing logic as above
                }
                 */

                //drawing sprites to screen
                gc.drawImage(space, 0, 0);

                //try to keep constants in the object methods
                for (int i = 0; i < lasers.size(); i++) {
                    gc.drawImage(fire, (lasers.get(i)).getX() - 7, (lasers.get(i)).getY() - 14);
                }
                for (int i = 0; i < robots.size(); i++) {
                    gc.drawImage(spider, (robots.get(i)).getX(), (robots.get(i)).getY());
                }

                gc.drawImage(ship, boot.getX() - 16, boot.getY() - 18);

            }
        }.start();

        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
