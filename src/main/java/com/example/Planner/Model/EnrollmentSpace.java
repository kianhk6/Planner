package com.example.Planner.Model;

public class EnrollmentSpace {
    int capacity;
    int takenSeat;

    public EnrollmentSpace(int capacity, int takenSeats) {
        this.capacity = capacity;
        this.takenSeat = takenSeats;
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
