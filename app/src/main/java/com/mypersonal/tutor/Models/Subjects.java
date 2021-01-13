package com.mypersonal.tutor.Models;

public class Subjects {

    private int id;
    private String subjectName;

    public Subjects(int id, String subjectName) {
        this.id = id;
        this.subjectName = subjectName;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getsubjectName() {
        return subjectName;
    }

    public void setsubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

}
