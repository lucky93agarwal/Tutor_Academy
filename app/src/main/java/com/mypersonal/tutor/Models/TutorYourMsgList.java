package com.mypersonal.tutor.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TutorYourMsgList {

    @Expose
    @SerializedName("student_name")
    public String student_name;


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
    @SerializedName("subjects")
    public String subjects;


    @Expose
    @SerializedName("weak_subjects")
    public String weak_subjects;



    @Expose
    @SerializedName("suitable_timing")
    public String suitable_timing;


    @Expose
    @SerializedName("notes")
    public String notes;


    @Expose
    @SerializedName("upload_pic")
    public String upload_pic;


    @Expose
    @SerializedName("student_id")
    public String student_id;


    @Expose
    @SerializedName("msg")
    public String msg;





    @Expose
    @SerializedName("status_msg")
    public String status_msg;


    @Expose
    @SerializedName("req_msg")
    public String req_msg;



    @Expose
    @SerializedName("board")
    public String board;
}
