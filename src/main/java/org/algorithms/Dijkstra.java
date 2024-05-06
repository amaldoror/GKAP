package org.algorithms;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * <b><u>Dijkstra-Algorithmus</u></b>
 * <br><br>
 * 1. <u>Initialisierung:</u> Setze die Kosten für alle Knoten auf unendlich und setze die Kosten für den Startknoten auf 0. <br>
 * 2. <u>Offene Liste:</u> Füge den Startknoten der offenen Liste hinzu. <br>
 * 3. <u>Loop:</u> Solange die offene Liste nicht leer ist: <br>
 *    a. Wähle den Knoten mit den geringsten Kosten aus der offenen Liste. <br>
 *    b. Betrachte alle Nachbarn des ausgewählten Knotens: <br>
 *       - Berechne die Kosten für jeden Nachbarn (Kosten bis zum ausgewählten Knoten + Kantenkosten). <br>
 *       - Wenn die berechneten Kosten geringer sind als die bisherigen, aktualisiere die Kosten. <br>
 *       - Füge den Nachbarn zur offenen Liste hinzu, wenn er noch nicht darin enthalten ist. <br>
 *    c. Markiere den ausgewählten Knoten als besucht und entferne ihn aus der offenen Liste. <br>
 * 4. <u>Ergebnis:</u> Die kürzesten Wege von Startknoten zu allen anderen Knoten wurden gefunden. <br>
 */

public class Dijkstra {

    public static void dijkstra(Graph graph, String startNodeId) {
        Node startNode = graph.getNode(startNodeId);

        if (startNode == null) {
            System.err.println("Startknoten nicht im Graphen gefunden.");
            return;
        }

        // PriorityQueue zur Verwaltung der Knoten nach ihren Kosten
        PriorityQueue<Node> openList = new PriorityQueue<>(Comparator.comparingDouble(node -> (double) node.getAttribute("distance")));

        // Menge zur Verwaltung der besuchten Knoten
        Set<Node> visited = new HashSet<>();

        // Setze die Kosten für alle Knoten auf unendlich und für den Startknoten auf 0
        for (Node node : graph) {
            node.setAttribute("distance", Double.POSITIVE_INFINITY);
        }
        startNode.setAttribute("distance", 0.0);

        // Füge den Startknoten zur offenen Liste hinzu
        openList.add(startNode);

        while (!openList.isEmpty()) {
            // Wähle den Knoten mit den geringsten Kosten aus der offenen Liste
            Node currentNode = openList.poll();

            // Betrachte alle Nachbarn des ausgewählten Knotens
            for (org.graphstream.graph.Edge edge : currentNode.leavingEdges().toList()) {
                Node neighbor = edge.getTargetNode();

                // Berechne die Kosten für jeden Nachbarn
                double tentativeDistance = (double) currentNode.getAttribute("distance") + edge.getNumber("weight");

                // Wenn die berechneten Kosten geringer sind als die bisherigen, aktualisiere die Kosten
                if (tentativeDistance < (double) neighbor.getAttribute("distance")) {
                    neighbor.setAttribute("distance", tentativeDistance);

                    if (!visited.contains(neighbor)) {
                        openList.add(neighbor);
                    }
                }
            }

            // Markiere den ausgewählten Knoten als besucht
            visited.add(currentNode);
        }
    }
}
