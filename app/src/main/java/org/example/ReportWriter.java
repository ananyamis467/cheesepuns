package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class ReportWriter {

    private final String outputPath;

    public ReportWriter(String outputPath) {
        this.outputPath = outputPath;
    }

    public void write(CheeseAnalysis analysis) throws IOException {
        String report = buildReport(analysis);
        Files.writeString(Paths.get(outputPath), report);
        System.out.println(report);
        System.out.println("Results written to " + outputPath);
    }

    //private helpers
    private String buildReport(CheeseAnalysis a) {
        StringBuilder sb = new StringBuilder();

        sb.append("=== Canadian Cheese Dataset Analysis ===\n\n");

        appendCalc1(sb, a);
        appendCalc2(sb, a);
        appendCalc3(sb, a);

        return sb.toString();
    }

    private void appendCalc1(StringBuilder sb, CheeseAnalysis a) {
        sb.append("--- Calculation 1: Milk Treatment Type ---\n");
        sb.append(String.format("Number of cheeses using Pasteurized milk: %d%n", a.getPasteurizedCount()));
        sb.append(String.format("Number of cheeses using Raw Milk: %d%n", a.getRawMilkCount()));
        sb.append("(Rows with missing or other treatment types are excluded from these counts)\n\n");
    }

    private void appendCalc2(StringBuilder sb, CheeseAnalysis a) {
        sb.append("--- Calculation 2: Organic Cheeses with Moisture > 41.0% ---\n");
        sb.append(String.format(
                "Number of organic cheeses (Organic=1) with moisture percentage > 41.0%%: %d%n",
                a.getOrganicHighMoistureCount()));
        sb.append("(Rows with missing Organic or MoisturePercent values are omitted)\n\n");
    }

    private void appendCalc3(StringBuilder sb, CheeseAnalysis a) {
        sb.append("--- Calculation 3: Most Common Animal Milk Type in Canada ---\n");
        sb.append("Milk type counts:\n");

        for (Map.Entry<String, Integer> entry : a.getMilkTypeCounts().entrySet()) {
            sb.append(String.format("  %-25s : %d%n", entry.getKey(), entry.getValue()));
        }

        sb.append(String.format("%nMost cheeses in Canada come from: %s (%d cheeses)%n",
                a.getMostCommonMilkType(),
                a.getMostCommonMilkTypeCount()));
        sb.append("(Rows with missing MilkTypeEn values are omitted)\n");
    }
}
