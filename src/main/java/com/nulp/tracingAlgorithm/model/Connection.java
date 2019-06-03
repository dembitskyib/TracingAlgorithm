package com.nulp.tracingAlgorithm.model;

import com.nulp.tracingAlgorithm.model.cell.Node;

public class Connection {

    private Node firstNode;
    private Node secondNode;
    private boolean connected;

    public Connection(Node firstNode, Node secondNode, boolean connected) {
        this.firstNode = firstNode;
        this.secondNode = secondNode;
        this.connected = connected;
    }

    public Node getFirstNode() {
        return firstNode;
    }

    public void setFirstNode(Node firstNode) {
        this.firstNode = firstNode;
    }

    public Node getSecondNode() {
        return secondNode;
    }

    public void setSecondNode(Node secondNode) {
        this.secondNode = secondNode;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    @Override
    public String toString() {
        return firstNode.getName() + " - " + secondNode.getName();
    }

}
