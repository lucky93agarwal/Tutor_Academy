package com.mypersonal.tutor.Models;

public class RequestDemo {
    private int tutorId;
    private int studentId;
    private boolean requestStatus;

    public RequestDemo(int tutorId, int studentId, boolean requestStatus) {
        this.tutorId = tutorId;
        this.studentId = studentId;
        this.requestStatus = requestStatus;
    }

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
