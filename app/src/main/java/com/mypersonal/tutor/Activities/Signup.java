package com.mypersonal.tutor.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mypersonal.tutor.DataPickerFragment;
import com.mypersonal.tutor.JSONSchemas.StatusSchema;
import com.mypersonal.tutor.Models.CitySignupRequestJson;
import com.mypersonal.tutor.Models.CitySignupResponseJson;
import com.mypersonal.tutor.Models.StudentSignupRequestJson;
import com.mypersonal.tutor.Models.StudentSignupResponseJson;
import com.mypersonal.tutor.Models.SubjectResponseJson;
import com.mypersonal.tutor.Models.TutorSignupRequestJson;
import com.mypersonal.tutor.Models.TutorSignupResponseJson;
import com.mypersonal.tutor.Models.timerResponseJson;
import com.mypersonal.tutor.Network.Api;
import com.mypersonal.tutor.R;
import com.mypersonal.tutor.Razorpay.RazorpayActivity;
import com.mypersonal.tutor.anurag.register.customadapter.RequestDemoClass;
import com.mypersonal.tutor.retrofit.ServiceGenerator;
import com.mypersonal.tutor.retrofit.UserService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Signup extends AppCompatActivity  implements DatePickerDialog.OnDateSetListener{
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor edits;
    public String encoded = "null";

    public String encoded_one = "null";
    public String encoded_two = "null";
    public String encoded_three = "null";
    public String encoded_four = "null";
    byte[] CDRIVES;
    private EditText edit_username, edit_email, edit_mobile,Tutercurrent_city,area_ofteaching,
            edit_tutorAge, tutor_experience, subject_offered, specialization, etboard;
    public Button time_duration;
    private RadioGroup radiogroup;
    private RadioButton professional_btn, visitor_btn;
    private View spinner_view;
    private ProgressDialog mRegProgress;
    private TextView user_text;
    //   List<Example> arrayList;
    private LinearLayout lyt_tutor_form,lyt_student_form;

    public Button btnsubmit,upload_student_pic;
    private ImageView student_pic;
    private Spinner spinnertutor_type,tutor_qualification,tutor_fee_structure,student_subject;

    List<String> citylist;
    public String City= "";
    Integer SELECT_FILE=0;


    List<String> timelist;

    List<String> timeidlist;


    List<String> subjectlist;
    List<String> subjectidlist;
    public String Subject= "", Subjectid= "",Subjecttwo;

    public Spinner city_spi, city_spi_student, subject_spi,subject_spi_student;


    public Button Subject_student;
    public EditText etui_student_signup;
    public EditText etui_email_student;
    public EditText etui_mobile_student,ui_school_student,student_city,student_location;
    public EditText etclass_student;
    public EditText etweak_subject;
    public Button etstudent_timing;
    public EditText etstudent_shortdec;
    int keyNameStr;


    private String spinnertutortype,spinnertutorqualification,spinnertutorfees,spinnerstudentsubject;

    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;


    protected CharSequence[] receivers;
    protected ArrayList<CharSequence> selectedReceivers = new ArrayList<>();

    protected CharSequence[] receiversweekSubject;
    protected ArrayList<CharSequence> selectedReceiversweekSubject = new ArrayList<>();
    public ArrayList<String> dayarrayweekSubject = new ArrayList<>();



    protected CharSequence[] receiverscity;
    protected ArrayList<CharSequence> selectedReceiverscity = new ArrayList<>();




    protected CharSequence[] receiverstime;
    protected ArrayList<CharSequence> selectedReceiverstime = new ArrayList<>();

    protected CharSequence[] receiversday = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
    protected ArrayList<CharSequence> selectedReceiversday = new ArrayList<>();
    public ArrayList<String> dayarray = new ArrayList<>();

    protected CharSequence[] receiverstutor_willing_to_traveling = {"1KM","2KM","3KM","4KM","5KM","6KM","7KM","8KM","9KM","10KM"};
    protected ArrayList<CharSequence> selectedReceiverstutor_willing_to_traveling = new ArrayList<>();
    public ArrayList<String> tutor_willing_to_travelingarray = new ArrayList<>();

    protected CharSequence[] receivertutor_tuition_type= {"Home","Tutor Home","Coaching"};
    protected ArrayList<CharSequence> selectedReceivertutor_tuition_type = new ArrayList<>();
    public ArrayList<String> tutor_tuition_typearray = new ArrayList<>();
    public String TutorTyple,Willing_to_travel,tutor_days,tutor_city;

    public ArrayList<String> cityarray = new ArrayList<>();
    public ArrayList<String> timearray = new ArrayList<>();
    public ArrayList<String> subjectarray = new ArrayList<>();

    public ArrayList<String> weeksubjectarray = new ArrayList<>();

    public Button city_spsi,btnsubject,btndays,btntutor_willing_to_traveling,btntutor_tuition_type;
    public Button city_spiss;
    public Button weeksubject;

    public int Numberxxx =0;
    public String btnNumber ="", tenth,twelth;


    public ImageView  ivonebtn, ivtwobtn, ivthreebtn, ivfourbtn;
    public LinearLayout lluploadlinear;


    public int cur = 0;
    static final int START_DATE = 1;
    static final int END_DATE = 2;
    public EditText etui_board_student, etschoolname,etclgname;
    public Button btndate,btnclgdate;
    ProgressDialog progressBar;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etui_board_student = (EditText)findViewById(R.id.ui_board_student);

        etschoolname = (EditText)findViewById(R.id.tutor_school_name);
        etclgname = (EditText)findViewById(R.id.tutor_clg_name);
        progressBar = new ProgressDialog(this);
        progressBar.setCancelable(true);
        progressBar.setMessage("Please wait ...");

        btndate = (Button)findViewById(R.id.tutor_passout_date);

        btnclgdate = (Button)findViewById(R.id.tutor_college_date);



        lluploadlinear = (LinearLayout)findViewById(R.id.uploadlinear);


        ivonebtn = (ImageView)findViewById(R.id.addonebtn);
        ivtwobtn = (ImageView)findViewById(R.id.addtwobtn);
        ivthreebtn = (ImageView)findViewById(R.id.addthreebtn);
        ivfourbtn = (ImageView)findViewById(R.id.addfourbtn);


        etboard = (EditText)findViewById(R.id.ui_board);
        etboard.setVisibility(View.GONE);

        ivonebtn.setVisibility(View.VISIBLE);
        ivtwobtn.setVisibility(View.VISIBLE);
        ivthreebtn.setVisibility(View.VISIBLE);
        ivfourbtn.setVisibility(View.VISIBLE);



        btndate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cur = START_DATE;
                DialogFragment datePicker = new DataPickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");   byte[] CDRIVES;

            }
        });
        btnclgdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cur = END_DATE;
                DialogFragment datePicker = new DataPickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
        ivonebtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                btnNumber = "Doc";
                Numberxxx=1;

                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                }
                else
                {
//                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                    startActivityForResult(cameraIntent, CAMERA_REQUEST);

//                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
//                    photoPickerIntent.setType("image/*");
//                    startActivityForResult(photoPickerIntent, CAMERA_REQUEST);



                    final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
                    AlertDialog.Builder builder = new AlertDialog.Builder(Signup.this);
                    builder.setTitle("Upload 10 Class Documents!");
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
        ivtwobtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                btnNumber = "Doc";
                Numberxxx=2;

                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                }
                else
                {
//                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                    startActivityForResult(cameraIntent, CAMERA_REQUEST);

//                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
//                    photoPickerIntent.setType("image/*");
//                    startActivityForResult(photoPickerIntent, CAMERA_REQUEST);



                    final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
                    AlertDialog.Builder builder = new AlertDialog.Builder(Signup.this);
                    builder.setTitle("Upload 12 Class Documents!");
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
        ivthreebtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                btnNumber = "Doc";
                Numberxxx=3;

                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                }
                else
                {
//                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                    startActivityForResult(cameraIntent, CAMERA_REQUEST);

//                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
//                    photoPickerIntent.setType("image/*");
//                    startActivityForResult(photoPickerIntent, CAMERA_REQUEST);



                    final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
                    AlertDialog.Builder builder = new AlertDialog.Builder(Signup.this);
                    builder.setTitle("Upload Graduate Documents!");
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

        ivfourbtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                btnNumber = "Doc";
                Numberxxx=4;

                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                }
                else
                {
//                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                    startActivityForResult(cameraIntent, CAMERA_REQUEST);

//                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
//                    photoPickerIntent.setType("image/*");
//                    startActivityForResult(photoPickerIntent, CAMERA_REQUEST);



                    final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
                    AlertDialog.Builder builder = new AlertDialog.Builder(Signup.this);
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

        citylist = new ArrayList<String>();
        timelist = new ArrayList<String>();
        timeidlist = new ArrayList<String>();
       city_spsi = (Button) findViewById(R.id.tutor_city);

        btntutor_tuition_type = (Button) findViewById(R.id.tutor_tuition_type);
        btntutor_tuition_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectReceiversDialogtuition_type();
            }
        });
        btndays = (Button) findViewById(R.id.tutor_days);

        btntutor_willing_to_traveling = (Button) findViewById(R.id.tutor_willing_to_traveling);
        btntutor_willing_to_traveling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectReceiversDialogtutor_willing_to_traveling();
            }
        });

        btndays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectReceiversDialogDay();
            }
        });
        city_spiss = (Button) findViewById(R.id.tutor_city_student);

        city_spiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectReceiversDialogCity();
            }
        });

        city_spsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectReceiversDialogCity();
            }
        });

        subjectlist = new ArrayList<String>();
        subjectidlist = new ArrayList<String>();
        Subject_student = (Button) findViewById(R.id.tutor_fee_subject);

        Subject_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectReceiversDialog();
            }
        });
        weeksubject = (Button)findViewById(R.id.week_subject);

        weeksubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWeekSelectReceiversDialog();
            }
        });
        btnsubject = (Button)findViewById(R.id.stdent_fee_subject);
        btnsubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectReceiversDialog();
            }
        });


        sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
        edits = sharedPreferences.edit();
