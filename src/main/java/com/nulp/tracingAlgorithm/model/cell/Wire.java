package com.nulp.tracingAlgorithm.model.cell;

import java.util.Random;

public class Wire extends Cell {

    private String color;

    public Wire(String color, int x, int y) {
        super(color, x, y);
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
