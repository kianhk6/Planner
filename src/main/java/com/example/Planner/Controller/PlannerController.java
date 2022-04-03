package com.example.Planner.Controller;

import com.example.Planner.Agents.CourseInfoGenerator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PlannerController {

    @GetMapping("/about")
    public String getMyName(){
        return "Kian Hosseinkhani & Sasha Vujisic";
    }

    @GetMapping("/dump-model")
    public void dumpModel(){
        CourseInfoGenerator courseInfoGenerator = new CourseInfoGenerator("./data/course_data_2018.csv");
        courseInfoGenerator.listDumpCourses();

    }
}
