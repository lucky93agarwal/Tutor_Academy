package com.mypersonal.tutor.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.mypersonal.tutor.Models.CitySignupRequestJson;
import com.mypersonal.tutor.Models.CitySignupResponseJson;
import com.mypersonal.tutor.Models.SubjectResponseJson;
import com.mypersonal.tutor.Models.TutorSignupRequestJson;
import com.mypersonal.tutor.Models.timerResponseJson;
import com.mypersonal.tutor.R;
import com.mypersonal.tutor.retrofit.ServiceGenerator;
import com.mypersonal.tutor.retrofit.UserService;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentEditProfileActivity extends AppCompatActivity {
    public String encoded = "null";
    byte[] CDRIVES;

    Integer SELECT_FILE=0;

    public EditText etname,etemail,etmobile,etschool_name, etcurrent_location, etclass, etarea_ofteaching;
    public Spinner  sp_tutor_fee_structure;
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    public ImageView ivstudent_pic;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor edits;
//    public String  spinnertutorfees;

    public Button btnsubject, btncity, btnweek_subject, btntutor_willing_to_traveling, btntutor_tuition_type, btntime_duration, btnupload_student_pic, btnsubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_edit_profile);


        sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
        edits = sharedPreferences.edit();

        ivstudent_pic = (ImageView)findViewById(R.id.student_pic);
        subjectidlist = new ArrayList<String>();
        subjectlist = new ArrayList<String>();

        etname = (EditText)findViewById(R.id.ui_username_name);
        etemail = (EditText)findViewById(R.id.ui_email_signup);
        etmobile = (EditText)findViewById(R.id.ui_tutor_mobile);
        etschool_name = (EditText)findViewById(R.id.ui_tutor_age);
        etarea_ofteaching = (EditText)findViewById(R.id.area_ofteaching);
        etcurrent_location = (EditText)findViewById(R.id.tutor_experience);
        etclass = (EditText)findViewById(R.id.specialization);


        btnsubject = (Button)findViewById(R.id.tutor_fee_subject);
        btncity = (Button)findViewById(R.id.tutor_city);
        btnweek_subject = (Button)findViewById(R.id.tutor_days);
//        btntutor_willing_to_traveling = (Button)findViewById(R.id.tutor_willing_to_traveling);
//        btntutor_tuition_type =(Button)findViewById(R.id.tutor_tuition_type);
        btntime_duration = (Button)findViewById(R.id.time_duration);
        btnupload_student_pic = (Button)findViewById(R.id.upload_student_pic);
        btnsubmit = (Button)findViewById(R.id.ui_signup);



        btnupload_student_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                }
                else
                {




                    final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
                    AlertDialog.Builder builder = new AlertDialog.Builder(StudentEditProfileActivity.this);
                    builder.setTitle("Upload Post Graduate Documents!");
                    builder.setItems(options, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int item) {
                            if (options[item].equals("Take Photo"))
                            {
                                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                            }
                            else if (options[item].equals("Choose from Gallery"))
                            {
                                Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(intent, SELECT_FILE);


                            }
                            else if (options[item].equals("Cancel")) {
                                dialog.dismiss();
                            }
                        }
                    });
                    builder.show();
                }
            }
        });


        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tutorSignup();
            }
        });
        btntime_duration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectReceiversDialogTime();
            }
        });

//        btntutor_tuition_type.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showSelectReceiversDialogtuition_type();
//            }
//        });
//        btntutor_willing_to_traveling.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showSelectReceiversDialogtutor_willing_to_traveling();
//            }
//        });

        btnweek_subject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectReceiversDialogDay();
            }
        });

        btncity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectReceiversDialogCity();
            }
        });

        btnsubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectReceiversDialog();
            }
        });

//        sp_quialifical = (Spinner)findViewById(R.id.tutor_qualification);


//        final ArrayList<String> arrayList2 = new ArrayList<>();
//        arrayList2.add(0,"Qualification");
//        arrayList2.add("Graduate");
//        arrayList2.add("Post Graduate");
//        arrayList2.add("Any Specialization");
//        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, arrayList2);
//        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        sp_quialifical.setAdapter(arrayAdapter2);
//        sp_quialifical.setSelection(0);

