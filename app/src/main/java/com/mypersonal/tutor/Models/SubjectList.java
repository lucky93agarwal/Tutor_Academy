package com.mypersonal.tutor.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubjectList {
    @Expose
    @SerializedName("id")
    public String id;


    @Expose
    @SerializedName("subject")
    public String subject;


    @Expose
    @SerializedName("count")
    public String count;
}
