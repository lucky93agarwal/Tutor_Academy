package com.mypersonal.tutor.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.mypersonal.tutor.Activities.Signup;
import com.mypersonal.tutor.Adapters.SubjectListAdapter;
import com.mypersonal.tutor.Adapters.TopCourseAdapter;
import com.mypersonal.tutor.Adapters.tutorlsitAdapter;
import com.mypersonal.tutor.JSONSchemas.CategorySchema;
import com.mypersonal.tutor.JSONSchemas.SubjectSchema;
import com.mypersonal.tutor.JSONSchemas.UserSchema;
import com.mypersonal.tutor.Models.Category;
import com.mypersonal.tutor.Models.CitySignupRequestJson;
import com.mypersonal.tutor.Models.GetSubjectData;
import com.mypersonal.tutor.Models.GetTutorData;
import com.mypersonal.tutor.Models.SubjectResponseJson;
import com.mypersonal.tutor.Models.Subjects;
import com.mypersonal.tutor.Models.TutorListRequestJson;
import com.mypersonal.tutor.Models.TutorListResponseJson;
import com.mypersonal.tutor.Models.User;
import com.mypersonal.tutor.Network.Api;
import com.mypersonal.tutor.Utils.Helpers;
import com.mypersonal.tutor.R;
import com.mypersonal.tutor.Adapters.CategoryAdapter;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;
import com.mypersonal.tutor.retrofit.ServiceGenerator;
import com.mypersonal.tutor.retrofit.UserService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CourseFragment extends Fragment {

    //vars
    private static final String TAG = "Fragment";
    // CategorySchema array of objects.
    private ArrayList<Category> mCategory = new ArrayList<>();
    // CourseSchema array of objects.
    private ArrayList<User> mtopCourse = new ArrayList<User>();

    private ArrayList<Subjects> mSubjects = new ArrayList<>();

    private RecyclerView recyclerViewForTopCourses = null;
    private RecyclerView recyclerViewForCategories = null;
    private ProgressBar progressBar;


    public List<GetTutorData> productList = new ArrayList<>();
    public GetTutorData getbcdata;

    public RecyclerView.LayoutManager mLayoutManager;
    public tutorlsitAdapter adapter;
    public String LIST_STATE_KEY = "list_state";




    ProgressDialog progressBars;

    public List<GetSubjectData> productList_subject = new ArrayList<>();
    public GetSubjectData getbcdatasubject;
    public RecyclerView mRecyclerView;
    public RecyclerView.LayoutManager mLayoutManagersubject;
    public SubjectListAdapter sub_adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        //This function is responsible for fetching the images from the web
        View view = inflater.inflate(R.layout.course_fragment, container, false);
        recyclerViewForTopCourses  = view.findViewById(R.id.recyclerViewForTopCourses);
        recyclerViewForCategories  = view.findViewById(R.id.recyclerViewForCategories);
        initProgressBar(view);
        Log.d("Done", "Start");

        progressBars = new ProgressDialog(getActivity());
        progressBars.setCancelable(true);
        progressBars.setMessage("Please wait ...");
//        getTopCourses();
//        getCategories();
        retrofit_subject();
        getTopTutorList();
        return view;
    }
    private void retrofit_subject(){
        progressBars.show();
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
                        progressBars.dismiss();
                        Toast.makeText(getActivity(), "Techincal problem. Please contact the admistrator", Toast.LENGTH_SHORT).show();

                    } else if (response.body().status.equalsIgnoreCase("1")) {//// All Okay

                        if (productList_subject.size() >= 0) {
                            productList_subject.clear();

                        }
                        for (int z = 0; z < response.body().datalist.size(); z++) {

                            getbcdatasubject = new GetSubjectData();
                            getbcdatasubject.setCount(response.body().datalist.get(z).count);
                            getbcdatasubject.setId(response.body().datalist.get(z).id);
                            getbcdatasubject.setName(response.body().datalist.get(z).subject);



                            productList_subject.add(getbcdatasubject);

                        }
                        progressBars.dismiss();
                        sub_adapter = new SubjectListAdapter(getActivity(), productList_subject);
                        mLayoutManagersubject = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                        recyclerViewForCategories.setLayoutManager(mLayoutManagersubject);
                        recyclerViewForCategories.setAdapter(sub_adapter);

                    }


                } else {
                    Log.d("walletwallets", "System code:= " + response.code());
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
            public void onFailure(Call<SubjectResponseJson> call, Throwable t) {
                t.printStackTrace();
                Log.d("walletwallets", "System error:= " + t.getLocalizedMessage());


                Log.e("System error:", t.getLocalizedMessage());

            }
        });

    }
    private void getTopTutorList() {
        progressBars.show();
        final TutorListRequestJson request = new TutorListRequestJson();
        request.setUser_id("0");
        Log.d("StatusLuccky","requst  ="+new Gson().toJson(request));

        UserService service = ServiceGenerator.createService(UserService.class, null, null);
        service.tutor_list(request).enqueue(new Callback<TutorListResponseJson>() {
            @Override
            public void onResponse(Call<TutorListResponseJson> call, retrofit2.Response<TutorListResponseJson> response) {
                Log.d("StatusLuccky", "id Status =" + response.code());
                Log.d("StatusLucckyYUYU","requst  ="+new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    Log.d("StatusLuccky", "id Status =" + response.code());
                    if (response.body().status.equalsIgnoreCase("0")) {
                        progressBars.dismiss();
                        Toast.makeText(getActivity(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    } else {
                        if (productList.size() > 0) {
                            productList.clear();
                        }

                        for (int i = 0; i < response.body().datalist.size(); i++) {
                            getbcdata = new GetTutorData();
                            getbcdata.setArea(response.body().datalist.get(i).area_of_teaching);
                            getbcdata.setTutorid(response.body().datalist.get(i).tutor_id);
                            getbcdata.setName(response.body().datalist.get(i).name);
                            getbcdata.setMobile(response.body().datalist.get(i).mobile_no);

                            getbcdata.setImage(response.body().datalist.get(i).upload_pic);
                            getbcdata.setSubject(response.body().datalist.get(i).subjects_offered);
                            getbcdata.setTime(response.body().datalist.get(i).time_duration_alloted_to_students);
                            getbcdata.setFee(response.body().datalist.get(i).fees);


                            getbcdata.setCity(response.body().datalist.get(i).current_city);
                            getbcdata.setExperience(response.body().datalist.get(i).experience_in_teaching);
                            getbcdata.setEmail(response.body().datalist.get(i).email);
                            getbcdata.setQty(response.body().datalist.get(i).qualification);
                            getbcdata.setRating(response.body().datalist.get(i).rating);
                            getbcdata.setDay(response.body().datalist.get(i).day);
                            getbcdata.setTuition_type(response.body().datalist.get(i).tuition_type);
                            getbcdata.setWilling_to_traveling(response.body().datalist.get(i).willing_to_traveling);
                            getbcdata.setTransaction_status(response.body().datalist.get(i).transaction_status);

                            productList.add(getbcdata);

                        }
                        progressBars.dismiss();
                        adapter = new tutorlsitAdapter(getActivity(), productList);
                        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                        recyclerViewForTopCourses.setLayoutManager(mLayoutManager);
                        recyclerViewForTopCourses.setAdapter(adapter);


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
            public void onFailure(Call<TutorListResponseJson> call, Throwable t) {
                t.printStackTrace();


                Log.e("System error:", t.getLocalizedMessage());

            }
        });
    }

    // Initialize the progress bar
    private void initProgressBar(View view) {
        progressBar = (ProgressBar)view.findViewById(R.id.progressBar);
        Sprite circularLoading = new Circle();
        progressBar.setIndeterminateDrawable(circularLoading);
    }

    private void initTopCourseRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewForTopCourses.setLayoutManager(layoutManager);
        TopCourseAdapter adapter = new TopCourseAdapter(getActivity(), mtopCourse);
        recyclerViewForTopCourses.setAdapter(adapter);
    }

    private void initCategoryListRecyclerView() {
        CategoryAdapter adapter = new CategoryAdapter(getActivity(), mSubjects);
        recyclerViewForCategories.setAdapter(adapter);
        recyclerViewForCategories.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void setHeight(int numberOfCategories) {

        int pixels = Helpers.convertDpToPixel((numberOfCategories * 90) + 10); // 9 is the number of categories and the 90 is each items height with the margin of top and bottom. Extra 10 dp for readability
        Log.d(TAG, "Lets change the height of recycler view");
        ViewGroup.LayoutParams params1 = recyclerViewForCategories.getLayoutParams();
        recyclerViewForCategories.setMinimumHeight(pixels);
        recyclerViewForCategories.requestLayout();
    }

    private  void getTopCourses() {




        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        Api api = retrofit.create(Api.class);
        Call<List<UserSchema>> call = api.getTutorsList();
        call.enqueue(new Callback<List<UserSchema>>() {
            @Override
            public void onResponse(Call<List<UserSchema>> call, Response<List<UserSchema>> response) {
                Log.d("StatusLuccky","requst  ="+new Gson().toJson(response.body()));
                Log.d(TAG, "CategorySchema Fetched Successfully");
                List<UserSchema> categorySchema = response.body();
                for (UserSchema m : categorySchema) {
                    mtopCourse.add(new User( m.getTutorId(), m.getName() , m.getSubject(),m.getPicture()));
                }

            //    initCategoryListRecyclerView();
                initTopCourseRecyclerView();
                setHeight(mtopCourse.size());
                Log.d("Done", " category done"+response.code());
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<List<UserSchema>> call, Throwable t) {
                Log.d(TAG, "CategorySchema Fetching Failed");
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

    }

    private void initViewElementsWithUserInfo(UserSchema userSchema) {
        progressBar.setVisibility(View.VISIBLE);



    }

    private void getCategories() {
      /*  progressBar.setVisibility(View.VISIBLE);
        // Making empty array of category
        mCategory = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        Api api = retrofit.create(Api.class);
        Call<List<CategorySchema>> call = api.getCategories();
        call.enqueue(new Callback<List<CategorySchema>>() {
            @Override
            public void onResponse(Call<List<CategorySchema>> call, Response<List<CategorySchema>> response) {
                Log.d(TAG, "CategorySchema Fetched Successfully");
                List<CategorySchema> categorySchema = response.body();
                for (CategorySchema m : categorySchema) {
                    mCategory.add(new Category(m.getId(), m.getName(), m.getThumbnail(), m.getNumberOfCourses()));
                }
                initCategoryListRecyclerView();
                setHeight(mCategory.size());
                Log.d("Done", " category done");
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<List<CategorySchema>> call, Throwable t) {
                Log.d(TAG, "CategorySchema Fetching Failed");
                progressBar.setVisibility(View.INVISIBLE);
            }
        });*/


        mSubjects = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        Api api = retrofit.create(Api.class);
        Call<List<SubjectSchema>> call = api.getSubjectList();
        call.enqueue(new Callback<List<SubjectSchema>>() {
            @Override
            public void onResponse(Call<List<SubjectSchema>> call, Response<List<SubjectSchema>> response) {
                Log.d(TAG, "CategorySchema Fetched Successfully");
                List<SubjectSchema> categorySchema = response.body();
                for (SubjectSchema m : categorySchema) {
                    mSubjects.add(new Subjects(m.getSubjectid(), m.getsubjectName()));
                }
                initCategoryListRecyclerView();
                setHeight(mCategory.size());
                Log.d("Done", " category done");
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<List<SubjectSchema>> call, Throwable t) {
                Log.d(TAG, "CategorySchema Fetching Failed");
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

    }
}