//        sp_quialifical.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if (arrayList2 != null && arrayList2.size() != 0){
//                    spinnertutorqualification = parent.getItemAtPosition(position).toString();
//                    //  spinnertutortype = arrayList1.get(position).toString();
//
//                }
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                Toast.makeText(parent.getContext(), "please Select Your type " ,Toast.LENGTH_LONG).show();
//            }
//        });
//        sp_tutor_fee_structure = (Spinner)findViewById(R.id.tutor_fee_structure);






//        final ArrayList<String> arrayList3 = new ArrayList<>();
//        arrayList3.add(0,"Fees");
//        arrayList3.add("Fees by subject");
//        arrayList3.add("Fees by timing");
//        arrayList3.add("Fees of alternate class");
//
//        ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, arrayList3);
//        arrayAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        sp_tutor_fee_structure.setAdapter(arrayAdapter3);
//        sp_tutor_fee_structure.setSelection(0);
//        sp_tutor_fee_structure.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if (arrayList3 != null && arrayList3.size() != 0){
//                    spinnertutorfees = parent.getItemAtPosition(position).toString();
//                    //  spinnertutortype = arrayList1.get(position).toString();
//
//                }
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                Toast.makeText(parent.getContext(), "please Select Your type " ,Toast.LENGTH_LONG).show();
//            }
//        });


        etname.setText(sharedPreferences.getString("name",""));
        etname.setEnabled(false);
        etemail.setText(sharedPreferences.getString("email",""));
        etemail.setEnabled(false);
        etmobile.setText(sharedPreferences.getString("mobile",""));
        etmobile.setEnabled(false);
        etschool_name.setText(sharedPreferences.getString("student_school",""));
        etarea_ofteaching.setText(sharedPreferences.getString("tutor_area",""));

        etcurrent_location.setText(sharedPreferences.getString("student_Location",""));
        etclass.setText(sharedPreferences.getString("student_class",""));

        btnsubject.setText(sharedPreferences.getString("subject",""));
        btnweek_subject.setText(sharedPreferences.getString("student_weak_subject",""));
        btncity.setText(sharedPreferences.getString("student_City",""));
//        btntutor_willing_to_traveling.setText(sharedPreferences.getString("Willing_to_travel",""));
//        btntutor_tuition_type.setText(sharedPreferences.getString("TutorTyple",""));
        btntime_duration.setText(sharedPreferences.getString("student_timing",""));


        Glide.with(StudentEditProfileActivity.this).load(sharedPreferences.getString("image","")).placeholder(R.drawable.logo_light).into(ivstudent_pic);

        retrofit_subject();
        retrofit_city();
        retrofit_time();

    }


    protected CharSequence[] receivertutor_tuition_type= {"Home","Tutor Home","Coaching"};
    protected ArrayList<CharSequence> selectedReceivertutor_tuition_type = new ArrayList<>();
    public ArrayList<String> tutor_tuition_typearray = new ArrayList<>();

    protected CharSequence[] receiverstutor_willing_to_traveling = {"1KM","2KM","3KM","4KM","5KM","6KM","7KM","8KM","9KM","10KM"};
    protected ArrayList<CharSequence> selectedReceiverstutor_willing_to_traveling = new ArrayList<>();
    public ArrayList<String> tutor_willing_to_travelingarray = new ArrayList<>();

    protected CharSequence[] receivers;
    protected ArrayList<CharSequence> selectedReceivers = new ArrayList<>();
    List<String> subjectidlist;
    List<String> subjectlist;
    public ArrayList<String> subjectarray = new ArrayList<>();


    protected CharSequence[] receiversweekSubject;
    protected ArrayList<CharSequence> selectedReceiversweekSubject = new ArrayList<>();


    protected CharSequence[] receiverscity;
    protected ArrayList<CharSequence> selectedReceiverscity = new ArrayList<>();

    public ArrayList<String> cityarray = new ArrayList<>();

    List<String> citylist = new ArrayList<String>();
    public ArrayList<String> dayarray = new ArrayList<>();

    protected CharSequence[] receiversday = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
    protected ArrayList<CharSequence> selectedReceiversday = new ArrayList<>();


    List<String> timeidlist = new ArrayList<String>();

    protected CharSequence[] receiverstime;
    protected ArrayList<CharSequence> selectedReceiverstime = new ArrayList<>();

    List<String> timelist = new ArrayList<String>();
    public ArrayList<String> timearray = new ArrayList<>();

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
                    String data = selectedReceiverstime.toString();
                    data = data.substring(0, data.length() - 1);
                    data = data.substring(1);
