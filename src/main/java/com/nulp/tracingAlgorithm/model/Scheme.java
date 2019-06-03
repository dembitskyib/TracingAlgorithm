package com.nulp.tracingAlgorithm.model;

import com.nulp.tracingAlgorithm.model.cell.Cell;
import com.nulp.tracingAlgorithm.model.cell.Node;
import com.nulp.tracingAlgorithm.model.cell.PossibleWire;
import com.nulp.tracingAlgorithm.model.cell.Wire;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Scheme {

    private List<Cell> cells;
    private List<Connection> connections = new ArrayList<>();
    private int xMax = 9;
    private int yMax = 11;

    public Scheme() {
        cells = fillCells();
    }

    public void updateCell(Cell updatedCell) {
        cells.removeIf(cell -> cell.getCoordinate().equals(updatedCell.getCoordinate()));
        cells.add(updatedCell);
    }

    public void connectNodes(String firstNodeName, String secondNodeName) {
        Node firstNode = (Node) cells.stream().filter(cell -> cell.getName().equals(firstNodeName)).findAny().get();
        Node secondNode = (Node) cells.stream().filter(cell -> cell.getName().equals(secondNodeName)).findAny().get();
        connections.add(new Connection(firstNode, secondNode, false));
    }

    public List<Cell> getPossibleWires(String indexNumber) {
        return cells.stream().filter(cell -> cell.getClass() == PossibleWire.class || cell.getName().equals(indexNumber)).collect(Collectors.toList());
    }

    public boolean checkCoordinate(Coordinate coordinate) {
        return cells.stream().anyMatch(cell -> cell.getCoordinate().equals(coordinate));
    }

    public boolean provideWire(Coordinate coordinate, int indexNumber) {
        if (checkCoordinate(coordinate)) {
            return getCell(coordinate).getClass() == PossibleWire.class &&
                    getCell(coordinate).getName().equals(Integer.toString(indexNumber));
        }
        return false;
    }

    public int calculateFreeSpace(Coordinate coordinate) {
        int emptySpace = 0;
        if (getCell(coordinate.moveRight()).getClass() != Wire.class &&
                getCell(coordinate.moveRight()).getClass() != Node.class) {
            emptySpace++;
        }
        if (getCell(coordinate.moveLeft()).getClass() != Wire.class &&
                getCell(coordinate.moveLeft()).getClass() != Node.class) {
            emptySpace++;
        }
        if (getCell(coordinate.moveUp()).getClass() != Wire.class &&
                getCell(coordinate.moveUp()).getClass() != Node.class) {
            emptySpace++;
        }
        if (getCell(coordinate.moveDown()).getClass() != Wire.class &&
                getCell(coordinate.moveDown()).getClass() != Node.class) {
            emptySpace++;
        }
        return emptySpace;
    }

    public void increaseSize() {
        draw();
        xMax++;
        yMax++;
        for (int i = 0; i < yMax; i++) {
            cells.add(new Cell("0", xMax - 1, i));
        }
        for (int i = 0; i < xMax; i++) {
            cells.add(new Cell("0", i, yMax - 1));
        }
        draw();
    }


    public void clearPossibleWires() {
        List<Cell> possibleCells = cells.stream().filter(cell -> cell.getClass() == PossibleWire.class).collect(Collectors.toList());
        possibleCells.forEach(cell -> updateCell(new Cell("0", cell.getCoordinate().getX(), cell.getCoordinate().getY())));
    }

    public void clearWires() {
        List<Cell> possibleCells = cells.stream().filter(cell -> cell.getClass() == Wire.class).collect(Collectors.toList());
        possibleCells.forEach(cell -> updateCell(new Cell("0", cell.getCoordinate().getX(), cell.getCoordinate().getY())));
    }

    private List<Cell> fillCells() {
        List<Cell> emptyCells = new ArrayList<>();
        for (int i = 0; i < yMax; i++) {
            for (int j = 0; j < yMax; j++) {
                emptyCells.add(new Cell("0", i, j));
            }
        }
        return emptyCells;
    }

    public void draw() {
        for (int i = yMax - 1; i >= 0; i--) {
            for (int j = 0; j < xMax; j++) {
                System.out.print(getCell(new Coordinate(j, i)).getName() + " ");
            }
            System.out.println();
        }
    }

    public List<Cell> getCells() {
        return cells;
    }

    public void setCells(List<Cell> cells) {
        this.cells = cells;
    }

    public Cell getCell(Coordinate coordinate) {
        return cells.stream().filter(cell -> cell.getCoordinate().equals(coordinate)).findAny().get();
    }

    public List<Connection> getConnections() {
        return connections;
    }

    public void setConnections(List<Connection> connections) {
        this.connections = connections;
    }

}
