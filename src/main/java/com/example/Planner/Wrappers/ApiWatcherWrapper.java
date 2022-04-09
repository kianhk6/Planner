package com.example.Planner.Wrappers;

import java.util.List;

public class ApiWatcherWrapper {
    public long id;
    public ApiDepartmentWrapper department;
    public ApiCourseWrapper course;
    public List<String> events;

    public ApiWatcherWrapper(long id, ApiDepartmentWrapper department, ApiCourseWrapper course, List<String> events) {
        this.id = id;
        this.department = department;
        this.course = course;
        this.events = events;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ApiDepartmentWrapper getDepartment() {
        return department;
    }

    public void setDepartment(ApiDepartmentWrapper department) {
        this.department = department;
    }

    public ApiCourseWrapper getCourse() {
        return course;
    }

    public void setCourse(ApiCourseWrapper course) {
        this.course = course;
    }

    public List<String> getEvents() {
        return events;
    }

    public void setEvents(List<String> events) {
        this.events = events;
    }
}
