package com.example.javafxtutorial;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Cell extends Rectangle {
    public static final int edge = 40;
    public int x,y;
    public Ship ship = null;
    public boolean wasShot = false;
    private Board board;

    public Cell(int x,int y,Board board){
        super(40,40);
        this.x = x;
        this.y = y;
        this.board = board;
        setFill(Color.LIGHTGRAY);
        setStroke(Color.BLACK);
        setStrokeWidth(1);
    }

    public boolean shoot() {
        wasShot = true;
        setFill(Color.BLACK);

        if(ship != null) {
            ship.hit();
            setFill(Color.RED);
            if(!ship.isAlive()) {
                board.ships--;
            }
            return true;
        }

        return false;
    }

}