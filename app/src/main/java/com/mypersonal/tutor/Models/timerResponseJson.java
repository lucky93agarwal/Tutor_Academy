package com.mypersonal.tutor.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class timerResponseJson {
    @Expose
    @SerializedName("status")
    public String status;


    @Expose
    @SerializedName("message")
    public String message;



    @Expose
    @SerializedName("data")
    public ArrayList<TimerList> datalist;
}
