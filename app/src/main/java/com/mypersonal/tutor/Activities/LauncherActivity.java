package com.mypersonal.tutor.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mypersonal.tutor.JSONSchemas.UserSchema;
import com.mypersonal.tutor.Models.User;
import com.mypersonal.tutor.Network.Api;
import com.mypersonal.tutor.R;
import com.mypersonal.tutor.anurag.register.PaymentGate;
import com.mypersonal.tutor.anurag.register.customadapter.RequestDemoClass;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LauncherActivity extends AppCompatActivity {
    public static int SPLASH_DISPLAY_LENGTH = 2000;

    private TextView emailFirebase ;
     private  TextView emailTutor;
    private  TextView emailStudent;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor edits;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
        edits = sharedPreferences.edit();


        emailFirebase = findViewById(R.id.firebase_email);
        emailTutor = findViewById(R.id.tutor_email);
        emailStudent = findViewById(R.id.student_email);

        splashScreen();

    }
        private void splashScreen() {

            new Handler().postDelayed(new Runnable(){
                @Override
                public void run() {

                    String id = sharedPreferences.getString("id","");
                    String type = sharedPreferences.getString("type","");
                    Log.d("WalletLuckkyYuYu","id = "+id);
                    Log.d("WalletLuckkyYuYu","type = "+type);

                    if(id.isEmpty() && id.equalsIgnoreCase("null")){

                        Intent intent = new Intent(LauncherActivity.this, SignInActivity.class);
                        startActivity(intent);
                        finish();
                        Log.d("WalletLuckkyYuYu","type = 1");

                    }else {

                        if(type.equalsIgnoreCase("student")){
                            Log.d("WalletLuckkyYuYu","type = 2");
                            Intent mainIntent = new Intent(LauncherActivity.this, MainActivity.class);
                            startActivity(mainIntent);
                            finish();

                        }else if(type.equalsIgnoreCase("tutor")){
                            Log.d("WalletLuckkyYuYu","type = 3");
                            Intent intent = new Intent(LauncherActivity.this, TutorActivity.class);
                            startActivity(intent);
                            finish();

                        }else{
                            Log.d("WalletLuckkyYuYu","type = 4");
                            Intent intent = new Intent(LauncherActivity.this, SignInActivity.class);
                            startActivity(intent);
                            finish();
                        }

                    }

                }
            }, SPLASH_DISPLAY_LENGTH);
        }

//    @Override
//    public void onStart() {
//        super.onStart();
//        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
//
//
//
//        if(currentUser != null){
//
//            String email = currentUser.getEmail();
//            emailFirebase.setText(email);
//            matchUser();
//      //      Intent intent = new Intent(LauncherActivity.this, MainActivity.class);
//        //    startActivity(intent);
//
//
//        }else{
//            Intent intent = new Intent(LauncherActivity.this, SignInActivity.class);
//            startActivity(intent);
//        }
//
//    }
//
//    private void matchUser() {
//
//        matchTutorEmail();
//        matchStudentEmail();
//        String sF,sT,sS;
//        sF = String.valueOf(emailFirebase);
//        sT = String.valueOf(emailTutor);
//        sS = String.valueOf(emailStudent);
//
//
//
//        if(sF.equalsIgnoreCase(sT)){
//
//        }else{
//            if(sF.equalsIgnoreCase(sS)){
//                Intent intent = new Intent(LauncherActivity.this, MainActivity.class);
//                startActivity(intent);
//            }
//        }
//
//    }
//
//    private void matchStudentEmail() {
//
//        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
//        Api api = retrofit.create(Api.class);
//        Call<List<UserSchema>> call = api.getStudentsList();
//        call.enqueue(new Callback<List<UserSchema>>() {
//            @Override
//            public void onResponse(Call<List<UserSchema>> call, Response<List<UserSchema>> response) {
//
//                Log.d("TAG", "CategorySchema Fetched Successfully");
//                List<UserSchema> categorySchema = response.body();
//                for (UserSchema m : categorySchema) {
//                    emailStudent.setText(m.getEmail());
//                }
//
//
//            }
//
//            @Override
//            public void onFailure(Call<List<UserSchema>> call, Throwable t) {
//                Log.d("TAG", "CategorySchema Fetching Failed");
//                //  progressBar.setVisibility(View.INVISIBLE);
//            }
//        });
//
//    }
//
//    private void matchTutorEmail() {
//
//        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
//        Api api = retrofit.create(Api.class);
//        Call<List<UserSchema>> call = api.getTutorsList();
//        call.enqueue(new Callback<List<UserSchema>>() {
//            @Override
//            public void onResponse(Call<List<UserSchema>> call, Response<List<UserSchema>> response) {
//
//                Log.d("TAG", "CategorySchema Fetched Successfully");
//                List<UserSchema> categorySchema = response.body();
//                for (UserSchema m : categorySchema) {
//                    emailTutor.setText(m.getEmail());
//                }
//
//
//            }
//
//            @Override
//            public void onFailure(Call<List<UserSchema>> call, Throwable t) {
//                Log.d("TAG", "CategorySchema Fetching Failed");
//              //  progressBar.setVisibility(View.INVISIBLE);
//            }
//        });
//
//
//    }


}
