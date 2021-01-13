package com.mypersonal.tutor.Fragments;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.mypersonal.tutor.Adapters.RequestLsitAdapter;
import com.mypersonal.tutor.Adapters.TutorRequestLsitAdapter;
import com.mypersonal.tutor.Adapters.YourMessageListAdapter;
import com.mypersonal.tutor.Models.GetRequestList;
import com.mypersonal.tutor.Models.GetTutorRequestList;
import com.mypersonal.tutor.Models.TutorRequestListRequestJson;
import com.mypersonal.tutor.Models.TutorResponseListRequestJson;
import com.mypersonal.tutor.Models.WalletResponseList;
import com.mypersonal.tutor.Models.getMsgTutor;
import com.mypersonal.tutor.R;
import com.mypersonal.tutor.anurag.register.customadapter.RequestDemoClass;
import com.mypersonal.tutor.retrofit.ServiceGenerator;
import com.mypersonal.tutor.retrofit.UserService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

import static android.content.Context.MODE_PRIVATE;

public class TutorRequestFragment extends Fragment {

    public List<GetRequestList> productList = new ArrayList<>();
    public GetRequestList getbcdata;
    private RecyclerView recyclerViewForTopCourses = null;
    public RecyclerView.LayoutManager mLayoutManager;
    public RequestLsitAdapter adapter;

    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor edits;

    ProgressDialog progressBar;


    public String wallet_amount;


    public List<GetTutorRequestList> productListSec = new ArrayList<>();
    public GetTutorRequestList getbcdataSec;
    public RecyclerView reyclerViewstudent = null;
    public TutorRequestLsitAdapter adapterSec;
    public RecyclerView.LayoutManager mLayoutManagerSec;


    public String Tutorid;


    public Button btnall, btnrequestbtn;

    public int Num = 0;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        //This function is responsible for fetching the images from the web
        View view = inflater.inflate(R.layout.tutorrequestlayut, container, false);
        sharedPreferences = getActivity().getSharedPreferences("data",MODE_PRIVATE);
        edits = sharedPreferences.edit();


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


                getTopYourRequestList();
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


