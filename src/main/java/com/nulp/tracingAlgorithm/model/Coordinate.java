package com.nulp.tracingAlgorithm.model;

import java.util.Objects;

public class Coordinate {

    private int x;
    private int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinate moveLeft() {
        return new Coordinate(x - 1, y);
    }

    public Coordinate moveRight() {
        return new Coordinate(x + 1, y);
    }

    public Coordinate moveUp() {
        return new Coordinate(x, y + 1);
    }

    public Coordinate moveDown() {
        return new Coordinate(x, y - 1);
    }

    public int getX() {
        return x;
    }


    public int getY() {
        return y;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return x == that.x &&
                y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

}
