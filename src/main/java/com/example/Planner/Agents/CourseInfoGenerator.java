package com.example.Planner.Agents;

import com.example.Planner.Model.CourseInfo;
import com.example.Planner.Model.EnrollmentSpace;
import com.example.Planner.Model.Label;
import com.example.Planner.Model.Semester;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class CourseInfoGenerator {
    List<CourseInfo> courses = new ArrayList<>();
    public void transferFileToObject(String path){
        try {
            File file = new File(path);
            Scanner scan = new Scanner(file);
            scan.nextLine();
            scan.useDelimiter(",");
            int i = 0;
            while (scan.hasNext()) {
                String[] line = scan.nextLine().split(",");

                String semesterStr = line[i].trim();

                semesterStr = semesterStr.trim();
                Semester semester = new Semester(semesterStr.substring(0, 3), semesterStr.charAt(3));
                i++;

                String subjectStr = line[i].trim();
                i++;

                String catalogNumStr = line[i].trim();
                i++;

                String location = line[i].trim();
                i++;

                String capacityStr = line[i].trim();
                int capacity = Integer.parseInt(capacityStr);
                i++;

                String takenSeatStr = line[i].trim();
                int takenSeat = Integer.parseInt(takenSeatStr);
                i++;

                List<String> instructors = new ArrayList<>();
                String firstInstruct = line[i].trim();
                i++;

                String OldFirstInstruct = firstInstruct;
                firstInstruct = firstInstruct.replace('"', ' ').trim();
                instructors.add(firstInstruct);
                if (OldFirstInstruct.startsWith("\"")) {
                    String current =line[i].trim();
                    i++;
                    while (current.charAt(current.length() - 1) != '"') {
                        instructors.add(current);
                        current = line[i].trim();
                        i++;
                    }
                    instructors.add(current.replace('"', ' ').trim());
                }
                String componentCodeStr = line[i].trim();
                i = 0;
                courses.add(new CourseInfo(instructors, semester,
                        new Label(subjectStr, catalogNumStr, componentCodeStr),
                        new EnrollmentSpace(capacity, takenSeat), location));
            }
        }
        catch (FileNotFoundException e){
            System.out.println("file not found");
        }
    }

    public void listCourses() {
        for (CourseInfo course : courses) {
            System.out.println(course);
        }
    }

    public void setCourses(List<CourseInfo> courses) {
        this.courses = courses;
    }
}
