package com.mypersonal.tutor.JSONSchemas;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TutorSchema implements Serializable {

    @SerializedName("user_id")
    @Expose
    private Integer userId;

    @SerializedName("name")
    @Expose
    private String tutorName;
    @SerializedName("email")
    @Expose
    private String tutorEmail;
    @SerializedName("mobile_no")
    @Expose
    private String tutorMobile;
    @SerializedName("qualification")
    @Expose
    private String qualification;
    @SerializedName("experience_in_teaching")
    @Expose
    private String tutorExperience;
    @SerializedName("subjects_offered")
    @Expose
    private String tutorSubjects;
    @SerializedName("current_city")
    @Expose
    private String tutorCity;
    @SerializedName("area_of_teaching")
    @Expose
    private String tutorArea;
    @SerializedName("time_duration_alloted_to_students")
    @Expose
    private String timeDuration;
    @SerializedName("fees")
    @Expose
    private String tutorFees;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTutorName() {
        return tutorName;
    }

    public void setTutorName(String tutorName) {
        this.tutorName = tutorName;
    }

    public String getTutorEmail() {
        return tutorEmail;
    }

    public void setTutorEmail(String tutorEmail) {
        this.tutorEmail = tutorEmail;
    }

    public String getTutorMobile() {
        return tutorMobile;
    }

    public void setTutorMobile(String tutorMobile) {
        this.tutorMobile = tutorMobile;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getTutorExperience() {
        return tutorExperience;
    }

    public void setTutorExperience(String tutorExperience) {
        this.tutorExperience = tutorExperience;
    }

    public String getTutorSubjects() {
        return tutorSubjects;
    }

    public void setTutorSubjects(String tutorSubjects) {
        this.tutorSubjects = tutorSubjects;
    }

    public String getTutorCity() {
        return tutorCity;
    }

    public void setTutorCity(String tutorCity) {
        this.tutorCity = tutorCity;
    }

    public String getTutorArea() {
        return tutorArea;
    }

    public void setTutorArea(String tutorArea) {
        this.tutorArea = tutorArea;
    }

    public String getTimeDuration() {
        return timeDuration;
    }

    public void setTimeDuration(String timeDuration) {
        this.timeDuration = timeDuration;
    }

    public String getTutorFees() {
        return tutorFees;
    }

    public void setTutorFees(String tutorFees) {
        this.tutorFees = tutorFees;
    }

    public String getTutorImage() {
        return tutorImage;
    }

    public void setTutorImage(String tutorImage) {
        this.tutorImage = tutorImage;
    }

    @SerializedName("upload_pic")
    @Expose
    private String tutorImage;



}
