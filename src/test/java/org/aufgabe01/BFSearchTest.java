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
        String filePath = "resources/graphs/graph01.gka";
        graph = GraphParser.makeGraphFromFile(filePath);
    }

    @AfterEach
    void tearDown() {
        // Räume Ressourcen nach jedem Testfall auf
        graph.clear();
    }

    @Test
    void bfSearch() {
        // Erwartete Ergebnisse definieren
        List<String> expectedVisitedNodes = Arrays.asList("a", "b", "b");

        // BFSearch auf dem Graphen ausführen
        BFSearch.bfSearch(graph, "a", "b");

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
