package com.mypersonal.tutor.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TutorRequestList {
    @Expose
    @SerializedName("name")
    public String name;


    @Expose
    @SerializedName("email")
    public String email;


    @Expose
    @SerializedName("mobile_no")
    public String mobile_no;


    @Expose
    @SerializedName("school_name")
    public String school_name;



    @Expose
    @SerializedName("class")
    public String classs;


    @Expose
    @SerializedName("city")
    public String city;


    @Expose
    @SerializedName("current_location")
    public String current_location;


    @Expose
    @SerializedName("student_id")
    public String student_id;



    @Expose
    @SerializedName("notes")
    public String notes;



    @Expose
    @SerializedName("upload_pic")
    public String upload_pic;


    @Expose
    @SerializedName("suitable_timing")
    public String suitable_timing;


    @Expose
    @SerializedName("weak_subjects")
    public String weak_subjects;


    @Expose
    @SerializedName("subjects")
    public String subjects;



    @Expose
    @SerializedName("req_subjects")
    public String req_subjects;


    @Expose
    @SerializedName("req_timing")
    public String req_timing;



    @Expose
    @SerializedName("msg")
    public String msg;



    @Expose
    @SerializedName("status")
    public String status;


    @Expose
    @SerializedName("request_id")
    public String request_id;


    @Expose
    @SerializedName("board")
    public String board;
}
