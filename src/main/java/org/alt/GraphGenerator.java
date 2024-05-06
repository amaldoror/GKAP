package org.alt;

import org.graphstream.graph.Graph;
//import java.util.Iterator;


public class GraphGenerator {

    public static void main(String[] args) {
        GraphGenerator generator = new GraphGenerator();
    }

    public void generateAndDisplayGraph(String filename) {
        System.setProperty("org.graphstream.ui", "javafx"); // Set UI package

        GraphParser parser = new GraphParser();
        Graph graph = parser.parseGraph(filename);
        graph.display();
    }
}
