package org.example;

/*
 represents a single cheese entry from the Canadian cheese dataset.
 *any column that is missing or blank in the CSV is stored as null so 
  callers can skip incomplete rows without relying on sentinel values 
  (e.g. -1 or empty string).
 */
public class Cheese {

    private final int cheeseId;
    private final String milkTreatmentType; // "Pasteurized", "Raw Milk", "Thermised", or null
    private final String milkType;          // "Cow", "Goat", "Ewe", etc., or null
    private final Boolean organic;          // true = organic (1), false = not organic (0), null = missing
    private final Double moisturePercent;   // null if missing

    //constructor
    public Cheese(int cheeseId, String milkTreatmentType,
                  String milkType, Boolean organic,
                  Double moisturePercent) {
        this.cheeseId = cheeseId;
        this.milkTreatmentType = milkTreatmentType;
        this.milkType = milkType;
        this.organic = organic;
        this.moisturePercent = moisturePercent;
    }

    //accessors
    public int getCheeseId() {
        return cheeseId;
    }

    //returns the milk treatment type, or null if not recorded
    public String getMilkTreatmentType() {
        return milkTreatmentType;
    }

    //returns the milk type (e.g. "Cow"), or null if not recorded
    public String getMilkType() {
        return milkType;
    }

    /**
     returns true if the cheese is certified organic, false if it is not,
     or null if the value was missing in the source data
     */
    public Boolean isOrganic() {
        return organic;
    }

    //returns the moisture percentage, or null if not recorded
    public Double getMoisturePercent() {
        return moisturePercent;
    }


    //convenience predicates — each guards against null before comparing
    public boolean isPasteurized() {
        return "Pasteurized".equalsIgnoreCase(milkTreatmentType);
    }

    public boolean isRawMilk() {
        return "Raw Milk".equalsIgnoreCase(milkTreatmentType);
    }

    /* returns true only when both the organic flag AND the moisture
    percentage are present and satisfy the criteria */
    public boolean isOrganicWithHighMoisture(double moistureThreshold) {
        return Boolean.TRUE.equals(organic)
                && moisturePercent != null
                && moisturePercent > moistureThreshold;
    }

    public boolean hasMilkType() {
        return milkType != null;
    }

    @Override
    public String toString() {
        return String.format(
                "Cheese{id=%d, treatment=%s, milkType=%s, organic=%s, moisture=%s}",
                cheeseId, milkTreatmentType, milkType, organic, moisturePercent);
    }
}
