package com.mypersonal.tutor.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class messageRequestJson {
    @SerializedName("tutorid")
    @Expose
    private String tutorid;

    @SerializedName("studentid")
    @Expose
    private String studentid;

    @SerializedName("msg")
    @Expose
    private String msg;


    @SerializedName("msg_id")
    @Expose
    private String msg_id;


    public String getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(String msg_id) {
        this.msg_id = msg_id;
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
