package com.example.sampletest;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner;
import org.apache.commons.math3.distribution.*;

public class Calculator {
    private int rows = 74;
    private int columns = 3;
    private double[][] weightToAge = new double[rows][columns];
    private double[][] lengthToAge = new double[rows][columns];
    private double[][] headCircumferenceToAge = new double[rows][columns];
    private double[] age = new double[rows/2];
    public Calculator() {
        age[0] = 0;
        for (int i = 1; i < rows/2; i++) {
            age[i] = i-0.5;
        }
    }
    public void readData() {
        // read weight to age data from data/w2a.csv
        String[] filesPath = {"data/w2a.csv", "data/l2a.csv", "data/hc2a.csv"};
        double[][] reference;
        for (int index = 0; index < 3; index++) {
            try {
                Scanner myReader = new Scanner(new File(filesPath[index]));
                String discarded = myReader.nextLine();
                for (int i = 0; i < rows; i++) {
                    String data = myReader.nextLine();
                    data = data.replace("\"", "");
                    String[] parts = data.split(",");
                    for (int j = 0; j < columns; j++) {
                        if (index == 0) {
                            reference = weightToAge;
                        }
                        else if (index == 1) {
                            reference = lengthToAge;
                        }
                        else {
                            reference = headCircumferenceToAge;
                        }
                        reference[i][j] = Double.parseDouble(parts[j+2]);
                    }
                }
                myReader.close();
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }

    }

    public int searchAge (double inputAge) {
        if (inputAge == 0){
            return 0;
        }
        int i = 1;
        while (age[i] + 0.49 < inputAge && i < 37) {
            i++;
        }
        return i;
    }

    public double findZ (int index, int gender, double measurement, int table) {
        if (gender == 2) {
            index += 37;
        }

        double[][] reference;
        if (table == 1) {
            reference = weightToAge;
        }
        else if (table == 2) {
            reference = lengthToAge;
        }
        else {
            reference = headCircumferenceToAge;
        }

        double L = reference[index][0];
        double M = reference[index][1];
        double S = reference[index][2];
        double Z = (Math.pow (measurement/M, L) - 1) / (L * S);
        return Z;
    }

    public double zScoreToPercentile(double zScore)
    {
        double percentile = 0;
        NormalDistribution dist = new NormalDistribution();
        percentile = dist.cumulativeProbability(zScore) * 100;
        return percentile;
    }

    public double returnPercentile(int table, int gender, double age, double measurement) {
        Calculator cal = new Calculator();
        cal.readData();

        double zScore, percentile;
        //age = 10.5;
        //table = 2;
        //gender = 2;
        //measurement = 9.043261854;
        //measurement = 71.91961673;
        //measurement = 44.53836794;
        int index = cal.searchAge(age);
        if (table == 1) {
            zScore = cal.findZ(index, gender, measurement, table);
        } else if (table == 2) {
            zScore = cal.findZ(index, gender, measurement, table);
        } else {
            zScore = cal.findZ(index, gender, measurement, table);
        }

        percentile = cal.zScoreToPercentile(zScore);
        return Math.round(percentile * 100.00)/100.00;
    }
}
