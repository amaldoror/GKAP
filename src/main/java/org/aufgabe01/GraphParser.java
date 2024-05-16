package org.aufgabe01;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.IdAlreadyInUseException;
import org.graphstream.graph.implementations.SingleGraph;

import java.io.*;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class zum Parsen und Speichern von Graphen.
 */
public class GraphParser {

    String nodeA, nodeB, direction;
    boolean isDirected = false;
    boolean isWeighted = false;


    /**
     * Speichert den Graphen in einer .gka-Datei.
     *
     * @param graph    Zu speichender Graph.
     * @param filePath Der Dateipfad zum Speichern des Graphen.
     * @return True, wenn der Graph erfolgreich gespeichert wurde, sonst false.
     */
    public static boolean saveGraphToFile(Graph graph, String filePath) {
        Edge edge;

        // Überprüfe, ob die Dateiendung .gka ist, falls nicht, füge sie hinzu
        if (!filePath.endsWith(".gka")) {
            System.out.println("Dateiendung angefügt");
            filePath += ".gka";
        }

        // Überprüfe, ob die Datei bereits existiert
        File file = new File(filePath);
        if (file.exists()) {
            System.err.printf("Datei %s existiert schon \n", filePath);
            return false;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Schreibe die Kanteninformationen in die Datei
            for (int i=0; i< graph.getEdgeCount(); i++) {
                edge = graph.getEdge(i);

                String nodeStart = edge.getNode0().getId();
                String nodeEnd = edge.getNode1().getId();
                boolean isDirected = edge.isDirected();
                Object weight = null;
                boolean isWeighted = false;

                // Überprüfe, ob die Kante gewichtet ist
                if (edge.hasAttribute("weight")) {
                    isWeighted = true;
                    weight = edge.getAttribute("weight");
                }

                // Erstelle den Text für die Kante und schreibe ihn in die Datei
                String graphText = nodeStart + (isDirected ? " -> " : " -- ") + nodeEnd + (isWeighted ? " : " + weight : "") + ";";
                System.out.println(graphText);
                writer.append(graphText).append("\n");
            }
            System.out.println("Datei wurde erfolgreich geschrieben.");
        } catch (IOException e) {
            System.err.println("Fehler beim Schreiben der Datei: " + e.getMessage());
        }
        return true;
    }

    /**
     * Erstellt einen Graphen aus einer .gka-Datei.
     *
     * @param filePath Der Dateipfad der .gka-Datei.
     * @return Der Graph der erstellt wurde.
     */
    public static Graph makeGraphFromFile(String filePath) {
        Graph graph = readGkaFile(filePath);
        visualizeGraph(graph);
        return graph;
    }


    private static Graph readGkaFile(String filePath) {
        int count = 0;
        int weight;
        String nodeA, nodeB, direction;
        boolean isDirected = false;
        boolean isWeighted = false;

        // Überprüfe, ob die Dateiendung .gka ist, falls nicht, gib einen Fehler aus
        if (!filePath.endsWith(".gka")) {
            System.err.printf("ERROR: %s is not a .gka file\n", filePath);
            return null;
        }

        // Erstelle einen Graphen mit eindeutiger ID
        String graphID = "graph" + count++;
        Graph graph = new SingleGraph(graphID);

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String graphText;
            while ((graphText = reader.readLine()) != null) {
                count++;

                // Regular Expression für das Parsen der Kanteninformationen
                String rgx = "(.*)\\s*(->|--)\\s*(.*?):?\\s*(\\d*)?\\s*;";
                Pattern pattern = Pattern.compile(rgx);
                Matcher matcher = pattern.matcher(graphText);

                if (matcher.find()) {
                    // Extrahiere die Informationen über die Kante
                    nodeA = matcher.group(1).trim();
                    nodeB = matcher.group(3).trim();
                    direction = matcher.group(2).trim();

                    // Überprüfe, ob die Kante gewichtet ist
                    if (!matcher.group(4).isEmpty()) {
                        weight = Integer.parseInt(matcher.group(4));
                    } else {
                        isWeighted = false;
                        weight = 0;
                    }
                    isDirected = direction.equals("->");

                    // Füge die Knoten zum Graphen hinzu, falls sie nicht bereits existieren
                    addNode(graph, nodeA);
                    addNode(graph, nodeB);

                    // Füge die Kante zum Graphen hinzu
                    addEdge(graph, nodeA, nodeB, isDirected, isWeighted, weight);
                } else {
                    System.err.println("Fehler Z" + count + ": ' " + graphText + " '");
                }
            }

            // Gebe eine Erfolgsmeldung aus
            System.out.println("SUCCESS! \n" +
                    "GraphID: "     + graphID + "\n" +
                    "is weighted: " + isWeighted + "\n" +
                    "is directed: " + isDirected);


            graph.display();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return graph;
    }


    public static void visualizeGraph(Graph graph){
        if (System.getProperties().containsKey("org.graphstream.ui")) {
            graph.display();
        }
    }


