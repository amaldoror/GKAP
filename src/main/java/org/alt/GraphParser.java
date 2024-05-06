package org.alt;

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Der GraphParser liest mit parseGraph() eine Textdatei ein und gibt einen entsprechenden Graphen aus.
**/
public class GraphParser {

    public Graph parseGraph(String filename) {
        Graph graph = new SingleGraph("Graph");

        try(BufferedReader br = new BufferedReader(new FileReader(filename))){
            String graphTxt;
            boolean isDirected = false;

            Pattern pattern = Pattern.compile("<(\\w+)>\\s*(?:->\\s*<(\\w+)>\\s*(?:\\((\\w+)\\))?\\s*(?::\\s*(\\d+))?)?");

            while ((graphTxt = br.readLine()) != null){

                if (graphTxt.contains("->")) {
                    isDirected = true;
                } else if (graphTxt.contains("--")) {
                    isDirected = false;
                } else {
                    // Zeilen skippen die keinen direktionalen Indikator haben
                   continue;
                }

                Matcher matcher = pattern.matcher(graphTxt);

                if (matcher.find()) {
                    String node1 = matcher.group(1);
                    String node2 = matcher.group(2);
                    String edge = matcher.group(3);
                    String weightStr = matcher.group(4);

                    int weight = (weightStr != null) ? Integer.parseInt(weightStr) : 1; // Default weight is 1 if not specified

                    System.out.print(isDirected? "gerichtet: " : "ungerichtet: ");
                    System.out.print(node1);
                    if (node2 != null) {
                        System.out.println(node2);
                        System.out.println(edge);
                        System.out.println(weight);
                    }
                }
            }



        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }


        return graph;
    }
}
