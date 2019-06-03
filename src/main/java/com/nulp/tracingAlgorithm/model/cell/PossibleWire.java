package com.nulp.tracingAlgorithm.model.cell;

public class PossibleWire extends Cell {

    private int indexNumber;

    public PossibleWire(int indexNumber, int x, int y) {
        super(Integer.toString(indexNumber), x, y);
    }

    public int getIndexNumber() {
        return indexNumber;
    }

    public void setIndexNumber(int indexNumber) {
        this.indexNumber = indexNumber;
    }

}
