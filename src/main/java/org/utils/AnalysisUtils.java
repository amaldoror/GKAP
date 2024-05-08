package org.utils;

import org.graphstream.graph.Graph;

import java.util.Arrays;
import java.util.Random;

public class AnalysisUtils {




    /**
     * Misst die Ausführungszeit einer übergebenen Operation.
     *
     * @param operation Die Operation, deren Ausführungszeit gemessen werden soll.
     * @return Die gemessene Ausführungszeit in Millisekunden.
     */
    public static long measureExecutionTime(Runnable operation) {
        long startTime = System.currentTimeMillis();
        operation.run();
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }


    /**
     * Überprüft den Speicherverbrauch vor und nach der Ausführung einer Operation.
     *
     * @param operation Die Operation, deren Speicherverbrauch überprüft werden soll.
     */
    public static void checkMemoryUsage(Runnable operation) {
        Runtime runtime = Runtime.getRuntime();
        long memoryBefore = runtime.totalMemory() - runtime.freeMemory();
        operation.run();
        long memoryAfter = runtime.totalMemory() - runtime.freeMemory();
        long memoryUsed = memoryAfter - memoryBefore;
        System.out.println("Speicherverbrauch: " + memoryUsed + " Bytes");
    }


    // Methode zur Berechnung des Durchschnitts einer Liste von Werten
    public static double calculateAverage(long[] values) {
        long sum = 0;
        for (long value : values) {
            sum += value;
        }
        return (double) sum / values.length;
    }

    // Methode zur Berechnung des Median einer Liste von Werten
    public static double calculateMedian(long[] values) {
        Arrays.sort(values);
        int middle = values.length / 2;
        if (values.length % 2 == 0) {
            return (double) (values[middle - 1] + values[middle]) / 2;
        } else {
            return values[middle];
        }
    }

    // Methode zur Berechnung der Standardabweichung einer Liste von Werten
    public static double calculateStandardDeviation(long[] values) {
        double mean = calculateAverage(values);
        double sumSquaredDifferences = 0;
        for (long value : values) {
            sumSquaredDifferences += Math.pow(value - mean, 2);
        }
        double variance = sumSquaredDifferences / values.length;
        return Math.sqrt(variance);
    }
}
