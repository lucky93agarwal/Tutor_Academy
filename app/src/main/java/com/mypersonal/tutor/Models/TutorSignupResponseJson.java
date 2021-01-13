package com.mypersonal.tutor.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TutorSignupResponseJson {
    @SerializedName("success")
    @Expose
    public String success;

    @SerializedName("image")
    @Expose
    public String image;


    @SerializedName("id")
    @Expose
    public String id;
}
