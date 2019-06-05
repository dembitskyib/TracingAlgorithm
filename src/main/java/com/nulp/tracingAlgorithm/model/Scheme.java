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
    private int xMax = 11;
    private int yMax = 13;

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
        connections.add(new Connection(firstNode, secondNode));
    }

    public List<Cell> getPossibleWires(String indexNumber) {
        return cells.stream().filter(cell -> cell.getClass() == PossibleWire.class && cell.getName().equals(indexNumber)).collect(Collectors.toList());
    }

    public boolean checkCoordinate(Coordinate coordinate) {
        return cells.stream().anyMatch(cell -> cell.getCoordinate().equals(coordinate));
    }

    public Coordinate provideWire(String color, Coordinate coordinate, int indexNumber) {
        Coordinate rightCoordinate = coordinate.moveRight();
        Coordinate leftCoordinate = coordinate.moveLeft();
        Coordinate upCoordinate = coordinate.moveUp();
        Coordinate downCoordinate = coordinate.moveDown();
        if (checkCoordinate(rightCoordinate) && getCell(rightCoordinate).getClass() == PossibleWire.class &&
                getCell(rightCoordinate).getName().equals(Integer.toString(indexNumber))) {
            updateCell(new Wire(color, rightCoordinate.getX(), rightCoordinate.getY()));
            return rightCoordinate;
        }
        if (checkCoordinate(leftCoordinate) && getCell(leftCoordinate).getClass() == PossibleWire.class &&
                getCell(leftCoordinate).getName().equals(Integer.toString(indexNumber))) {
            updateCell(new Wire(color, leftCoordinate.getX(), leftCoordinate.getY()));
            return leftCoordinate;
        }
        if (checkCoordinate(upCoordinate) && getCell(upCoordinate).getClass() == PossibleWire.class &&
                getCell(upCoordinate).getName().equals(Integer.toString(indexNumber))) {
            updateCell(new Wire(color, upCoordinate.getX(), upCoordinate.getY()));
            return upCoordinate;
        }
        if (checkCoordinate(downCoordinate) && getCell(downCoordinate).getClass() == PossibleWire.class &&
                getCell(downCoordinate).getName().equals(Integer.toString(indexNumber))) {
            updateCell(new Wire(color, downCoordinate.getX(), downCoordinate.getY()));
            return downCoordinate;
        }
        return null;
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
        System.out.println();
        for (int i = yMax - 1; i >= 0; i--) {
            for (int j = 0; j < xMax; j++) {
                System.out.print(getCell(new Coordinate(j, i)).getName() + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public Cell getCell(Coordinate coordinate) {
        return cells.stream().filter(cell -> cell.getCoordinate().equals(coordinate)).findAny().get();
    }

    public List<Connection> getConnections() {
        return connections;
    }

}
