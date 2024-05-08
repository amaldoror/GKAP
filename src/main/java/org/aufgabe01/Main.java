package org.aufgabe01;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

public class Main {
    public static void main(String[] args) {
        // Pfad zur .gka-Datei
        String filePath = "src/main/resources/graphs/graph01.gka";

        // Start- und Zielknoten
        String startNodeId = "a";
        String targetNodeId = "k";

        // Graph aus der .gka-Datei lesen
        Graph graph = GraphParser.makeGraphFromFile(filePath);

        // Breitensuche anwenden
        assert graph != null;
        BFS.bfSearch(graph, startNodeId, targetNodeId);

    }
}