// Student Form Field

        lyt_student_form = (LinearLayout) findViewById(R.id.student_details);
        etui_student_signup= (EditText)findViewById(R.id.ui_student_signup);
        etui_email_student= (EditText)findViewById(R.id.ui_email_student);
        etui_mobile_student= (EditText)findViewById(R.id.ui_mobile_student);
        ui_school_student = (EditText) findViewById(R.id.ui_school_student);
        etclass_student= (EditText)findViewById(R.id.class_student);
        etweak_subject= (EditText)findViewById(R.id.weak_subject);
        etstudent_timing= (Button)findViewById(R.id.student_timing);
        etstudent_timing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectReceiversDialogTime();
            }
        });
        etstudent_shortdec= (EditText)findViewById(R.id.student_shortdec);
        student_subject = (Spinner) findViewById(R.id.student_subject);
        upload_student_pic =(Button) findViewById(R.id.upload_student_pic);
        student_pic        =(ImageView) findViewById(R.id.student_pic) ;
        student_city= (EditText)findViewById(R.id.ui_city_student);
        student_location= (EditText)findViewById(R.id.ui_Location_student);


        // tutor Form fields
        lyt_tutor_form = (LinearLayout) findViewById(R.id.tutor_details);
    //   spinnertutor_type = (Spinner) findViewById(R.id.ui_tutor_type);
        tutor_qualification = (Spinner) findViewById(R.id.tutor_qualification);
        tutor_fee_structure = (Spinner) findViewById(R.id.tutor_fee_structure);
        mRegProgress = new ProgressDialog(this);


        edit_username = (EditText) findViewById(R.id.ui_username_name);
        edit_email = (EditText) findViewById(R.id.ui_email_signup);
        edit_mobile = (EditText) findViewById(R.id.ui_tutor_mobile);
        edit_tutorAge = (EditText) findViewById(R.id.ui_tutor_age);
        subject_offered = (EditText) findViewById(R.id.tutor_subject_offered);
        tutor_experience = (EditText) findViewById(R.id.tutor_experience);
        specialization = (EditText) findViewById(R.id.specialization);
        Tutercurrent_city = (EditText) findViewById(R.id.tutor_current_city);
        area_ofteaching = (EditText) findViewById(R.id.area_ofteaching);
        time_duration = (Button) findViewById(R.id.time_duration);
        time_duration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectReceiversDialogTime();
            }
        });


        radiogroup = (RadioGroup) findViewById(R.id.ui_radiogroup_signup);
        professional_btn = (RadioButton) findViewById(R.id.ui_professional_radiobtn);
        visitor_btn = (RadioButton) findViewById(R.id.ui_visitor_radiobtn);

        spinner_view = (View) findViewById(R.id.ui_spinner_view);

        user_text = (TextView) findViewById(R.id.ui_user_text);


        Bundle bn = getIntent().getExtras();
        String email = bn.getString("email");
        edit_email.setText(email);
        edit_email.setEnabled(false);
        etui_email_student.setText(String.valueOf(email));
        etui_email_student.setEnabled(false);
        // Submit Button
        btnsubmit = (Button)findViewById(R.id.ui_signup);

        // Camera Button

        upload_student_pic.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                btnNumber = "Cam";
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                }
                else
                {
//                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                    startActivityForResult(cameraIntent, CAMERA_REQUEST);

//                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
//                    photoPickerIntent.setType("image/*");
//                    startActivityForResult(photoPickerIntent, CAMERA_REQUEST);



                    final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
                    AlertDialog.Builder builder = new AlertDialog.Builder(Signup.this);
                    builder.setTitle("Add Photo!");
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


        retrofit_city();
        retrofit_subject();

        retrofit_time();
       // Radio group

        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int option = radiogroup.getCheckedRadioButtonId();
                switch (option) {
                    case R.id.ui_professional_radiobtn:
                        if (professional_btn.isChecked()) {
                     //       spinnertutor_type.setVisibility(View.VISIBLE);
                            spinner_view.setVisibility(View.VISIBLE);
                            lyt_tutor_form.setVisibility(View.VISIBLE);
                            ivonebtn.setVisibility(View.VISIBLE);
                            ivtwobtn.setVisibility(View.VISIBLE);
                            ivthreebtn.setVisibility(View.VISIBLE);
                            ivfourbtn.setVisibility(View.VISIBLE);
                            lluploadlinear.setVisibility(View.VISIBLE);
                            lyt_student_form.setVisibility(View.GONE);
                            user_text.setText("Tutor");
                        }
                    case R.id.ui_visitor_radiobtn:
                        if (visitor_btn.isChecked()) {
                       //     spinnertutor_type.setVisibility(View.GONE);
                            spinner_view.setVisibility(View.GONE);
                            ivonebtn.setVisibility(View.GONE);
                            lluploadlinear.setVisibility(View.GONE);
                            ivtwobtn.setVisibility(View.GONE);
                            ivthreebtn.setVisibility(View.GONE);
                            ivfourbtn.setVisibility(View.GONE);
                            lyt_tutor_form.setVisibility(View.GONE);
                            lyt_student_form.setVisibility(View.VISIBLE);
                            user_text.setText("Student");
                        }
                }
            }
        });

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(professional_btn.isChecked()){
                    lyt_tutor_form.setVisibility(View.VISIBLE);
                    progressBar.show();
                    tutorSignup();
                }else{
                    lyt_student_form.setVisibility(View.VISIBLE);
//                    singup();
                    progressBar.show();
                    retrofit_singup();
                }

            }
        });



        // spinner tutor qualification

        final ArrayList<String> arrayList2 = new ArrayList<>();
        arrayList2.add(0,"Qualification");
        arrayList2.add("Graduate");
        arrayList2.add("Post Graduate");
        arrayList2.add("Any Specialization");

        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, arrayList2);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tutor_qualification.setAdapter(arrayAdapter2);
        tutor_qualification.setSelection(0);

        tutor_qualification.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (arrayList2 != null && arrayList2.size() != 0){
                    spinnertutorqualification = parent.getItemAtPosition(position).toString();
                    //  spinnertutortype = arrayList1.get(position).toString();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(parent.getContext(), "please Select Your type " ,Toast.LENGTH_LONG).show();
            }
        });

        //spinner tutor fees structure
        final ArrayList<String> arrayList3 = new ArrayList<>();
        arrayList3.add(0,"Fees");
        arrayList3.add("Fees by subject");
        arrayList3.add("Fees by timing");
        arrayList3.add("Fees of alternate class");

        ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, arrayList3);
        arrayAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tutor_fee_structure.setAdapter(arrayAdapter3);
        tutor_fee_structure.setSelection(0);
        tutor_fee_structure.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (arrayList3 != null && arrayList3.size() != 0){
                    spinnertutorfees = parent.getItemAtPosition(position).toString();
                    //  spinnertutortype = arrayList1.get(position).toString();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(parent.getContext(), "please Select Your type " ,Toast.LENGTH_LONG).show();
            }
        });

   //     showSelectReceiversDialog();
        // spinner student
        final ArrayList<String> arrayList4 = new ArrayList<>();
        arrayList4.add(0,"Select Subject");
        arrayList4.add("All Subjects");
        arrayList4.add("Specific Subjects");
        arrayList4.add("Only one subject");

        ArrayAdapter<String> arrayAdapter4 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, arrayList4);
        arrayAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        student_subject.setAdapter(arrayAdapter4);
        student_subject.setSelection(0);


        student_subject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (arrayList4 != null && arrayList4.size() != 0){
                    spinnerstudentsubject = parent.getItemAtPosition(position).toString();
                    //  spinnertutortype = arrayList1.get(position).toString();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(parent.getContext(), "please Select Your Subject " ,Toast.LENGTH_LONG).show();
            }
        });

    }

