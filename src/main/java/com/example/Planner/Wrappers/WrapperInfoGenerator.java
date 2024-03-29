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
        HashMap<String, Integer> departmentsHashMap = idGenerator.getDepartmentsHashMapForDumpCourses();
        Set<String> names = departmentsHashMap.keySet();
        for(String name : names){
            departments.add(new ApiDepartmentWrapper(name, departmentsHashMap.get(name)));
        }
    }

    public List<ApiCourseWrapper>  getCoursesBasedOnDeptId(long deptId){
//        List<CourseInfo> courses = courseInfoGenerator.getCourses();
//        List<ApiCourseWrapper> courseForDept = new ArrayList<>();
//        for(CourseInfo course : courses){
//            if(course.getLabel().getDeptId() == deptId){
//                courseForDept.add(new ApiCourseWrapper(course.getLabel().getCatalogNum(), course.getLabel().getCourseId()));
//            }
//        }
//
//        List<ApiCourseWrapper> noRepetitionCourses = new ArrayList<>();
//        noRepetitionCourses.add(courseForDept.get(0));
//        int oldI = 1;
//        while (oldI < courseForDept.size()){
//            if(noRepetitionCourses.get(noRepetitionCourses.size()-1).getCourseId() != courseForDept.get(oldI).getCourseId()){
//                noRepetitionCourses.add(courseForDept.get(oldI));
//            }
//            oldI++;
//        }
//        return noRepetitionCourses;
        List<CourseInfo> courses = courseInfoGenerator.getDumpCourses();
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

    public List<ApiCourseOfferingWrapper> getCoursesBasedOnIds(long deptId, long courseId){
        List<CourseInfo> courses = courseInfoGenerator.getDumpCourses();
        List<ApiCourseOfferingWrapper> offerings = new ArrayList<>();
        List<Long> ids = new ArrayList<>();
        for(CourseInfo course : courses){
            if(course.getLabel().getDeptId() == deptId
                    && course.getLabel().getCourseId() == courseId){
                if(!ids.contains(course.getLabel().getCourseOfferingId())){
                    offerings.add(new ApiCourseOfferingWrapper(
                            course.getLabel().getCourseOfferingId(),
                            course.getLocation(),
                            course.getInstructorString(),
                            course.getTermString(),
                            parseLong(course.getSemester().getYear() + course.getSemester().getTerm()),
                            course.getYear()
                    ));
                    ids.add( course.getLabel().getCourseOfferingId());
                    System.out.println("new: " + course);
                }
                else{
                    System.out.println("already in use: " + course);
                }

            }
        }
        return offerings;
    }
    public CourseInfo getCourseBasedOnIds(long deptId, long courseId){
        List<CourseInfo> courses = courseInfoGenerator.getDumpCourses();
        List<ApiCourseOfferingWrapper> offerings = new ArrayList<>();
        List<Long> ids = new ArrayList<>();
        for(CourseInfo course : courses){
            if(course.getLabel().getDeptId() == deptId
                    && course.getLabel().getCourseId() == courseId){
                    return course;
                }
            }
        return null;
    }



    public List<ApiOfferingSectionWrapper> getOfferingBasedOnCourseOfferingId(long deptId, long courseId, long courseOfferingId) {
        List<CourseInfo> courses = courseInfoGenerator.getDumpCourses();
        List<ApiOfferingSectionWrapper> offeringInfo = new ArrayList<>();
        for (CourseInfo course : courses) {
            if (course.getLabel().getDeptId() == deptId
                    && course.getLabel().getCourseId() == courseId && course.getLabel().getCourseOfferingId() == courseOfferingId) {
                    offeringInfo.add(new ApiOfferingSectionWrapper(
                            course.getLabel().getComponentCode(),
                            course.getEnrollmentSpace().getCapacity(),
                            course.getEnrollmentSpace().getTakenSeat()
                    ));
            }
        }
        return offeringInfo;
    }

    public List<ApiGraphDataPointWrapper> getGraphPointsBasedOnDeptId(long deptId) {
        List<CourseInfo> courses = courseInfoGenerator.getDumpCourses();
        List<ApiGraphDataPointWrapper> graphPoints = new ArrayList<>();
        List<Long> ids = new ArrayList<>();
        for (CourseInfo course : courses) {
            if(course.getLabel().getDeptId() == deptId && course.getLabel().getComponentCode().equals("LEC")) {
                if(!ids.contains(course.getSemester().makeSemesterCode())){
                    graphPoints.add(new ApiGraphDataPointWrapper(course.getSemester().makeSemesterCode(), course.getEnrollmentSpace().getTakenSeat()));
                    ids.add(course.getSemester().makeSemesterCode());
                }
                else{
                    for (int i = 0; i < ids.size(); i++) {
                        if (ids.get(i) == course.getSemester().makeSemesterCode()) {
                            graphPoints.get(i).setTotalCoursesTaken(graphPoints.get(i).getTotalCoursesTaken() + course.getEnrollmentSpace().getTakenSeat());
                        }
                    }
                }
            }
        }
        graphPoints.sort(new Comparator<ApiGraphDataPointWrapper>() {
            @Override
            public int compare(ApiGraphDataPointWrapper o1, ApiGraphDataPointWrapper o2) {
                if ((int)o1.getSemesterCode() > (int)o2.getSemesterCode()) {
                    return 1;
                } else if((int)o1.getSemesterCode() < (int)o2.getSemesterCode()) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
        return graphPoints;
    }


    public List<ApiDepartmentWrapper> getDepartments() {
        return departments;
    }
}
