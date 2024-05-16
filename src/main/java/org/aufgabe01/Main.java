package org.aufgabe01;

import org.graphstream.graph.BreadthFirstIterator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

public class Main {
    public static void main(String[] args) {
        System.setProperty("org.graphstream.ui", "swing");
        // Pfad zur .gka-Datei
        String filePath = "src/main/resources/graphs/graph01.gka";

        Graph graph = new SingleGraph("graph01");


        BFS.bfs(graph, "a", "b");

        // Start- und Zielknoten
        String startNodeId = "a";
        Node startNode = graph.getNode(startNodeId);

        boolean isDirected = true;

        // Graph aus der .gka-Datei lesen
        Graph graphFromFile = GraphParser.makeGraphFromFile(filePath);

        // Breitensuche anwenden
        assert graphFromFile != null;
        assert graphFromFile.getNode(startNodeId) != null;
        BreadthFirstIterator breadthFirstIterator = new BreadthFirstIterator(startNode, isDirected);

        while (breadthFirstIterator.hasNext()){
            Node node = breadthFirstIterator.next();

        }
        GraphParser.showGraph(graphFromFile);
    }



    public void newGraphTest(){
        Graph graph = new SingleGraph("Graph");
        String name = "a";
        for (int i = 0; i < 26; i++) {
            graph.addNode(name+i);
        }
        for (int i = 1; i < 26; i++) {
            String edgeName = name.concat(name+1);
            String node2 = name+1;
            graph.addEdge(edgeName, name, node2);
        }
    }
}

