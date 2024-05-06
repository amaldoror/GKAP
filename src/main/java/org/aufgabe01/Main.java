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


        // Überprüfen, ob der Graph erfolgreich erstellt wurde
        if (graph != null) {

            graph.display();
            // Breitensuche anwenden
            BFSearch.bfSearch(graph, startNodeId);

            // Überprüfen, ob der Zielknoten erreicht wurde
            Node targetNode = graph.getNode(targetNodeId);
            if (targetNode != null && targetNode.hasAttribute("visited")) {
                System.out.println("Der kürzeste Weg von " + startNodeId + " zu " + targetNodeId + " wurde gefunden.");
            } else {
                System.out.println("Es wurde kein Weg von " + startNodeId + " zu " + targetNodeId + " gefunden.");
            }
        } else {
            System.out.println("Fehler beim Lesen des Graphen aus der Datei: " + filePath);
        }
    }
}

