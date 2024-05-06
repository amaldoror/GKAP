package org.alt;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

public class MainApp {
    public static void main(String args[]) {
        System.setProperty("org.graphstream.ui", "swing");

        Graph graph = new SingleGraph("Test01");

        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addEdge("AB", "A", "B");
        graph.addEdge("BC", "B", "C");
        graph.addEdge("CA", "C", "A");

        graph.display();
    }
}
