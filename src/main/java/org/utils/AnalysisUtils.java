package org.utils;

import java.util.Arrays;

public class AnalysisUtils {

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
