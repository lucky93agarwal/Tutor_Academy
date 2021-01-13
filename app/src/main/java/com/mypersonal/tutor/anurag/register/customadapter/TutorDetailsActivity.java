package com.mypersonal.tutor.anurag.register.customadapter;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.mypersonal.tutor.Activities.MainActivity;
import com.mypersonal.tutor.Activities.Signup;
import com.mypersonal.tutor.JSONSchemas.CourseDetailsSchema;
import com.mypersonal.tutor.JSONSchemas.RequestDemoSchema;
import com.mypersonal.tutor.JSONSchemas.SectionSchema;
import com.mypersonal.tutor.JSONSchemas.StatusSchema;
import com.mypersonal.tutor.JSONSchemas.UserSchema;
import com.mypersonal.tutor.Models.Course;
import com.mypersonal.tutor.Models.CourseDetails;
import com.mypersonal.tutor.Models.Section;
import com.mypersonal.tutor.Models.User;
import com.mypersonal.tutor.Network.Api;
import com.mypersonal.tutor.R;
import com.mypersonal.tutor.Utils.Helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TutorDetailsActivity extends AppCompatActivity {

    TextView tutorName, tutorFees, tutorExpereience, tutorCity, tutorAddress, tutorContact,
            tutorTime, tutorSubject, tutorAvailability;
    ImageButton shareMe, likeMe, playVideo;
    Button requestDemo;
    ImageView tutorPic;
    private User mUser;
    private boolean requestStatus = false;
    private int tutorId;
    private int studentId;
    private int requestid;
    private ArrayList<User> mEachCourseDetail = new ArrayList<>();
    //  int courseId;
    UserSchema reqDetail;

    public String name,  image, fee, exp, subject, city, time, mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_details);

//       int courseId = (int) getIntent().getSerializableExtra("id");
        initView();
//        getTutorObject();


    }

    @SuppressLint("CheckResult")
    private void getTutorObject() {

        if (getIntent().hasExtra("id")) {
            int courseId = (int) getIntent().getSerializableExtra("id");
            loadTutorDetails(courseId);
        } else {

            mUser = (User) getIntent().getSerializableExtra("User");
            tutorName.setText(mUser.getName());
            tutorSubject.setText(mUser.getSubject());

            Glide.with(this)
                    .asBitmap()
                    .load(mUser.getPicture());

            tutorContact.setText(mUser.getEmail());
            tutorTime.setText(Float.toString(mUser.getTutorId()));
            loadTutorDetails(mUser.getTutorId());


        }


    }

    private void initView() {
        image = getIntent().getStringExtra("image");
        name = getIntent().getStringExtra("name");
        fee = getIntent().getStringExtra("fee");

        exp = getIntent().getStringExtra("exp");
        city = getIntent().getStringExtra("city");
        subject = getIntent().getStringExtra("subject");


        time = getIntent().getStringExtra("time");
        mobile = getIntent().getStringExtra("mobile");

        tutorName = (TextView) findViewById(R.id.tutor_name);
        tutorFees = (TextView) findViewById(R.id.tutor_fees_charge);
        tutorExpereience = (TextView) findViewById(R.id.total_experience);
        tutorCity = (TextView) findViewById(R.id.tutor_detail_city);
        tutorAddress = (TextView) findViewById(R.id.tutor_teaching_area);
        tutorContact = (TextView) findViewById(R.id.tutor_contact_no);
        tutorTime = (TextView) findViewById(R.id.time_duration);
        tutorAvailability = (TextView) findViewById(R.id.time_availability);
        tutorSubject = (TextView) findViewById(R.id.tutor_subject);
        shareMe = (ImageButton) findViewById(R.id.shareThisCourse);
        likeMe = (ImageButton) findViewById(R.id.wishlistThisCourse);
        playVideo = (ImageButton) findViewById(R.id.playCoursePreview);
        requestDemo = (Button) findViewById(R.id.buyThisCourseButton);
        tutorPic = (ImageView) findViewById(R.id.tutor_profile_pic);

        Glide.with(TutorDetailsActivity.this).load(image).placeholder(R.drawable.logo_light).into(tutorPic);
        tutorName.setText(name);
        tutorFees.setText(fee);

        tutorExpereience.setText(exp);
        tutorCity.setText(city);
        tutorSubject.setText(subject);

        tutorTime.setText(time);
        tutorContact.setText(mobile);


        requestDemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                studentId = mUser.getUserId();
                tutorId = mUser.getTutorId();
                sendMessage();
                sendDemoRequest();

            }
        });
    }

    private void sendDemoRequest() {


        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        Api api = retrofit.create(Api.class);
        Call<RequestDemoSchema> call = api.postRequestDemo(tutorId, studentId, requestStatus);

        call.enqueue(new Callback<RequestDemoSchema>() {
            @Override
            public void onResponse(Call<RequestDemoSchema> call, Response<RequestDemoSchema> response) {


                Log.d("WalletLucky1", "Response = " + response.code());

                Toast.makeText(TutorDetailsActivity.this, "Request Send Successfully", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(TutorDetailsActivity.this, MainActivity.class);
                startActivity(intent);

            }

            @Override
            public void onFailure(Call<RequestDemoSchema> call, Throwable t) {

                Toast.makeText(TutorDetailsActivity.this, "An Error Occured", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void sendMessage() {

    }

    private void loadTutorDetails(final int courseId) {

        SharedPreferences preferences = getSharedPreferences(Helpers.SHARED_PREF, 0);
        final String authToken = preferences.getString("userToken", "");
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        Api api = retrofit.create(Api.class);
        Call<List<UserSchema>> call = api.getTutorsDetails(courseId, authToken);
        call.enqueue(new Callback<List<UserSchema>>() {
            @Override
            public void onResponse(Call<List<UserSchema>> call, Response<List<UserSchema>> response) {
                List<UserSchema> tutorDetailsSchemas = response.body();
                Log.d("CourseDetailsHere", new Gson().toJson(tutorDetailsSchemas));

                if (response.isSuccessful()) {
                    Log.d("walletwallet", "res=" + response.body());
                    for (UserSchema d : tutorDetailsSchemas) {
                        if (d.getTutorId().equals(courseId)) {
                            reqDetail = d;

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {//update your views here
                                    tutorName.setText(reqDetail.getName());
                                    tutorSubject.setText(reqDetail.getSubject());
                                }
                            });

                            /*reqDetail will be having everything that you need and you can get it using the following code.
                            reqDetail.getName();
                            reqDetail.getAge();
                            reqDetail.getEmail();
                            reqDetail.getMobile();*/
                        }
                    }

                    //    tutorName.setText(mUser.getName());
                    //  tutorSubject.setText(mUser.getSubject());

                }

            }

            @Override
            public void onFailure(Call<List<UserSchema>> call, Throwable t) {
                Log.d("CourseDetailsHere", t.toString());
            }
        });

    }


}