package com.mypersonal.tutor.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TutorSignupRequestJson {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("mobile_no")
    @Expose
    private String mobile_no;

    @SerializedName("age")
    @Expose
    private String age;

    @SerializedName("qualification")
    @Expose
    private String qualification;

    @SerializedName("experience_in_teaching")
    @Expose
    private String experience_in_teaching;

    @SerializedName("subjects_offered")
    @Expose
    private String subjects_offered;

    @SerializedName("any_specialization")
    @Expose
    private String any_specialization;

    @SerializedName("current_city")
    @Expose
    private String current_city;

    @SerializedName("area_of_teaching")
    @Expose
    private String area_of_teaching;


    @SerializedName("time_duration_alloted_to_students")
    @Expose
    private String time_duration_alloted_to_students;



    @SerializedName("fees")
    @Expose
    private String fees;


    @SerializedName("upload_pic")
    @Expose
    private String upload_pic;


    @SerializedName("transectionid")
    @Expose
    private String transectionid;



    @SerializedName("day")
    @Expose
    private String day;


    @SerializedName("Willing_to_travel")
    @Expose
    private String Willing_to_travel;


    @SerializedName("tutortyple")
    @Expose
    private String tutortyple;


    @SerializedName("transaction_status")
    @Expose
    private String transaction_status;

    @SerializedName("one_doc")
    @Expose
    private String one_doc;

    @SerializedName("two_doc")
    @Expose
    private String two_doc;

    @SerializedName("three_doc")
    @Expose
    private String three_doc;

    @SerializedName("four_doc")
    @Expose
    private String four_doc;

    @SerializedName("board")
    @Expose
    private String board;




    @SerializedName("board_date")
    @Expose
    private String board_date;



    @SerializedName("school_date")
    @Expose
    private String school_date;


    @SerializedName("school_name")
    @Expose
    private String school_name;


    @SerializedName("clg_name")
    @Expose
    private String clg_name;


    @SerializedName("clg_date")
    @Expose
    private String clg_date;


    public String getSchool_date() {
        return school_date;
    }

    public void setSchool_date(String school_date) {
        this.school_date = school_date;
    }

    public String getSchool_name() {
        return school_name;
    }

    public void setSchool_name(String school_name) {
        this.school_name = school_name;
    }

    public String getClg_name() {
        return clg_name;
    }

    public void setClg_name(String clg_name) {
        this.clg_name = clg_name;
    }

    public String getClg_date() {
        return clg_date;
    }

    public void setClg_date(String clg_date) {
        this.clg_date = clg_date;
    }

    public String getBoard_date() {
        return board_date;
    }

    public void setBoard_date(String board_date) {
        this.board_date = board_date;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public String getOne_doc() {
        return one_doc;
    }

    public void setOne_doc(String one_doc) {
        this.one_doc = one_doc;
    }

    public String getTwo_doc() {
        return two_doc;
    }

    public void setTwo_doc(String two_doc) {
        this.two_doc = two_doc;
    }

    public String getThree_doc() {
        return three_doc;
    }

    public void setThree_doc(String three_doc) {
        this.three_doc = three_doc;
    }

    public String getFour_doc() {
        return four_doc;
    }

    public void setFour_doc(String four_doc) {
        this.four_doc = four_doc;
    }

    public String getTransaction_status() {
        return transaction_status;
    }

    public void setTransaction_status(String transaction_status) {
        this.transaction_status = transaction_status;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getWilling_to_travel() {
        return Willing_to_travel;
    }

    public void setWilling_to_travel(String willing_to_travel) {
        Willing_to_travel = willing_to_travel;
    }

    public String getTutortyple() {
        return tutortyple;
    }

    public void setTutortyple(String tutortyple) {
        this.tutortyple = tutortyple;
    }

    public String getTransectionid() {
        return transectionid;
    }

    public void setTransectionid(String transectionid) {
        this.transectionid = transectionid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getExperience_in_teaching() {
        return experience_in_teaching;
    }

    public void setExperience_in_teaching(String experience_in_teaching) {
        this.experience_in_teaching = experience_in_teaching;
    }

    public String getSubjects_offered() {
        return subjects_offered;
    }

    public void setSubjects_offered(String subjects_offered) {
        this.subjects_offered = subjects_offered;
    }

    public String getAny_specialization() {
        return any_specialization;
    }

    public void setAny_specialization(String any_specialization) {
        this.any_specialization = any_specialization;
    }

    public String getCurrent_city() {
        return current_city;
    }

    public void setCurrent_city(String current_city) {
        this.current_city = current_city;
    }

    public String getArea_of_teaching() {
        return area_of_teaching;
    }

    public void setArea_of_teaching(String area_of_teaching) {
        this.area_of_teaching = area_of_teaching;
    }

    public String getTime_duration_alloted_to_students() {
        return time_duration_alloted_to_students;
    }

    public void setTime_duration_alloted_to_students(String time_duration_alloted_to_students) {
        this.time_duration_alloted_to_students = time_duration_alloted_to_students;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public String getUpload_pic() {
        return upload_pic;
    }

    public void setUpload_pic(String upload_pic) {
        this.upload_pic = upload_pic;
    }
}
