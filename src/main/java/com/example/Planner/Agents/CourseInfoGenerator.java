package com.example.Planner.Agents;

import com.example.Planner.Model.CourseInfo;
import com.example.Planner.Model.EnrollmentSpace;
import com.example.Planner.Model.Label;
import com.example.Planner.Model.Semester;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;


public class CourseInfoGenerator {
    List<CourseInfo> courses = new ArrayList<>();
    List<CourseInfo> dumpCourses = new ArrayList<>();
    public void transferFileToObject(String path){
        try {
            File file = new File(path);
            Scanner scan = new Scanner(file);
            scan.nextLine();
            scan.useDelimiter(",");
            int i = 0;
            while (scan.hasNext()) {
                String[] line = scan.nextLine().split(",");

                String semesterStr = line[i].trim();

                semesterStr = semesterStr.trim();
                Semester semester = new Semester(semesterStr.substring(0, 3), semesterStr.charAt(3));
                i++;

                String subjectStr = line[i].trim();
                i++;

                String catalogNumStr = line[i].trim();
                i++;

                String location = line[i].trim();
                i++;

                String capacityStr = line[i].trim();
                int capacity = Integer.parseInt(capacityStr);
                i++;

                String takenSeatStr = line[i].trim();
                int takenSeat = Integer.parseInt(takenSeatStr);
                i++;

                List<String> instructors = new ArrayList<>();
                String firstInstruct = line[i].trim();
                i++;

                String OldFirstInstruct = firstInstruct;
                firstInstruct = firstInstruct.replace('"', ' ').trim();
                if((firstInstruct.equals("(null)") || firstInstruct.equals("<null>"))){
                    instructors.add("");
                }
                else{
                    instructors.add(firstInstruct.replace('"', ' ').trim());
                }

                if (OldFirstInstruct.startsWith("\"")) {
                    String current =line[i].trim();
                    i++;
                    while (current.charAt(current.length() - 1) != '"') {
                        instructors.add(current);
                        current = line[i].trim();
                        i++;
                    }
                    if((current.equals("(null)") || current.equals("<null>"))){
                        instructors.add("");
                    }
                    else{
                        instructors.add(current.replace('"', ' ').trim());
                    }

                }
                String componentCodeStr = line[i].trim();
                i = 0;
                courses.add(new CourseInfo(instructors, semester,
                        new Label(subjectStr, catalogNumStr, componentCodeStr),
                        new EnrollmentSpace(capacity, takenSeat), location));
            }
        }
        catch (FileNotFoundException e){
            System.out.println("file not found");
        }
    }

    public void listCourses() {
        for (CourseInfo course : courses) {
            System.out.println(course);//
        }
    }
    public void listDumpCourses() {
        for (CourseInfo course : dumpCourses) {
            System.out.println(course);//
        }
    }
    public void makeDumpModel() {
        int dumpCourseIndex = 0;
        int nextDumpCourseIndex = 1;
        int dumpCourseSize = dumpCourses.size();

        while (dumpCourseIndex < dumpCourseSize-1) {
            CourseInfo current = dumpCourses.get(dumpCourseIndex);
            CourseInfo next = dumpCourses.get(nextDumpCourseIndex);
            if (current.getLabel().equals(next.getLabel())) {

            }
        }
    }

    public void setCourses(List<CourseInfo> courses) {
        this.courses = courses;
    }

    public void sortCourses() {
        Collections.sort(courses);
    }

    public void setUpDumpCourses(){
        int nextCourseIndex = 1;
        int currentCourseIndex = 0;

        int coursesSize = courses.size()-1;
        while (currentCourseIndex <= coursesSize){
            CourseInfo current = courses.get(currentCourseIndex);
            List<String> currentInstructors = new ArrayList<>();
            int currentCapacity = 0;
            int currentTaken = 0;
            while(nextCourseIndex <= coursesSize && sameCourse(current, courses.get(nextCourseIndex))){
                System.out.println("nextCourseIndex: " + nextCourseIndex);
                CourseInfo nextCourse = courses.get(nextCourseIndex);

                List<String> nextInstructors = nextCourse.getInstructors();
                for(String instructor : nextInstructors){
                    if(!currentInstructors.contains(instructor)) {
                        currentInstructors.add((instructor));
                    }
                }
                currentCapacity = currentCapacity + nextCourse.getEnrollmentSpace().getCapacity();
                currentTaken = currentTaken + nextCourse.getEnrollmentSpace().getTakenSeat();
                System.out.println("for " + currentInstructors.get(0));
                System.out.println(" capacity: " +currentCapacity);
                System.out.println("current seatsTaken: " + currentTaken);
                nextCourseIndex++;
            }


            String currentYear = current.getSemester().getYear();
            char currentTerm = current.getSemester().getTerm();
            Semester currentSemester = new Semester(currentYear, currentTerm);
            Label currentLabel = current.getLabel();
            String currentLocation = current.getLocation();
            currentCapacity = currentCapacity + current.getEnrollmentSpace().getCapacity();
            currentTaken = currentTaken + current.getEnrollmentSpace().getTakenSeat();
            for(String instructor : current.getInstructors()){
                if(!currentInstructors.contains(instructor)) {
                    currentInstructors.add((instructor));
                }
            }


            dumpCourses.add(new CourseInfo(currentInstructors, currentSemester, currentLabel,
                    new EnrollmentSpace(currentCapacity, currentTaken), currentLocation));
            currentCourseIndex = nextCourseIndex;
            nextCourseIndex++;
        }
    }

    private boolean sameCourse(CourseInfo current, CourseInfo next) {
        return current.getSemester().getYear().equals(next.getSemester().getYear()) &&
                current.getSemester().getTerm() == next.getSemester().getTerm() &&
                current.getLabel().getSubject().equals(next.getLabel().getSubject()) &&
                current.getLabel().getCatalogNum().equals(next.getLabel().getCatalogNum()) &&
                current.getLabel().getComponentCode().equals(next.getLabel().getComponentCode()) &&
                current.getLocation().equals(next.getLocation());
    }
}
