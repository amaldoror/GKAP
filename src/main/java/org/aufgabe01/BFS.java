package org.aufgabe01;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.*;

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
public class BFS {

    public static void main(String[] args) {
        Graph g = setupGraph();
        g.display();
        bfs(g, "Node0", "Node1");
    }

    public static Graph setupGraph(){
        System.setProperty("org.graphstream.ui", "swing");
        int numNodes = 10;
        int numEdges = 10;
        Graph graph = new SingleGraph("ExampleGraph");
        return GraphParser.generateRandomGraph(graph, numNodes, numEdges);
    }

    /**
     * Führt den BFS-Algorithmus auf dem gegebenen Graphen aus, um einen Pfad von
     * einem Startknoten zu einem Zielknoten zu finden.
     *
     * @param graph        Der Graph, in dem die Suche durchgeführt werden soll.
     * @param startNodeId  Die ID des Startknotens.
     * @param targetNodeId Die ID des Zielknotens.
     */
    public static void bfs(Graph graph, String startNodeId, String targetNodeId) {

        Node startNode = graph.getNode(startNodeId);
        Node targetNode = graph.getNode(targetNodeId);

        if (startNode == null || targetNode == null) {
            System.err.println("Start- oder Zielknoten nicht im Graphen gefunden.");
            return;
        }

        // Queue für die zu besuchenden Knoten
        Queue<Node> queue = new LinkedList<>();
        Map<String, String> predecessorMap = new HashMap<>();
        List<String> path = new ArrayList<>();

        // Markierung für besuchte Knoten
        startNode.setAttribute("visited", true);
        queue.offer(startNode);
        predecessorMap.put(startNode.getId(), null);


        // BFS-Schleife
        while (!queue.isEmpty()) {
            // Aktuellen Knoten aus der Queue holen
            Node currentNode = queue.poll();
            System.out.println(currentNode.getId());

            if (currentNode.getId().equals(targetNodeId)) {
                // Rekonstruiere den Pfad und gib ihn zurück
                String predecessor = predecessorMap.get(targetNodeId);
                path.add(0, targetNodeId);
                while (predecessor != null) {
                    path.add(0, predecessor);
                    predecessor = predecessorMap.get(predecessor);
                }
                System.out.println("Pfad vom Start- zum Zielknoten: " + path);
                return;
            }

            for (Edge edge : currentNode.leavingEdges().toList()) {
                Node neighbor = edge.getTargetNode();
                if (!neighbor.hasAttribute("visited")) {
                    neighbor.setAttribute("visited", true);
                    queue.offer(neighbor);
                    predecessorMap.put(neighbor.getId(), currentNode.getId());
                }
            }
        }

        // Reset der Besuchsattribute nach dem Durchlauf
        for (Node node : graph) {
            node.removeAttribute("visited");
        }
    }
}
