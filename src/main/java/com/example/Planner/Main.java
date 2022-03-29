package com.example.Planner;

import com.example.Planner.Model.CourseInfo;
import com.example.Planner.Model.EnrollmentSpace;
import com.example.Planner.Model.Label;
import com.example.Planner.Model.Semester;

import java.io.File;
import java.io.FileNotFoundException;
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
                Semester semester = new Semester(semesterStr.substring(0, 3), semesterStr.charAt(3));


                String subjectStr = scan.next().trim();

                String catalogNumStr = scan.next().trim();

                String location = scan.next().trim();

                String capacityStr = scan.next().trim();
                int capacity = Integer.parseInt(capacityStr);

                String takenSeatStr = scan.next().trim();
                int takenSeat = Integer.parseInt(takenSeatStr);

                List<String> instructors = new ArrayList<>();
                String firstInstruct = scan.next().trim();
                String OldFirstInstruct = firstInstruct;
                firstInstruct = firstInstruct.replace('"', ' ').trim();
                instructors.add(firstInstruct);
                if (OldFirstInstruct.startsWith("\"")) {
                    String current = scan.next().trim();
                    while (current.charAt(current.length() - 1) != '"') {
                        instructors.add(current);
                        current = scan.next().trim();
                    }
                    instructors.add(current.replace('"', ' ').trim());
                }

                String componentCodeStr = scan.next().trim();
                if (componentCodeStr.contains("\n")){
                    componentCodeStr = componentCodeStr.substring(0,2);

                }

//                courses.add(new CourseInfo(instructors, semester,
//                        new Label(subjectStr, catalogNumStr, componentCodeStr),
//                        new EnrollmentSpace(capacity, takenSeat), location));
                    scan.nextLine();
            }
        }
        catch (FileNotFoundException e){
            System.err.println(e.fillInStackTrace());
        }

    }
}
