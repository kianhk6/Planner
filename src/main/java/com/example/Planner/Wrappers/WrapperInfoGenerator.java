package com.example.Planner.Wrappers;

import com.example.Planner.Agents.CourseInfoGenerator;

import java.util.*;

public class WrapperInfoGenerator {
    List<ApiDepartmentWrapper> departments = new ArrayList<>();
    CourseInfoGenerator courseInfoGenerator;



    public WrapperInfoGenerator(CourseInfoGenerator courseInfoGenerator){
        this.courseInfoGenerator = courseInfoGenerator;
        generateDepartments(courseInfoGenerator);
    }

    private void generateDepartments(CourseInfoGenerator courseInfoGenerator) {
        HashMap<String, Integer> departmentsHashMap = courseInfoGenerator.getIdGenerator().getDepartmentsHashMap();
        Set<String> names = departmentsHashMap.keySet();
        for(String name : names){
            departments.add(new ApiDepartmentWrapper(name, departmentsHashMap.get(name)));
        }
    }

    public List<ApiDepartmentWrapper> getDepartments() {
        return departments;
    }
}
