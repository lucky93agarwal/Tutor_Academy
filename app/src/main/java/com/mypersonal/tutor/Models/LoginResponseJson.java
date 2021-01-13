package com.mypersonal.tutor.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LoginResponseJson {
    @SerializedName("status")
    @Expose
    public String status;

    @SerializedName("message")
    @Expose
    public String message;


    @SerializedName("type")
    @Expose
    public String type;



    @Expose
    @SerializedName("data")
    public ArrayList<LoginDataList> datalist;
}
