package com.nulp.tracingAlgorithm.algrorithm;

import com.google.common.collect.Collections2;
import com.nulp.tracingAlgorithm.model.Connection;
import com.nulp.tracingAlgorithm.model.Coordinate;
import com.nulp.tracingAlgorithm.model.Scheme;
import com.nulp.tracingAlgorithm.model.cell.Node;
import com.nulp.tracingAlgorithm.model.cell.PossibleWire;
import com.nulp.tracingAlgorithm.model.cell.Wire;

import java.util.Collection;
import java.util.List;
import java.util.Random;

public class WaveAlgorithm {

    private Scheme scheme;
    private int currentIndex;
    private boolean matchFound;

    public WaveAlgorithm(Scheme scheme) {
        this.scheme = scheme;
        currentIndex = 1;
        matchFound = false;
    }

    public void run() {
        boolean success = false;
        scheme.increaseSize();
        scheme.increaseSize();
        scheme.increaseSize();
        while (!success) {
            Collection<List<Connection>> listConnections = Collections2.permutations(scheme.getConnections());
            for (List<Connection> currentConnections : listConnections) {
                if (currentConnections.stream().allMatch(currentConnection -> spreadWave(currentConnection.getFirstNode(), currentConnection.getSecondNode()))) {
                    System.out.println(currentConnections);
                    success = true;
                    break;
                } else {
                    softReset();
                    scheme.clearWires();
                }
            }
            if (!success) {
                scheme.increaseSize();
            }
        }
    }

    private boolean spreadWave(Node inputNode, Node outputNode) {
        System.out.println();
        System.out.println(inputNode.getName() + " " + outputNode.getName());
        checkCellsAround(inputNode.getCoordinate(), outputNode);
        currentIndex++;
        while (!matchFound) {
            if (scheme.getPossibleWires(Integer.toString(currentIndex)).stream()
                    .anyMatch(possibleWire -> checkCellsAround(possibleWire.getCoordinate(), outputNode))) {
                currentIndex++;
            } else
                return false;
        }
        softReset();
        return true;
    }

    private boolean spreadWaveToCell(Coordinate coordinate, int indexNumber, Node outputNode) {
        if (scheme.checkCoordinate(coordinate)) {
            if (scheme.getCell(coordinate).getName().equals(outputNode.getName())) {
                matchFound = true;
                tracePath(generateRandomColor(), coordinate, indexNumber);
                scheme.clearPossibleWires();
                return true;
            }
            if (!(scheme.getCell(coordinate).getClass() == Node.class ||
                    scheme.getCell(coordinate).getClass() == PossibleWire.class ||
                    scheme.getCell(coordinate).getClass() == Wire.class)) {
                scheme.updateCell(new PossibleWire(indexNumber, coordinate.getX(), coordinate.getY()));
                return true;
            }
        }
        return false;
    }

    private boolean checkCellsAround(Coordinate coordinate, Node outputNode) {
        return spreadWaveToCell(coordinate.moveDown(), currentIndex, outputNode) |
                spreadWaveToCell(coordinate.moveUp(), currentIndex, outputNode) |
                spreadWaveToCell(coordinate.moveLeft(), currentIndex, outputNode) |
                spreadWaveToCell(coordinate.moveRight(), currentIndex, outputNode);
    }

    private void tracePath(String color, Coordinate coordinate, int indexNumber) {
        for (int i = --indexNumber; i > 0; i--) {
            int downEmptySpace = 0;
            int upEmptySpace = 0;
            int leftEmptySpace = 0;
            int rightEmptySpace = 0;
            if (scheme.provideWire(coordinate.moveDown(), i)) {
                downEmptySpace = scheme.calculateFreeSpace(coordinate.moveDown());
            }
            if (scheme.provideWire(coordinate.moveUp(), i)) {
                upEmptySpace = scheme.calculateFreeSpace(coordinate.moveUp());
            }
            if (scheme.provideWire(coordinate.moveLeft(), i)) {
                leftEmptySpace = scheme.calculateFreeSpace(coordinate.moveUp());
            }
            if (scheme.provideWire(coordinate.moveRight(), i)) {
                rightEmptySpace = scheme.calculateFreeSpace(coordinate.moveUp());
            }
            int maxEmptySpace = Integer.max(Integer.max(downEmptySpace, upEmptySpace), Integer.max(leftEmptySpace, rightEmptySpace));
            System.out.println(downEmptySpace + " " + upEmptySpace + " " + leftEmptySpace + " " + rightEmptySpace);
            if (downEmptySpace == maxEmptySpace) {
                coordinate = coordinate.moveDown();
            }
            if (upEmptySpace == maxEmptySpace) {
                coordinate = coordinate.moveUp();
            }
            if (leftEmptySpace == maxEmptySpace) {
                coordinate = coordinate.moveLeft();
            }
            if (rightEmptySpace == maxEmptySpace) {
                coordinate = coordinate.moveRight();
            }
            scheme.updateCell(new Wire(color, coordinate.getX(), coordinate.getY()));
        }
    }

    private void softReset() {
        currentIndex = 1;
        matchFound = false;
        scheme.clearPossibleWires();
        System.out.println();
        scheme.draw();
    }

//    private void hardReset() {
//        currentIndex = 1;
//        matchFound = false;
//        scheme = new Scheme();
//        scheme.updateCell(new Node("E", 1, 2));
//        scheme.updateCell(new Node("H", 1, 5));
//        scheme.updateCell(new Node("B", 3, 8));
//        scheme.updateCell(new Node("A", 4, 3));
//        scheme.updateCell(new Node("F", 6, 6));
//        scheme.updateCell(new Node("C", 6, 9));
//        scheme.updateCell(new Node("D", 7, 1));
//        scheme.connectNodes("B", "A");
//        scheme.connectNodes("B", "A");
//        scheme.connectNodes("C", "A");
//        scheme.connectNodes("H", "A");
//        scheme.connectNodes("D", "E");
//        scheme.connectNodes("D", "E");
//        scheme.connectNodes("D", "B");
//        scheme.connectNodes("D", "B");
//        scheme.connectNodes("E", "F");
//        scheme.connectNodes("E", "C");
//        scheme.connectNodes("C", "F");
//        scheme.connectNodes("F", "H");
//        scheme.connectNodes("F", "H");
//    }

    private String generateRandomColor() {
        char randomChar;
        do {
            randomChar = (char) (new Random().nextInt(26) + 'a');
        }
        while (randomChar == 'a' || randomChar == 'b' || randomChar == 'c' || randomChar == 'd' || randomChar == 'e' || randomChar == 'f' || randomChar == 'h');
        return String.valueOf(randomChar);
    }

}
