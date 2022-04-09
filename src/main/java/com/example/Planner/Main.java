package com.example.Planner;

import com.example.Planner.Agents.CourseInfoGenerator;
import com.example.Planner.Agents.IdGenerator;

public class Main {

    public static void main(String[] args) {
        CourseInfoGenerator courseInfoGenerator = new CourseInfoGenerator("./data/course_data_2018.csv");
        IdGenerator idGenerator = new IdGenerator(courseInfoGenerator);
        // write your code here
    }
}

