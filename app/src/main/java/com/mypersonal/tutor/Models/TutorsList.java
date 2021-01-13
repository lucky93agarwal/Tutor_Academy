package com.mypersonal.tutor.Models;

public class TutorsList {
    public int id;
    public String name;
    public String email;
    public Object mobile_no;
    public String age;
    public String qualification;
    public String experience_in_teaching;
    public String subjects_offered;
    public String any_specialization;
    public String current_city;
    public String area_of_teaching;
    public String time_duration_alloted_to_students;
    public String fees;
    public Object upload_pic;

    public TutorsList(int id, String name, String email, Object mobile_no,
                      String age, String qualification, String experience_in_teaching,
                      String subjects_offered, String any_specialization, String current_city,
                      String area_of_teaching, String time_duration_alloted_to_students,
                      String fees, Object upload_pic) {

        this.id = id;
        this.name = name;
        this.email = email;
        this.mobile_no = mobile_no;
        this.age = age;
        this.qualification = qualification;
        this.experience_in_teaching = experience_in_teaching;
        this.subjects_offered = subjects_offered;
        this.any_specialization = any_specialization;
        this.current_city = current_city;
        this.area_of_teaching = area_of_teaching;
        this.time_duration_alloted_to_students = time_duration_alloted_to_students;
        this.fees = fees;
        this.upload_pic = upload_pic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Object getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(Object mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getExperience_in_teaching() {
        return experience_in_teaching;
    }

    public void setExperience_in_teaching(String experience_in_teaching) {
        this.experience_in_teaching = experience_in_teaching;
    }

    public String getSubjects_offered() {
        return subjects_offered;
    }

    public void setSubjects_offered(String subjects_offered) {
        this.subjects_offered = subjects_offered;
    }

    public String getAny_specialization() {
        return any_specialization;
    }

    public void setAny_specialization(String any_specialization) {
        this.any_specialization = any_specialization;
    }

    public String getCurrent_city() {
        return current_city;
    }

    public void setCurrent_city(String current_city) {
        this.current_city = current_city;
    }

    public String getArea_of_teaching() {
        return area_of_teaching;
    }

    public void setArea_of_teaching(String area_of_teaching) {
        this.area_of_teaching = area_of_teaching;
    }

    public String getTime_duration_alloted_to_students() {
        return time_duration_alloted_to_students;
    }

    public void setTime_duration_alloted_to_students(String time_duration_alloted_to_students) {
        this.time_duration_alloted_to_students = time_duration_alloted_to_students;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public Object getUpload_pic() {
        return upload_pic;
    }

    public void setUpload_pic(Object upload_pic) {
        this.upload_pic = upload_pic;
    }
}

