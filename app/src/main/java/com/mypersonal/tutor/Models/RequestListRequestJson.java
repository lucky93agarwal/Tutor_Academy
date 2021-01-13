package com.mypersonal.tutor.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestListRequestJson {
    @SerializedName("tutorid")
    @Expose
    private String tutorid;

    @SerializedName("studentid")
    @Expose
    private String studentid;

    @SerializedName("msg")
    @Expose
    private String msg;


    @SerializedName("time")
    @Expose
    private String time;

    @SerializedName("subject")
    @Expose
    private String subject;


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTutorid() {
        return tutorid;
    }

    public void setTutorid(String tutorid) {
        this.tutorid = tutorid;
    }

    public String getStudentid() {
        return studentid;
    }

    public void setStudentid(String studentid) {
        this.studentid = studentid;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
