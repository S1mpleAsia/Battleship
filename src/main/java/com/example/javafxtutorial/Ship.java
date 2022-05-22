package com.example.javafxtutorial;

import javafx.scene.Parent;


public class Ship extends Parent {
    public int type;
    public boolean vertical = true;
    private int health;
    public String name;
    public int x;
    public int y;

    public Ship(int type,boolean vertical) {
        this.type = type;
        this.vertical = vertical;
        health = type;
    }

    public Ship(int type,boolean vertical,String name){
        this(type,vertical);
        this.name = name;
    }

    public void hit() {
        health--;
    }

    public boolean isAlive() {
        return health > 0;
    }
}
