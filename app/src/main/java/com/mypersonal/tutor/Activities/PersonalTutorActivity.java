package com.mypersonal.tutor.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.mypersonal.tutor.Adapters.tutorlsitAdapter;
import com.mypersonal.tutor.Models.GetTutorData;
import com.mypersonal.tutor.Models.RatingRequestJson;
import com.mypersonal.tutor.Models.RequestListRequestJson;
import com.mypersonal.tutor.Models.RequestResponseJson;
import com.mypersonal.tutor.Models.TutorListRequestJson;
import com.mypersonal.tutor.Models.TutorListResponseJson;
import com.mypersonal.tutor.R;
import com.mypersonal.tutor.anurag.register.customadapter.TutorDetailsActivity;
import com.mypersonal.tutor.popup.msgpopup;
import com.mypersonal.tutor.retrofit.ServiceGenerator;
import com.mypersonal.tutor.retrofit.UserService;

import retrofit2.Call;
import retrofit2.Callback;

public class PersonalTutorActivity extends AppCompatActivity {

    public ImageView ivimg;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor edits;
    public TextView tvname,tvemail,tvmobile,
            tvexpi, tvqty, tvcitytv,tvexpir,
            tvsubject, tvrequest,tvage,tvcityss,
    tvwilling_to_travelingtv,
    tvtutortypetv;
    ProgressDialog progressBars;
    public String name,  image, fee, exp, subject, city, time, mobile, email, qtys, student_id, tutor_id, rating;
    public LinearLayout btnrequest;
    public EditText etmssage;
    public RatingBar mratingbar;
    public LinearLayout linearLayout;
    public String ratings;
    AlertDialog.Builder builder;
    public ImageView ivedit;
    private static msgpopup addpopup;
    public boolean check= false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_tutor);
        addpopup = new msgpopup(PersonalTutorActivity.this);


        progressBars = new ProgressDialog(this);
        progressBars.setCancelable(true);
        progressBars.setMessage("Please wait ...");
        ivimg = (ImageView)findViewById(R.id.imageview);
        builder = new AlertDialog.Builder(this);
        ivedit = (ImageView)findViewById(R.id.editicon);
        rating = getIntent().getStringExtra("rating");
        tvwilling_to_travelingtv = (TextView)findViewById(R.id.willing_to_travelingtv);

        mratingbar = (RatingBar)findViewById(R.id.ratingbar);
        mratingbar.setEnabled(false);
        mratingbar.setRating(Float.parseFloat(rating));

        tvage = (TextView)findViewById(R.id.agetv);
        tvcityss = (TextView)findViewById(R.id.citytvs);
        tvtutortypetv =(TextView)findViewById(R.id.tutortypetv);

        ivedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(PersonalTutorActivity.this, ReserveRequestActivity.class);
//                intent.putExtra("id",tutor_id);
//                intent.putExtra("type","2");
//                startActivity(intent);
                addpopup.addpopup(tutor_id);
            }
        });




        mratingbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratings = String.valueOf(mratingbar.getRating());

//                Toast.makeText(PersonalTutorActivity.this, ratings, Toast.LENGTH_LONG).show();

                //Uncomment the below code to Set the message and title from the strings.xml file
                builder.setMessage("") .setTitle("Send Rating");

                //Setting message manually and performing action on button click
                builder.setMessage("Do you want to submit rating?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                                sendrating();

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();

                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Rating");
                alert.show();


            }
        });



        etmssage = (EditText)findViewById(R.id.msget);
        tvname = (TextView)findViewById(R.id.tvnametv);
        sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
        edits = sharedPreferences.edit();

        tvcitytv = (TextView)findViewById(R.id.citytv);

        tvrequest = (TextView)findViewById(R.id.requesttv);
        tvrequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalTutorActivity.this, ReserveRequestActivity.class);
                intent.putExtra("id",tutor_id);
                intent.putExtra("type","1");
                startActivity(intent);
//                sendrequest();
            }
        });

        btnrequest = (LinearLayout)findViewById(R.id.requestll);

        btnrequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalTutorActivity.this, ReserveRequestActivity.class);
                intent.putExtra("id",tutor_id);
                intent.putExtra("type","1");
                startActivity(intent);
