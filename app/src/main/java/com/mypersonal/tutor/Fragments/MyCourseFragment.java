package com.mypersonal.tutor.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.mypersonal.tutor.Activities.SignInActivity;
import com.mypersonal.tutor.Adapters.MyCourseAdapter;
import com.mypersonal.tutor.Adapters.RequestLsitAdapter;
import com.mypersonal.tutor.Adapters.RequestStudentLsitAdapter;
import com.mypersonal.tutor.Adapters.RequestTutorStudentLsitAdapter;
import com.mypersonal.tutor.JSONSchemas.CourseSchema;
import com.mypersonal.tutor.Models.GetRequestList;
import com.mypersonal.tutor.Models.GetStudentRequestList;
import com.mypersonal.tutor.Models.GetTutorStudentRequestList;
import com.mypersonal.tutor.Models.MyCourse;
import com.mypersonal.tutor.Models.TutorRequestListRequestJson;
import com.mypersonal.tutor.Models.TutorResponseListRequestJson;
import com.mypersonal.tutor.Models.TutorStudentRequestListRequestJson;
import com.mypersonal.tutor.Models.TutorStudentResponseListRequestJson;
import com.mypersonal.tutor.Network.Api;
import com.mypersonal.tutor.R;
import com.mypersonal.tutor.Utils.Helpers;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;
import com.mypersonal.tutor.anurag.register.customadapter.RequestDemoClass;
import com.mypersonal.tutor.retrofit.ServiceGenerator;
import com.mypersonal.tutor.retrofit.UserService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

public class MyCourseFragment extends Fragment {
    private static final String TAG = "MyCourseFragment";
    GridView myCoursesGridLayout;
    private ProgressBar progressBar;
    Button signInButton;
    RelativeLayout myCourseView;
    RelativeLayout signInPlaceholder;
    ProgressDialog progressBars;



    public List<GetTutorStudentRequestList> productListSec = new ArrayList<>();
    public GetTutorStudentRequestList getbcdataSec;


    public RecyclerView.LayoutManager mLayoutManagerSec;
    public RequestTutorStudentLsitAdapter adapterSec;


    public List<GetStudentRequestList> productList = new ArrayList<>();
    public GetStudentRequestList getbcdata;

    public RecyclerView.LayoutManager mLayoutManager;
    public RequestStudentLsitAdapter adapter;
    public String LIST_STATE_KEY = "list_state";
    
    
    public RecyclerView mRecyclerView,mSecRecyclerView;
    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor edits;

    public Button btnall, btnrequestbtn;

    public int Num = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.studentrequestlayout, container, false);
        init(view);
        initProgressBar(view);
        progressBars = new ProgressDialog(getActivity());
        progressBars.setCancelable(true);
        progressBars.setMessage("Please wait ...");
        sharedPreferences = getActivity().getSharedPreferences("data",MODE_PRIVATE);
        edits = sharedPreferences.edit();
        mRecyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);

        mSecRecyclerView = (RecyclerView)view.findViewById(R.id.messagerecyclerView);

        btnall = (Button)view.findViewById(R.id.allrequest);

        btnrequestbtn = (Button)view.findViewById(R.id.connectbtn);


        btnall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnrequestbtn.setBackgroundColor(Color.parseColor("#8398E3"));
                btnall.setBackgroundColor(Color.parseColor("#FFA23D"));
                Num = 0;

                if(productListSec.size() >0){
                    productListSec.clear();
                }
                if(productList.size()>0){
                    productList.clear();
                }


                getTopTutorList();


                getTopTutorListSec();
            }
        });

        btnrequestbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnall.setBackgroundColor(Color.parseColor("#8398E3"));
                btnrequestbtn.setBackgroundColor(Color.parseColor("#FFA23D"));
                Num = 1;
                if(productListSec.size() >0){
                    productListSec.clear();
                }
                if(productList.size()>0){
                    productList.clear();
                }
                getTopTutorList();


                getTopTutorListSec();
            }
        });


        getTopTutorList();

        getTopTutorListSec();
