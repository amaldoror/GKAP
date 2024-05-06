package org.aufgabe01;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BFSearchTest {

    private Graph graph;

    @BeforeEach
    void setUp() {
        // Initialisiere den Graphen für jeden Testfall
        graph = new SingleGraph("testGraph");
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addEdge("AB", "A", "B");
        graph.addEdge("BC", "B", "C");
        graph.addEdge("CA", "C", "A");
    }

    @AfterEach
    void tearDown() {
        // Räume Ressourcen nach jedem Testfall auf
        graph.clear();
    }

    @Test
    void bfSearch() {
        // Erwartete Ergebnisse definieren
        List<String> expectedVisitedNodes = Arrays.asList("A", "B", "C");

        // BFSearch auf dem Graphen ausführen
        BFSearch.bfSearch(graph, "A");

        // Besuchte Knoten aus dem Graphen abrufen
        List<String> actualVisitedNodes = new ArrayList<>();
        for (Node node : graph) {
            if (node.hasAttribute("visited")) {
                actualVisitedNodes.add(node.getId());
            }
        }

        // Überprüfen, ob die besuchten Knoten den erwarteten entsprechen
        assertEquals(expectedVisitedNodes.size(), actualVisitedNodes.size());
        assertTrue(expectedVisitedNodes.containsAll(actualVisitedNodes));
    }

}
