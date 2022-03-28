package com.example.Planner.Model;

public class Semester {
    String year;
    char term;

    public Semester(String year, char term) {
        this.year = year;
        this.term = term;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setTerm(char term) {
        this.term = term;
    }

    public String getYear() {
        return year;
    }

    public char getTerm() {
        return term;
    }
}
