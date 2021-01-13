package com.mypersonal.tutor.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mypersonal.tutor.R;

public class TutordetailsActivity extends AppCompatActivity {

    public ImageView ivimg;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor edits;
    public TextView tvname,tvemail,tvmobile,tvexpi, tvqty, tvcitytv,
            tvrequest,tvage, tvlocationtv,
            tvsubjecttv,tvtuition_typetv,
            tvWilling_to_travelstv,tvtutordaystv,
            tvsubject_offerdstv, tvspecializationtv, tvfeetv,tvtiming;

    public String name,  image, fee, exp, subject, city, time, mobile, email, qtys, student_id, tutor_id,tutor_area,Tutercurrent_city,Subject;
    public LinearLayout btnrequest;
    public EditText etmssage;
    public RatingBar mratingbar;
    public LinearLayout linearLayout;
    public String ratings;
    AlertDialog.Builder builder;

    public TextView tvfeestv,tvnewfeestv;
    public View vnewviewfeestv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutor_details_layout);
        ivimg = (ImageView)findViewById(R.id.imageview);//


        vnewviewfeestv = (View)findViewById(R.id.newviewfeestv);

        tvnewfeestv = (TextView)findViewById(R.id.newfeestv);
        tvfeestv = (TextView)findViewById(R.id.feestv);

        tvtutordaystv = (TextView)findViewById(R.id.tutordaystv);
        tvspecializationtv = (TextView)findViewById(R.id.specializationtv);

        tvfeetv = (TextView)findViewById(R.id.feetv);
        tvsubject_offerdstv = (TextView)findViewById(R.id.subject_offerdstv);

        tvtuition_typetv = (TextView)findViewById(R.id.tuition_typetv);

        tvtiming = (TextView)findViewById(R.id.timingtv);


        tvWilling_to_travelstv = (TextView)findViewById(R.id.Willing_to_travelstv);
        builder = new AlertDialog.Builder(this);

        mratingbar = (RatingBar)findViewById(R.id.ratingbar);
        mratingbar.setVisibility(View.GONE);
        tvage = (TextView)findViewById(R.id.agetvs);

        tvsubjecttv = (TextView)findViewById(R.id.subjecttv);
        tvlocationtv = (TextView)findViewById(R.id.locationtv);










        etmssage = (EditText)findViewById(R.id.msget);
        tvname = (TextView)findViewById(R.id.tvnametv);////
        sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
        edits = sharedPreferences.edit();

        tvcitytv = (TextView)findViewById(R.id.citytv);

        tvrequest = (TextView)findViewById(R.id.requesttv);
        tvrequest.setVisibility(View.GONE);

        btnrequest = (LinearLayout)findViewById(R.id.requestll);
        btnrequest.setVisibility(View.GONE);


        tvemail = (TextView)findViewById(R.id.emailtv);
        tvmobile = (TextView)findViewById(R.id.mobiletv);

        tvexpi = (TextView)findViewById(R.id.expitv);





        tvqty = (TextView)findViewById(R.id.qtytv);






        image = sharedPreferences.getString("image","");
        name = sharedPreferences.getString("name","");


        exp =  sharedPreferences.getString("tutor_exp","");
        city = sharedPreferences.getString("Tutercurrent_city","");
        subject = sharedPreferences.getString("subject_details","");


        time = sharedPreferences.getString("timeDuration","");
        mobile = sharedPreferences.getString("mobile","");


        qtys = sharedPreferences.getString("qualification","");
        tutor_id = sharedPreferences.getString("id","");
        email = sharedPreferences.getString("email","");
        tutor_area = sharedPreferences.getString("tutor_area","");

        Tutercurrent_city= sharedPreferences.getString("Tutercurrent_city","");
        String Subjecttwointext = sharedPreferences.getString("Subjecttwointext","");
        String age = sharedPreferences.getString("age","");
        String tuition_type = sharedPreferences.getString("TutorTyple","");
        String Willing_to_travels = sharedPreferences.getString("Willing_to_travel","");
        String tutordays = sharedPreferences.getString("day","");
        String subject_offerd = sharedPreferences.getString("Subjecttwointext","");
        String Specialization =  sharedPreferences.getString("specialization","");
        String Fee = sharedPreferences.getString("schoolname","up");

        if(sharedPreferences.getString("approved_doc","").equalsIgnoreCase("0")){
            tvfeestv.setVisibility(View.GONE);
            tvnewfeestv.setVisibility(View.GONE);
            vnewviewfeestv.setVisibility(View.GONE);
        }else {

            tvfeestv.setText(sharedPreferences.getString("fee","200"));
        }


        tvname.setText(name);
        tvmobile.setText(mobile);

        tvexpi.setText(exp+" Year");

        tvtuition_typetv.setText(tuition_type);
        tvtutordaystv.setText(tutordays);
        tvWilling_to_travelstv.setText(Willing_to_travels);
        tvqty.setText(qtys);
        tvage.setText(age+" Year");
        if (Subjecttwointext.length()!=0){
            tvsubjecttv.setText(Subjecttwointext);
        }

        tvcitytv.setText(Tutercurrent_city);
        tvsubject_offerdstv.setText(subject_offerd);
        tvemail.setText(email);

        tvlocationtv.setText(tutor_area);
        tvtiming.setText(sharedPreferences.getString("timeDuration",""));
        tvfeetv.setText(Fee);
        TextView tvdate = (TextView)findViewById(R.id.feepassedoutyeartv);


        TextView tvclgnametv = (TextView)findViewById(R.id.clgnametv);
        tvclgnametv.setText(sharedPreferences.getString("collage",""));

        TextView tvschooldate = (TextView)findViewById(R.id.datetv);
        tvschooldate.setText(sharedPreferences.getString("school_date",""));
        tvdate.setText(sharedPreferences.getString("collage_date",""));
        tvspecializationtv.setText(Specialization);
        Glide.with(TutordetailsActivity.this).load(image).placeholder(R.drawable.logo_light).into(ivimg);


    }
}