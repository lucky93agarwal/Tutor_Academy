package com.mypersonal.tutor.anurag.register.customadapter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mypersonal.tutor.R;

public class StudentDetailActivity extends AppCompatActivity {

    TextView student_name,student_class,student_address,student_subject,student_weekSubject,student_city
             ,student_time,student_area,student_school;
    ImageView student_profile;
    Button interested,notIntrested;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail);

        initView();
        getStudentDetails();

    }

    private void getStudentDetails() {



    }

    private void initView() {

        student_name = (TextView) findViewById(R.id.Student_name);
        student_class = (TextView) findViewById(R.id.student_details_class);
        student_address = (TextView) findViewById(R.id.student_address);
        student_subject = (TextView) findViewById(R.id.subject_choise);
        student_weekSubject = (TextView) findViewById(R.id.student_week_subject);
        student_city = (TextView) findViewById(R.id.student_detail_city);
        student_time = (TextView) findViewById(R.id.time_availability);
        student_area = (TextView) findViewById(R.id.student_study_area);
        student_school = (TextView) findViewById(R.id.student_school);
        student_profile = (ImageView) findViewById(R.id.student_profile_pic);
        interested = (Button) findViewById(R.id.interested_teaching);
        notIntrested =(Button) findViewById(R.id.not_interested_teaching);

        interested.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInterest();
            }
        });

        notIntrested.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNotInterested();
            }
        });


    }

    private void showNotInterested() {

    }

    private void showInterest() {



    }


}