package com.mypersonal.tutor.Models;

public class Students {
    public String id;
    public String student_name;
    public String email;
    public String mobile_no;
    public String school_name;
    public String classes;
    public String city;
    public String current_location;
    public String subjects;
    public String weak_subjects;
    public String suitable_timing;
    public Object upload_pic;
    public String notes;

    public Students(String id, String student_name, String email, String mobile_no,
                    String school_name, String classes, String city, String current_location,
                    String subjects, String weak_subjects,
                    String suitable_timing, Object upload_pic, String notes) {

        this.id = id;
        this.student_name = student_name;
        this.email = email;
        this.mobile_no = mobile_no;
        this.school_name = school_name;
        this.classes = classes;
        this.city = city;
        this.current_location = current_location;
        this.subjects = subjects;
        this.weak_subjects = weak_subjects;
        this.suitable_timing = suitable_timing;
        this.upload_pic = upload_pic;
        this.notes = notes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public Object getUpload_pic() {
        return upload_pic;
    }

    public void setUpload_pic(Object upload_pic) {
        this.upload_pic = upload_pic;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
