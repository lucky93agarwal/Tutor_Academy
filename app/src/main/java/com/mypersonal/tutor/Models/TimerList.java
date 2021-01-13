package com.mypersonal.tutor.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TimerList {
    @Expose
    @SerializedName("id")
    public String id;


    @Expose
    @SerializedName("time")
    public String time;
}
