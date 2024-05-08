package org.aufgabe01;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;


/**
 * <b><u>Breadth-First-Search Algorithmus</u></b>
 * <br><br>
 * 1. <u>Startpunkt wählen:</u> Wähle einen Startknoten im Graphen aus, von dem aus die Suche beginnen soll. <br>
 * 2. <u>Initialisierung:</u> Markiere den Startknoten als besucht und füge ihn einer Warteschlange hinzu. Dies ist die Queue, in der die nächsten Knoten, die besucht werden sollen, gespeichert werden. <br>
 * 3. <u>Schleife:</u> Führe die folgenden Schritte so lange aus, bis die Warteschlange leer ist: <br>
 *    a. Nehme den Knoten aus der Warteschlange und betrachte alle seine unbesuchten Nachbarn. <br>
 *    b. Markiere jeden Nachbarn als besucht und füge ihn der Warteschlange hinzu. <br>
 * 4. <u>Wiederholung:</u> Fahre mit Schritt 3 fort, bis die Warteschlange leer ist.
 *    Zu diesem Zeitpunkt wurden alle erreichbaren Knoten besucht. <br>
 * 5. <u>Ergebnis:</u> Die Reihenfolge, in der die Knoten besucht wurden,
 *    entspricht einer Breitensuche des Graphen ab dem gewählten Startknoten. <br>
 */

public class BFSearch {

    public static void bfSearch(Graph graph, String startNodeId, String goalNodeId) {

        Node startNode = graph.getNode(startNodeId);
        Node goalNode = graph.getNode(goalNodeId);

        if (startNode == null) {
            System.err.println("Startknoten nicht im Graphen gefunden.");
            return;
        }

        // Queue für die zu besuchenden Knoten
        Queue<Node> queue = new ArrayDeque<>();

        // Markierung für besuchte Knoten
        startNode.setAttribute("visited", true);

        // Startknoten zur Queue hinzufügen
        queue.add(startNode);

        // BFS-Schleife
        while (!queue.isEmpty()) {
            // Aktuellen Knoten aus der Queue holen
            Node currentNode = queue.poll();

            // NodeID ausgeben
            getNodeID(currentNode);

            // Iterator über die ausgehenden Kanten des aktuellen Knotens erhalten
            Iterator<Edge> edgeIterator = currentNode.leavingEdges().iterator();

            // Über die Kanten iterieren, solange weitere vorhanden sind
            while (edgeIterator.hasNext()) {
                Edge edge = edgeIterator.next();
                Node neighbor = edge.getTargetNode(); // Zielknoten der Kante erhalten

                // Wenn der Nachbar noch nicht besucht wurde
                if (!neighbor.hasAttribute("visited")) {
                    // Markiere den Nachbarknoten als besucht und füge ihn zur Queue hinzu
                    neighbor.setAttribute("visited", true);
                    queue.add(neighbor);
                }
            }
        }

        // Reset der Besuchsattribute nach dem Durchlauf
        for (Node node : graph) {
            node.removeAttribute("visited");
        }
    }

    private static void getNodeID(Node node) {
        System.out.println("Besuchter Knoten: " + node.getId());
    }
}