// camera method and image loading
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currentDateString = dateParser.format(c.getTime());

        if(cur == START_DATE){
            btndate.setText(currentDateString);
        }else {
            btnclgdate.setText(currentDateString);
        }


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
                    Willing_to_travel = data;
                    btntutor_willing_to_traveling.setText(data);


                }else {
                    selectedReceiverstutor_willing_to_traveling.remove(receiverstutor_willing_to_traveling[which]);
                    tutor_willing_to_travelingarray.remove(String.valueOf(receiverstutor_willing_to_traveling[which]));
                    String data = selectedReceiverstutor_willing_to_traveling.toString();
                    data = data.substring(0, data.length() - 1);
                    data = data.substring(1);
                    Willing_to_travel = data;
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
                    TutorTyple= data;
                    btntutor_tuition_type.setText(data);


                }else {
                    selectedReceivertutor_tuition_type.remove(receivertutor_tuition_type[which]);
                    tutor_tuition_typearray.remove(String.valueOf(receivertutor_tuition_type[which]));
                    String data = selectedReceivertutor_tuition_type.toString();
                    data = data.substring(0, data.length() - 1);
                    data = data.substring(1);
                    TutorTyple= data;
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


    protected void showSelectReceiversDialogDay(){
        boolean[] checkedReceivers = new boolean[receiversday.length];
        int count = receiversday.length;

        for(int i = 0; i < count; i++)
            checkedReceivers[i] = selectedReceiversday.contains(receiversday[i]);

        DialogInterface.OnMultiChoiceClickListener receiversDialogListener = new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if(isChecked) {
                    selectedReceiversday.add(receiversday[which]);
                    dayarray.add(String.valueOf(receiversday[which]));
                    String data = selectedReceiversday.toString();
                    data = data.substring(0, data.length() - 1);
                    data = data.substring(1);
                    tutor_days = data;
                    btndays.setText(data);


                }else {
                    selectedReceiversday.remove(receiversday[which]);
                    dayarray.remove(String.valueOf(receiversday[which]));
                    String data = selectedReceiversday.toString();
                    data = data.substring(0, data.length() - 1);
                    data = data.substring(1);
                    tutor_days = data;
                    btndays.setText(data);
                }

//                onChangeSelectedReceivers();
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle("Select Day")
                .setMultiChoiceItems(receiversday, checkedReceivers, receiversDialogListener)
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
                    tutor_city = data;
                    city_spiss.setText(data);
                    city_spsi.setText(data);


                }else {
                    selectedReceiverscity.remove(receiverscity[which]);
                    cityarray.remove(String.valueOf(receiverscity[which]));
                    String data = selectedReceiverscity.toString();
                    data = data.substring(0, data.length() - 1);
                    data = data.substring(1);
                    tutor_city = data;
                    city_spiss.setText(data);
                    city_spsi.setText(data);
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
                    time_duration.setText(data);
                    etstudent_timing.setText(data);

                }else {
                    selectedReceiverstime.remove(receiverstime[which]);
                    timearray.remove(timeidlist.get(which));
                    String data = selectedReceiverstime.toString();
                    data = data.substring(0, data.length() - 1);
                    data = data.substring(1);
                    time_duration.setText(data);
                    etstudent_timing.setText(data);
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
                    String data = selectedReceivers.toString();
                    data = data.substring(0, data.length() - 1);
                    data = data.substring(1);
                    btnsubject.setText(data);
                    Subjecttwo = data;
                    Subject_student.setText(data);
                }else {
                    selectedReceivers.remove(receivers[which]);
                    subjectarray.remove(subjectidlist.get(which));
                    String data = selectedReceivers.toString();
                    data = data.substring(0, data.length() - 1);
                    data = data.substring(1);
                    btnsubject.setText(data);
                    Subjecttwo = data;
                    Subject_student.setText(data);
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

    protected void showWeekSelectReceiversDialog() {

        boolean[] checkedReceivers = new boolean[receiversweekSubject.length];
        int count = receiversweekSubject.length;

        for(int i = 0; i < count; i++)
            checkedReceivers[i] = selectedReceiversweekSubject.contains(receiversweekSubject[i]);

        DialogInterface.OnMultiChoiceClickListener receiversDialogListener = new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if(isChecked) {
                    selectedReceiversweekSubject.add(receiversweekSubject[which]);
                    String data = selectedReceiversweekSubject.toString();
                    data = data.substring(0, data.length() - 1);
                    data = data.substring(1);
                    weeksubject.setText(data);
                    weeksubjectarray.add(subjectlist.get(which));
                }else {
                    selectedReceiversweekSubject.remove(receiversweekSubject[which]);
                    String data = selectedReceiversweekSubject.toString();
                    data = data.substring(0, data.length() - 1);
                    data = data.substring(1);
                    weeksubject.setText(data);
                    weeksubjectarray.remove(subjectlist.get(which));
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            if(btnNumber.equalsIgnoreCase("Doc")){
                Bitmap photo = (Bitmap) data.getExtras().get("data");
//                student_pic.setImageBitmap(photo);

                Bundle bundle = data.getExtras();
                final Bitmap bmp = (Bitmap) bundle.get("data");
                //  ivuploadimg.setImageBitmap(bmp);
                //    uploadtitle.setText("screenshot upload successfully");

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                CDRIVES = byteArrayOutputStream.toByteArray();
//                encoded = Base64.encodeToString(CDRIVES, Base64.DEFAULT);



                if(Numberxxx == 1){
                    tenth = "10";
                    ivonebtn.setVisibility(View.VISIBLE);
                    ivonebtn.setImageBitmap(photo);
                    encoded_one = Base64.encodeToString(CDRIVES, Base64.DEFAULT);

                }else  if(Numberxxx == 2){
                    twelth= "12";
                    ivtwobtn.setVisibility(View.VISIBLE);
                    ivtwobtn.setImageBitmap(photo);
                    encoded_two = Base64.encodeToString(CDRIVES, Base64.DEFAULT);

                }else if(Numberxxx == 3){
                    ivthreebtn.setVisibility(View.VISIBLE);
                    ivthreebtn.setImageBitmap(photo);

                    encoded_three = Base64.encodeToString(CDRIVES, Base64.DEFAULT);
                }else if(Numberxxx == 4){
                    ivfourbtn.setVisibility(View.VISIBLE);
                    ivfourbtn.setImageBitmap(photo);

                    encoded_four = Base64.encodeToString(CDRIVES, Base64.DEFAULT);
                }
            }else {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                student_pic.setImageBitmap(photo);

                Bundle bundle = data.getExtras();
                final Bitmap bmp = (Bitmap) bundle.get("data");
                //  ivuploadimg.setImageBitmap(bmp);
                //    uploadtitle.setText("screenshot upload successfully");

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                CDRIVES = byteArrayOutputStream.toByteArray();
                encoded = Base64.encodeToString(CDRIVES, Base64.DEFAULT);
            }
        }else if(requestCode==SELECT_FILE){
            if(btnNumber.equalsIgnoreCase("Doc")){
                Uri selectedImageUri = data.getData();
//                student_pic.setImageURI(selectedImageUri);
                Bitmap selectedImage = null;
                try {
                    selectedImage = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                CDRIVES = byteArrayOutputStream.toByteArray();
//                encoded = Base64.encodeToString(CDRIVES, Base64.DEFAULT);

                if(Numberxxx == 1){
                    tenth = "10";
                    ivonebtn.setVisibility(View.VISIBLE);
                    ivonebtn.setImageURI(selectedImageUri);

                    encoded_one = Base64.encodeToString(CDRIVES, Base64.DEFAULT);

                }else  if(Numberxxx == 2){
                    twelth= "12";
                    ivtwobtn.setVisibility(View.VISIBLE);
                    ivtwobtn.setImageURI(selectedImageUri);

                    encoded_two = Base64.encodeToString(CDRIVES, Base64.DEFAULT);

                }else if(Numberxxx == 3){
                    ivthreebtn.setVisibility(View.VISIBLE);
                    ivthreebtn.setImageURI(selectedImageUri);

                    encoded_three = Base64.encodeToString(CDRIVES, Base64.DEFAULT);


                }else if(Numberxxx == 4){
                    ivfourbtn.setVisibility(View.VISIBLE);
                    ivfourbtn.setImageURI(selectedImageUri);

                    encoded_four = Base64.encodeToString(CDRIVES, Base64.DEFAULT);
                }
            }else {
                Uri selectedImageUri = data.getData();
                student_pic.setImageURI(selectedImageUri);
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
            }


//                Toast.makeText(ComplainsActivity.this, encoded, Toast.LENGTH_LONG).show();
        }
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

                        Toast.makeText(Signup.this, "Techincal problem. Please contact the admistrator", Toast.LENGTH_SHORT).show();

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
                            Toast.makeText(Signup.this, "email and pass not check", Toast.LENGTH_SHORT).show();
                            break;
                        case 403:
                            Toast.makeText(Signup.this, "ForbiddenException", Toast.LENGTH_SHORT).show();
                            break;
                        case 404:
                            Toast.makeText(Signup.this, "not found", Toast.LENGTH_SHORT).show();
                            break;
                        case 500:
                            Toast.makeText(Signup.this, "server broken", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(Signup.this, "unknown error", Toast.LENGTH_SHORT).show();
                            break;
                    }
                    Toast.makeText(Signup.this, "Something error please try again", Toast.LENGTH_SHORT).show();


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

                        Toast.makeText(Signup.this, "Techincal problem. Please contact the admistrator", Toast.LENGTH_SHORT).show();

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
                            Toast.makeText(Signup.this, "email and pass not check", Toast.LENGTH_SHORT).show();
                            break;
                        case 403:
                            Toast.makeText(Signup.this, "ForbiddenException", Toast.LENGTH_SHORT).show();
                            break;
                        case 404:
                            Toast.makeText(Signup.this, "not found", Toast.LENGTH_SHORT).show();
                            break;
                        case 500:
                            Toast.makeText(Signup.this, "server broken", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(Signup.this, "unknown error", Toast.LENGTH_SHORT).show();
                            break;
                    }
                    Toast.makeText(Signup.this, "Something error please try again", Toast.LENGTH_SHORT).show();


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

                        Toast.makeText(Signup.this, "Techincal problem. Please contact the admistrator", Toast.LENGTH_SHORT).show();

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
                            Toast.makeText(Signup.this, "email and pass not check", Toast.LENGTH_SHORT).show();
                            break;
                        case 403:
                            Toast.makeText(Signup.this, "ForbiddenException", Toast.LENGTH_SHORT).show();
                            break;
                        case 404:
                            Toast.makeText(Signup.this, "not found", Toast.LENGTH_SHORT).show();
                            break;
                        case 500:
                            Toast.makeText(Signup.this, "server broken", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(Signup.this, "unknown error", Toast.LENGTH_SHORT).show();
                            break;
                    }
                    Toast.makeText(Signup.this, "Something error please try again", Toast.LENGTH_SHORT).show();


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

    // student signup Method


    private void retrofit_singup() {

        String studentname = etui_student_signup.getText().toString().trim();
        final String student_email = etui_email_student.getText().toString().trim();
        String student_mobile = etui_mobile_student.getText().toString().trim();
        String student_class = etclass_student.getText().toString().trim();
        //  String student_subject_string = student_subject.getText().toString().trim();
        String student_weak_subject = etweak_subject.getText().toString().trim();
//        String student_timing = etstudent_timing.getText().toString().trim();
        String student_sortDesc = etstudent_shortdec.getText().toString().trim();
        String student_school = ui_school_student.getText().toString().trim();
        String student_City = student_city.getText().toString().trim();
        String student_Location = student_location.getText().toString().trim();







        if(student_email.length() ==0){
            Toast.makeText(this, "Please Enter your email", Toast.LENGTH_SHORT).show();
        }else if(studentname.length() == 0){
            Toast.makeText(this, "Please Enter you name", Toast.LENGTH_SHORT).show();
        }else if (etui_mobile_student.getText().length() ==0){
            Toast.makeText(this, "Please Enter your mobile number", Toast.LENGTH_SHORT).show();
        }else if (student_school.length() ==0){
            Toast.makeText(this, "Please Enter your School", Toast.LENGTH_SHORT).show();
        }else if(student_class.length() ==0){
            Toast.makeText(this, "Please Select your class", Toast.LENGTH_SHORT).show();
        }else if(cityarray.size() ==0) {
            Toast.makeText(this, "Please Select your city", Toast.LENGTH_SHORT).show();
        }else if(student_Location.length() ==0){
            Toast.makeText(this, "Please Enter your location", Toast.LENGTH_SHORT).show();
        }else if(subjectarray.size() == 0){
            Toast.makeText(this, "Please Select your Subject", Toast.LENGTH_SHORT).show();
        }else if(weeksubjectarray.size()==0){
            Toast.makeText(this,"Please Select your Week Subject",Toast.LENGTH_LONG).show();
        }else if(timearray.size() ==0){
            Toast.makeText(this, "Please Select your time", Toast.LENGTH_SHORT).show();
        }else if(encoded.length() ==0){
            Toast.makeText(this, "Please Select image", Toast.LENGTH_SHORT).show();
        }else if (student_sortDesc.length() ==0){
            Toast.makeText(this, "Please enter your sort desc.", Toast.LENGTH_SHORT).show();
        }else {


            final StudentSignupRequestJson request = new StudentSignupRequestJson();
            request.setStudent_name(studentname);
            request.setMobile_no(etui_mobile_student.getText().toString());
            request.setEmail(student_email);
            request.setSchool_name(student_school);
            request.setClasss(student_class);
            request.setCity(cityarray.toString());
            request.setCurrent_location(student_Location);
            request.setSubjects(subjectarray.toString());
            request.setWeak_subjects(weeksubjectarray.toString());
            request.setSuitable_timing(timearray.toString());
            request.setUpload_pic(encoded);
            request.setNotes(student_sortDesc);
            request.setBoard(etui_board_student.getText().toString());
            Log.d("StatusLucckydfjkk", "requst  =" + new Gson().toJson(request));


            Log.d("LuckyYUYU", "Email = " + etui_email_student.getText().toString().trim());//
            Log.d("LuckyYUYU", "UserName = " + etui_student_signup.getText().toString());//
            Log.d("LuckyYUYU", "Mobile = " + etui_mobile_student.getText().toString());//
            Log.d("LuckyYUYU", "School = " + ui_school_student.getText().toString());//
            Log.d("LuckyYUYU", "City = " + city_spiss.getText().toString());//
            Log.d("LuckyYUYU", "Current Location = " + student_location.getText().toString());//
            Log.d("LuckyYUYU", "Class = " + etclass_student.getText().toString());//
            Log.d("LuckyYUYU", "Week Subject = " + weeksubject.getText().toString());

            Log.d("LuckyYUYU", "Subject = " + Subjecttwo.toString());
            Log.d("LuckyYUYU", "Time = " + etstudent_timing.getText().toString());
            Log.d("LuckyYUYU", "student_sortDesc = " + student_sortDesc.toString());


            UserService service = ServiceGenerator.createService(UserService.class, null, null);
            service.student_signup(request).enqueue(new Callback<StudentSignupResponseJson>() {
                @Override
                public void onResponse(Call<StudentSignupResponseJson> call, Response<StudentSignupResponseJson> response) {
                    if (response.isSuccessful()) {
                        Log.d("StatusLucckydfjkk", "response  =" + new Gson().toJson(response.body()));
                        if (response.body().success.equalsIgnoreCase("false")) { ////New App

                            Toast.makeText(Signup.this, "Techincal problem. Please contact the admistrator", Toast.LENGTH_SHORT).show();
                            progressBar.dismiss();
                        } else if (response.body().success.equalsIgnoreCase("true")) {//// All Okay


                            edits.putString("id", response.body().id);
                            edits.putString("type", "student");
                            edits.putString("image", response.body().image);
                            edits.putString("email", etui_email_student.getText().toString().trim());
                            edits.putString("name", etui_student_signup.getText().toString().trim());
                            edits.putString("mobile", etui_mobile_student.getText().toString().trim());
                            edits.putString("student_class", etclass_student.getText().toString().trim());
                            edits.putString("student_weak_subject", weeksubject.getText().toString().trim());
                            edits.putString("student_timing", etstudent_timing.getText().toString().trim());
                            edits.putString("subject", Subjecttwo.toString());

                            edits.putString("student_sortDesc", etstudent_shortdec.getText().toString().trim());
                            edits.putString("student_school", ui_school_student.getText().toString().trim());
                            edits.putString("student_City", city_spiss.getText().toString());
                            edits.putString("student_Location", student_location.getText().toString().trim());
                            edits.putString("student_board", etboard.getText().toString().trim());


                            edits.apply();

                            Intent intent = new Intent(Signup.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            progressBar.dismiss();

                        }


                    } else {
                        Log.d("walletwallets", "System code:= " + response.code());
                        switch (response.code()) {
                            case 401:
                                Toast.makeText(Signup.this, "email and pass not check", Toast.LENGTH_SHORT).show();
                                break;
                            case 403:
                                Toast.makeText(Signup.this, "ForbiddenException", Toast.LENGTH_SHORT).show();
                                break;
                            case 404:
                                Toast.makeText(Signup.this, "not found", Toast.LENGTH_SHORT).show();
                                break;
                            case 500:
                                Toast.makeText(Signup.this, "server broken", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                Toast.makeText(Signup.this, "unknown error", Toast.LENGTH_SHORT).show();
                                break;
                        }
                        Toast.makeText(Signup.this, "Something error please try again", Toast.LENGTH_SHORT).show();

                        progressBar.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<StudentSignupResponseJson> call, Throwable t) {
                    t.printStackTrace();
                    Log.d("walletwallets", "System error:= " + t.getLocalizedMessage());


                    Log.e("System error:", t.getLocalizedMessage());

                }
            });
        }
    }


    public String data ="";
    // Tutor SignUp Method
    private void tutorSignup() {

      //  String user_type = user_text.getText().toString().trim();
      //  int directory_type = keyNameStr;
        String username = edit_username.getText().toString().trim();
        final String email = edit_email.getText().toString().trim();
        String tutor_mobile = edit_mobile.getText().toString().trim();
        String tutor_age = edit_tutorAge.getText().toString().trim();
        String tutor_exp = tutor_experience.getText().toString().trim();
        String subject_offerd = subject_offered.getText().toString().trim();
        String specializaionT = specialization.getText().toString().trim();
        String tutor_citys = Tutercurrent_city.getText().toString().trim();
        String tutor_area = area_ofteaching.getText().toString().trim();
//        String timeDuration = time_duration.getText().toString().trim();

        String imageString1 = student_pic.getImageMatrix().toString().trim();
      /*  int age = Integer.parseInt(tutor_age);
        int expere =  Integer.parseInt(tutor_exp);*/


       if(email.length() ==0){
           Toast.makeText(this, "Please Enter your email", Toast.LENGTH_SHORT).show();
       }else if(edit_username.getText().length() == 0){
           Toast.makeText(this, "Please Enter you name", Toast.LENGTH_SHORT).show();
       }else if (edit_mobile.getText().length() ==0){
           Toast.makeText(this, "Please Enter your mobile number", Toast.LENGTH_SHORT).show();
       }else if (edit_tutorAge.getText().length() ==0){
           Toast.makeText(this, "Please Enter your age", Toast.LENGTH_SHORT).show();
       }else if(subjectarray.size() ==0){
           Toast.makeText(this, "Please Select your Subject", Toast.LENGTH_SHORT).show();
       }else if(spinnertutorqualification.length() ==0) {
           Toast.makeText(this, "Please Select your Qualification", Toast.LENGTH_SHORT).show();
       }else if(tutor_experience.getText().length() ==0){
           Toast.makeText(this, "Please Enter your Experience", Toast.LENGTH_SHORT).show();
       }else if(Willing_to_travel.length()==0){
           Toast.makeText(this,"Please Select your willing to travel",Toast.LENGTH_LONG).show();
       }else if(tutor_days.length() ==0){
           Toast.makeText(this, "Please Select your days", Toast.LENGTH_SHORT).show();
       }else if(specialization.getText().length() ==0){
           Toast.makeText(this, "Please Select any specialization", Toast.LENGTH_SHORT).show();
       }else if (tutor_city.length() ==0){
           Toast.makeText(this, "Please Select your city", Toast.LENGTH_SHORT).show();
       }else if (area_ofteaching.getText().length() ==0){
           Toast.makeText(this, "Please Enter your area of teaching", Toast.LENGTH_SHORT).show();
       }else if(timearray.size() == 0){
           Toast.makeText(this, "Please Enter your timing", Toast.LENGTH_SHORT).show();
       }
//       else if(tenth.equalsIgnoreCase("10")){
//           Toast.makeText(this, "Please Upload 10th document.", Toast.LENGTH_SHORT).show();
//       }else if(twelth.equalsIgnoreCase("12")){
//           Toast.makeText(this, "Please Upload 12th document.", Toast.LENGTH_SHORT).show();
//       }
       else{
           Log.d("LuckyYUYU","Email = "+email);//
           Log.d("LuckyYUYU","UserName = "+edit_username.getText().toString());//
           Log.d("LuckyYUYU","Mobile = "+edit_mobile.getText().toString());//
           Log.d("LuckyYUYU","Age = "+edit_tutorAge.getText().toString());//
           Log.d("LuckyYUYU","Subject = "+subjectarray.toString());//
           Log.d("LuckyYUYU","Qualification = "+spinnertutorqualification.toString());//
           Log.d("LuckyYUYU","Experience = "+tutor_experience.getText().toString());//
           Log.d("LuckyYUYU","tutor type = "+TutorTyple.toString());
           Log.d("LuckyYUYU","willing to traveling = "+Willing_to_travel.toString());
           Log.d("LuckyYUYU","day = "+tutor_days.toString());
           Log.d("LuckyYUYU","subject_offerd = "+subject_offered.getText().toString());
           Log.d("LuckyYUYU","any specialization = "+specialization.getText().toString());
           Log.d("LuckyYUYU","City = "+tutor_city.toString());
           Log.d("LuckyYUYU","area of teaching = "+area_ofteaching.getText().toString());
           Log.d("LuckyYUYU","time = "+timearray.toString());
           Log.d("LuckyYUYU","fee = "+"");


           edits.putString("email",email);
           edits.putString("name",edit_username.getText().toString().trim());
           edits.putString("mobile",edit_mobile.getText().toString().trim());
           edits.putString("age",edit_tutorAge.getText().toString().trim());
           edits.putString("qualification",spinnertutorqualification);
           edits.putString("tutor_exp",tutor_experience.getText().toString().trim());
           edits.putString("Subjectid",subjectarray.toString());


           edits.putString("day",tutor_days);
           edits.putString("Willing_to_travel",Willing_to_travel.toString());
           edits.putString("TutorTyple",TutorTyple.toString());


           edits.putString("specialization",specialization.getText().toString().trim());
           edits.putString("mobile",edit_mobile.getText().toString().trim());
           edits.putString("age",edit_tutorAge.getText().toString().trim());
           edits.putString("qualification",spinnertutorqualification);
           edits.putString("Tutercurrent_city",tutor_city.toString());
           edits.putString("subject_offerd",subjectarray.toString());

           edits.putString("Subjecttwointext",Subjecttwo);

           edits.putString("subject_details",subjectlist.toString());

           edits.putString("tutor_area",area_ofteaching.getText().toString().trim());
           edits.putString("timeDuration",timearray.toString());
           edits.putString("img",encoded);

           edits.putString("fee","");
           edits.putString("Transaction_status","0");



           edits.putString("board",etboard.getText().toString());
           edits.putString("board_date",btndate.getText().toString());


           edits.putString("schoolname",etschoolname.getText().toString());
           edits.putString("school_date",btndate.getText().toString());
           edits.putString("collage",etclgname.getText().toString());
           edits.putString("collage_date",btnclgdate.getText().toString());
           edits.putString("approved_doc","");

           edits.apply();

           Log.d("StatusLucckydfj","age  ="+edit_tutorAge.getText().toString().trim());



           TutorSignupRequestJson request = new TutorSignupRequestJson();
           request.setName(sharedPreferences.getString("name",""));
           request.setEmail(sharedPreferences.getString("email",""));
           request.setMobile_no(sharedPreferences.getString("mobile",""));
           request.setAge(sharedPreferences.getString("age",""));
           request.setQualification(sharedPreferences.getString("qualification",""));
           request.setExperience_in_teaching(sharedPreferences.getString("tutor_exp",""));
           request.setSubjects_offered(sharedPreferences.getString("subject_offerd",""));

           request.setAny_specialization(sharedPreferences.getString("specialization",""));
           request.setCurrent_city(sharedPreferences.getString("Tutercurrent_city",""));
//        request.setAge(sharedPreferences.getString("tutor_area",""));
           request.setTime_duration_alloted_to_students(sharedPreferences.getString("timeDuration",""));
           request.setFees("0");
           request.setUpload_pic(sharedPreferences.getString("img",""));
           request.setTransectionid("0");

           request.setDay(sharedPreferences.getString("day",""));
           request.setArea_of_teaching(sharedPreferences.getString("tutor_area",""));
           request.setTutortyple(sharedPreferences.getString("TutorTyple",""));
           request.setWilling_to_travel(sharedPreferences.getString("Willing_to_travel",""));
           request.setTransaction_status("0");

           request.setOne_doc(encoded_one);
           request.setTwo_doc(encoded_two);
           request.setThree_doc(encoded_three);
           request.setFour_doc(encoded_four);
           request.setBoard(etboard.getText().toString());
           request.setBoard_date(btndate.getText().toString());

           request.setSchool_name(etschoolname.getText().toString());
           request.setSchool_date(btndate.getText().toString());

           request.setClg_name(etclgname.getText().toString());
           request.setClg_date(btnclgdate.getText().toString());
           Log.d("StatusLucckydfj","requst  ="+new Gson().toJson(request));

           UserService service = ServiceGenerator.createService(UserService.class, null, null);
           service.tutor_signup(request).enqueue(new Callback<TutorSignupResponseJson>() {
               @Override
               public void onResponse(Call<TutorSignupResponseJson> call, Response<TutorSignupResponseJson> response) {
                   if (response.isSuccessful()) {
                       Log.d("StatusLucckydfj","response  ="+new Gson().toJson(response.body()));
                       if (response.body().success.equalsIgnoreCase("false")) { ////New App

                           Toast.makeText(Signup.this, "Techincal problem. Please contact the admistrator", Toast.LENGTH_SHORT).show();

                       } else if (response.body().success.equalsIgnoreCase("true")) {//// All Okay

                           edits.putString("id",response.body().id);
                           edits.putString("type","tutor");
                           edits.putString("image",response.body().image);
                           edits.putString("fee","0");
                           edits.apply();

                           Intent intent = new Intent(Signup.this, TutorActivity.class);
                           startActivity(intent);
                           finish();



                       }


                   } else {
                       Log.d("walletwallets", "System code:= " + response.code());
                       switch (response.code()) {
                           case 401:
                               Toast.makeText(Signup.this, "email and pass not check", Toast.LENGTH_SHORT).show();
                               break;
                           case 403:
                               Toast.makeText(Signup.this, "ForbiddenException", Toast.LENGTH_SHORT).show();
                               break;
                           case 404:
                               Toast.makeText(Signup.this, "not found", Toast.LENGTH_SHORT).show();
                               break;
                           case 500:
                               Toast.makeText(Signup.this, "server broken", Toast.LENGTH_SHORT).show();
                               break;
                           default:
                               Toast.makeText(Signup.this, "unknown error", Toast.LENGTH_SHORT).show();
                               break;
                       }
                       Toast.makeText(Signup.this, "Something error please try again", Toast.LENGTH_SHORT).show();
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
}
