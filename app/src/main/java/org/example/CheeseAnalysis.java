package org.example;

import java.util.Map;

public class CheeseAnalysis {

    // Calculation 1
    private final int pasteurizedCount;
    private final int rawMilkCount;

    // Calculation 2
    private final int organicHighMoistureCount;

    // Calculation 3
    private final Map<String, Integer> milkTypeCounts; // all types → count, sorted descending
    private final String mostCommonMilkType;
    private final int mostCommonMilkTypeCount;

    public CheeseAnalysis(int pasteurizedCount,
                          int rawMilkCount,
                          int organicHighMoistureCount,
                          Map<String, Integer> milkTypeCounts,
                          String mostCommonMilkType,
                          int mostCommonMilkTypeCount) {
        this.pasteurizedCount           = pasteurizedCount;
        this.rawMilkCount               = rawMilkCount;
        this.organicHighMoistureCount   = organicHighMoistureCount;
        this.milkTypeCounts             = milkTypeCounts;
        this.mostCommonMilkType         = mostCommonMilkType;
        this.mostCommonMilkTypeCount    = mostCommonMilkTypeCount;
    }

    public int getPasteurizedCount() { return pasteurizedCount; }
    public int getRawMilkCount() { return rawMilkCount; }
    public int getOrganicHighMoistureCount() { return organicHighMoistureCount; }
    public Map<String, Integer> getMilkTypeCounts() { return milkTypeCounts; }
    public String getMostCommonMilkType() { return mostCommonMilkType; }
    public int getMostCommonMilkTypeCount() { return mostCommonMilkTypeCount; }
}
