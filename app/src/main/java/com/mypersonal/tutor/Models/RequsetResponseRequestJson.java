package com.mypersonal.tutor.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequsetResponseRequestJson {


    @Expose
    @SerializedName("status")
    public String status;


    @Expose
    @SerializedName("message")
    public String message;
}
