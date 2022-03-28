package com.example.Planner;

import com.example.Planner.Model.CourseInfo;
import com.example.Planner.Model.Semester;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        List<CourseInfo> courses = new ArrayList<>();
        try {
            File file = new File("./data/small_data.csv");
            Scanner scan = new Scanner(file);
            scan.next();
            scan.useDelimiter(",");
            while (scan.hasNext()) {
                String semesterStr = scan.next();
                semesterStr = semesterStr.trim();
                Semester semester = new Semester(semesterStr.substring(0,3), semesterStr.charAt(3));
                //System.out.println("year: " + semester.getYear() + " term: " + semester.getTerm());

                String subjectStr = scan.next();
                subjectStr = subjectStr.trim();




                scan.nextLine();


            }
        }
        catch (FileNotFoundException e){
            System.err.println(e.fillInStackTrace());
        }

    }
}
