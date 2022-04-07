package com.example.Planner.Model;

import java.util.Comparator;
import java.util.List;

public class CourseInfo implements Comparable<CourseInfo>{
    List<String> instructors;
    Semester semester;
    Label label;
    EnrollmentSpace enrollmentSpace;
    String location;


    public CourseInfo(List<String> instructors, Semester semester,
                      Label label, EnrollmentSpace enrollmentSpace,
                      String location) {
        this.instructors = instructors;
        this.semester = semester;
        this.label = label;
        this.enrollmentSpace = enrollmentSpace;
        this.location = location;
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
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    @Override
    public String toString(){
        return semester.getYear()+ " , " + semester.getTerm() + " , " + label.dept + " , " + label.catalogNum
                + " , " + location + " , " + enrollmentSpace.takenSeat + "/" + enrollmentSpace.capacity + " , " + instructors + " , " + label.componentCode;
    }


    @Override
    public int compareTo(CourseInfo o) {
        int yearOne =  Integer.parseInt(o.semester.getYear());
        int yearTwo = Integer.parseInt(this.semester.getYear());


        if(yearOne > yearTwo){
            return 1;
        }
        else if(yearOne < yearTwo){
            return -1;
        }
        else{
            int termOne = Character.getNumericValue(this.getSemester().getTerm());
            int termTwo =  Character.getNumericValue(o.getSemester().getTerm());
            if (termOne > termTwo) {
                return 1;
            }
            else if (termOne < termTwo) {
                return -1;
            }
            else {
                String subjectOne = this.getLabel().getDept();
                String subjectTwo = o.getLabel().getDept();
                int compareSubject = subjectOne.compareTo(subjectTwo);

                if(compareSubject != 0){
                    return compareSubject;

                }
                else{
                    String catalogNumberOne = this.getLabel().getCatalogNum();
                    String catalogNumberTwo = o.getLabel().getCatalogNum();
                    int compareCatalogNumbers = catalogNumberOne.compareTo(catalogNumberTwo);

                    if(compareCatalogNumbers != 0){
                        return compareCatalogNumbers;
                    }
                    else{
                        String locationOne = this.getLocation();
                        String locationTwo = o.getLocation();
                        int compareLocation = locationOne.compareTo(locationTwo);

                        if (compareLocation != 0) {
                            return compareLocation;
                        }

                        else{
                            this.getInstructors().sort(new Comparator<String>() {
                                @Override
                                public int compare(String o1, String o2) {
                                    return o1.compareTo(o2);
                                }
                            });
                            o.getInstructors().sort(new Comparator<String>() {
                                @Override
                                public int compare(String o1, String o2) {
                                    return o1.compareTo(o2);
                                }
                            });
                            String instructorOne = this.getInstructors().get(0);
                            String instructorTwo  = o.getInstructors().get(0);
                            int instructorsCompare = instructorOne.compareTo(instructorTwo);
                            if(instructorsCompare != 0){
                                return instructorsCompare;
                            }
                            else{
                                String componentCodeOne = this.getLabel().getComponentCode();
                                String componentCodeTwo = o.getLabel().getComponentCode();
                                int compareComponentCode = componentCodeOne.compareTo(componentCodeTwo);
                                if (compareComponentCode != 0) {
                                    return compareComponentCode;
                                } else {
                                    return 0;
                                }
                            }
                        }
                    }


                }


            }

        }

    }


}
