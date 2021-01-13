package com.mypersonal.tutor.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RatingRequestJson {
    @SerializedName("tutor_id")
    @Expose
    private String tutor_id;

    @SerializedName("rating")
    @Expose
    private String rating;

    @SerializedName("student_id")
    @Expose
    private String student_id;


    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getTutor_id() {
        return tutor_id;
    }

    public void setTutor_id(String tutor_id) {
        this.tutor_id = tutor_id;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
