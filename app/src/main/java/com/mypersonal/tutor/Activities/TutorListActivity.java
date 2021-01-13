package com.mypersonal.tutor.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mypersonal.tutor.Adapters.tutorlsitAdapter;
import com.mypersonal.tutor.Models.GetTutorData;
import com.mypersonal.tutor.Models.TutorListRequestJson;
import com.mypersonal.tutor.Models.TutorListResponseJson;
import com.mypersonal.tutor.R;
import com.mypersonal.tutor.retrofit.ServiceGenerator;
import com.mypersonal.tutor.retrofit.UserService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class TutorListActivity extends AppCompatActivity {

    public List<GetTutorData> productList = new ArrayList<>();
    public GetTutorData getbcdata;
    public RecyclerView mRecyclerView;
    public RecyclerView.LayoutManager mLayoutManager;
    public tutorlsitAdapter adapter;
    public String LIST_STATE_KEY = "list_state";

    ProgressDialog progressBars;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_list);
        progressBars = new ProgressDialog(this);
        progressBars.setCancelable(true);
        progressBars.setMessage("Please wait ...");
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerview);

        getTopTutorList();
    }

    private void getTopTutorList() {
        progressBars.show();
        final TutorListRequestJson request = new TutorListRequestJson();
        request.setUser_id(getIntent().getStringExtra("id"));
        Log.d("StatusLuccky","requst  ="+new Gson().toJson(request));

        UserService service = ServiceGenerator.createService(UserService.class, null, null);
        service.tutor_listsss(request).enqueue(new Callback<TutorListResponseJson>() {
            @Override
            public void onResponse(Call<TutorListResponseJson> call, retrofit2.Response<TutorListResponseJson> response) {
                Log.d("StatusLuccky", "id Status =" + response.code());
                Log.d("StatusLucckyYUYU","requst  ="+new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    progressBars.dismiss();
                    Log.d("StatusLuccky", "id Status =" + response.code());
                    if (response.body().status.equalsIgnoreCase("0")) {
                        progressBars.dismiss();
                        Toast.makeText(TutorListActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
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
                        adapter = new tutorlsitAdapter(TutorListActivity.this, productList);
                        mLayoutManager = new LinearLayoutManager(TutorListActivity.this, LinearLayoutManager.VERTICAL, false);
                        mRecyclerView.setLayoutManager(mLayoutManager);
                        mRecyclerView.setAdapter(adapter);


                    }


                } else {
                    switch (response.code()) {
                        case 401:
                            Toast.makeText(TutorListActivity.this, "email and pass not check", Toast.LENGTH_SHORT).show();
                            break;
                        case 403:
                            Toast.makeText(TutorListActivity.this, "ForbiddenException", Toast.LENGTH_SHORT).show();
                            break;
                        case 404:
                            Toast.makeText(TutorListActivity.this, "not found", Toast.LENGTH_SHORT).show();
                            break;
                        case 500:
                            Toast.makeText(TutorListActivity.this, "server broken", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(TutorListActivity.this, "unknown error", Toast.LENGTH_SHORT).show();
                            break;

                    }
                    Toast.makeText(TutorListActivity.this, "Something error please try again", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<TutorListResponseJson> call, Throwable t) {
                t.printStackTrace();


                Log.e("System error:", t.getLocalizedMessage());

            }
        });
    }
}