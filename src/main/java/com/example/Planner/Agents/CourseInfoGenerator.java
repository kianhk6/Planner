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
        int currentDumpIndex = 0;
        int nextDumpIndex = 1;
        int dumpSize = dumpCourses.size();

        for (CourseInfo course : dumpCourses) {
            System.out.println(course);
        }

        while (currentDumpIndex < dumpSize) {
            CourseInfo current = dumpCourses.get(currentDumpIndex);
            System.out.println(current.getLabel().getSubject() + " " + current.getLabel().getCatalogNum());
            List<String> semesterLocationList = new ArrayList<>();

            while (nextDumpIndex < dumpSize && sameSubjectCatalogNumber(current, dumpCourses.get(nextDumpIndex))) {
                Boolean needsComma = false;
                String StringOfFinalInstructors = " by ";
                for (String instructor : dumpCourses.get(nextDumpIndex-1).getInstructors()) { //Makes the teacher string
                    if(!StringOfFinalInstructors.contains(instructor)){
                        if (needsComma) {
                            StringOfFinalInstructors += ", " + instructor;
                        } else {
                            StringOfFinalInstructors += instructor;
                        }
                        needsComma = true;
                    }
                }
                String newSemesterLocation = dumpCourses.get(nextDumpIndex-1).getSemester().getYear() +
                        dumpCourses.get(nextDumpIndex-1).getSemester().getTerm() + " in " +
                        dumpCourses.get(nextDumpIndex-1).getLocation() + StringOfFinalInstructors;

                String typeAndSpace = "type=" + dumpCourses.get(nextDumpIndex-1).getLabel().getComponentCode() +
                        ", " + "Enrollment=" + dumpCourses.get(nextDumpIndex-1).getEnrollmentSpace().getTakenSeat() + "/" + dumpCourses.get(nextDumpIndex-1).getEnrollmentSpace().getCapacity() ;




                Boolean isAlreadyInList = false;
                if (semesterLocationList.size() > 0) { //"1147 in Burnaby by Anthony Dixon" is a string in this list.
                    for(String semestersLocation : semesterLocationList){
                        if(semestersLocation.equals(newSemesterLocation)){
                            isAlreadyInList = true;
                        }
                    }
                }
                if(isAlreadyInList){
                    //Checks if prev entry (like an array of teachers) doesnt contain the teacher about to be added
                    semesterLocationList.add("\n   " + typeAndSpace);
                }
                else{
                    semesterLocationList.add(newSemesterLocation);
                    semesterLocationList.add("\n   " + typeAndSpace);

                }

                    nextDumpIndex++;
            }

            if (semesterLocationList.size() == 0) { //Handle subjects that only appear once in list
                int i = 0;
                String instructors = " by ";
                for (String instructor : dumpCourses.get(nextDumpIndex-1).getInstructors()) {
                    if (i > 0) {
                        instructors += ", " + instructor;
                    } else {
                        instructors += instructor;
                    }
                    i++;
                }

                String semesterLocation = dumpCourses.get(nextDumpIndex-1).getSemester().getYear() +
                        dumpCourses.get(nextDumpIndex-1).getSemester().getTerm() +
                        " in " + dumpCourses.get(nextDumpIndex-1).getLocation() + instructors;

                semesterLocationList.add(semesterLocation);
            }

            for (String semesterLocation : semesterLocationList) { //prints out the locations, semesters, and who offers it
                System.out.println(semesterLocation);
            }
            semesterLocationList.clear(); //clears list before going to next subject
            currentDumpIndex = nextDumpIndex;
            nextDumpIndex++;
        }
    }

//    public void listDumpCourses2() {
//        int currentDumpIndex = 0;
//        int nextDumpIndex = 1;
//        int dumpSize = dumpCourses.size();
//        for ( CourseInfo course : dumpCourses) {
//            System.out.println(course);
//        }
//
//        System.out.println(dumpSize);
//        while (currentDumpIndex < dumpSize) {
//            CourseInfo current = dumpCourses.get(currentDumpIndex);
//
//            System.out.println(current.getLabel().getSubject() + " " + current.getLabel().getCatalogNum());
//            while (nextDumpIndex < dumpSize && sameSubjectCatalogNumber(current, dumpCourses.get(nextDumpIndex))) {
//
//                System.out.println(current.getSemester().getYear() + current.getSemester().getTerm() +
//                        " in " + current.getLocation() + " by " + current.getInstructors());
//                int nextDumpLocationYearTermIndex = nextDumpIndex;
//                while (nextDumpLocationYearTermIndex < dumpSize && sameLocation(current, dumpCourses.get(nextDumpLocationYearTermIndex)) && sameYearAndTerm(current, dumpCourses.get(nextDumpLocationYearTermIndex))) {
//                    System.out.println("TYPE=" + current.getLabel().getComponentCode());
//                    int nextComponentCodeIndex = nextDumpIndex;
//                    while(nextComponentCodeIndex < dumpSize && sameComponentCode(current, dumpCourses.get(nextComponentCodeIndex))) {
//                        nextComponentCodeIndex++;
//                    }
//                    nextDumpLocationYearTermIndex++;
//                }
//                nextDumpIndex++;
//            }
//            currentDumpIndex = nextDumpIndex;
//            nextDumpIndex++;
//            System.out.println("main while loop: " + nextDumpIndex + " with dumpSize " + dumpSize);
//        }
//
//    }

    private boolean sameLocation(CourseInfo current, CourseInfo next) {
//        System.out.println("current: " + current);
//        System.out.println("next: " + next);
//        System.out.println("equal: " + current.getLocation().equals(next.getLocation()));

        return current.getLocation().equals(next.getLocation());
    }

    private boolean sameYearAndTerm(CourseInfo current, CourseInfo next) {
        return (current.getSemester().getYear().equals(next.getSemester().getYear())) &&
                Character.getNumericValue(current.getSemester().getTerm()) == Character.getNumericValue(next.getSemester().getTerm());
    }

    private boolean sameSubjectCatalogNumber(CourseInfo current, CourseInfo next) {
        return current.getLabel().getSubject().equals((next.getLabel().getSubject())) &&
                current.getLabel().getCatalogNum().equals(next.getLabel().getCatalogNum());
    }

    private boolean sameComponentCode(CourseInfo current, CourseInfo next) {
        return current.getLabel().getComponentCode().equals(next.getLabel().getComponentCode());
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
                CourseInfo nextCourse = courses.get(nextCourseIndex);

                List<String> nextInstructors = nextCourse.getInstructors();
                for(String instructor : nextInstructors){
                    if(!currentInstructors.contains(instructor)) {
                        currentInstructors.add((instructor));
                    }
                }
                currentCapacity = currentCapacity + nextCourse.getEnrollmentSpace().getCapacity();
                currentTaken = currentTaken + nextCourse.getEnrollmentSpace().getTakenSeat();
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