//                    time_duration.setText(data);
                    btntime_duration.setText(data);

                }else {
                    selectedReceiverstime.remove(receiverstime[which]);
                    timearray.remove(timeidlist.get(which));
                    String data = selectedReceiverstime.toString();
                    data = data.substring(0, data.length() - 1);
                    data = data.substring(1);
//                    time_duration.setText(data);
                    btntime_duration.setText(data);
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





    protected void showSelectReceiversDialogtuition_type(){
        boolean[] checkedReceivers = new boolean[receivertutor_tuition_type.length];
        int count = receivertutor_tuition_type.length;

        for(int i = 0; i < count; i++)
            checkedReceivers[i] = selectedReceivertutor_tuition_type.contains(receivertutor_tuition_type[i]);

        DialogInterface.OnMultiChoiceClickListener receiversDialogListener = new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if(isChecked) {
                    selectedReceivertutor_tuition_type.add(receivertutor_tuition_type[which]);
                    tutor_tuition_typearray.add(String.valueOf(receivertutor_tuition_type[which]));
                    String data = selectedReceivertutor_tuition_type.toString();
                    data = data.substring(0, data.length() - 1);
                    data = data.substring(1);
//                    TutorTyple= data;
                    btntutor_tuition_type.setText(data);


                }else {
                    selectedReceivertutor_tuition_type.remove(receivertutor_tuition_type[which]);
                    tutor_tuition_typearray.remove(String.valueOf(receivertutor_tuition_type[which]));
                    String data = selectedReceivertutor_tuition_type.toString();
                    data = data.substring(0, data.length() - 1);
                    data = data.substring(1);
//                    TutorTyple= data;
                    btntutor_tuition_type.setText(data);
                }

//                onChangeSelectedReceivers();
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle("Select Tuition Type")
                .setMultiChoiceItems(receivertutor_tuition_type, checkedReceivers, receiversDialogListener)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    protected void showSelectReceiversDialogtutor_willing_to_traveling(){
        boolean[] checkedReceivers = new boolean[receiverstutor_willing_to_traveling.length];
        int count = receiverstutor_willing_to_traveling.length;

        for(int i = 0; i < count; i++)
            checkedReceivers[i] = selectedReceiverstutor_willing_to_traveling.contains(receiverstutor_willing_to_traveling[i]);

        DialogInterface.OnMultiChoiceClickListener receiversDialogListener = new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if(isChecked) {
                    selectedReceiverstutor_willing_to_traveling.add(receiverstutor_willing_to_traveling[which]);
                    tutor_willing_to_travelingarray.add(String.valueOf(receiverstutor_willing_to_traveling[which]));
                    String data = selectedReceiverstutor_willing_to_traveling.toString();
                    data = data.substring(0, data.length() - 1);
                    data = data.substring(1);
//                    Willing_to_travel = data;
                    btntutor_willing_to_traveling.setText(data);


                }else {
                    selectedReceiverstutor_willing_to_traveling.remove(receiverstutor_willing_to_traveling[which]);
                    tutor_willing_to_travelingarray.remove(String.valueOf(receiverstutor_willing_to_traveling[which]));
                    String data = selectedReceiverstutor_willing_to_traveling.toString();
                    data = data.substring(0, data.length() - 1);
                    data = data.substring(1);
//                    Willing_to_travel = data;
                    btntutor_willing_to_traveling.setText(data);
                }

//                onChangeSelectedReceivers();
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle("Select willing to traveling")
                .setMultiChoiceItems(receiverstutor_willing_to_traveling, checkedReceivers, receiversDialogListener)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }




    protected void showSelectReceiversDialogDay(){
        boolean[] checkedReceivers = new boolean[receiversweekSubject.length];
        int count = receiversweekSubject.length;

        for(int i = 0; i < count; i++)
            checkedReceivers[i] = selectedReceiversweekSubject.contains(receiversweekSubject[i]);

        DialogInterface.OnMultiChoiceClickListener receiversDialogListener = new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if(isChecked) {
                    selectedReceiversweekSubject.add(receiversweekSubject[which]);
                    dayarray.add(String.valueOf(receiversweekSubject[which]));
                    String data = selectedReceiversweekSubject.toString();
                    data = data.substring(0, data.length() - 1);
                    data = data.substring(1);
//                    tutor_days = data;
                    btnweek_subject.setText(data);


                }else {
                    selectedReceiversweekSubject.remove(receiversweekSubject[which]);
                    dayarray.remove(String.valueOf(receiversweekSubject[which]));
                    String data = selectedReceiversweekSubject.toString();
                    data = data.substring(0, data.length() - 1);
                    data = data.substring(1);
//                    tutor_days = data;
                    btnweek_subject.setText(data);
                }

//                onChangeSelectedReceivers();
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle("Select Week Subject")
                .setMultiChoiceItems(receiversweekSubject, checkedReceivers, receiversDialogListener)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    protected void showSelectReceiversDialogCity(){
        boolean[] checkedReceivers = new boolean[receiverscity.length];
        int count = receiverscity.length;

        for(int i = 0; i < count; i++)
            checkedReceivers[i] = selectedReceiverscity.contains(receiverscity[i]);

        DialogInterface.OnMultiChoiceClickListener receiversDialogListener = new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if(isChecked) {
                    selectedReceiverscity.add(receiverscity[which]);
                    cityarray.add(String.valueOf(receiverscity[which]));
                    String data = selectedReceiverscity.toString();
                    data = data.substring(0, data.length() - 1);
                    data = data.substring(1);
//                    tutor_city = data;
//                    city_spiss.setText(data);
                    btncity.setText(data);


                }else {
                    selectedReceiverscity.remove(receiverscity[which]);
                    cityarray.remove(String.valueOf(receiverscity[which]));
                    String data = selectedReceiverscity.toString();
                    data = data.substring(0, data.length() - 1);
                    data = data.substring(1);
//                    tutor_city = data;
//                    city_spiss.setText(data);
                    btncity.setText(data);
                }

//                onChangeSelectedReceivers();
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle("Select City")
                .setMultiChoiceItems(receiverscity, checkedReceivers, receiversDialogListener)
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
                    String data = selectedReceivers.toString();
                    data = data.substring(0, data.length() - 1);
                    data = data.substring(1);
                    btnsubject.setText(data);
//                    Subjecttwo = data;
//                    Subject_student.setText(data);
                }else {
                    selectedReceivers.remove(receivers[which]);
                    subjectarray.remove(subjectidlist.get(which));
                    String data = selectedReceivers.toString();
                    data = data.substring(0, data.length() - 1);
                    data = data.substring(1);
                    btnsubject.setText(data);
//                    Subjecttwo = data;
//                    Subject_student.setText(data);
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

    private void retrofit_city() {




        final CitySignupRequestJson request = new CitySignupRequestJson();
        request.setId("0");

        Log.d("StatusLucckydfj","requst  ="+new Gson().toJson(request));
        UserService service = ServiceGenerator.createService(UserService.class, null, null);
        service.city_list(request).enqueue(new Callback<CitySignupResponseJson>() {
            @Override
            public void onResponse(Call<CitySignupResponseJson> call, Response<CitySignupResponseJson> response) {
                if (response.isSuccessful()) {
                    Log.d("StatusLucckydfj","response  ="+new Gson().toJson(response.body()));
                    if (response.body().status.equalsIgnoreCase("0")) { ////New App

                        Toast.makeText(StudentEditProfileActivity.this, "Techincal problem. Please contact the admistrator", Toast.LENGTH_SHORT).show();

                    } else if (response.body().status.equalsIgnoreCase("1")) {//// All Okay

                        if (citylist.size() >= 0) {
                            citylist.clear();

                        }
                        for (int z = 0; z < response.body().datalist.size(); z++) {

                            citylist.add(response.body().datalist.get(z).city_name);

                        }
                        receiverscity= citylist.toArray(new CharSequence[citylist.size()]);


                    }


                } else {
                    Log.d("walletwallets", "System code:= " + response.code());
                    switch (response.code()) {
                        case 401:
                            Toast.makeText(StudentEditProfileActivity.this, "email and pass not check", Toast.LENGTH_SHORT).show();
                            break;
                        case 403:
                            Toast.makeText(StudentEditProfileActivity.this, "ForbiddenException", Toast.LENGTH_SHORT).show();
                            break;
                        case 404:
                            Toast.makeText(StudentEditProfileActivity.this, "not found", Toast.LENGTH_SHORT).show();
                            break;
                        case 500:
                            Toast.makeText(StudentEditProfileActivity.this, "server broken", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(StudentEditProfileActivity.this, "unknown error", Toast.LENGTH_SHORT).show();
                            break;
                    }
                    Toast.makeText(StudentEditProfileActivity.this, "Something error please try again", Toast.LENGTH_SHORT).show();


                }
            }

            @Override
            public void onFailure(Call<CitySignupResponseJson> call, Throwable t) {
                t.printStackTrace();
                Log.d("walletwallets", "System error:= " + t.getLocalizedMessage());


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

                        Toast.makeText(StudentEditProfileActivity.this, "Techincal problem. Please contact the admistrator", Toast.LENGTH_SHORT).show();

                    } else if (response.body().status.equalsIgnoreCase("1")) {//// All Okay

                        if (subjectlist.size() >= 0) {
                            subjectlist.clear();


                        }
                        for (int z = 0; z < response.body().datalist.size(); z++) {

                            subjectlist.add(response.body().datalist.get(z).subject);
                            subjectidlist.add(response.body().datalist.get(z).id);



                        }
                        receivers= subjectlist.toArray(new CharSequence[subjectlist.size()]);
                        receiversweekSubject = subjectlist.toArray(new CharSequence[subjectlist.size()]);


                    }


                } else {
                    Log.d("walletwallets", "System code:= " + response.code());
                    switch (response.code()) {
                        case 401:
                            Toast.makeText(StudentEditProfileActivity.this, "email and pass not check", Toast.LENGTH_SHORT).show();
                            break;
                        case 403:
                            Toast.makeText(StudentEditProfileActivity.this, "ForbiddenException", Toast.LENGTH_SHORT).show();
                            break;
                        case 404:
                            Toast.makeText(StudentEditProfileActivity.this, "not found", Toast.LENGTH_SHORT).show();
                            break;
                        case 500:
                            Toast.makeText(StudentEditProfileActivity.this, "server broken", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(StudentEditProfileActivity.this, "unknown error", Toast.LENGTH_SHORT).show();
                            break;
                    }
                    Toast.makeText(StudentEditProfileActivity.this, "Something error please try again", Toast.LENGTH_SHORT).show();


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

                        Toast.makeText(StudentEditProfileActivity.this, "Techincal problem. Please contact the admistrator", Toast.LENGTH_SHORT).show();

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
                            Toast.makeText(StudentEditProfileActivity.this, "email and pass not check", Toast.LENGTH_SHORT).show();
                            break;
                        case 403:
                            Toast.makeText(StudentEditProfileActivity.this, "ForbiddenException", Toast.LENGTH_SHORT).show();
                            break;
                        case 404:
                            Toast.makeText(StudentEditProfileActivity.this, "not found", Toast.LENGTH_SHORT).show();
                            break;
                        case 500:
                            Toast.makeText(StudentEditProfileActivity.this, "server broken", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(StudentEditProfileActivity.this, "unknown error", Toast.LENGTH_SHORT).show();
                            break;
                    }
                    Toast.makeText(StudentEditProfileActivity.this, "Something error please try again", Toast.LENGTH_SHORT).show();


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






    // Tutor SignUp Method
    private void tutorSignup() {

        //  String user_type = user_text.getText().toString().trim();
        //  int directory_type = keyNameStr;
        String username = etname.getText().toString().trim();
        final String email = etemail.getText().toString().trim();
        String tutor_mobile = etmobile.getText().toString().trim();
        String tutor_age = etschool_name.getText().toString().trim();
        String tutor_exp = etcurrent_location.getText().toString().trim();
        String subject_offerd = btnsubject.getText().toString().trim();
        String specializaionT = etclass.getText().toString().trim();
        String tutor_citys = btncity.getText().toString().trim();
        String tutor_area = etarea_ofteaching.getText().toString().trim();
//        String timeDuration = time_duration.getText().toString().trim();

//        String imageString1 = student_pic.getImageMatrix().toString().trim();
      /*  int age = Integer.parseInt(tutor_age);
        int expere =  Integer.parseInt(tutor_exp);*/


        Log.d("LuckyYUYU", "Email = " + email);//
        Log.d("LuckyYUYU", "UserName = " + etname.getText().toString());//
        Log.d("LuckyYUYU", "Mobile = " + etmobile.getText().toString());//
        Log.d("LuckyYUYU", "Age = " + etschool_name.getText().toString());//
        Log.d("LuckyYUYU", "Subject = " + subjectarray.toString());//
//        Log.d("LuckyYUYU", "Qualification = " + spinnertutorqualification.toString());
        Log.d("LuckyYUYU", "Experience = " + etcurrent_location.getText().toString());//
        Log.d("LuckyYUYU", "tutor type = " + btntutor_tuition_type.toString());
        Log.d("LuckyYUYU", "willing to traveling = " + btntutor_willing_to_traveling.toString());
        Log.d("LuckyYUYU", "day = " + btnweek_subject.toString());
        Log.d("LuckyYUYU", "subject_offerd = " + btnsubject.getText().toString());
        Log.d("LuckyYUYU", "any specialization = " + etclass.getText().toString());
        Log.d("LuckyYUYU", "City = " + btncity.toString());
        Log.d("LuckyYUYU", "area of teaching = " + etarea_ofteaching.getText().toString());
        Log.d("LuckyYUYU", "time = " + timearray.toString());
//        Log.d("LuckyYUYU", "fee = " + spinnertutorfees.toString());


        edits.putString("email", email);
        edits.putString("name", etname.getText().toString().trim());
        edits.putString("mobile", etmobile.getText().toString().trim());
        edits.putString("age", etschool_name.getText().toString().trim());
//        edits.putString("qualification", spinnertutorqualification);
        edits.putString("tutor_exp", etcurrent_location.getText().toString().trim());
        edits.putString("Subjectid", subjectarray.toString());


        edits.putString("day", btnweek_subject.getText().toString());
        edits.putString("Willing_to_travel", btntutor_willing_to_traveling.toString());
        edits.putString("TutorTyple", btntutor_tuition_type.toString());


        edits.putString("specialization", etclass.getText().toString().trim());
        edits.putString("mobile", etmobile.getText().toString().trim());
        edits.putString("age", etschool_name.getText().toString().trim());
//        edits.putString("qualification", spinnertutorqualification);
        edits.putString("Tutercurrent_city", btncity.toString());
        edits.putString("subject_offerd", subjectarray.toString());

        edits.putString("Subjecttwointext", btnsubject.getText().toString());

        edits.putString("subject_details", subjectlist.toString());

        edits.putString("tutor_area", etarea_ofteaching.getText().toString().trim());
        edits.putString("timeDuration", timearray.toString());
        edits.putString("img", encoded);

//        edits.putString("fee", spinnertutorfees.toString());
        edits.putString("Transaction_status", "0");
        edits.apply();

        Log.d("StatusLucckydfj", "age  =" + etschool_name.getText().toString().trim());


        TutorSignupRequestJson request = new TutorSignupRequestJson();
        request.setName(sharedPreferences.getString("name", ""));
        request.setEmail(sharedPreferences.getString("email", ""));
        request.setMobile_no(sharedPreferences.getString("mobile", ""));
        request.setAge(sharedPreferences.getString("age", ""));
        request.setQualification(sharedPreferences.getString("qualification", ""));
        request.setExperience_in_teaching(sharedPreferences.getString("tutor_exp", ""));
        request.setSubjects_offered(sharedPreferences.getString("subject_offerd", ""));

        request.setAny_specialization(sharedPreferences.getString("specialization", ""));
        request.setCurrent_city(sharedPreferences.getString("Tutercurrent_city", ""));
//        request.setAge(sharedPreferences.getString("tutor_area",""));
        request.setTime_duration_alloted_to_students(sharedPreferences.getString("timeDuration", ""));
        request.setFees("0");
        request.setUpload_pic(sharedPreferences.getString("img", ""));
        request.setTransectionid("0");

        request.setDay(sharedPreferences.getString("day", ""));
        request.setArea_of_teaching(sharedPreferences.getString("tutor_area", ""));
        request.setTutortyple(sharedPreferences.getString("TutorTyple", ""));
        request.setWilling_to_travel(sharedPreferences.getString("Willing_to_travel", ""));
        request.setTransaction_status("0");


        Log.d("StatusLucckydfj", "requst  =" + new Gson().toJson(request));

        UserService service = ServiceGenerator.createService(UserService.class, null, null);
//        service.tutor_signup(request).enqueue(new Callback<TutorSignupResponseJson>() {
//            @Override
//            public void onResponse(Call<TutorSignupResponseJson> call, Response<TutorSignupResponseJson> response) {
//                if (response.isSuccessful()) {
//                    Log.d("StatusLucckydfj", "response  =" + new Gson().toJson(response.body()));
//                    if (response.body().success.equalsIgnoreCase("false")) { ////New App
//
//                        Toast.makeText(StudentEditProfileActivity.this, "Techincal problem. Please contact the admistrator", Toast.LENGTH_SHORT).show();
//
//                    } else if (response.body().success.equalsIgnoreCase("true")) {//// All Okay
//
//                        edits.putString("id", response.body().id);
//                        edits.putString("type", "tutor");
//                        edits.putString("image", response.body().image);
//                        edits.apply();

        Intent intent = new Intent(StudentEditProfileActivity.this, TutorActivity.class);
        startActivity(intent);
        finish();


//                    }
//
//
//                } else {
//                    Log.d("walletwallets", "System code:= " + response.code());
//                    switch (response.code()) {
//                        case 401:
//                            Toast.makeText(StudentEditProfileActivity.this, "email and pass not check", Toast.LENGTH_SHORT).show();
//                            break;
//                        case 403:
//                            Toast.makeText(StudentEditProfileActivity.this, "ForbiddenException", Toast.LENGTH_SHORT).show();
//                            break;
//                        case 404:
//                            Toast.makeText(StudentEditProfileActivity.this, "not found", Toast.LENGTH_SHORT).show();
//                            break;
//                        case 500:
//                            Toast.makeText(StudentEditProfileActivity.this, "server broken", Toast.LENGTH_SHORT).show();
//                            break;
//                        default:
//                            Toast.makeText(StudentEditProfileActivity.this, "unknown error", Toast.LENGTH_SHORT).show();
//                            break;
//                    }
//                    Toast.makeText(StudentEditProfileActivity.this, "Something error please try again", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<TutorSignupResponseJson> call, Throwable t) {
//                t.printStackTrace();
//                Log.d("walletwallets", "System error:= " + t.getLocalizedMessage());
//
//
//                Log.e("System error:", t.getLocalizedMessage());
//
//            }
//        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {

            Bitmap photo = (Bitmap) data.getExtras().get("data");
            ivstudent_pic.setImageBitmap(photo);

            Bundle bundle = data.getExtras();
            final Bitmap bmp = (Bitmap) bundle.get("data");
            //  ivuploadimg.setImageBitmap(bmp);
            //    uploadtitle.setText("screenshot upload successfully");

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            CDRIVES = byteArrayOutputStream.toByteArray();
            encoded = Base64.encodeToString(CDRIVES, Base64.DEFAULT);

        }else if(requestCode==SELECT_FILE){

            Uri selectedImageUri = data.getData();
            ivstudent_pic.setImageURI(selectedImageUri);
            Bitmap selectedImage = null;
            try {
                selectedImage = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
            } catch (Exception e) {
                e.printStackTrace();
            }

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            CDRIVES = byteArrayOutputStream.toByteArray();
            encoded = Base64.encodeToString(CDRIVES, Base64.DEFAULT);



//                Toast.makeText(ComplainsActivity.this, encoded, Toast.LENGTH_LONG).show();
        }
    }
}