//        if (isLoggedIn()){
//            this.loggedInView();
//        }else{
//            this.loggedOutView();
//        }
        return view;
    }

    private void getTopTutorListSec(){
        progressBars.show();
        final TutorStudentRequestListRequestJson request = new TutorStudentRequestListRequestJson();
        request.setStudentid(sharedPreferences.getString("id",""));

        Log.d("StatusLucckyLuckyNew","requst 1  ="+new Gson().toJson(request));

        UserService service = ServiceGenerator.createService(UserService.class, null, null);
        service.tutor_student_requset_listsssjjj(request).enqueue(new Callback<TutorStudentResponseListRequestJson>() {
            @Override
            public void onResponse(Call<TutorStudentResponseListRequestJson> call, retrofit2.Response<TutorStudentResponseListRequestJson> response) {
                Log.d("StatusLucckyLuckyNew","response 1  ="+new Gson().toJson(response.body()));

//                Log.d("StatusLuccky", "id Status =" + response.code());
                if (response.isSuccessful()) {
//                    Log.d("StatusLuccky", "id Status =" + response.code());
                    if (response.body().status.equalsIgnoreCase("0")) {
                        progressBars.dismiss();
                        Toast.makeText(getActivity(), "No Request Found", Toast.LENGTH_LONG).show();
                    } else {
                        if (productListSec.size() > 0) {
                            productListSec.clear();
                        }

                        for (int i = 0; i < response.body().datalist.size(); i++) {
                            if(Integer.valueOf(response.body().datalist.get(i).status) != 2) {

                                if (Integer.valueOf(response.body().datalist.get(i).status) == Num) {
                                    getbcdataSec = new GetTutorStudentRequestList();
                                    getbcdataSec.setName(response.body().datalist.get(i).name);
                                    getbcdataSec.setEmail(response.body().datalist.get(i).email);
                                    getbcdataSec.setMobile_no(response.body().datalist.get(i).mobile_no);
                                    getbcdataSec.setAge(response.body().datalist.get(i).age);

                                    getbcdataSec.setQualification(response.body().datalist.get(i).qualification);
                                    getbcdataSec.setExperience_in_teaching(response.body().datalist.get(i).experience_in_teaching);
                                    getbcdataSec.setSubjects_offered(response.body().datalist.get(i).subjects_offered);
                                    getbcdataSec.setAny_specialization(response.body().datalist.get(i).any_specialization);


                                    getbcdataSec.setCurrent_city(response.body().datalist.get(i).current_city);
                                    getbcdataSec.setUpload_pic(response.body().datalist.get(i).upload_pic);

                                    getbcdataSec.setArea_of_teaching(response.body().datalist.get(i).area_of_teaching);
                                    getbcdataSec.setTime_duration_alloted_to_students(response.body().datalist.get(i).time_duration_alloted_to_students);


                                    getbcdataSec.setTutor_id(response.body().datalist.get(i).tutor_id);
                                    getbcdataSec.setRating(response.body().datalist.get(i).rating);


                                    getbcdataSec.setStatus(response.body().datalist.get(i).status);


                                    getbcdataSec.setReq_timing(response.body().datalist.get(i).req_timing);
                                    getbcdataSec.setReq_subjects(response.body().datalist.get(i).req_subjects);
                                    Log.d("StatusLuccky", "req_subjects  =" + response.body().datalist.get(i).req_subjects);
                                    getbcdataSec.setArea_of_teaching(response.body().datalist.get(i).area_of_teaching);
                                    getbcdataSec.setTime_duration_alloted_to_students(response.body().datalist.get(i).time_duration_alloted_to_students);
                                    getbcdataSec.setTransaction_status(response.body().datalist.get(i).transaction_status);

                                    getbcdataSec.setRequest_id(response.body().datalist.get(i).request_id);


                                    productListSec.add(getbcdataSec);
                                }
                            }

                        }
                        progressBars.dismiss();
                        adapterSec = new RequestTutorStudentLsitAdapter(getActivity(), productListSec);
                        mLayoutManagerSec = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                        mSecRecyclerView.setLayoutManager(mLayoutManagerSec);
                        mSecRecyclerView.setAdapter(adapterSec);


                    }


                } else {
                    switch (response.code()) {
                        case 401:
                            Toast.makeText(getActivity(), "email and pass not check", Toast.LENGTH_SHORT).show();
                            break;
                        case 403:
                            Toast.makeText(getActivity(), "ForbiddenException", Toast.LENGTH_SHORT).show();
                            break;
                        case 404:
                            Toast.makeText(getActivity(), "not found", Toast.LENGTH_SHORT).show();
                            break;
                        case 500:
                            Toast.makeText(getActivity(), "server broken", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(getActivity(), "unknown error", Toast.LENGTH_SHORT).show();
                            break;

                    }
                    Toast.makeText(getActivity(), "Something error please try again", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<TutorStudentResponseListRequestJson> call, Throwable t) {
                t.printStackTrace();


                Log.e("System error:", t.getLocalizedMessage());

            }
        });


    }
    private void getTopTutorList() {
        progressBars.show();
        final TutorStudentRequestListRequestJson request = new TutorStudentRequestListRequestJson();
        request.setStudentid(sharedPreferences.getString("id",""));

        Log.d("StatusLucckyLuckyNew","response 2  ="+new Gson().toJson(request));

        UserService service = ServiceGenerator.createService(UserService.class, null, null);
        service.tutor_student_requset_listsss(request).enqueue(new Callback<TutorStudentResponseListRequestJson>() {
            @Override
            public void onResponse(Call<TutorStudentResponseListRequestJson> call, retrofit2.Response<TutorStudentResponseListRequestJson> response) {

                Log.d("StatusLucckyLuckyNew","response 2  ="+new Gson().toJson(response.body()));
//                Log.d("StatusLuccky", "id Status =" + response.code());
                if (response.isSuccessful()) {
//                    Log.d("StatusLuccky", "id Status =" + response.code());
                    if (response.body().status.equalsIgnoreCase("0")) {
                        progressBars.dismiss();
                        Toast.makeText(getActivity(), "No Request Found", Toast.LENGTH_LONG).show();
                    } else {
                        if (productList.size() > 0) {
                            productList.clear();
                        }

                        for (int i = 0; i < response.body().datalist.size(); i++) {
                            getbcdata = new GetStudentRequestList();
                            if(Integer.valueOf(response.body().datalist.get(i).status) != 2) {

                                if (Integer.valueOf(response.body().datalist.get(i).status) == Num) {
                                    getbcdata.setName(response.body().datalist.get(i).name);
                                    getbcdata.setEmail(response.body().datalist.get(i).email);
                                    getbcdata.setMobile_no(response.body().datalist.get(i).mobile_no);
                                    getbcdata.setAge(response.body().datalist.get(i).age);

                                    getbcdata.setQualification(response.body().datalist.get(i).qualification);
                                    getbcdata.setExperience_in_teaching(response.body().datalist.get(i).experience_in_teaching);
                                    getbcdata.setSubjects_offered(response.body().datalist.get(i).subjects_offered);
                                    getbcdata.setAny_specialization(response.body().datalist.get(i).any_specialization);


                                    getbcdata.setCurrent_city(response.body().datalist.get(i).current_city);
                                    getbcdata.setUpload_pic(response.body().datalist.get(i).upload_pic);

                                    getbcdata.setArea_of_teaching(response.body().datalist.get(i).area_of_teaching);
                                    getbcdata.setTime_duration_alloted_to_students(response.body().datalist.get(i).time_duration_alloted_to_students);


                                    getbcdata.setTutor_id(response.body().datalist.get(i).tutor_id);
                                    getbcdata.setRating(response.body().datalist.get(i).rating);


                                    getbcdata.setStatus(response.body().datalist.get(i).status);


                                    getbcdata.setReq_timing(response.body().datalist.get(i).req_timing);
                                    getbcdata.setReq_subjects(response.body().datalist.get(i).req_subjects);
                                    Log.d("StatusLuccky", "req_subjects  =" + response.body().datalist.get(i).req_subjects);
                                    getbcdata.setArea_of_teaching(response.body().datalist.get(i).area_of_teaching);
                                    getbcdata.setTime_duration_alloted_to_students(response.body().datalist.get(i).time_duration_alloted_to_students);

                                    getbcdata.setTransaction_status(response.body().datalist.get(i).transaction_status);
                                    productList.add(getbcdata);
                                }
                            }

                        }
                        progressBars.dismiss();
                        adapter = new RequestStudentLsitAdapter(getActivity(), productList);
                        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                        mRecyclerView.setLayoutManager(mLayoutManager);
                        mRecyclerView.setAdapter(adapter);


                    }


                } else {
                    switch (response.code()) {
                        case 401:
                            Toast.makeText(getActivity(), "email and pass not check", Toast.LENGTH_SHORT).show();
                            break;
                        case 403:
                            Toast.makeText(getActivity(), "ForbiddenException", Toast.LENGTH_SHORT).show();
                            break;
                        case 404:
                            Toast.makeText(getActivity(), "not found", Toast.LENGTH_SHORT).show();
                            break;
                        case 500:
                            Toast.makeText(getActivity(), "server broken", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(getActivity(), "unknown error", Toast.LENGTH_SHORT).show();
                            break;

                    }
                    Toast.makeText(getActivity(), "Something error please try again", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<TutorStudentResponseListRequestJson> call, Throwable t) {
                t.printStackTrace();


                Log.e("System error:", t.getLocalizedMessage());

            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, isLoggedIn()+"");
//        if (isLoggedIn()){
//            this.loggedInView();
//        }else{
//            this.loggedOutView();
//        }
    }

    private void init(View view) {
        myCoursesGridLayout = view.findViewById(R.id.myCoursesGridLayout);
        myCourseView = view.findViewById(R.id.myCourseView);
        signInPlaceholder = view.findViewById(R.id.signInPlaceHolder);
        signInButton = view.findViewById(R.id.signInButton);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SignInActivity.class);
                startActivity(intent);
            }
        });
    }

    // check if the user is logged in or not
    private boolean isLoggedIn() {
        SharedPreferences preferences = getContext().getSharedPreferences(Helpers.SHARED_PREF, 0);
        int userValidity = preferences.getInt("userValidity", 0);
        if (userValidity == 1) {
            return true;
        }else{
            return false;
        }
    }

    // it will show the looged in view
    private void loggedInView() {
        // fetching all of the my courses
        getMyCourses();
        signInPlaceholder.setVisibility(View.GONE);
        myCourseView.setVisibility(View.VISIBLE);
    }

    // it will show the looged out view
    private void loggedOutView() {
        signInPlaceholder.setVisibility(View.VISIBLE);
        myCourseView.setVisibility(View.GONE);
    }
    // Initialize the progress bar
    private void initProgressBar(View view) {
        progressBar = (ProgressBar)view.findViewById(R.id.progressBar);
        Sprite circularLoading = new Circle();
        progressBar.setIndeterminateDrawable(circularLoading);
    }
    private  void getMyCourses() {
        progressBar.setVisibility(View.VISIBLE);
        // CourseSchema array of objects.
        final ArrayList<MyCourse> mMyCourse = new ArrayList<>();
        // Auth Token
        SharedPreferences preferences = getContext().getSharedPreferences(Helpers.SHARED_PREF, 0);
        String authToken = preferences.getString("userToken", "loggedOut");

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        Api api = retrofit.create(Api.class);
        Call<List<CourseSchema>> call = api.getMyCourses(authToken);
        call.enqueue(new Callback<List<CourseSchema>>() {
            @Override
            public void onResponse(Call<List<CourseSchema>> call, Response<List<CourseSchema>> response) {
                Log.d("Size of response", response.toString());
                List<CourseSchema> myCourseSchema = response.body();
                for (CourseSchema m : myCourseSchema){
                    mMyCourse.add(new MyCourse(m.getId(), m.getTitle(), m.getThumbnail(), m.getPrice(), m.getInstructorName(), m.getRating(), m.getNumberOfRatings(), m.getTotalEnrollment(),m.getCourseCompletion(), m.getTotalNumberOfLessons(), m.getTotalNumberOfCompletedLessons(), m.getShareableLink(), m.getCourseOverviewProvider(), m.getCourseOverviewUrl()));
                }
                initMyCourseGridView(mMyCourse);
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<List<CourseSchema>> call, Throwable t) {
                Log.d(TAG, "Top Course Fetched Failed");
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void initMyCourseGridView(ArrayList<MyCourse> mMyCourse){
        MyCourseAdapter adapter = new MyCourseAdapter(getActivity(), mMyCourse);
        myCoursesGridLayout.setAdapter(adapter);
    }
}
