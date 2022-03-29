package com.example.Planner.Model;

import java.util.ArrayList;
import java.util.List;

public class CourseInfo {
    List<String> instructors;
    Semester semester;
    Label label;
    EnrollmentSpace enrollmentSpace;
    String Location;

    public CourseInfo(List<String> instructors, Semester semester,
                      Label label, EnrollmentSpace enrollmentSpace,
                      String location) {
        this.instructors = instructors;
        this.semester = semester;
        this.label = label;
        this.enrollmentSpace = enrollmentSpace;
        Location = location;
    }

    public List<String> getInstructors() {
        return instructors;
    }

    public void setInstructors(List<String> instructors) {
        this.instructors = instructors;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public EnrollmentSpace getEnrollmentSpace() {
        return enrollmentSpace;
    }

    public void setEnrollmentSpace(EnrollmentSpace enrollmentSpace) {
        this.enrollmentSpace = enrollmentSpace;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }
}
