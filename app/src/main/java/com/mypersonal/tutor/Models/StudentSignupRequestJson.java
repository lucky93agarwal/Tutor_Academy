package com.mypersonal.tutor.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StudentSignupRequestJson {
    @SerializedName("student_name")
    @Expose
    private String student_name;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("mobile_no")
    @Expose
    private String mobile_no;

    @SerializedName("school_name")
    @Expose
    private String school_name;

    @SerializedName("class")
    @Expose
    private String classs;

    @SerializedName("current_location")
    @Expose
    private String current_location;

    @SerializedName("subjects")
    @Expose
    private String subjects;

    @SerializedName("weak_subjects")
    @Expose
    private String weak_subjects;

    @SerializedName("suitable_timing")
    @Expose
    private String suitable_timing;

    @SerializedName("upload_pic")
    @Expose
    private String upload_pic;


    @SerializedName("notes")
    @Expose
    private String notes;



    @SerializedName("city")
    @Expose
    private String city;




    @SerializedName("board")
    @Expose
    private String board;


    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getSchool_name() {
        return school_name;
    }

    public void setSchool_name(String school_name) {
        this.school_name = school_name;
    }

    public String getClasss() {
        return classs;
    }

    public void setClasss(String classs) {
        this.classs = classs;
    }

    public String getCurrent_location() {
        return current_location;
    }

    public void setCurrent_location(String current_location) {
        this.current_location = current_location;
    }

    public String getSubjects() {
        return subjects;
    }

    public void setSubjects(String subjects) {
        this.subjects = subjects;
    }

    public String getWeak_subjects() {
        return weak_subjects;
    }

    public void setWeak_subjects(String weak_subjects) {
        this.weak_subjects = weak_subjects;
    }

    public String getSuitable_timing() {
        return suitable_timing;
    }

    public void setSuitable_timing(String suitable_timing) {
        this.suitable_timing = suitable_timing;
    }

    public String getUpload_pic() {
        return upload_pic;
    }

    public void setUpload_pic(String upload_pic) {
        this.upload_pic = upload_pic;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
