package com.example.Planner.Model;

public class EnrollmentSpace {
    String dept;
    int capacity;
    int takenSeat;
    long deptId;
    long courseId;
    long courseOfferingId;

    public EnrollmentSpace(int capacity, int takenSeats) {
        this.capacity = capacity;
        this.takenSeat = takenSeats;
        this.deptId = 0;
    }

    public long getDeptId() {
        return deptId;
    }

    public String getDept() {
        return dept;
    }

    public long getCourseOfferingId() {
        return courseOfferingId;
    }

    public void setCourseOfferingId(long courseOfferingId) {
        this.courseOfferingId = courseOfferingId;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getTakenSeat() {
        return takenSeat;
    }

    public void setTakenSeat(int takenSeat) {
        this.takenSeat = takenSeat;
    }
}
