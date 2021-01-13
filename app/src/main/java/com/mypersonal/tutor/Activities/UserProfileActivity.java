package com.mypersonal.tutor.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mypersonal.tutor.R;

public class UserProfileActivity extends AppCompatActivity {
    public ImageView ivimg;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor edits;
    public TextView tvname,tvemail,tvmobile,
            tvexpi, tvqty, tvexpir,
             tvrequest,tvlocationtv,
    tvweek_Subjecttv, tvsubjecttv,tvclassstv,tvboardtv,
    tvcityss,tvtimingtv,tvdisctv;

    public String name,  image, fee, exp, subject, city, time, mobile, email, qtys, student_id, tutor_id;
    public LinearLayout btnrequest;
    public EditText etmssage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        tvsubjecttv = (TextView)findViewById(R.id.subjecttv);
        tvclassstv = (TextView)findViewById(R.id.classstv);

        tvdisctv = (TextView)findViewById(R.id.disctv);
        tvboardtv = (TextView) findViewById(R.id.classstvnew);


        tvtimingtv = (TextView)findViewById(R.id.timingtv);

        tvlocationtv = (TextView)findViewById(R.id.locationtv);
        tvweek_Subjecttv = (TextView)findViewById(R.id.week_Subjecttv);

        ivimg = (ImageView)findViewById(R.id.imageview);

        tvcityss = (TextView)findViewById(R.id.citysstv);

        etmssage = (EditText)findViewById(R.id.msget);
        tvname = (TextView)findViewById(R.id.tvnametv);
        sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
        edits = sharedPreferences.edit();

        tvlocationtv.setText(sharedPreferences.getString("student_Location",""));
        tvcityss.setText(sharedPreferences.getString("student_City",""));



        tvrequest = (TextView)findViewById(R.id.requesttv);
//        tvrequest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                sendrequest();
//            }
//        });

        btnrequest = (LinearLayout)findViewById(R.id.requestll);

//        btnrequest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                sendrequest();
//            }
//        });


        tvemail = (TextView)findViewById(R.id.emailtv);
        tvmobile = (TextView)findViewById(R.id.mobiletv);

        tvexpi = (TextView)findViewById(R.id.expitv);

        tvexpir = (TextView)findViewById(R.id.exeptv);



        tvqty = (TextView)findViewById(R.id.qtytv);






        image = sharedPreferences.getString("image","");
        name = sharedPreferences.getString("name","");
        fee = sharedPreferences.getString("age","");

        exp = sharedPreferences.getString("student_school","");
        city = sharedPreferences.getString("student_City","");
        subject = sharedPreferences.getString("student_weak_subject","");


        time = sharedPreferences.getString("student_timing","");
        mobile = sharedPreferences.getString("mobile","");


        qtys = sharedPreferences.getString("student_school","");
        tutor_id = sharedPreferences.getString("id","");
        email = sharedPreferences.getString("email","");

        tvname.setText(name);
        tvmobile.setText(mobile);

        tvexpi.setText(exp);
        tvexpir.setText(exp+" Year");
        tvqty.setText(qtys);
        tvweek_Subjecttv.setText(sharedPreferences.getString("student_weak_subject",""));
        tvsubjecttv.setText(sharedPreferences.getString("subject",""));
        tvtimingtv.setText(sharedPreferences.getString("student_timing",""));
        tvdisctv.setText(sharedPreferences.getString("student_sortDesc",""));
        tvclassstv.setText(sharedPreferences.getString("student_class",""));
        tvboardtv.setText(sharedPreferences.getString("student_board",""));

        tvemail.setText(email);

        Glide.with(UserProfileActivity.this).load(image).placeholder(R.drawable.logo_light).into(ivimg);
    }
}