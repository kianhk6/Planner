package com.example.Planner.Agents;

import com.example.Planner.Model.CourseInfo;
import com.example.Planner.Model.EnrollmentSpace;
import com.example.Planner.Model.Label;
import com.example.Planner.Model.Semester;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class CourseInfoGenerator {
    List<CourseInfo> courses = new ArrayList<>();
    List<CourseInfo> dumpCourses = new ArrayList<>();




    public CourseInfoGenerator(String path){
        setUpCourses(path);
        sortCourses();
        setUpDumpCourses();
        SortCoursesBasedOnSubject();
    }

    public void setDumpCourses(List<CourseInfo> dumpCourses) {
        this.dumpCourses = dumpCourses;
    }

    private void setUpCourses(String path) {
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

    public void printNormalCourses(){
        for(CourseInfo course : courses){
            System.out.println(course);
        }
    }
    public void printDumpCourses(){
        for(CourseInfo course : dumpCourses){
            System.out.println(course);
        }
    }

    public void listDumpCourses() {
        SortDumpCourseBasedOnSubject();
        int currentDumpIndex = 0;
        int nextDumpIndex = 1;
        int dumpSize = dumpCourses.size();

        for (CourseInfo course : dumpCourses) {
            System.out.println(course);
        }

        while (currentDumpIndex < dumpSize) {
            CourseInfo current = dumpCourses.get(currentDumpIndex);
            System.out.println(current.getLabel().getDept() + " " + current.getLabel().getCatalogNum());
            List<String> semesterLocationList = new ArrayList<>();
            String StringOfFirstInstructors = getDumpInstructors(currentDumpIndex);
            String FirstSemesterLocation = getDumpSemesterAndLocation(currentDumpIndex, StringOfFirstInstructors);
            semesterLocationList.add(FirstSemesterLocation);
            semesterLocationList.add(getDumpTypeAndSpace(currentDumpIndex));
            while (nextDumpIndex < dumpSize && sameSubjectCatalogNumber(current, dumpCourses.get(nextDumpIndex))) {
                String StringOfFinalInstructors = getDumpInstructors(nextDumpIndex);
                String newSemesterLocation = getDumpSemesterAndLocation(nextDumpIndex, StringOfFinalInstructors);
                String typeAndSpace = getDumpTypeAndSpace(nextDumpIndex);
                boolean isAlreadyThere = false;
                for(String semesterLocation : semesterLocationList){
                    if (semesterLocation.equals(newSemesterLocation)) {
                        isAlreadyThere = true;
                        break;
                    }
                }
                if(isAlreadyThere){
                    semesterLocationList.add(typeAndSpace);
                }
                else{
                    semesterLocationList.add(newSemesterLocation);
                    semesterLocationList.add(typeAndSpace);
                }
                nextDumpIndex++;
            }
            for (String semesterLocation : semesterLocationList) { //prints out the locations, semesters, and who offers it
                if( Character.isDigit(semesterLocation.charAt(0))){
                    System.out.println("    " + semesterLocation);
                }
                else{
                    System.out.println("        " + semesterLocation);
                }
            }
            semesterLocationList.clear(); //clears list before going to next subject
            currentDumpIndex = nextDumpIndex;
            nextDumpIndex++;
        }
    }

    public void SortCoursesBasedOnSubject() {
        courses.sort(new Comparator<CourseInfo>() {
            @Override
            public int compare(CourseInfo o1, CourseInfo o2) {
                String subjectOne = o1.getLabel().getDept();
                String subjectTwo = o2.getLabel().getDept();
                int compareSubject = subjectOne.compareTo(subjectTwo);

                if (compareSubject != 0) {
                    return compareSubject;
                } else {
                    String catalogNumberOne = o1.getLabel().getCatalogNum();
                    String catalogNumberTwo = o2.getLabel().getCatalogNum();
                    int compareCatalog = catalogNumberOne.compareTo(catalogNumberTwo);
                    if(compareCatalog != 0){
                        return compareCatalog;
                    } else { // for 0
                        int yearOne = Integer.parseInt(o1.getSemester().getYear());
                        int yearTwo = Integer.parseInt(o2.getSemester().getYear());

                        if (yearOne > yearTwo) {
                            return 1;
                        } else if (yearOne < yearTwo) {
                            return -1;
                        } else {
                            int termOne = Character.getNumericValue(o1.getSemester().getTerm());
                            int termTwo = Character.getNumericValue(o2.getSemester().getTerm());
                            if (termOne > termTwo) {
                                return 1;
                            } else if (termOne < termTwo) {
                                return -1;
                            }
                            return 0;
                        }
                    }
                }
            }});
    }

    public void SortCoursesBasedOnCatalogNumber() {
        courses.sort(new Comparator<CourseInfo>() {
            @Override
            public int compare(CourseInfo o1, CourseInfo o2) {
                String subjectOne = o1.getLabel().getDept();
                String subjectTwo = o2.getLabel().getDept();
                int compareSubject = subjectOne.compareTo(subjectTwo);

                if (compareSubject != 0) {
                    return compareSubject;
                } else {
                    String catalogNumberOne = o1.getLabel().getCatalogNum();
                    String catalogNumberTwo = o2.getLabel().getCatalogNum();
                    int compareCatalog = catalogNumberOne.compareTo(catalogNumberTwo);
                    if(compareCatalog != 0){
                        return compareCatalog;
                    } else { // for 0
                        int yearOne = Integer.parseInt(o1.getSemester().getYear());
                        int yearTwo = Integer.parseInt(o2.getSemester().getYear());

                        if (yearOne > yearTwo) {
                            return 1;
                        } else if (yearOne < yearTwo) {
                            return -1;
                        } else {
                            int termOne = Character.getNumericValue(o1.getSemester().getTerm());
                            int termTwo = Character.getNumericValue(o2.getSemester().getTerm());
                            if (termOne > termTwo) {
                                return 1;
                            } else if (termOne < termTwo) {
                                return -1;
                            }
                            return 0;
                        }
                    }
                }
            }});
    }

    public void SortDumpCourseBasedOnSubject() {
        dumpCourses.sort(new Comparator<CourseInfo>() {
            @Override
            public int compare(CourseInfo o1, CourseInfo o2) {
                String subjectOne = o1.getLabel().getDept();
                String subjectTwo = o2.getLabel().getDept();
                int compareSubject = subjectOne.compareTo(subjectTwo);

                if (compareSubject != 0) {
                    return compareSubject;
                } else {
                    String catalogNumberOne = o1.getLabel().getCatalogNum();
                    String catalogNumberTwo = o2.getLabel().getCatalogNum();
                    int compareCatalog = catalogNumberOne.compareTo(catalogNumberTwo);
                    if(compareCatalog != 0){
                        return compareCatalog;
                    } else { // for 0
                        int yearOne = Integer.parseInt(o1.getSemester().getYear());
                        int yearTwo = Integer.parseInt(o2.getSemester().getYear());

                        if (yearOne > yearTwo) {
                            return 1;
                        } else if (yearOne < yearTwo) {
                            return -1;
                        } else {
                            int termOne = Character.getNumericValue(o1.getSemester().getTerm());
                            int termTwo = Character.getNumericValue(o2.getSemester().getTerm());
                            if (termOne > termTwo) {
                                return 1;
                            } else if (termOne < termTwo) {
                                return -1;
                            }
                            return 0;
                        }
                    }
                }
            }});
    }

    private String getDumpSemesterAndLocation(int nextDumpIndex, String StringOfFinalInstructors) {
        return dumpCourses.get(nextDumpIndex).getSemester().getYear() +
                dumpCourses.get(nextDumpIndex).getSemester().getTerm() + " in " +
                dumpCourses.get(nextDumpIndex).getLocation() + StringOfFinalInstructors;
    }

    private String getDumpTypeAndSpace(int nextDumpIndex) {
        return "type=" + dumpCourses.get(nextDumpIndex).getLabel().getComponentCode() +
                ", " + "Enrollment=" + dumpCourses.get(nextDumpIndex).getEnrollmentSpace().getTakenSeat()
                + "/" + dumpCourses.get(nextDumpIndex).getEnrollmentSpace().getCapacity();
    }

    private String getDumpInstructors(int currentDumpIndex) {
        Boolean FirstNeedsComma = false;
        String StringOfFirstInstructors = " by ";
        for (String instructor : dumpCourses.get(currentDumpIndex).getInstructors()) { //Makes the teacher string
            if(!StringOfFirstInstructors.contains(instructor)){
                if (FirstNeedsComma) {
                    StringOfFirstInstructors += ", " + instructor;
                } else {
                    StringOfFirstInstructors += instructor;
                }
                FirstNeedsComma = true;
            }
        }
        return StringOfFirstInstructors;
    }

    private boolean sameSubjectCatalogNumber(CourseInfo current, CourseInfo next) {
        return current.getLabel().getDept().equals((next.getLabel().getDept())) &&
                current.getLabel().getCatalogNum().equals(next.getLabel().getCatalogNum());
    }

    public void sortCourses() {
        Collections.sort(courses);
    }

    public List<CourseInfo> getCourses() {
        return courses;
    }

    public List<CourseInfo> getDumpCourses() {
        return dumpCourses;
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
                current.getLabel().getDept().equals(next.getLabel().getDept()) &&
                current.getLabel().getCatalogNum().equals(next.getLabel().getCatalogNum()) &&
                current.getLabel().getComponentCode().equals(next.getLabel().getComponentCode()) &&
                current.getLocation().equals(next.getLocation());
    }
}
