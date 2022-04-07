package com.example.Planner.Controller;

import com.example.Planner.Agents.CourseInfoGenerator;
import com.example.Planner.Wrappers.ApiDepartmentWrapper;
import com.example.Planner.Wrappers.WrapperInfoGenerator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.Planner.Agents.IdGenerator.reverse;

@RestController
@RequestMapping("/api")
public class PlannerController {
    CourseInfoGenerator courseInfoGenerator = new CourseInfoGenerator("./data/course_data_2018.csv");
    WrapperInfoGenerator wrapperInfoGenerator = new WrapperInfoGenerator(courseInfoGenerator);
    @GetMapping("/about")
    public Map<String, String> getMyName(){
        HashMap<String, String> About = new HashMap<>();
        About.put("appName", "SFU Course Planner clone");
        About.put("authorName", "Kian Hosseinkhani and Sasha Vujisic");
        return About;
    }

    @GetMapping("/dump-model")
    public void dumpModel(){
        courseInfoGenerator.listDumpCourses();
    }

    @GetMapping("/departments")
    public List<ApiDepartmentWrapper> ListDepartments() {
        return wrapperInfoGenerator.getDepartments();
    }





}
