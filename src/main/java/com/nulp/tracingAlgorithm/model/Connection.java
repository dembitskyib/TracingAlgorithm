package com.nulp.tracingAlgorithm.model;

import com.nulp.tracingAlgorithm.model.cell.Node;

public class Connection {

    private Node firstNode;
    private Node secondNode;

    Connection(Node firstNode, Node secondNode) {
        this.firstNode = firstNode;
        this.secondNode = secondNode;
    }

    public Node getFirstNode() {
        return firstNode;
    }


    public Node getSecondNode() {
        return secondNode;
    }


    @Override
    public String toString() {
        return firstNode.getName() + " - " + secondNode.getName();
    }

}
