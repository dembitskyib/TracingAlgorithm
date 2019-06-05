package com.nulp.tracingAlgorithm.model.cell;

import com.nulp.tracingAlgorithm.model.Coordinate;

public class Cell {

    private Coordinate coordinate;
    private String name;

    public Cell(String name, int x, int y) {
        this.name = name;
        coordinate = new Coordinate(x, y);
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }


    public String getName() {
        return name;
    }

}
