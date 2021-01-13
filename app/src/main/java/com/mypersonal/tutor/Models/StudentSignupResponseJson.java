package com.mypersonal.tutor.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StudentSignupResponseJson {
    @SerializedName("success")
    @Expose
    public String success;




    @SerializedName("id")
    @Expose
    public String id;


    @SerializedName("image")
    @Expose
    public String image;
}
