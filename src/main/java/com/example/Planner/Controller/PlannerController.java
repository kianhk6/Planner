package com.example.Planner.Controller;

import com.example.Planner.Agents.CourseInfoGenerator;
import com.example.Planner.Agents.IdGenerator;
import com.example.Planner.Wrappers.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PlannerController {
    CourseInfoGenerator courseInfoGenerator = new CourseInfoGenerator("./data/course_data_2018.csv");
    IdGenerator idGenerator = new IdGenerator(courseInfoGenerator);
    WrapperInfoGenerator wrapperInfoGenerator = new WrapperInfoGenerator(courseInfoGenerator, idGenerator);
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

    @GetMapping("/departments/{id}/courses")
    public List<ApiCourseWrapper> getCourseDept(@PathVariable("id") long deptId) {
        return wrapperInfoGenerator.getCoursesBasedOnDeptId(deptId);
    }

    @GetMapping("/departments/{deptId}/courses/{courseId}/offerings")
    public List<ApiCourseOfferingWrapper> getCourseDept(@PathVariable("deptId") long deptId, @PathVariable("courseId") long courseId) {
        return wrapperInfoGenerator.getCourseBasedOnIds(deptId, courseId);
    }


    @GetMapping("/departments/{deptId}/courses/{courseId}/offerings/{courseOfferingId}")
    public List<ApiOfferingSectionWrapper> getCourseOffering(@PathVariable("deptId") long deptId,
                                                             @PathVariable("courseId") long courseId, @PathVariable("courseOfferingId") long courseOfferingId) {
        return wrapperInfoGenerator.getOfferingBasedOnCourseOfferingId(deptId, courseId, courseOfferingId);
    }





}
