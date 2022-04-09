package com.example.Planner;

import com.example.Planner.Agents.CourseInfoGenerator;
import com.example.Planner.Agents.IdGenerator;

public class Main {

    public static void main(String[] args) {
        CourseInfoGenerator courseInfoGenerator = new CourseInfoGenerator("./data/small_data.csv");
        IdGenerator idGenerator = new IdGenerator(courseInfoGenerator);
        // write your code here
    }
}

