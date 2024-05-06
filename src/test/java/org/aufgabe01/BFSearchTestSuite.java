package org.aufgabe01;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

public class BFSearchTestSuite {

    public static void main(String[] args) {
        // Testfall: Leistungstest
        performanceTest();

        // Testfall: Speichertest
        memoryTest();

        // Testfall: Validierungstest
        validationTest();

        // Testfall: Randtest
        edgeCaseTest();

        // Testfall: Stresstest
        stressTest();
    }

    // Testfall: Leistungstest
    private static void performanceTest() {
        System.out.println("Leistungstest:");

        for (int i = 1; i <= 5; i++) {
            int numNodes = i * 1000;
            int numEdges = i * 5000;
            Graph graph = new SingleGraph("randomGraph");
            GraphParser.generateRandomGraph(graph, numNodes, numEdges);
            long executionTime = GraphParser.measureExecutionTime(() -> {
                BFSearch.bfSearch(graph, "Node0"); // Startknoten kann angepasst werden
            });
            System.out.println("Für " + numNodes + " Knoten und " + numEdges + " Kanten: " + executionTime + " Millisekunden.");
        }
    }

    // Testfall: Speichertest
    private static void memoryTest() {
        int numNodes = 10000;
        int numEdges = 50000;

        System.out.println("Speichertest:");
        Graph graph = new SingleGraph("randomGraph");
        GraphParser.generateRandomGraph(graph, numNodes, numEdges);
        System.out.println("Vor Ausführung von BFS:");
        GraphParser.checkMemoryUsage(() -> {});
        System.out.println("Nach Ausführung von BFS:");
        GraphParser.checkMemoryUsage(() -> {
            BFSearch.bfSearch(graph, "Node0"); // Startknoten kann angepasst werden
        });
    }

    // Testfall: Validierungstest
    private static void validationTest() {
        System.out.println("Validierungstest:");

        // Erstelle einen einfachen gerichteten oder ungerichteten Graphen
        Graph graph = new SingleGraph("validationGraph");
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addEdge("AB", "A", "B");
        graph.addEdge("BC", "B", "C");
        graph.addEdge("CA", "C", "A");

        // Führe den BFS-Algorithmus aus
        BFSearch.bfSearch(graph, "A"); // Startknoten kann angepasst werden

        // Überprüfe die Ausgabe oder andere erwartete Ergebnisse
        // Hier könntest du zum Beispiel überprüfen, ob die besuchten Knoten die erwarteten sind
    }


    private static void edgeCaseTest() {
        System.out.println("Randtest:");

        // Graph mit einem einzigen Knoten
        Graph singleNodeGraph = new SingleGraph("singleNodeGraph");
        singleNodeGraph.addNode("A");

        // Graph ohne Kanten
        Graph noEdgeGraph = new SingleGraph("noEdgeGraph");
        noEdgeGraph.addNode("A");
        noEdgeGraph.addNode("B");

        // Führe den BFS-Algorithmus aus und überprüfe die Ergebnisse
        BFSearch.bfSearch(singleNodeGraph, "A");
        BFSearch.bfSearch(noEdgeGraph, "A");
    }


    // Testfall: Stresstest
    private static void stressTest() {
        System.out.println("Stresstest:");

        // Erstelle einen extrem großen Graphen
        int numNodes = 1000000; // Anzahl der Knoten anpassen
        int numEdges = 10000000; // Anzahl der Kanten anpassen
        Graph largeGraph = new SingleGraph("largeGraph");
        GraphParser.generateRandomGraph(largeGraph, numNodes, numEdges);

        // Führe den BFS-Algorithmus aus und messe die Ausführungszeit
        long startTime = System.currentTimeMillis();
        BFSearch.bfSearch(largeGraph, "Node0"); // Startknoten kann angepasst werden
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        System.out.println("Ausführungszeit für extrem großen Graphen: " + executionTime + " Millisekunden.");
    }

}
