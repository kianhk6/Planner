package com.example.Planner;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
//
    public static void main(String[] args) {
        try {
            File file = new File("./data/small_data.csv");
            Scanner scan = new Scanner(file);
            scan.useDelimiter(",");
            while (scan.hasNext()) {
                System.out.printf(scan.next());
            }
        }
        catch (FileNotFoundException e){
            System.err.println(e.fillInStackTrace());
        }

    }
}
