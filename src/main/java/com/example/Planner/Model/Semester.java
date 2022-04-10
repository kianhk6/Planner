package com.example.Planner.Model;
import java.util.*;

import static java.lang.Long.parseLong;

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

    public long makeSemesterCode() {
        String stringYear = year;
        String stringTerm = Character.toString(term);
        String stringYearTerm = stringYear + stringTerm;
        return parseLong(stringYearTerm);
    }
}
