package com.mypersonal.tutor.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TutorRazorpayRequestJson {
    @SerializedName("tutorid")
    @Expose
    private String tutorid;

    @SerializedName("fee")
    @Expose
    private String fee;


    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getTutorid() {
        return tutorid;
    }

    public void setTutorid(String tutorid) {
        this.tutorid = tutorid;
    }
}
