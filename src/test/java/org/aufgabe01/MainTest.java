package org.aufgabe01;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

public class MainTest {

    public static void main(String[] args) {
        // Anzahl der Knoten und Kanten für den zufälligen Graphen
        int numNodes = 10000; // Anpassen der Größe je nach Bedarf
        int numEdges = 50000; // Anpassen der Größe je nach Bedarf

        // Erstellen des Graphen
        Graph graph = new SingleGraph("randomGraph");
        GraphParser.generateRandomGraph(graph, numNodes, numEdges);

        // Ausführung und Messung von BFS, Laufzeit und Speicherverbrauch
        long executionTime = GraphParser.measureExecutionTime(() -> {
            BFSearch.bfSearch(graph, "Node0"); // Startknoten kann angepasst werden
        });

        System.out.println("BFS wurde in " + executionTime + " Millisekunden ausgeführt.");

        System.out.println("Vor Ausführung von BFS:");
        GraphParser.checkMemoryUsage(() -> {}); // Leerlauf, um den Speicherverbrauch vor der BFS-Ausführung zu messen

        System.out.println("Nach Ausführung von BFS:");
        GraphParser.checkMemoryUsage(() -> {
            BFSearch.bfSearch(graph, "Node0"); // Startknoten kann angepasst werden
        });
    }
}

