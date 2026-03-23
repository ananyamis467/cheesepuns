package org.example;

import java.io.IOException;
import java.util.List;

//driver; no tests for this program
public class App {

    private static final String INPUT_FILE = "cheese_data.csv";
    private static final String OUTPUT_FILE = "output.txt";

    public static void main(String[] args) {
        CsvParser parser = new CsvParser();
        CheeseCalculator calculator = new CheeseCalculator();
        ReportWriter writer = new ReportWriter(OUTPUT_FILE);

        try{ //for errors
            List<Cheese>   cheeses  = parser.parse(INPUT_FILE);
            CheeseAnalysis analysis = calculator.calculate(cheeses);
            writer.write(analysis);

        }catch (IOException e) {
            System.err.println("File error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Data error: " + e.getMessage());
        }
    }
}

    


    

