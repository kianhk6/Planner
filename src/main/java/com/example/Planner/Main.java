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
        File file = new File("./data/course_data_2018.csv");
        for(CourseInfo course : courses) {
            System.out.println(course);
        }

    }

}
