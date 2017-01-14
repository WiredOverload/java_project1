/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

/**
 * 32 * 35, might change to 36
 * @author Michael Hodges
 */
public class Boots {

    private int xVelocity;
    private int yVelocity;
    private int x;
    private int y;
    private int health;
    private long startT;

    public Boots(int xVelocity, int yVelocity, int x, int y) {
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
        this.x = x;
        this.y = y;
        this.health = 10;
    }

    public int getxVelocity() {
        return xVelocity;
    }

    public int getyVelocity() {
        return yVelocity;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHealth() {
        return health;
    }
    
    public long getStartT() {
        return startT;
    }
    
    

    public void setxVelocity(int xVelocity) {
        this.xVelocity = xVelocity;
    }

    public void setyVelocity(int yVelocity) {
        this.yVelocity = yVelocity;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setStartT(long start) {
        this.startT = start;
    }
    
    public void update() {
        if (x > 1022) {
            xVelocity = -10;
        } else if (x < 2) {
            xVelocity = 10;
        }
        if (y > 510) {
            yVelocity = 10;
        } else if (y < 2) {
            yVelocity = -10;
        }

        x += xVelocity;
        y -= yVelocity;
        yVelocity -= 1;
    }

}
