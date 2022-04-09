package com.example.Planner.Wrappers;

import com.example.Planner.Agents.CourseInfoGenerator;
import com.example.Planner.Agents.IdGenerator;
import com.example.Planner.Model.CourseInfo;

import java.util.*;

import static java.lang.Long.parseLong;

public class WrapperInfoGenerator {
    List<ApiDepartmentWrapper> departments = new ArrayList<>();

    CourseInfoGenerator courseInfoGenerator;
    IdGenerator idGenerator;



    public WrapperInfoGenerator(CourseInfoGenerator courseInfoGenerator, IdGenerator idGenerator){
        this.courseInfoGenerator = courseInfoGenerator;
        this.idGenerator = idGenerator;
        generateDepartments();
    }

    private void generateDepartments() {
        HashMap<String, Integer> departmentsHashMap = idGenerator.getDepartmentsHashMap();
        Set<String> names = departmentsHashMap.keySet();
        for(String name : names){
            departments.add(new ApiDepartmentWrapper(name, departmentsHashMap.get(name)));
        }
    }

    public List<ApiCourseWrapper>  getCoursesBasedOnDeptId(long deptId){
        List<CourseInfo> courses = courseInfoGenerator.getCourses();
        List<ApiCourseWrapper> courseForDept = new ArrayList<>();
        for(CourseInfo course : courses){
            if(course.getLabel().getDeptId() == deptId){
                courseForDept.add(new ApiCourseWrapper(course.getLabel().getCatalogNum(), course.getLabel().getCourseId()));
            }
        }

        List<ApiCourseWrapper> noRepetitionCourses = new ArrayList<>();
        noRepetitionCourses.add(courseForDept.get(0));
        int oldI = 1;
        while (oldI < courseForDept.size()){
            if(noRepetitionCourses.get(noRepetitionCourses.size()-1).getCourseId() != courseForDept.get(oldI).getCourseId()){
                noRepetitionCourses.add(courseForDept.get(oldI));
            }
            oldI++;
        }
        return noRepetitionCourses;
    }

    public List<ApiCourseOfferingWrapper> getCourseBasedOnIds(long deptId, long courseId){
        List<CourseInfo> courses = courseInfoGenerator.getCourses();
        List<ApiCourseOfferingWrapper> offerings = new ArrayList<>();
        for(CourseInfo course : courses){
            if(course.getLabel().getDeptId() == deptId
                    && course.getLabel().getCourseId() == courseId){
                offerings.add(new ApiCourseOfferingWrapper(
                        course.getLabel().getCourseOfferingId(),
                        course.getLocation(),
                        course.getInstructorString(),
                        course.getTermString(),
                        parseLong(course.getSemester().getYear() + course.getSemester().getTerm()),
                        course.getYear()
                        ));
            }
        }
        return offerings;

    }

    public List<ApiDepartmentWrapper> getDepartments() {
        return departments;
    }
}
