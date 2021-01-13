package com.mypersonal.tutor.Fragments;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.mypersonal.tutor.Adapters.StudentListNewAdapter;
import com.mypersonal.tutor.Models.GetRequestList;
import com.mypersonal.tutor.Models.TutorRequestListRequestJson;
import com.mypersonal.tutor.Models.TutorResponseListRequestJson;
import com.mypersonal.tutor.R;
import com.mypersonal.tutor.retrofit.ServiceGenerator;
import com.mypersonal.tutor.retrofit.UserService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

import static android.content.Context.MODE_PRIVATE;

public class TutorStudentListFragment  extends Fragment {

    public List<GetRequestList> productList = new ArrayList<>();
    public GetRequestList getbcdata;
    private RecyclerView recyclerViewForTopCourses = null;
    public RecyclerView.LayoutManager mLayoutManager;
    public StudentListNewAdapter adapter;

    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor edits;

    ProgressDialog progressBar;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        //This function is responsible for fetching the images from the web
        View view = inflater.inflate(R.layout.tutorfirstlayout, container, false);
        sharedPreferences = getActivity().getSharedPreferences("data",MODE_PRIVATE);
        edits = sharedPreferences.edit();
        recyclerViewForTopCourses  = view.findViewById(R.id.recyclerViewForTopCourses);

        progressBar = new ProgressDialog(getActivity());
        progressBar.setCancelable(true);
        progressBar.setMessage("Please wait ...");
        getTopTutorList();
        return view;
    }
    private void getTopTutorList() {
        progressBar.show();
        final TutorRequestListRequestJson request = new TutorRequestListRequestJson();
        request.setTutorid(sharedPreferences.getString("id",""));
//        request.setTutorid("UT25826");

        Log.d("StatusLucckynewnew","requst  ="+new Gson().toJson(request));

        UserService service = ServiceGenerator.createService(UserService.class, null, null);
        service.tutor_student_listsss(request).enqueue(new Callback<TutorResponseListRequestJson>() {
            @Override
            public void onResponse(Call<TutorResponseListRequestJson> call, retrofit2.Response<TutorResponseListRequestJson> response) {
                Log.d("StatusLuccky", "id Status =" + response.code());

                Log.d("StatusLucckynewnew","requst response  ="+new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    Log.d("StatusLuccky", "id Status =" + response.code());
                    if (response.body().status.equalsIgnoreCase("0")) {
                        progressBar.dismiss();
                        Toast.makeText(getActivity(), "No Request Found", Toast.LENGTH_LONG).show();
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

                            getbcdata.setBoard(response.body().datalist.get(i).board);

                            productList.add(getbcdata);

                        }
                        progressBar.dismiss();
                        adapter = new StudentListNewAdapter(getActivity(), productList);
                        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
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
            public void onFailure(Call<TutorResponseListRequestJson> call, Throwable t) {
                t.printStackTrace();


                Log.e("System error:", t.getLocalizedMessage());

            }
        });
    }
}
