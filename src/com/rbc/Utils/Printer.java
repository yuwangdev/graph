package com.rbc.Utils;

import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by yuwang on 5/26/17.
 */
public class Printer {

    public static void writeToCsvFile(String fileName, String[][] re) {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(fileName), CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER);
            for (String[] lines : re) {
                writer.writeNext(lines);
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
