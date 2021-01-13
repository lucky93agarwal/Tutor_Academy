package com.mypersonal.tutor.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mypersonal.tutor.Models.CitySignupRequestJson;
import com.mypersonal.tutor.Models.RequestListRequestJson;
import com.mypersonal.tutor.Models.RequestResponseJson;
import com.mypersonal.tutor.Models.SubjectResponseJson;
import com.mypersonal.tutor.Models.timerResponseJson;
import com.mypersonal.tutor.R;
import com.mypersonal.tutor.retrofit.ServiceGenerator;
import com.mypersonal.tutor.retrofit.UserService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReserveRequestActivity extends AppCompatActivity {

    public String tutor_id;
    List<String> timelist;

    public TextView tvrequest;

    List<String> timeidlist;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor edits;
    List<String> subjectlist;
    List<String> subjectidlist;


    protected CharSequence[] receivers;
    protected ArrayList<CharSequence> selectedReceivers = new ArrayList<>();


    public Button btnsubject, btntiming;


    public ArrayList<String> timearray = new ArrayList<>();
    public ArrayList<String> subjectarray = new ArrayList<>();


    protected CharSequence[] receiverstime;
    protected ArrayList<CharSequence> selectedReceiverstime = new ArrayList<>();


    public LinearLayout linearLayout;

    public EditText etmesg;
    public String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_request);
        sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
        edits = sharedPreferences.edit();
        linearLayout = (LinearLayout)findViewById(R.id.requestll);
        etmesg = (EditText)findViewById(R.id.etmessageet);
        timelist = new ArrayList<String>();
        timeidlist = new ArrayList<String>();

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendrequest();
            }
        });
        tvrequest = (TextView)findViewById(R.id.requesttv);
        tvrequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendrequest();
            }
        });
        btnsubject = (Button)findViewById(R.id.subjectbtn);
        btntiming = (Button)findViewById(R.id.timebtn);


        btnsubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectReceiversDialog();
            }
        });

        btntiming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectReceiversDialogTime();
            }
        });

        subjectlist = new ArrayList<String>();
        subjectidlist = new ArrayList<String>();
        tutor_id = getIntent().getStringExtra("id");
        type = getIntent().getStringExtra("type");
        if (type.equalsIgnoreCase("2")){
            btnsubject.setVisibility(View.GONE);
            btntiming.setVisibility(View.GONE);
            tvrequest.setText("Send Message");
        }
        retrofit_subject();
        retrofit_time();
    }
    protected void showSelectReceiversDialogTime(){
        boolean[] checkedReceivers = new boolean[receiverstime.length];
        int count = receiverstime.length;

        for(int i = 0; i < count; i++)
            checkedReceivers[i] = selectedReceiverstime.contains(receiverstime[i]);

        DialogInterface.OnMultiChoiceClickListener receiversDialogListener = new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if(isChecked) {
                    selectedReceiverstime.add(receiverstime[which]);
                    timearray.add(timeidlist.get(which));

                }else {
                    selectedReceiverstime.remove(receiverstime[which]);
                    timearray.remove(timeidlist.get(which));
                }

//                onChangeSelectedReceivers();
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle("Select Time")
                .setMultiChoiceItems(receiverstime, checkedReceivers, receiversDialogListener)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    protected void showSelectReceiversDialog() {

        boolean[] checkedReceivers = new boolean[receivers.length];
        int count = receivers.length;

        for(int i = 0; i < count; i++)
            checkedReceivers[i] = selectedReceivers.contains(receivers[i]);

        DialogInterface.OnMultiChoiceClickListener receiversDialogListener = new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if(isChecked) {
                    selectedReceivers.add(receivers[which]);
                    subjectarray.add(subjectidlist.get(which));
                }else {
                    selectedReceivers.remove(receivers[which]);
                    subjectarray.remove(subjectidlist.get(which));
                }

//                onChangeSelectedReceivers();
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle("Select Subject")
                .setMultiChoiceItems(receivers, checkedReceivers, receiversDialogListener)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void sendrequest() {


        final RequestListRequestJson request = new RequestListRequestJson();
        request.setMsg(etmesg.getText().toString());
        request.setStudentid(sharedPreferences.getString("id",""));
        request.setTutorid(tutor_id);
        request.setSubject(subjectidlist.toString());
        request.setTime(timeidlist.toString());
        Log.d("StatusLuccky","requst  ="+new Gson().toJson(request));

        UserService service = ServiceGenerator.createService(UserService.class, null, null);
        service.request_list(request).enqueue(new Callback<RequestResponseJson>() {
            @Override
            public void onResponse(Call<RequestResponseJson> call, retrofit2.Response<RequestResponseJson> response) {
                Log.d("StatusLuccky", "id Status =" + response.code());
                if (response.isSuccessful()) {
                    Log.d("StatusLuccky", "id Status =" + response.code());
                    if (response.body().status.equalsIgnoreCase("0")) {
                        Toast.makeText(ReserveRequestActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    } else {

                        Toast.makeText(ReserveRequestActivity.this, "your request has been submitted successfully.", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    switch (response.code()) {
                        case 401:
                            Toast.makeText(ReserveRequestActivity.this, "email and pass not check", Toast.LENGTH_SHORT).show();
                            break;
                        case 403:
                            Toast.makeText(ReserveRequestActivity.this, "ForbiddenException", Toast.LENGTH_SHORT).show();
                            break;
                        case 404:
                            Toast.makeText(ReserveRequestActivity.this, "not found", Toast.LENGTH_SHORT).show();
                            break;
                        case 500:
                            Toast.makeText(ReserveRequestActivity.this, "server broken", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(ReserveRequestActivity.this, "unknown error", Toast.LENGTH_SHORT).show();
                            break;

                    }
                    Toast.makeText(ReserveRequestActivity.this, "Something error please try again", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<RequestResponseJson> call, Throwable t) {
                t.printStackTrace();


                Log.e("System error:", t.getLocalizedMessage());

            }
        });
    }
    private void retrofit_subject(){

        final CitySignupRequestJson request = new CitySignupRequestJson();
        request.setId("0");

        Log.d("StatusLucckydfj","requst  ="+new Gson().toJson(request));
        UserService service = ServiceGenerator.createService(UserService.class, null, null);
        service.subject_list(request).enqueue(new Callback<SubjectResponseJson>() {
            @Override
            public void onResponse(Call<SubjectResponseJson> call, Response<SubjectResponseJson> response) {
                if (response.isSuccessful()) {
                    Log.d("StatusLucckydfj","response  ="+new Gson().toJson(response.body()));
                    if (response.body().status.equalsIgnoreCase("0")) { ////New App

                        Toast.makeText(ReserveRequestActivity.this, "Techincal problem. Please contact the admistrator", Toast.LENGTH_SHORT).show();

                    } else if (response.body().status.equalsIgnoreCase("1")) {//// All Okay

                        if (subjectlist.size() >= 0) {
                            subjectlist.clear();

                        }
                        for (int z = 0; z < response.body().datalist.size(); z++) {

                            subjectlist.add(response.body().datalist.get(z).subject);
                            subjectidlist.add(response.body().datalist.get(z).id);



                        }
                        receivers= subjectlist.toArray(new CharSequence[subjectlist.size()]);


                    }


                } else {
                    Log.d("walletwallets", "System code:= " + response.code());
                    switch (response.code()) {
                        case 401:
                            Toast.makeText(ReserveRequestActivity.this, "email and pass not check", Toast.LENGTH_SHORT).show();
                            break;
                        case 403:
                            Toast.makeText(ReserveRequestActivity.this, "ForbiddenException", Toast.LENGTH_SHORT).show();
                            break;
                        case 404:
                            Toast.makeText(ReserveRequestActivity.this, "not found", Toast.LENGTH_SHORT).show();
                            break;
                        case 500:
                            Toast.makeText(ReserveRequestActivity.this, "server broken", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(ReserveRequestActivity.this, "unknown error", Toast.LENGTH_SHORT).show();
                            break;
                    }
                    Toast.makeText(ReserveRequestActivity.this, "Something error please try again", Toast.LENGTH_SHORT).show();


                }
            }

            @Override
            public void onFailure(Call<SubjectResponseJson> call, Throwable t) {
                t.printStackTrace();
                Log.d("walletwallets", "System error:= " + t.getLocalizedMessage());


                Log.e("System error:", t.getLocalizedMessage());

            }
        });


    }


    private void retrofit_time(){
        final CitySignupRequestJson request = new CitySignupRequestJson();
        request.setId("0");
        UserService service = ServiceGenerator.createService(UserService.class, null, null);
        service.time_list(request).enqueue(new Callback<timerResponseJson>() {
            @Override
            public void onResponse(Call<timerResponseJson> call, Response<timerResponseJson> response) {
                if (response.isSuccessful()) {
                    Log.d("StatusLucckydfj","response  ="+new Gson().toJson(response.body()));
                    if (response.body().status.equalsIgnoreCase("0")) { ////New App

                        Toast.makeText(ReserveRequestActivity.this, "Techincal problem. Please contact the admistrator", Toast.LENGTH_SHORT).show();

                    } else if (response.body().status.equalsIgnoreCase("1")) {//// All Okay

                        if (timelist.size() >= 0) {
                            timelist.clear();

                        }
                        for (int z = 0; z < response.body().datalist.size(); z++) {

                            timelist.add(response.body().datalist.get(z).time);
                            timeidlist.add(response.body().datalist.get(z).id);


                        }
                        receiverstime= timelist.toArray(new CharSequence[timelist.size()]);


                    }


                } else {
                    Log.d("walletwallets", "System code:= " + response.code());
                    switch (response.code()) {
                        case 401:
                            Toast.makeText(ReserveRequestActivity.this, "email and pass not check", Toast.LENGTH_SHORT).show();
                            break;
                        case 403:
                            Toast.makeText(ReserveRequestActivity.this, "ForbiddenException", Toast.LENGTH_SHORT).show();
                            break;
                        case 404:
                            Toast.makeText(ReserveRequestActivity.this, "not found", Toast.LENGTH_SHORT).show();
                            break;
                        case 500:
                            Toast.makeText(ReserveRequestActivity.this, "server broken", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(ReserveRequestActivity.this, "unknown error", Toast.LENGTH_SHORT).show();
                            break;
                    }
                    Toast.makeText(ReserveRequestActivity.this, "Something error please try again", Toast.LENGTH_SHORT).show();


                }
            }

            @Override
            public void onFailure(Call<timerResponseJson> call, Throwable t) {
                t.printStackTrace();
                Log.d("walletwallets", "System error:= " + t.getLocalizedMessage());


                Log.e("System error:", t.getLocalizedMessage());

            }
        });
    }
}