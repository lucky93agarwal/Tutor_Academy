package com.mypersonal.tutor.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TutorList {
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
    @SerializedName("age")
    public String age;


    @Expose
    @SerializedName("qualification")
    public String qualification;


    @Expose
    @SerializedName("experience_in_teaching")
    public String experience_in_teaching;


    @Expose
    @SerializedName("subjects_offered")
    public String subjects_offered;


    @Expose
    @SerializedName("any_specialization")
    public String any_specialization;

    @Expose
    @SerializedName("current_city")
    public String current_city;


    @Expose
    @SerializedName("area_of_teaching")
    public String area_of_teaching;


    @Expose
    @SerializedName("time_duration_alloted_to_students")
    public String time_duration_alloted_to_students;


    @Expose
    @SerializedName("fees")
    public String fees;



    @Expose
    @SerializedName("upload_pic")
    public String upload_pic;


    @Expose
    @SerializedName("tutor_id")
    public String tutor_id;



    @Expose
    @SerializedName("rating")
    public String rating;






    @Expose
    @SerializedName("day")
    public String day;


    @Expose
    @SerializedName("tuition_type")
    public String tuition_type;



    @Expose
    @SerializedName("willing_to_traveling")
    public String willing_to_traveling;




    @Expose
    @SerializedName("transaction_status")
    public String transaction_status;
}
