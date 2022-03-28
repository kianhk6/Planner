package com.example.Planner.Model;

public class EnrollmentSpace {
    int capacity;
    int takenSeats;

    public EnrollmentSpace(int capacity, int takenSeats) {
        this.capacity = capacity;
        this.takenSeats = takenSeats;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getTakenSeats() {
        return takenSeats;
    }

    public void setTakenSeats(int takenSeats) {
        this.takenSeats = takenSeats;
    }
}
