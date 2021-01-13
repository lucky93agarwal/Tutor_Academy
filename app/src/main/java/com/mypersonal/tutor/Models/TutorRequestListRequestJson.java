package com.mypersonal.tutor.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TutorRequestListRequestJson {
    @SerializedName("tutorid")
    @Expose
    private String tutorid;


    public String getTutorid() {
        return tutorid;
    }

    public void setTutorid(String tutorid) {
        this.tutorid = tutorid;
    }
}
