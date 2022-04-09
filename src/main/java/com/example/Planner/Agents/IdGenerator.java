package com.example.Planner.Agents;

import com.example.Planner.Model.CourseInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class IdGenerator {
    public CourseInfoGenerator courseInfoGenerator;
    public HashMap<String, Integer> departmentsHashMap = new HashMap<String, Integer>();
    public HashMap<String, Integer> departmentsHashMapForDumpCourses = new HashMap<String, Integer>();
    public HashMap<String, Integer> courseHashmap = new HashMap<String, Integer>();
    public HashMap<String, Integer> courseHashmapForDumpCourses = new HashMap<String, Integer>();
    public IdGenerator(CourseInfoGenerator courseInfoGenerator) {
        this.courseInfoGenerator = courseInfoGenerator;
        courseInfoGenerator.SortCoursesBasedOnSubject();
        courseInfoGenerator.SortDumpCourseBasedOnSubject();
        generateIdForDept();
        setDeptIdForCourses();
        generateIdForCourses();
        setCourseIdForCourses();
        generateAndSetIdForOffering();
//        printCourseId();
    }

    public void generateIdForDept() {
        Integer i = 1;
        List<CourseInfo> courses = courseInfoGenerator.getCourses();
        List<CourseInfo> dumpCourses = courseInfoGenerator.getDumpCourses();
        for(CourseInfo course : courses ){
            if(!departmentsHashMap.containsKey(course.getLabel().getDept())){
                departmentsHashMap.put(course.getLabel().getDept(), i);
                i++;
            }
        }

        int j = 0;
        for(CourseInfo course : dumpCourses ){
            if(!departmentsHashMapForDumpCourses.containsKey(course.getLabel().getDept())){
                departmentsHashMapForDumpCourses.put(course.getLabel().getDept(), j);
                j++;
            }
        }
    }

    public void generateIdForCourses(){
        Integer i = 1;
        List<CourseInfo> courses = courseInfoGenerator.getCourses();
        for(CourseInfo course : courses ){
            if(!courseHashmap.containsKey(course.getLabel().getDept() + ' ' + course.getLabel().getCatalogNum())){
                courseHashmap.put(course.getLabel().getDept() + ' ' + course.getLabel().getCatalogNum(), i);
//                System.out.println(course.getLabel().getDept() + ' ' + course.getLabel().getCatalogNum() + ", id: " + i);
                i++;
            }
        }

        int j = 0;
        List<CourseInfo> DumpCourses = courseInfoGenerator.getDumpCourses();
        for(CourseInfo course : DumpCourses ){
            if(!courseHashmapForDumpCourses.containsKey(course.getLabel().getDept() + ' ' + course.getLabel().getCatalogNum())){
                courseHashmapForDumpCourses.put(course.getLabel().getDept() + ' ' + course.getLabel().getCatalogNum(), j);
                System.out.println();
                j++;
            }
        }
    }

    public void generateAndSetIdForOffering(){
        int i = 1;
        List<CourseInfo> courses = courseInfoGenerator.getCourses();
        for(CourseInfo course : courses){
            course.getLabel().setCourseOfferingId(i);
            i++;
        }
    }

    public void printCourseId(){
        List<CourseInfo> courses = courseInfoGenerator.getCourses();
        for(CourseInfo course : courses ){
            System.out.println(course.getLabel().getDept() +" "+course.getLabel().getCatalogNum() + "," + course.getLabel().getCourseId());
        }
    }

    public void setDeptIdForCourses(){
        List<CourseInfo> courses = courseInfoGenerator.getCourses();
        for(CourseInfo course : courses ){
            course.getLabel().setDeptId(departmentsHashMap.get(course.getLabel().getDept()));
        }

        List<CourseInfo> dumpCourses = courseInfoGenerator.getDumpCourses();
        for(CourseInfo course : dumpCourses ){
            course.getLabel().setDeptId(departmentsHashMapForDumpCourses.get(course.getLabel().getDept()));
        }
    }

    public CourseInfoGenerator getCourseInfoGenerator() {
        return courseInfoGenerator;
    }

    public void setCourseInfoGenerator(CourseInfoGenerator courseInfoGenerator) {
        this.courseInfoGenerator = courseInfoGenerator;
    }

    public void setDepartmentsHashMap(HashMap<String, Integer> departmentsHashMap) {
        this.departmentsHashMap = departmentsHashMap;
    }

    public HashMap<String, Integer> getDepartmentsHashMapForDumpCourses() {
        return departmentsHashMapForDumpCourses;
    }

    public void setDepartmentsHashMapForDumpCourses(HashMap<String, Integer> departmentsHashMapForDumpCourses) {
        this.departmentsHashMapForDumpCourses = departmentsHashMapForDumpCourses;
    }

    public HashMap<String, Integer> getCourseHashmap() {
        return courseHashmap;
    }

    public void setCourseHashmap(HashMap<String, Integer> courseHashmap) {
        this.courseHashmap = courseHashmap;
    }

    public HashMap<String, Integer> getCourseHashmapForDumpCourses() {
        return courseHashmapForDumpCourses;
    }

    public void setCourseHashmapForDumpCourses(HashMap<String, Integer> courseHashmapForDumpCourses) {
        this.courseHashmapForDumpCourses = courseHashmapForDumpCourses;
    }

    public void setCourseIdForCourses(){
        List<CourseInfo> courses = courseInfoGenerator.getCourses();
        for(CourseInfo course : courses ){
            course.getLabel().setCourseId(courseHashmap.get(course.getLabel().getDept() +' '+ course.getLabel().getCatalogNum()));
        }

        List<CourseInfo> dumpCourses = courseInfoGenerator.getDumpCourses();
        for(CourseInfo course : dumpCourses ){
            course.getLabel().setCourseId(departmentsHashMapForDumpCourses.get(course.getLabel().getDept()
                    + ' ' + course.getLabel().getCatalogNum()));
        }
    }

    public void printDeptAndId(){
        List<CourseInfo> courses = courseInfoGenerator.getCourses();
        for(CourseInfo course : courses ){
            System.out.println(course.getLabel().getDept() + "," + course.getLabel().getDeptId());
        }
    }


    public static <K,V> HashMap<V,K> reverse(HashMap<K,V> map) {
        HashMap<V,K> rev = new HashMap<V, K>();
        for(HashMap.Entry<K,V> entry : map.entrySet())
            rev.put(entry.getValue(), entry.getKey());
        return rev;
    }

    public HashMap<String, Integer> getDepartmentsHashMap() {
        return departmentsHashMap;
    }
}
