package com.mypersonal.tutor.Razorpay;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mypersonal.tutor.Activities.MainActivity;
import com.mypersonal.tutor.Activities.Signup;
import com.mypersonal.tutor.Activities.TutorActivity;
import com.mypersonal.tutor.Models.StudentSignupRequestJson;
import com.mypersonal.tutor.Models.StudentSignupResponseJson;
import com.mypersonal.tutor.Models.TutorRazorpayRequestJson;
import com.mypersonal.tutor.Models.TutorSignupRequestJson;
import com.mypersonal.tutor.Models.TutorSignupResponseJson;
import com.mypersonal.tutor.R;
import com.mypersonal.tutor.anurag.register.customadapter.RequestDemoClass;
import com.mypersonal.tutor.retrofit.ServiceGenerator;
import com.mypersonal.tutor.retrofit.UserService;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RazorpayActivity extends AppCompatActivity implements PaymentResultListener {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor edits;
    public String name, Email, mobile,Transectionid;
    public String TotalPricess = "200";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_razorpay);
        sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
        edits = sharedPreferences.edit();
        TotalPricess = getIntent().getStringExtra("wallet_amount");
        name = sharedPreferences.getString("name","");
        Email = sharedPreferences.getString("email","");
        mobile = sharedPreferences.getString("mobile","");

        startPayment();
    }

    public void startPayment() {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final Activity activity = this;

        final Checkout co = new Checkout();

        try {
            JSONObject options = new JSONObject();
            options.put("name", name);
            options.put("description", "Card Payment");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://desiflea.com/admin/api/desiflea_logo.png");
            options.put("currency", "INR");
            options.put("amount", TotalPricess+"00");

            JSONObject preFill = new JSONObject();
            preFill.put("email", Email);
            preFill.put("contact", mobile);

            options.put("prefill", preFill);

            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }


    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        try {
            Toast.makeText(this, "Payment Successful: " + razorpayPaymentID, Toast.LENGTH_SHORT).show();
            Log.d("Wallet", "Success = " + razorpayPaymentID);
//            ProductAPI login = new ProductAPI();
            Transectionid = razorpayPaymentID;
            retrofit_singup();
            /// RUN API
        } catch (Exception e) {

            Log.d("Wallet", "Success e = " + e);
        }
    }



    @Override
    public void onPaymentError(int code, String response) {
        try {
            Toast.makeText(this, "Payment failed: " + code + " " + response, Toast.LENGTH_SHORT).show();
            Log.d("Wallet", "Payment failed: = " + code + " " + response);
        } catch (Exception e) {

            Log.d("Wallet", "Payment failed: e = " + e);
        }
    }



    private void retrofit_singup() {





        TutorRazorpayRequestJson request = new TutorRazorpayRequestJson();
        request.setTutorid(sharedPreferences.getString("id",""));

        request.setFee(TotalPricess);



        Log.d("StatusLucckydfj","requst R  ="+new Gson().toJson(request));

        UserService service = ServiceGenerator.createService(UserService.class, null, null);
        service.tutor_razorpay(request).enqueue(new Callback<TutorSignupResponseJson>() {
            @Override
            public void onResponse(Call<TutorSignupResponseJson> call, Response<TutorSignupResponseJson> response) {
                if (response.isSuccessful()) {
                    Log.d("StatusLucckydfj","response  ="+new Gson().toJson(response.body()));
                    if (response.body().success.equalsIgnoreCase("false")) { ////New App

                        Toast.makeText(RazorpayActivity.this, "Techincal problem. Please contact the admistrator", Toast.LENGTH_SHORT).show();

                    } else if (response.body().success.equalsIgnoreCase("true")) {//// All Okay


                        edits.putString("type","tutor");
                        edits.putString("Transaction_status","1");
                        edits.apply();

                        Intent intent = new Intent(RazorpayActivity.this, TutorActivity.class);
                        startActivity(intent);
                        finish();



                    }


                } else {
                    Log.d("walletwallets", "System code:= " + response.code());
                    switch (response.code()) {
                        case 401:
                            Toast.makeText(RazorpayActivity.this, "email and pass not check", Toast.LENGTH_SHORT).show();
                            break;
                        case 403:
                            Toast.makeText(RazorpayActivity.this, "ForbiddenException", Toast.LENGTH_SHORT).show();
                            break;
                        case 404:
                            Toast.makeText(RazorpayActivity.this, "not found", Toast.LENGTH_SHORT).show();
                            break;
                        case 500:
                            Toast.makeText(RazorpayActivity.this, "server broken", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(RazorpayActivity.this, "unknown error", Toast.LENGTH_SHORT).show();
                            break;
                    }
                    Toast.makeText(RazorpayActivity.this, "Something error please try again", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<TutorSignupResponseJson> call, Throwable t) {
                t.printStackTrace();
                Log.d("walletwallets", "System error:= " + t.getLocalizedMessage());


                Log.e("System error:", t.getLocalizedMessage());

            }
        });
    }
}