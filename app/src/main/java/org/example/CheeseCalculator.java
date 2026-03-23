package org.example;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CheeseCalculator {

    private static final double ORGANIC_MOISTURE_THRESHOLD = 41.0;

    /**
     * Runs all three calculations and returns the combined results.
     *
     * @param cheeses the full dataset; rows with missing fields are
     *                silently skipped per-calculation as documented below
     * @return a {@link CheeseAnalysis} containing all results
     */
    public CheeseAnalysis calculate(List<Cheese> cheeses) {

        // Calculation 1 — counts cheeses by milk treatment type
        // Rows where MilkTreatmentTypeEn is null are omitted
        int pasteurizedCount = 0;
        int rawMilkCount     = 0;

        // Calculation 2 — counts organic cheeses with moisture > 41%
        // Rows where Organic or MoisturePercent is null are omitted
        int organicHighMoistureCount = 0;

        // Calculation 3 — tallies cheeses by animal milk type
        // Rows where MilkTypeEn is null are omitted
        Map<String, Integer> milkTypeCounts = new LinkedHashMap<>();

        for (Cheese cheese : cheeses) {

            // --- Calculation 1 ---
            if (cheese.isPasteurized()) {
                pasteurizedCount++;
            } else if (cheese.isRawMilk()) {
                rawMilkCount++;
            }

            // --- Calculation 2 ---
            if (cheese.isOrganicWithHighMoisture(ORGANIC_MOISTURE_THRESHOLD)) {
                organicHighMoistureCount++;
            }

            // --- Calculation 3 ---
            if (cheese.hasMilkType()) {
                milkTypeCounts.merge(cheese.getMilkType(), 1, Integer::sum);
            }
        }

        // Sort milk type map by count descending for convenient reporting
        Map<String, Integer> sortedMilkTypeCounts = sortByValueDescending(milkTypeCounts);

        // Find the single most common milk type
        String mostCommonMilkType  = null;
        int    mostCommonMilkCount = 0;
        for (Map.Entry<String, Integer> entry : sortedMilkTypeCounts.entrySet()) {
            if (entry.getValue() > mostCommonMilkCount) {
                mostCommonMilkCount = entry.getValue();
                mostCommonMilkType  = entry.getKey();
            }
        }

        return new CheeseAnalysis(
                pasteurizedCount,
                rawMilkCount,
                organicHighMoistureCount,
                sortedMilkTypeCounts,
                mostCommonMilkType,
                mostCommonMilkCount
        );
    }

    //private helpers
    private Map<String, Integer> sortByValueDescending(Map<String, Integer> input) {
        Map<String, Integer> sorted = new LinkedHashMap<>();
        input.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEach(e -> sorted.put(e.getKey(), e.getValue()));
        return sorted;
    }
}
