package com.mypersonal.tutor.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.mypersonal.tutor.Models.TutorRequestListRequestJson;
import com.mypersonal.tutor.Models.WalletResponseList;
import com.mypersonal.tutor.R;
import com.mypersonal.tutor.Razorpay.RazorpayActivity;
import com.mypersonal.tutor.popup.msgpopup;
import com.mypersonal.tutor.popup.studentmsgpopup;
import com.mypersonal.tutor.retrofit.ServiceGenerator;
import com.mypersonal.tutor.retrofit.UserService;

import retrofit2.Call;
import retrofit2.Callback;

public class StudentDetailsActivity extends AppCompatActivity {
    public ImageView ivimg;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor edits;
    public TextView tvname,tvemail,tvmobile,tvexpi, tvqty, tvcitytv,tvexpir, tvsubject, tvrequest, tvcurrentLocation, tvweeksubject;

    public String name,  image, fee, exp, subject, city, time, mobile, email, qtys, current_location, student_id,weak_subjects,suitable_timing;
    public LinearLayout btnrequest;
    public EditText etmssage;


    private static studentmsgpopup addpopup;
    ProgressDialog progressBar;


    public String wallet_amount;
    public ImageView ivedit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);
        ivimg = (ImageView)findViewById(R.id.imageview);
        tvweeksubject = (TextView)findViewById(R.id.weeksubject);
        progressBar = new ProgressDialog(StudentDetailsActivity.this);
        progressBar.setCancelable(true);
        progressBar.setMessage("Please wait ...");
        addpopup = new studentmsgpopup(StudentDetailsActivity.this);
        ivedit = (ImageView)findViewById(R.id.editicon);


        ivedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sharedPreferences.getString("Transaction_status","0").equalsIgnoreCase("0")){

                    Intent intent = new Intent(StudentDetailsActivity.this, RazorpayActivity.class);
                    intent.putExtra("wallet_amount",wallet_amount);
                    startActivity(intent);
                }else {
                    addpopup.addpopup(student_id);
                }


            }
        });

        tvcurrentLocation = (TextView)findViewById(R.id.current_locationtv);
        etmssage = (EditText)findViewById(R.id.msget);
        tvname = (TextView)findViewById(R.id.tvnametv);
        sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
        edits = sharedPreferences.edit();

        tvcitytv = (TextView)findViewById(R.id.citytv);

        tvrequest = (TextView)findViewById(R.id.requesttv);
        tvrequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sharedPreferences.getString("Transaction_status","0").equalsIgnoreCase("0")){

                    Intent intent = new Intent(StudentDetailsActivity.this, RazorpayActivity.class);
                    intent.putExtra("wallet_amount",wallet_amount);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(StudentDetailsActivity.this, StudentReserveRequestActivity.class);
                    intent.putExtra("id",student_id);
                    intent.putExtra("type","1");
                    startActivity(intent);
                }
            }
        });

        btnrequest = (LinearLayout)findViewById(R.id.requestll);

        btnrequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sharedPreferences.getString("Transaction_status","0").equalsIgnoreCase("0")){

                    Intent intent = new Intent(StudentDetailsActivity.this, RazorpayActivity.class);
                    intent.putExtra("wallet_amount",wallet_amount);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(StudentDetailsActivity.this, StudentReserveRequestActivity.class);
                    intent.putExtra("id",student_id);
                    intent.putExtra("type","1");
                    startActivity(intent);
                }

            }
        });


        tvemail = (TextView)findViewById(R.id.emailtv);
        tvmobile = (TextView)findViewById(R.id.mobiletv);

        tvexpi = (TextView)findViewById(R.id.expitv);

        tvexpir = (TextView)findViewById(R.id.exeptv);

        tvsubject = (TextView)findViewById(R.id.subjectstv);

        tvqty = (TextView)findViewById(R.id.qtytv);



        suitable_timing = getIntent().getStringExtra("suitable_timing");



        image = getIntent().getStringExtra("image");
        name = getIntent().getStringExtra("name");
        fee = getIntent().getStringExtra("fee");

        exp = getIntent().getStringExtra("school_name");
        city = getIntent().getStringExtra("city");
        subject = getIntent().getStringExtra("subject");


        time = getIntent().getStringExtra("time");
        mobile = getIntent().getStringExtra("mobile");

        current_location = getIntent().getStringExtra("current_location");

        qtys = getIntent().getStringExtra("classs");
        student_id = getIntent().getStringExtra("id");
        email = getIntent().getStringExtra("email");

        weak_subjects = getIntent().getStringExtra("weak_subjects");

        tvname.setText(name);

        tvcurrentLocation.setText(current_location);
        tvexpi.setText(exp);
        tvexpir.setText(getIntent().getStringExtra("board"));
        tvqty.setText(qtys);
        tvweeksubject.setText(weak_subjects);
        tvcitytv.setText(suitable_timing);

        tvsubject.setText(subject);
        Log.d("StatusLucckydfjnew","transaction_status 2  ="+sharedPreferences.getString("Transaction_status","0"));
        if (sharedPreferences.getString("Transaction_status","0").equalsIgnoreCase("0")){
            tvmobile.setText("");
            findViewById(R.id.tvmobilenew).setVisibility(View.GONE);
            findViewById(R.id.tvemailnew).setVisibility(View.GONE);
            findViewById(R.id.onev).setVisibility(View.GONE);

            tvemail.setText("");
        }else {
            tvmobile.setText(mobile);
            tvemail.setText(email);
        }
        Glide.with(StudentDetailsActivity.this).load(image).placeholder(R.drawable.logo_light).into(ivimg);


        getWallet();
    }


    private void getWallet() {
        progressBar.show();
        final TutorRequestListRequestJson request = new TutorRequestListRequestJson();
        request.setTutorid(sharedPreferences.getString("id",""));
//        request.setTutorid("UT25826");

        Log.d("requstresponserequstresponse=","requst  ="+new Gson().toJson(request));

        UserService service = ServiceGenerator.createService(UserService.class, null, null);
        service.wallet_amount(request).enqueue(new Callback<WalletResponseList>() {
            @Override
            public void onResponse(Call<WalletResponseList> call, retrofit2.Response<WalletResponseList> response) {
                Log.d("StatusLuccky", "id Status =" + response.code());

                Log.d("requstresponserequstresponse=","requst response  ="+new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    Log.d("StatusLuccky", "id Status =" + response.code());
                    if (response.body().status.equalsIgnoreCase("0")) {
                        progressBar.dismiss();
                        Toast.makeText(StudentDetailsActivity.this, "No Request Found", Toast.LENGTH_LONG).show();
                    } else {

                        wallet_amount = response.body().price;


                        progressBar.dismiss();


                    }


                } else {
                    switch (response.code()) {
                        case 401:
                            Toast.makeText(StudentDetailsActivity.this, "email and pass not check", Toast.LENGTH_SHORT).show();
                            break;
                        case 403:
                            Toast.makeText(StudentDetailsActivity.this, "ForbiddenException", Toast.LENGTH_SHORT).show();
                            break;
                        case 404:
                            Toast.makeText(StudentDetailsActivity.this, "not found", Toast.LENGTH_SHORT).show();
                            break;
                        case 500:
                            Toast.makeText(StudentDetailsActivity.this, "server broken", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(StudentDetailsActivity.this, "unknown error", Toast.LENGTH_SHORT).show();
                            break;

                    }
                    Toast.makeText(StudentDetailsActivity.this, "Something error please try again", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<WalletResponseList> call, Throwable t) {
                t.printStackTrace();


                Log.e("System error:", t.getLocalizedMessage());

            }
        });
    }
}