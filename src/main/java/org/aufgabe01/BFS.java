package org.aufgabe01;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.*;

/**
 * <b><u>Breadth-First Search (BFS)</u></b>
 * <br><br>
 * Diese Klasse implementiert den Breadth-First Search Algorithmus zur Suche eines Pfads
 * von einem Startknoten zu einem Zielknoten in einem Graphen.
 */
public class BFS {

    /**
     * Führt den BFS-Algorithmus auf dem gegebenen Graphen aus, um einen Pfad von
     * einem Startknoten zu einem Zielknoten zu finden.
     *
     * @param graph        Der Graph, in dem die Suche durchgeführt werden soll.
     * @param startNodeID  Die ID des Startknotens.
     * @param targetNodeID   Die ID des Zielknotens.
     */
    public static void bfSearch(Graph graph, String startNodeID, String targetNodeID) {
        Node startNode = graph.getNode(startNodeID);
        Node targetNode = graph.getNode(targetNodeID);

        if (startNode == null || targetNode == null) {
            System.err.println("Start- oder Zielknoten nicht im Graphen gefunden.");
            return;
        }

        // Queue für die zu besuchenden Knoten
        Queue<Node> queue = new ArrayDeque<>();
        // Menge zur Verfolgung der besuchten Knoten
        Set<Node> visitedNodes = new HashSet<>();
        // Menge zur Verfolgung des Pfades
        Set<Node> path = new HashSet<>();
        // Mapping der Knoten zum Vorgängerknoten
        Map<Node, Node> predecessorMap = new HashMap<>();

        queue.offer(startNode);
        visitedNodes.add(startNode);

        // BFS durchführen
        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();

            // Zielknoten erreicht
            if (currentNode == targetNode) {
                reconstructPath(predecessorMap, startNode, targetNode);
                return;
            }

            // Über alle Nachbarn des aktuellen Knotens iterieren
            for (Node neighbor : currentNode.neighborNodes().toList()) {
                if (!visitedNodes.contains(neighbor)) {
                    queue.offer(neighbor);
                    visitedNodes.add(neighbor);
                    predecessorMap.put(neighbor, currentNode);
                }
            }
        }


        if (targetNode != null && targetNode.hasAttribute("visited")) {
            System.out.println("Der kürzeste Weg von " + startNodeID + " zu " + targetNodeID + " wurde gefunden.");
        } else {
            System.out.println("Es wurde kein Weg von " + startNodeID + " zu " + targetNodeID + " gefunden.");
        }
    }

    /**
     * Rekonstruiert den Pfad vom Startknoten bis zum Zielknoten und gibt ihn aus.
     *
     * @param predecessorMap  Eine Map, die jeden Knoten mit seinem Vorgängerknoten verbindet.
     * @param startNode       Der Startknoten.
     * @param goalNode        Der Zielknoten.
     */
    private static void reconstructPath(Map<Node, Node> predecessorMap, Node startNode, Node goalNode) {
        System.out.println("Pfad gefunden:");
        Node currentNode = goalNode;
        while (currentNode != startNode) {
            System.out.print(currentNode.getId());
            Node predecessor = predecessorMap.get(currentNode);
            if (predecessor != null) {
                System.out.print(" <- ");
                currentNode = predecessor;
            } else {
                System.err.println("Fehler: Konnte Pfad nicht rekonstruieren.");
                return;
            }
        }
        System.out.print(startNode.getId());
        System.out.println();
    }
}
