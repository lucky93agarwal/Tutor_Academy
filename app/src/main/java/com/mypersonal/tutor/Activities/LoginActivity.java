package com.mypersonal.tutor.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mypersonal.tutor.R;

public class LoginActivity extends AppCompatActivity {

    public EditText etmobile, etemail;
    public Button btntutor, btnstudent,btnsubmit;
    public String check="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnsubmit = (Button)findViewById(R.id.continubtn);

        btntutor = (Button)findViewById(R.id.tutorbtn);
        btnstudent = (Button)findViewById(R.id.studentbtn);

        etmobile = (EditText)findViewById(R.id.mobileet);
        etemail = (EditText)findViewById(R.id.emailet);


        btnstudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check = "S";
            }
        });

        btntutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check = "T";
            }
        });

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etemail.getText().length() == 0){
                    Toast.makeText(LoginActivity.this, "Please Enter your email", Toast.LENGTH_SHORT).show();
                }else if(etmobile.getText().length() == 0){
                    Toast.makeText(LoginActivity.this, "Please Enter your mobile number", Toast.LENGTH_SHORT).show();
                }else {
                    
                }
            }
        });
    }
}