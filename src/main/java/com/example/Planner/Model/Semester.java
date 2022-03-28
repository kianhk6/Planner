package com.example.Planner.Model;

public class Semester {
    int year;
    int term;

    public Semester(int year, int term) {
        this.year = year;
        this.term = term;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public int getYear() {
        return year;
    }

    public int getTerm() {
        return term;
    }
}
