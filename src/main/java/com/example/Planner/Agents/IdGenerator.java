package com.example.Planner.Agents;

import com.example.Planner.Model.CourseInfo;

import java.util.HashMap;
import java.util.List;

public class IdGenerator {
    public List<CourseInfo> courses;
    public HashMap<String, Integer> departmentsHashMap = new HashMap<String, Integer>();

    public IdGenerator(List<CourseInfo> courses) {
        this.courses = courses;
        generateIdForDept();
        setDeptIdForCourses();
    }

    public void generateIdForDept() {
        Integer i = 1;
        for(CourseInfo course : courses ){
            if(!departmentsHashMap.containsKey(course.getLabel().getDept())){
                departmentsHashMap.put(course.getLabel().getDept(), i);
                i++;
            }
        }
    }

    public void setDeptIdForCourses(){
        for(CourseInfo course : courses ){
            course.getLabel().setDeptId(departmentsHashMap.get(course.getLabel().getDept()));
        }
    }

    public void printDeptAndId(){
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
