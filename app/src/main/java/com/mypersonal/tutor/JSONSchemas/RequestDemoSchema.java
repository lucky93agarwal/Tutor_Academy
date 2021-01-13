package com.mypersonal.tutor.JSONSchemas;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RequestDemoSchema implements Serializable {

    @SerializedName("tutor_id")
    @Expose
    private int tutorId;
    @SerializedName("student_id")
    @Expose
    private int studentId;
    @SerializedName("request_status")
    @Expose
    private boolean requestStatus;

    public int getTutorId() {
        return tutorId;
    }

    public void setTutorId(int tutorId) {
        this.tutorId = tutorId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public boolean isRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(boolean requestStatus) {
        this.requestStatus = requestStatus;
    }
}
