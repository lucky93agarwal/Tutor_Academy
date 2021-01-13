package com.mypersonal.tutor.anurag.register.customadapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mypersonal.tutor.Activities.CoursesActivity;
import com.mypersonal.tutor.Activities.PersonalTutorActivity;
import com.mypersonal.tutor.Activities.SignInActivity;
import com.mypersonal.tutor.Activities.TutorListActivity;
import com.mypersonal.tutor.Activities.TutordetailsActivity;
import com.mypersonal.tutor.Adapters.CoursesAdapter;
import com.mypersonal.tutor.Adapters.MessageListAdapter;
import com.mypersonal.tutor.Adapters.RequestLsitAdapter;

import com.mypersonal.tutor.JSONSchemas.CourseSchema;
import com.mypersonal.tutor.Models.Course;
import com.mypersonal.tutor.Models.GetMessage;
import com.mypersonal.tutor.Models.GetRequestList;
import com.mypersonal.tutor.Models.GetTutorData;
import com.mypersonal.tutor.Models.RequestResponseRequestJson;
import com.mypersonal.tutor.Models.RequsetResponseRequestJson;
import com.mypersonal.tutor.Models.TutorListRequestJson;
import com.mypersonal.tutor.Models.TutorListResponseJson;
import com.mypersonal.tutor.Models.TutorRequestListRequestJson;
import com.mypersonal.tutor.Models.TutorResponseListRequestJson;
import com.mypersonal.tutor.Models.User;
import com.mypersonal.tutor.Network.Api;
import com.mypersonal.tutor.R;
import com.mypersonal.tutor.popup.msgpopup;
import com.mypersonal.tutor.popup.msgreplypopup;
import com.mypersonal.tutor.retrofit.ServiceGenerator;
import com.mypersonal.tutor.retrofit.UserService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RequestDemoClass extends AppCompatActivity {

    private ArrayList<Course> mCourses = new ArrayList<>();
    private ArrayList<User> mStudent = new ArrayList<>();
    Button showSearchBoxButton;
    Button hideSearchBoxButton;
    Button backButton;
    ImageView applicationLogo;
    EditText searchStringInputField;
    Editable searchString;

    private String selectedCategory;
    private String selectedPrice;
    private String selectedDifficultyLevel;
    private String selectedLanguage;
    private String selectedRating;
    private static msgreplypopup addpopup;

    public Button btnprofilebtn,btnlogoutbtn;

    private RecyclerView recyclerViewForStudentsRequest = null;
    private RecyclerView recyclerViewForStudentsmessage = null;




    public List<GetRequestList> productList = new ArrayList<>();
    public GetRequestList getbcdata;

    public RecyclerView.LayoutManager mLayoutManager;
    public RequestLsitAdapter adapter;
    public String LIST_STATE_KEY = "list_state";
    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor edits;
    public Button btnrequest, btnmsg;


    public List<GetMessage> productListSec = new ArrayList<>();
    public GetMessage getbcdatasec;
    public MessageListAdapter adaptersec;
    public RecyclerView.LayoutManager mLayoutManagerSec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_demo_class);

        addpopup = new msgreplypopup(RequestDemoClass.this);
        recyclerViewForStudentsmessage = (RecyclerView) findViewById(R.id.recyclerViewmessage);

        btnrequest = (Button)findViewById(R.id.btnrequest);
        btnmsg = (Button)findViewById(R.id.btnmessage);


        btnmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnmsg.setBackgroundColor(Color.parseColor("#164384"));
                btnrequest.setBackgroundColor(Color.parseColor("#205cb3"));
                recyclerViewForStudentsmessage.setVisibility(View.VISIBLE);
                recyclerViewForStudentsRequest.setVisibility(View.GONE);
                getTopMessageList();
            }
        });



        btnrequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnrequest.setBackgroundColor(Color.parseColor("#164384"));
                btnmsg.setBackgroundColor(Color.parseColor("#205cb3"));

                recyclerViewForStudentsmessage.setVisibility(View.GONE);
                recyclerViewForStudentsRequest.setVisibility(View.VISIBLE);

                getTopTutorList();
            }
        });
        sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
        edits = sharedPreferences.edit();

        btnprofilebtn = (Button)findViewById(R.id.profilebtn);
        btnlogoutbtn = (Button)findViewById(R.id.logoutbtn);


        btnlogoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edits.clear();
                edits.commit();
                Intent intent = new Intent(RequestDemoClass.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });


        btnprofilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RequestDemoClass.this, TutordetailsActivity.class);
                startActivity(intent);
            }
        });
        initView();

        getTopTutorList();
    }


    public void cancel(String status,String id){
        final RequestResponseRequestJson request = new RequestResponseRequestJson();
        request.setRequestid(id);


        Log.d("StatusLuccky","requst  ="+new Gson().toJson(request));
        UserService service = ServiceGenerator.createService(UserService.class, null, null);
        service.cancel_request_li(request).enqueue(new Callback<RequsetResponseRequestJson>() {
            @Override
            public void onResponse(Call<RequsetResponseRequestJson> call, retrofit2.Response<RequsetResponseRequestJson> response) {
                Log.d("StatusLuccky", "id Status =" + response.code());
                if (response.isSuccessful()) {
                    Log.d("StatusLuccky", "id Status =" + response.code());
                    if (response.body().status.equalsIgnoreCase("0")) {
                        Toast.makeText(RequestDemoClass.this, "No Request Found", Toast.LENGTH_LONG).show();
                    } else {

                        Toast.makeText(RequestDemoClass.this, "Request Send", Toast.LENGTH_LONG).show();


                    }


                } else {
                    switch (response.code()) {
                        case 401:
                            Toast.makeText(RequestDemoClass.this, "email and pass not check", Toast.LENGTH_SHORT).show();
                            break;
                        case 403:
                            Toast.makeText(RequestDemoClass.this, "ForbiddenException", Toast.LENGTH_SHORT).show();
                            break;
                        case 404:
                            Toast.makeText(RequestDemoClass.this, "not found", Toast.LENGTH_SHORT).show();
                            break;
                        case 500:
                            Toast.makeText(RequestDemoClass.this, "server broken", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(RequestDemoClass.this, "unknown error", Toast.LENGTH_SHORT).show();
                            break;

                    }
                    Toast.makeText(RequestDemoClass.this, "Something error please try again", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<RequsetResponseRequestJson> call, Throwable t) {
                t.printStackTrace();


                Log.e("System error:", t.getLocalizedMessage());

            }
        });
    }

    public void msgreply(String status,String id){

    }
    public void doAddition(String status,String id){
        final RequestResponseRequestJson request = new RequestResponseRequestJson();
        request.setRequestid(id);


        Log.d("StatusLuccky","requst  ="+new Gson().toJson(request));
        UserService service = ServiceGenerator.createService(UserService.class, null, null);
        service.request_li(request).enqueue(new Callback<RequsetResponseRequestJson>() {
            @Override
            public void onResponse(Call<RequsetResponseRequestJson> call, retrofit2.Response<RequsetResponseRequestJson> response) {
                Log.d("StatusLuccky", "id Status =" + response.code());
                if (response.isSuccessful()) {
                    Log.d("StatusLuccky", "id Status =" + response.code());
                    if (response.body().status.equalsIgnoreCase("0")) {
                        Toast.makeText(RequestDemoClass.this, "No Request Found", Toast.LENGTH_LONG).show();
                    } else {

                        Toast.makeText(RequestDemoClass.this, "Request Send", Toast.LENGTH_LONG).show();


                    }


                } else {
                    switch (response.code()) {
                        case 401:
                            Toast.makeText(RequestDemoClass.this, "email and pass not check", Toast.LENGTH_SHORT).show();
                            break;
                        case 403:
                            Toast.makeText(RequestDemoClass.this, "ForbiddenException", Toast.LENGTH_SHORT).show();
                            break;
                        case 404:
                            Toast.makeText(RequestDemoClass.this, "not found", Toast.LENGTH_SHORT).show();
                            break;
                        case 500:
                            Toast.makeText(RequestDemoClass.this, "server broken", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(RequestDemoClass.this, "unknown error", Toast.LENGTH_SHORT).show();
                            break;

                    }
                    Toast.makeText(RequestDemoClass.this, "Something error please try again", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<RequsetResponseRequestJson> call, Throwable t) {
                t.printStackTrace();


                Log.e("System error:", t.getLocalizedMessage());

            }
        });
    }
    private void getTopMessageList() {

        final TutorRequestListRequestJson request = new TutorRequestListRequestJson();
        request.setTutorid(sharedPreferences.getString("id",""));
//        request.setTutorid("UT25826");

        Log.d("StatusLuccky","requst  ="+new Gson().toJson(request));

        UserService service = ServiceGenerator.createService(UserService.class, null, null);
        service.tutor_msg_listsss(request).enqueue(new Callback<TutorResponseListRequestJson>() {
            @Override
            public void onResponse(Call<TutorResponseListRequestJson> call, retrofit2.Response<TutorResponseListRequestJson> response) {
                Log.d("StatusLuccky", "id Status =" + response.code());
                Log.d("StatusLucckynew","requst response message  ="+response.code());
                if (response.isSuccessful()) {
                    Log.d("StatusLuccky", "id Status =" + response.code());
                    if (response.body().status.equalsIgnoreCase("0")) {
                        Toast.makeText(RequestDemoClass.this, "No Request Found", Toast.LENGTH_LONG).show();
                    } else {
                        if (productListSec.size() > 0) {
                            productListSec.clear();
                        }

                        for (int i = 0; i < response.body().datalist.size(); i++) {
                            getbcdatasec = new GetMessage();
                            getbcdatasec.setName(response.body().datalist.get(i).name);
                            getbcdatasec.setEmail(response.body().datalist.get(i).email);
                            getbcdatasec.setMobile_no(response.body().datalist.get(i).mobile_no);
                            getbcdatasec.setClasss(response.body().datalist.get(i).classs);

                            getbcdatasec.setCity(response.body().datalist.get(i).city);
                            getbcdatasec.setCurrent_location(response.body().datalist.get(i).current_location);
                            getbcdatasec.setStudent_id(response.body().datalist.get(i).student_id);
                            getbcdatasec.setNotes(response.body().datalist.get(i).notes);
                            getbcdatasec.setMsg(response.body().datalist.get(i).msg);
                            getbcdatasec.setSchool_name(response.body().datalist.get(i).school_name);

                            getbcdatasec.setUpload_pic(response.body().datalist.get(i).upload_pic);
                            getbcdatasec.setSuitable_timing(response.body().datalist.get(i).suitable_timing);

                            getbcdatasec.setWeak_subjects(response.body().datalist.get(i).weak_subjects);


                            getbcdatasec.setMsg(response.body().datalist.get(i).msg);
                            getbcdatasec.setMsg_id(response.body().datalist.get(i).request_id);
                            getbcdatasec.setStatus(response.body().datalist.get(i).status);



                            getbcdatasec.setTransation_status(sharedPreferences.getString("Transaction_status","0"));



                            productListSec.add(getbcdatasec);

                        }

                        adaptersec = new MessageListAdapter(RequestDemoClass.this, productListSec);
                        mLayoutManagerSec = new LinearLayoutManager(RequestDemoClass.this, LinearLayoutManager.VERTICAL, false);
                        recyclerViewForStudentsmessage.setLayoutManager(mLayoutManagerSec);
                        recyclerViewForStudentsmessage.setAdapter(adaptersec);


                    }


                } else {
                    switch (response.code()) {
                        case 401:
                            Toast.makeText(RequestDemoClass.this, "email and pass not check", Toast.LENGTH_SHORT).show();
                            break;
                        case 403:
                            Toast.makeText(RequestDemoClass.this, "ForbiddenException", Toast.LENGTH_SHORT).show();
                            break;
                        case 404:
                            Toast.makeText(RequestDemoClass.this, "not found", Toast.LENGTH_SHORT).show();
                            break;
                        case 500:
                            Toast.makeText(RequestDemoClass.this, "server broken", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(RequestDemoClass.this, "unknown error", Toast.LENGTH_SHORT).show();
                            break;

                    }
                    Toast.makeText(RequestDemoClass.this, "Something error please try again", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<TutorResponseListRequestJson> call, Throwable t) {
                t.printStackTrace();


                Log.e("System error:", t.getLocalizedMessage());

            }
        });
    }


    private void getTopTutorList() {

        final TutorRequestListRequestJson request = new TutorRequestListRequestJson();
        request.setTutorid(sharedPreferences.getString("id",""));
//        request.setTutorid("UT25826");

        Log.d("StatusLuccky","requst  ="+new Gson().toJson(request));

        UserService service = ServiceGenerator.createService(UserService.class, null, null);
        service.tutor_requset_listsss(request).enqueue(new Callback<TutorResponseListRequestJson>() {
            @Override
            public void onResponse(Call<TutorResponseListRequestJson> call, retrofit2.Response<TutorResponseListRequestJson> response) {
                Log.d("StatusLuccky", "id Status =" + response.code());

                Log.d("StatusLucckynew","requst response  ="+new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    Log.d("StatusLuccky", "id Status =" + response.code());
                    if (response.body().status.equalsIgnoreCase("0")) {
                        Toast.makeText(RequestDemoClass.this, "No Request Found", Toast.LENGTH_LONG).show();
                    } else {
                        if (productList.size() > 0) {
                            productList.clear();
                        }

                        for (int i = 0; i < response.body().datalist.size(); i++) {
                            getbcdata = new GetRequestList();
                            getbcdata.setName(response.body().datalist.get(i).name);
                            getbcdata.setEmail(response.body().datalist.get(i).email);
                            getbcdata.setMobile_no(response.body().datalist.get(i).mobile_no);
                            getbcdata.setClasss(response.body().datalist.get(i).classs);

                            getbcdata.setCity(response.body().datalist.get(i).city);
                            getbcdata.setCurrent_location(response.body().datalist.get(i).current_location);
                            getbcdata.setStudent_id(response.body().datalist.get(i).student_id);
                            getbcdata.setNotes(response.body().datalist.get(i).notes);
                            getbcdata.setSchool_name(response.body().datalist.get(i).school_name);


                            getbcdata.setUpload_pic(response.body().datalist.get(i).upload_pic);
                            getbcdata.setSuitable_timing(response.body().datalist.get(i).suitable_timing);

                            getbcdata.setWeak_subjects(response.body().datalist.get(i).weak_subjects);
                            getbcdata.setSubjects(response.body().datalist.get(i).subjects);
                            getbcdata.setReq_subjects(response.body().datalist.get(i).req_subjects);

                            getbcdata.setReq_timing(response.body().datalist.get(i).req_timing);
                            getbcdata.setStatus(response.body().datalist.get(i).status);

                            getbcdata.setRequest_id(response.body().datalist.get(i).request_id);


                            getbcdata.setTransation_status(sharedPreferences.getString("Transaction_status","0"));

                            productList.add(getbcdata);

                        }

                        adapter = new RequestLsitAdapter(RequestDemoClass.this, productList);
                        mLayoutManager = new LinearLayoutManager(RequestDemoClass.this, LinearLayoutManager.VERTICAL, false);
                        recyclerViewForStudentsRequest.setLayoutManager(mLayoutManager);
                        recyclerViewForStudentsRequest.setAdapter(adapter);


                    }


                } else {
                    switch (response.code()) {
                        case 401:
                            Toast.makeText(RequestDemoClass.this, "email and pass not check", Toast.LENGTH_SHORT).show();
                            break;
                        case 403:
                            Toast.makeText(RequestDemoClass.this, "ForbiddenException", Toast.LENGTH_SHORT).show();
                            break;
                        case 404:
                            Toast.makeText(RequestDemoClass.this, "not found", Toast.LENGTH_SHORT).show();
                            break;
                        case 500:
                            Toast.makeText(RequestDemoClass.this, "server broken", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(RequestDemoClass.this, "unknown error", Toast.LENGTH_SHORT).show();
                            break;

                    }
                    Toast.makeText(RequestDemoClass.this, "Something error please try again", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<TutorResponseListRequestJson> call, Throwable t) {
                t.printStackTrace();


                Log.e("System error:", t.getLocalizedMessage());

            }
        });
    }

    private void initView() {

        showSearchBoxButton = findViewById(R.id.showSearchBarButton);
        hideSearchBoxButton = findViewById(R.id.hideSearchBarButton);
        applicationLogo = findViewById(R.id.applicationLogo);
        backButton = findViewById(R.id.backButton);
        backButton.setVisibility(View.VISIBLE);

        searchStringInputField = findViewById(R.id.searchStringInputField);
        searchStringInputField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchCourse();
                    return true;
                }
                return false;

            }
        });


        recyclerViewForStudentsRequest = (RecyclerView) findViewById(R.id.recyclerViewForSudentRequest);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);

        StudentRequestAdapter adapter = new StudentRequestAdapter(RequestDemoClass.this, mStudent);
        recyclerViewForStudentsRequest.setAdapter(adapter);
        recyclerViewForStudentsRequest.setLayoutManager(layoutManager);

    }
    public void searchCourse() {
        searchString = searchStringInputField.getText();
        filterCourse();
    }

    // This function is responsible for filtering the course list
    private void filterCourse() {
        String searchedString = searchString+"";
        Log.d("Search String", "The search string "+searchString);
       // progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        Api api = retrofit.create(Api.class);
        Call<List<CourseSchema>> call = api.getFilteredCourses(selectedCategory, selectedPrice, selectedDifficultyLevel, selectedLanguage, selectedRating, searchedString);
        call.enqueue(new Callback<List<CourseSchema>>() {
            @Override
            public void onResponse(Call<List<CourseSchema>> call, Response<List<CourseSchema>> response) {
                Log.d("AG", "The Response "+response.toString());
                mCourses = new ArrayList<>();
                List<CourseSchema> courseSchemas = response.body();
                for (CourseSchema m : courseSchemas) {
                    mCourses.add(new Course(m.getId(), m.getTitle(), m.getThumbnail(), m.getPrice(), m.getInstructorName(), m.getRating(), m.getNumberOfRatings(),m.getTotalEnrollment(), m.getShareableLink(), m.getCourseOverviewProvider(), m.getCourseOverviewUrl()));
                    Log.d("TAG", "Fetched Data "+m.getTitle());
                }
          //      progressBar.setVisibility(View.INVISIBLE);
              //  reloadCourses(mCourses);

            }

            @Override
            public void onFailure(Call<List<CourseSchema>> call, Throwable t) {
                Toast.makeText(RequestDemoClass.this, "Some error occurred", Toast.LENGTH_SHORT).show();
              //  progressBar.setVisibility(View.INVISIBLE);
            }
        });

        // Toggling keyboard
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchStringInputField.getWindowToken(),0);
    }



}