//                sendrequest();
            }
        });


        tvemail = (TextView)findViewById(R.id.emailtv);
        tvmobile = (TextView)findViewById(R.id.mobiletv);

        tvexpi = (TextView)findViewById(R.id.expitv);

        tvexpir = (TextView)findViewById(R.id.exeptv);

        tvsubject = (TextView)findViewById(R.id.subjectstv);

        tvqty = (TextView)findViewById(R.id.qtytv);






        image = getIntent().getStringExtra("image");
        name = getIntent().getStringExtra("name");
        fee = getIntent().getStringExtra("fee");

        exp = getIntent().getStringExtra("exp");
        city = getIntent().getStringExtra("city");
        subject = getIntent().getStringExtra("subject");


        time = getIntent().getStringExtra("time");
        mobile = getIntent().getStringExtra("mobile");


        qtys = getIntent().getStringExtra("qty");
        tutor_id = getIntent().getStringExtra("id");
        email = getIntent().getStringExtra("email");

        tvname.setText(name);
        tvmobile.setText(mobile);
        tvcityss.setText(city);
        tvexpi.setText(exp+" Year");
        tvexpir.setText(getIntent().getStringExtra("day"));
        tvwilling_to_travelingtv.setText(getIntent().getStringExtra("willing_to_traveling"));
        tvqty.setText(qtys);
        tvtutortypetv.setText(getIntent().getStringExtra("tuition_type"));
        tvage.setText(subject);
        tvcitytv.setText(time);
        tvemail.setText(email);
        tvsubject.setText(subject);
        Glide.with(PersonalTutorActivity.this).load(image).placeholder(R.drawable.logo_light).into(ivimg);
        sendrequest();

        Log.d("sljdflsjlfjsjflsjf","status  === "+getIntent().getStringExtra("transaction_status"));

        if(getIntent().getStringExtra("transaction_status").equalsIgnoreCase("0")){

            Log.d("sljdflsjlfjsjflsjf","786  === ");
            findViewById(R.id.emailtvs).setVisibility(View.GONE);
            findViewById(R.id.mobiletvs).setVisibility(View.GONE);
            tvemail.setVisibility(View.GONE);

            tvmobile.setVisibility(View.GONE);
        }
    }

    private void sendrating() {

        progressBars.show();
        final RatingRequestJson request = new RatingRequestJson();
        request.setTutor_id(tutor_id);
        request.setRating(ratings);
        request.setStudent_id(sharedPreferences.getString("id",""));

        Log.d("StatusLuccky","requst  ="+new Gson().toJson(request));

        UserService service = ServiceGenerator.createService(UserService.class, null, null);
        service.rating(request).enqueue(new Callback<RequestResponseJson>() {
            @Override
            public void onResponse(Call<RequestResponseJson> call, retrofit2.Response<RequestResponseJson> response) {
                Log.d("StatusLuccky", "id Status =" + response.code());
                if (response.isSuccessful()) {
                    Log.d("StatusLuccky", "id Status =" + response.code());
                    if (response.body().status.equalsIgnoreCase("0")) {
                        progressBars.dismiss();
                        Toast.makeText(PersonalTutorActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    } else {
                        progressBars.dismiss();
                        Toast.makeText(PersonalTutorActivity.this, "your rating has been submitted successfully.", Toast.LENGTH_SHORT).show();

                    }


                } else {
                    switch (response.code()) {
                        case 401:
                            Toast.makeText(PersonalTutorActivity.this, "email and pass not check", Toast.LENGTH_SHORT).show();
                            break;
                        case 403:
                            Toast.makeText(PersonalTutorActivity.this, "ForbiddenException", Toast.LENGTH_SHORT).show();
                            break;
                        case 404:
                            Toast.makeText(PersonalTutorActivity.this, "not found", Toast.LENGTH_SHORT).show();
                            break;
                        case 500:
                            Toast.makeText(PersonalTutorActivity.this, "server broken", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(PersonalTutorActivity.this, "unknown error", Toast.LENGTH_SHORT).show();
                            break;

                    }
                    Toast.makeText(PersonalTutorActivity.this, "Something error please try again", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<RequestResponseJson> call, Throwable t) {
                t.printStackTrace();


                Log.e("System error:", t.getLocalizedMessage());

            }
        });
    }

    private void sendrequest() {
        progressBars.show();
        String rating = "Rating is :" + mratingbar.getRating();

//        Toast.makeText(PersonalTutorActivity.this, rating, Toast.LENGTH_LONG).show();

        final RequestListRequestJson request = new RequestListRequestJson();
        request.setStudentid(sharedPreferences.getString("id",""));
        request.setTutorid(tutor_id);
        Log.d("StatusLucckyYUYU","requst  ="+new Gson().toJson(request));

        UserService service = ServiceGenerator.createService(UserService.class, null, null);
        service.check_get(request).enqueue(new Callback<RequestResponseJson>() {
            @Override
            public void onResponse(Call<RequestResponseJson> call, retrofit2.Response<RequestResponseJson> response) {
                Log.d("StatusLucckyYUYU", "id Status =" + response.code());
                Log.d("StatusLucckyYUYU","requst  ="+new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    Log.d("StatusLuccky", "id Status =" + response.code());
                    if (response.body().status.equalsIgnoreCase("0")) {
                        progressBars.dismiss();
                        check = true;
                        mratingbar.setEnabled(true);
//                        Toast.makeText(PersonalTutorActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    } else {
                        progressBars.dismiss();
                        mratingbar.setEnabled(false);
                        check = false;

//                        Toast.makeText(PersonalTutorActivity.this, "your request has been submitted successfully.", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    switch (response.code()) {
                        case 401:
                            Toast.makeText(PersonalTutorActivity.this, "email and pass not check", Toast.LENGTH_SHORT).show();
                            break;
                        case 403:
                            Toast.makeText(PersonalTutorActivity.this, "ForbiddenException", Toast.LENGTH_SHORT).show();
                            break;
                        case 404:
                            Toast.makeText(PersonalTutorActivity.this, "not found", Toast.LENGTH_SHORT).show();
                            break;
                        case 500:
                            Toast.makeText(PersonalTutorActivity.this, "server broken", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(PersonalTutorActivity.this, "unknown error", Toast.LENGTH_SHORT).show();
                            break;

                    }
                    Toast.makeText(PersonalTutorActivity.this, "Something error please try again", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<RequestResponseJson> call, Throwable t) {
                t.printStackTrace();


                Log.e("System error:", t.getLocalizedMessage());

            }
        });
    }
}