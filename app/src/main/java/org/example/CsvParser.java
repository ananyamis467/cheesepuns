package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvParser {
    //column names expected in the header row
    private static final String COL_ID = "CheeseId";
    private static final String COL_TREATMENT = "MilkTreatmentTypeEn";
    private static final String COL_MILK_TYPE = "MilkTypeEn";
    private static final String COL_ORGANIC = "Organic";
    private static final String COL_MOISTURE = "MoisturePercent";
 
    /*
     parses the given CSV file and returns a list of {@link Cheese} objects;
     throws exceptions as necessary
     */
    public List<Cheese> parse(String filePath) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filePath));
 
        if (lines.isEmpty()) {
            return new ArrayList<>();
        }
 
        Map<String, Integer> colIndex = buildColumnIndex(lines.get(0));
        validateRequiredColumns(colIndex);
 
        int idxId = colIndex.get(COL_ID);
        int idxTreatment = colIndex.get(COL_TREATMENT);
        int idxMilkType = colIndex.get(COL_MILK_TYPE);
        int idxOrganic = colIndex.get(COL_ORGANIC);
        int idxMoisture = colIndex.get(COL_MOISTURE);
 
        List<Cheese> cheeses = new ArrayList<>();
 
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty()) continue;
 
            String[] fields = parseCSVLine(line);
 
            int     id        = parseIntOrDefault(getField(fields, idxId), -1);
            String  treatment = getField(fields, idxTreatment);
            String  milkType  = getField(fields, idxMilkType);
            Boolean organic   = parseOrganic(getField(fields, idxOrganic));
            Double  moisture  = parseDouble(getField(fields, idxMoisture));
 
            cheeses.add(new Cheese(id, treatment, milkType, organic, moisture));
        }
 
        return cheeses;
    }

    //private helpers

    //builds a map from header name → column index 
    private Map<String, Integer> buildColumnIndex(String headerLine) {
        String[] headers = parseCSVLine(headerLine);
        Map<String, Integer> index = new HashMap<>();
        for (int i = 0; i < headers.length; i++) {
            index.put(headers[i].trim(), i);
        }
        return index;
    }
 
    private void validateRequiredColumns(Map<String, Integer> colIndex) {
        for (String col : new String[]{COL_ID, COL_TREATMENT, COL_MILK_TYPE, COL_ORGANIC, COL_MOISTURE}) {
            if (!colIndex.containsKey(col)) {
                throw new IllegalArgumentException("Required column not found in CSV: " + col);
            }
        }
    }
 
    /**
     returns the trimmed field value at {@code index}, or null if the
     index is out of range or the value is blank.
     */
    private String getField(String[] fields, int index) {
        if (index < 0 || index >= fields.length) return null;
        String val = fields[index].trim();
        return val.isEmpty() ? null : val;
    }
 
    /**
     converts the CSV "Organic" column (0 / 1) to a Boolean
     returns null if the value is missing or not parseable
     */
    private Boolean parseOrganic(String value) {
        if (value == null) return null;
        try {
            return Integer.parseInt(value) == 1;
        } catch (NumberFormatException e) {
            return null;
        }
    }
 
    private Double parseDouble(String value) {
        if (value == null) return null;
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
 
    private int parseIntOrDefault(String value, int defaultValue) {
        if (value == null) return defaultValue;
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
 
    /*
     parses a single CSV line respecting RFC 4180 quoting rules:
     fields may be enclosed in double-quotes, and a literal double-quote
     inside a quoted field is represented as two consecutive double-quotes.
     */
    private String[] parseCSVLine(String line) {
        List<String> fields = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;
 
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
 
            if (c == '"') {
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    current.append('"');
                    i++; // skip the second quote of the escaped pair
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (c == ',' && !inQuotes) {
                fields.add(current.toString());
                current.setLength(0);
            } else {
                current.append(c);
            }
        }
 
        fields.add(current.toString()); //last field (no trailing comma)
        return fields.toArray(new String[0]);
    }
}
