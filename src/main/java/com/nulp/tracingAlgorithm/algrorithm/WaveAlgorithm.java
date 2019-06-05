package com.nulp.tracingAlgorithm.algrorithm;

import com.nulp.tracingAlgorithm.model.Connection;
import com.nulp.tracingAlgorithm.model.Coordinate;
import com.nulp.tracingAlgorithm.model.Scheme;
import com.nulp.tracingAlgorithm.model.cell.Node;
import com.nulp.tracingAlgorithm.model.cell.PossibleWire;
import com.nulp.tracingAlgorithm.model.cell.Wire;

import java.util.*;

public class WaveAlgorithm {

    private Scheme scheme;
    private int currentIndex;
    private boolean matchFound;
    private boolean deadEnd;
    private List<Character> usedWireColors;

    public WaveAlgorithm(Scheme scheme) {
        this.scheme = scheme;
        currentIndex = 0;
        matchFound = false;
        usedWireColors = new ArrayList<>();
    }

    public void run() {
        List<Connection> currentWires = new ArrayList<>(scheme.getConnections());
        for (int i = 0; i < 1000; i++) {
            Collections.shuffle(currentWires);
            scheme.getConnections().forEach(currentConnection -> spreadWave(currentConnection.getFirstNode(), currentConnection.getSecondNode()));
            if (!deadEnd) {
                System.out.println("Success!!!!!!");
                break;
            }
            scheme.clearWires();
            softReset();
        }
    }

    private void spreadWave(Node inputNode, Node outputNode) {
        if (deadEnd) {
            return;
        }
        checkCellsAround(inputNode.getCoordinate(), outputNode);
        do {
            scheme.getPossibleWires(Integer.toString(currentIndex))
                    .forEach(possibleWire -> checkCellsAround(possibleWire.getCoordinate(), outputNode));
            currentIndex++;
            if (scheme.getPossibleWires(Integer.toString(currentIndex)).isEmpty() && !matchFound) {
                deadEnd = true;
                return;
            }
        } while (!matchFound);
        softReset();
    }

    private void spreadWaveToCell(Coordinate coordinate, int indexNumber, Node outputNode) {
        if (scheme.checkCoordinate(coordinate) && !matchFound) {
            if (scheme.getCell(coordinate).getName().equals(outputNode.getName())) {
                matchFound = true;
                tracePath(generateRandomColor(), coordinate, indexNumber);
                scheme.clearPossibleWires();
                scheme.draw();
                return;
            }
            if (!(scheme.getCell(coordinate).getClass() == Node.class ||
                    scheme.getCell(coordinate).getClass() == PossibleWire.class ||
                    scheme.getCell(coordinate).getClass() == Wire.class)) {
                scheme.updateCell(new PossibleWire(indexNumber, coordinate.getX(), coordinate.getY()));
            }
        }
    }

    private void checkCellsAround(Coordinate coordinate, Node outputNode) {
        spreadWaveToCell(coordinate.moveDown(), currentIndex + 1, outputNode);
        spreadWaveToCell(coordinate.moveUp(), currentIndex + 1, outputNode);
        spreadWaveToCell(coordinate.moveLeft(), currentIndex + 1, outputNode);
        spreadWaveToCell(coordinate.moveRight(), currentIndex + 1, outputNode);
    }

    private void tracePath(String color, Coordinate coordinate, int indexNumber) {
        for (int i = --indexNumber; i > 0; i--) {
            coordinate = scheme.provideWire(color, coordinate, i);
        }
    }

    private void softReset() {
        currentIndex = 0;
        matchFound = false;
        deadEnd = false;
        scheme.clearPossibleWires();
    }

    private String generateRandomColor() {
        char randomChar;
        do {
            randomChar = (char) (new Random().nextInt(26) + 'a');
        }
        while (usedWireColors.contains(randomChar));
        usedWireColors.add(randomChar);
        return String.valueOf(randomChar);
    }

}