    /**
     * Fügt einen Knoten zum Graphen hinzu, wenn der Knoten noch nicht existiert.
     *
     * @param graph  Der Graph, dem ein Knoten hinzugefügt wird.
     * @param nodeId Die ID des hinzugefügten Knotens.
     */
    static void addNode(Graph graph, String nodeId) {
        if (graph.getNode(nodeId) == null) {
            graph.addNode(nodeId);
            graph.getNode(nodeId).setAttribute("ui.label", nodeId);
            graph.getNode(nodeId).setAttribute("ui.style",
                    "text-size: 15; " +
                            "text-color: red; " +
                            "text-alignment: at-left; ");
        }
    }

    /**
     * Fügt eine Kante zum Graphen hinzu.
     *
     * @param graph      Der Graph, dem eine Kante hinzugefügt wird.
     * @param nodeA      Die ID des ersten Knotens.
     * @param nodeB      Die ID des zweiten Knotens.
     * @param isDirected Ist der Graph gerichtet?
     * @param isWeighted Ist der Graph gewichtet?
     * @param weight     Gewicht der Kante (falls gewichtet).
     */
    static void addEdge(Graph graph, String nodeA, String nodeB, boolean isDirected, boolean isWeighted, int weight) {
        String edgeId = nodeA + nodeB;

        // Überprüfe, ob die Kante noch nicht existiert und füge sie hinzu.
        if (graph.getEdge(edgeId) == null) {
            Edge edge = graph.addEdge(edgeId, nodeA, nodeB, isDirected);


            // Füge das Gewicht zur Kante hinzu, falls sie gewichtet ist
            if (isWeighted) {
                graph.getEdge(edge.getId()).setAttribute("weight", weight);
            }
        } else {
            System.err.println("Edge " + edgeId + " already exists");
        }
    }

    /**
     * Generiert einen zufälligen Graphen mit einer gegebenen Anzahl von Knoten und Kanten.<br>
     * Per Default ist der Graph ungerichtet und ungewichtet.
     *
     * @param graph     Der Graph, der aktualisiert werden soll.
     * @param numNodes  Die Anzahl der Knoten im Graphen.
     * @param numEdges  Die Anzahl der Kanten im Graphen.
     * @param isDirected Ist der Graph gerichtet? Default ist false
     * @param isWeighted Ist der Graph gewichtet? Default ist false
     */
    public static Graph generateRandomGraph(Graph graph, int numNodes, int numEdges, boolean isDirected, boolean isWeighted){
        return _generateRandomGraph(graph, numNodes, numEdges, isDirected, isWeighted);
    }

    /**
     * Generiert einen zufälligen Graphen mit einer gegebenen Anzahl von Knoten und Kanten.<br>
     * Per Default ist der Graph ungerichtet und ungewichtet.
     *
     * @param graph     Der Graph, der aktualisiert werden soll.
     * @param numNodes  Die Anzahl der Knoten im Graphen.
     * @param numEdges  Die Anzahl der Kanten im Graphen.
     */
    public static Graph generateRandomGraph(Graph graph, int numNodes, int numEdges){
        return _generateRandomGraph(graph, numNodes, numEdges, false, false);
    }

    /**
     * Generiert einen zufälligen Graphen mit einer gegebenen Anzahl von Knoten und Kanten.<br>
     * Per Default ist der Graph ungerichtet und ungewichtet.<br>
     *
     * @param graph     Der Graph, der aktualisiert werden soll.
     */
    public static Graph generateRandomGraph(Graph graph){
        Random rand = new Random();
        return _generateRandomGraph(graph, rand.nextInt(), rand.nextInt(), false, false);
    }

    public static Graph generateRandomGraph(){
        Random rand = new Random();
        Graph graph = new SingleGraph("randomGraph");
        return _generateRandomGraph(graph, rand.nextInt(), rand.nextInt(), false, false);
    }

    // Private helper method
    private static Graph _generateRandomGraph(Graph graph, int numNodes, int numEdges, boolean isDirected, boolean isWeighted) {
        Random random = new Random();
        graph.getAttribute("");
        String direction = isDirected ? " -> " : " -- ";


        // Knoten hinzufügen
        for (int i = 0; i < numNodes; i++) {
            String nodeName = "Node" + i;
            GraphParser.addNode(graph, nodeName);
        }

        // Kanten hinzufügen
        for (int i = 0; i < numEdges; i++) {
            String nodeA = "Node" + random.nextInt(numNodes);
            String nodeB = "Node" + random.nextInt(numNodes);
            String edge = nodeA + nodeB;

            //String weight = isWeighted ? (" : " + random.nextInt() + ";") : "";
            int weight = random.nextInt();
            GraphParser.addEdge(graph, nodeA, nodeB, isDirected, isWeighted, weight);
            graph.getEdge(edge).setAttribute("weight", weight);
        }
        return graph;
    }


    public static Graph generateGraph(){
        return new SingleGraph("Graph");
    }

    public static Graph generateGraph(String filename){
        return new SingleGraph(filename);
    }

    public static Graph generateGraph(String filename, boolean isDirected, boolean isWeighted, int numNodes, int numEdges){
        return new SingleGraph(filename, isDirected, isWeighted, numNodes,numEdges);
    }

    public static Graph generateGraphFromFile(String filename, boolean isDirected, boolean isWeighted, int numNodes, int numEdges){
        return null;
    }

    public static void showGraph(Graph graph) {
        graph.display();
    }
}
