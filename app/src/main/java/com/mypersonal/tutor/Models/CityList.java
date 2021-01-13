package com.mypersonal.tutor.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CityList {
    @Expose
    @SerializedName("id")
    public String name;


    @Expose
    @SerializedName("city_name")
    public String city_name;
}