                getTopYourRequestList();
            }
        });
        recyclerViewForTopCourses  = view.findViewById(R.id.recyclerViewForTopCourses);
        progressBar = new ProgressDialog(getActivity());
        progressBar.setCancelable(true);
        progressBar.setMessage("Please wait ...");
        Tutorid = sharedPreferences.getString("id","");
        reyclerViewstudent = view.findViewById(R.id.yourmsgrecycler);

        getWallet();
        getTopTutorList();


        getTopYourRequestList();


        return view;
    }
    private void getWallet() {
        progressBar.show();
        final TutorRequestListRequestJson request = new TutorRequestListRequestJson();
        request.setTutorid(sharedPreferences.getString("id",""));
//        request.setTutorid("UT25826");

        Log.d("requstresponserequstresponse=","requst  ="+new Gson().toJson(request));

        UserService service = ServiceGenerator.createService(UserService.class, null, null);
        service.wallet_amount(request).enqueue(new Callback<WalletResponseList>() {
            @Override
            public void onResponse(Call<WalletResponseList> call, retrofit2.Response<WalletResponseList> response) {
                Log.d("StatusLuccky", "id Status =" + response.code());

                Log.d("requstresponserequstresponse=","requst response  ="+new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    Log.d("StatusLuccky", "id Status =" + response.code());
                    if (response.body().status.equalsIgnoreCase("0")) {
                        progressBar.dismiss();
                        Toast.makeText(getActivity(), "No Request Found", Toast.LENGTH_LONG).show();
                    } else {

                        wallet_amount = response.body().price;


                        progressBar.dismiss();


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
            public void onFailure(Call<WalletResponseList> call, Throwable t) {
                t.printStackTrace();


                Log.e("System error:", t.getLocalizedMessage());

            }
        });
    }
    private void getTopYourRequestList(){
        progressBar.show();
        final TutorRequestListRequestJson request = new TutorRequestListRequestJson();
        request.setTutorid(Tutorid);
        Log.d("StatusLucckynewnewsdf","requst 2  ="+new Gson().toJson(request));
        UserService service = ServiceGenerator.createService(UserService.class, null, null);
        service.tutor_requset_listsssddd(request).enqueue(new Callback<TutorResponseListRequestJson>() {
            @Override
            public void onResponse(Call<TutorResponseListRequestJson> call, retrofit2.Response<TutorResponseListRequestJson> response) {


                Log.d("StatusLucckynewnewsdf","requst response 2  ="+new Gson().toJson(response.body()));
                if (response.isSuccessful()) {

                    if (response.body().status.equalsIgnoreCase("0")) {
                        progressBar.dismiss();
//                        Toast.makeText(getActivity(), "No Request Found", Toast.LENGTH_LONG).show();
                    } else {
                        if (productListSec.size() > 0) {
                            productListSec.clear();
                        }

                        for (int i = 0; i < response.body().datalist.size(); i++) {
                            if(Integer.valueOf(response.body().datalist.get(i).status) != 2) {

                                if(Integer.valueOf(response.body().datalist.get(i).status) == Num) {
                                    getbcdataSec = new GetTutorRequestList();
                                    getbcdataSec.setName(response.body().datalist.get(i).name);
                                    getbcdataSec.setEmail(response.body().datalist.get(i).email);
                                    getbcdataSec.setMobile_no(response.body().datalist.get(i).mobile_no);
                                    getbcdataSec.setClasss(response.body().datalist.get(i).classs);


                                    getbcdataSec.setMsg(response.body().datalist.get(i).msg);

                                    getbcdataSec.setCity(response.body().datalist.get(i).city);
                                    getbcdataSec.setCurrent_location(response.body().datalist.get(i).current_location);
                                    getbcdataSec.setStudent_id(response.body().datalist.get(i).student_id);
                                    getbcdataSec.setNotes(response.body().datalist.get(i).notes);
                                    getbcdataSec.setSchool_name(response.body().datalist.get(i).school_name);


                                    getbcdataSec.setUpload_pic(response.body().datalist.get(i).upload_pic);
                                    getbcdataSec.setSuitable_timing(response.body().datalist.get(i).suitable_timing);

                                    getbcdataSec.setWeak_subjects(response.body().datalist.get(i).weak_subjects);
                                    getbcdataSec.setSubjects(response.body().datalist.get(i).subjects);
                                    getbcdataSec.setReq_subjects(response.body().datalist.get(i).req_subjects);

                                    getbcdataSec.setReq_timing(response.body().datalist.get(i).req_timing);
                                    getbcdataSec.setStatus(response.body().datalist.get(i).status);

                                    getbcdataSec.setRequest_id(response.body().datalist.get(i).request_id);
                                    getbcdataSec.setBoard(response.body().datalist.get(i).board);


                                    getbcdataSec.setTransation_status(sharedPreferences.getString("Transaction_status", "0"));
                                    getbcdataSec.setWallet(wallet_amount);
                                    Log.d("StatusLucckynewnewsdf", "requst response 2 Transaction_status =" + sharedPreferences.getString("Transaction_status", "0"));
                                    Log.d("StatusLucckynewnewsdf", "requst response 2 wallet_amount =" + wallet_amount);
                                    productListSec.add(getbcdataSec);
                                }
                            }

                        }
                        progressBar.dismiss();
                        adapterSec = new TutorRequestLsitAdapter(getActivity(), productListSec);
                        mLayoutManagerSec = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                        reyclerViewstudent.setLayoutManager(mLayoutManagerSec);
                        reyclerViewstudent.setAdapter(adapterSec);


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

    private void getTopTutorList() {
        progressBar.show();
        final TutorRequestListRequestJson request = new TutorRequestListRequestJson();
        request.setTutorid(Tutorid);
//        request.setTutorid("UT25826");

        Log.d("StatusLucckynewnewsdf","requst 1  ="+new Gson().toJson(request));

        UserService service = ServiceGenerator.createService(UserService.class, null, null);
        service.tutor_requset_listsss(request).enqueue(new Callback<TutorResponseListRequestJson>() {
            @Override
            public void onResponse(Call<TutorResponseListRequestJson> call, retrofit2.Response<TutorResponseListRequestJson> response) {


                Log.d("StatusLucckynewnewsdf","requst response 1  ="+new Gson().toJson(response.body()));
                if (response.isSuccessful()) {

                    if (response.body().status.equalsIgnoreCase("0")) {
                        progressBar.dismiss();
//                        Toast.makeText(getActivity(), "No Request Found", Toast.LENGTH_LONG).show();
                    } else {
                        if (productList.size() > 0) {
                            productList.clear();
                        }

                        for (int i = 0; i < response.body().datalist.size(); i++) {
                            if(Integer.valueOf(response.body().datalist.get(i).status) != 2){
                                if(Integer.valueOf(response.body().datalist.get(i).status) == Num) {

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
                                    getbcdata.setBoard(response.body().datalist.get(i).board);


                                    getbcdata.setTransation_status(sharedPreferences.getString("Transaction_status", "0"));
                                    getbcdata.setWallet(wallet_amount);
                                    Log.d("StatusLucckynewnewsdf", "requst response 1 Transaction_status =" + sharedPreferences.getString("Transaction_status", "0"));
                                    Log.d("StatusLucckynewnewsdf", "requst response 1 wallet_amount =" + wallet_amount);
                                    productList.add(getbcdata);
                                }
                            }

                        }
                        progressBar.dismiss();
                        adapter = new RequestLsitAdapter(getActivity(), productList);
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

