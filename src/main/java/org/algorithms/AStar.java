package org.algorithms;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * <b><u>A*-Algorithmus</u></b>
 * <br><br>
 * A*-Algorithmus zur Suche eines optimalen Pfads in einem Graphen.
 * Verwendet eine heuristische Schätzung (H) der verbleibenden Kosten zum Zielknoten.
 */
public class AStar {

    /**
     * Führt den A*-Algorithmus auf dem gegebenen Graphen aus, um den optimalen Pfad von
     * einem Startknoten zu einem Zielknoten zu finden.
     *
     * @param graph        Der Graph, in dem die Suche durchgeführt werden soll.
     * @param startNodeId  Die ID des Startknotens.
     * @param goalNodeId   Die ID des Zielknotens.
     */
    public static void aStarSearch(Graph graph, String startNodeId, String goalNodeId) {
        Node startNode = graph.getNode(startNodeId);
        Node goalNode = graph.getNode(goalNodeId);

        if (startNode == null || goalNode == null) {
            System.err.println("Start- oder Zielknoten nicht im Graphen gefunden.");
            return;
        }

        // Priority Queue für die zu besuchenden Knoten (nach geringsten Kosten sortiert)
        PriorityQueue<NodeWrapper> queue = new PriorityQueue<>(Comparator.comparingDouble(NodeWrapper::getTotalCost));

        // Menge zur Verfolgung der besuchten Knoten
        Set<Node> visitedNodes = new HashSet<>();

        // Startknoten zur Queue hinzufügen (Kosten von Start zu Start sind 0)
        queue.offer(new NodeWrapper(startNode, null, 0.0, heuristicCost(startNode, goalNode)));

        while (!queue.isEmpty()) {
            // Aktuellen Knoten aus der Queue holen
            NodeWrapper currentNodeWrapper = queue.poll();
            Node currentNode = currentNodeWrapper.getNode();

            // Zielknoten erreicht
            if (currentNode == goalNode) {
                reconstructPath(currentNodeWrapper); // Pfad rekonstruieren und ausgeben
                return;
            }

            // Falls der Knoten bereits besucht wurde, überspringen
            if (!visitedNodes.contains(currentNode)) {
                visitedNodes.add(currentNode);

                // Über alle Nachbarn des aktuellen Knotens iterieren
                for (Edge edge : currentNode.leavingEdges().toList()) {                    Node neighbor = edge.getTargetNode();
                    double edgeCost = (double) edge.getAttribute("cost"); // Kosten der Kante

                    // Neue Gesamtkosten zum Nachbarknoten berechnen
                    double newCost = currentNodeWrapper.getAccumulatedCost() + edgeCost;
                    double heuristic = heuristicCost(neighbor, goalNode);
                    double totalCost = newCost + heuristic;

                    // Nachbarknoten zur Queue hinzufügen, wenn er noch nicht besucht wurde
                    if (!visitedNodes.contains(neighbor)) {
                        queue.offer(new NodeWrapper(neighbor, currentNodeWrapper, newCost, heuristic));
                    }
                }
            }
        }

        System.err.println("Kein Pfad gefunden.");
    }


    /**
     * Berechnet die heuristischen Kosten von einem Knoten zum Zielknoten.
     *
     * @param node      Der aktuelle Knoten.
     * @param goalNode  Der Zielknoten.
     * @return Die heuristischen Kosten (z.B. die geschätzten Luftlinienentfernung).
     */
    private static double heuristicCost(Node node, Node goalNode) {
        // Beispiel: Nutzung der euklidischen Distanz als Heuristik
        double dx = (double) node.getAttribute("x") - (double) goalNode.getAttribute("x");
        double dy = (double) node.getAttribute("y") - (double) goalNode.getAttribute("y");
        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * Rekonstruiert den Pfad vom Startknoten bis zum Zielknoten und gibt ihn aus.
     *
     * @param goalWrapper  Der Wrapper des Zielknotens.
     */
    private static void reconstructPath(NodeWrapper goalWrapper) {
        System.out.println("Optimaler Pfad gefunden:");
        NodeWrapper current = goalWrapper;
        while (current != null) {
            System.out.print(current.getNode().getId());
            if (current.getPrevious() != null) {
                System.out.print(" <- ");
            }
            current = current.getPrevious();
        }
        System.out.println();
    }


    /**
     * Wrapper-Klasse für Knoten, die zusätzliche Informationen für den A*-Algorithmus enthält.
     */
    private static class NodeWrapper {
        private final Node node;
        private final NodeWrapper previous;
        private final double accumulatedCost; // Gesamtkosten von Startknoten bis zum aktuellen Knoten
        private final double heuristicCost;   // Heuristik (geschätzte Kosten zum Zielknoten)

        public NodeWrapper(Node node, NodeWrapper previous, double accumulatedCost, double heuristicCost) {
            this.node = node;
            this.previous = previous;
            this.accumulatedCost = accumulatedCost;
            this.heuristicCost = heuristicCost;
        }

        public Node getNode() {
            return node;
        }

        public NodeWrapper getPrevious() {
            return previous;
        }

        public double getAccumulatedCost() {
            return accumulatedCost;
        }

        public double getHeuristicCost() {
            return heuristicCost;
        }

        public double getTotalCost() {
            return accumulatedCost + heuristicCost;
        }
    }
}
