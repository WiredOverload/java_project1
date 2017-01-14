/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import java.util.*;

/**
 * 16 * 8
 * @author Michael Hodges
 */
public class Robot {
    private int x;
    private int y;

    public Robot(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public Robot(int[] Xs) {
        Random r = new Random();
        x = (r.nextInt(64) * 16);
        y = 504 - (8 * Xs[x / 16]);
        Xs[x / 16] += 1;
        //return Xs;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    
}
