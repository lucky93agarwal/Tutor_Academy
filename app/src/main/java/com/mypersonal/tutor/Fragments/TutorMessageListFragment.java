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
import com.mypersonal.tutor.Adapters.MessageListAdapter;
import com.mypersonal.tutor.Adapters.YourMessageListAdapter;
import com.mypersonal.tutor.Models.GetMessage;
import com.mypersonal.tutor.Models.TutorRequestListRequestJson;
import com.mypersonal.tutor.Models.TutorResponseListRequestJson;
import com.mypersonal.tutor.Models.TutorYourResponseListRequestJson;
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

public class TutorMessageListFragment   extends Fragment {

    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor edits;

    private RecyclerView recyclerViewForTopCourses = null;


    public List<getMsgTutor> productList = new ArrayList<>();
    public getMsgTutor getbcdata;
    public RecyclerView reyclerViewstudent = null;
    public YourMessageListAdapter adapter;
    public RecyclerView.LayoutManager mLayoutManager;

    public List<GetMessage> productListSec = new ArrayList<>();
    public GetMessage getbcdatasec;
    public MessageListAdapter adaptersec;
    public RecyclerView.LayoutManager mLayoutManagerSec;
    ProgressDialog progressBar;
    public String Tutorid,wallet_amount;
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

        Tutorid = sharedPreferences.getString("id","");
        reyclerViewstudent = view.findViewById(R.id.yourmsgrecycler);


        getWallet();
        getTopMessageList();


        getTopYourMessageList();
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
    private void getTopYourMessageList(){
        progressBar.show();
        final TutorRequestListRequestJson request = new TutorRequestListRequestJson();
        request.setTutorid(Tutorid);
        Log.d("StatusLucckynewmslg","requst 2  ="+new Gson().toJson(request));

        UserService service = ServiceGenerator.createService(UserService.class, null, null);
        service.tutor_msg_listsssxxx(request).enqueue(new Callback<TutorYourResponseListRequestJson>() {
            @Override
            public void onResponse(Call<TutorYourResponseListRequestJson> call, retrofit2.Response<TutorYourResponseListRequestJson> response) {
//                Log.d("StatusLuccky", "id Status =" + response.code());
                Log.d("StatusLucckynewmslg","requst response message  ="+new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
//                    Log.d("StatusLuccky", "id Status =" + response.code());
                    if (response.body().status.equalsIgnoreCase("0")) {
                        progressBar.dismiss();
                        Toast.makeText(getActivity(), "No Request Found", Toast.LENGTH_LONG).show();
                    } else {
                        if (productList.size() > 0) {
                            productList.clear();
                        }

                        for (int i = 0; i < response.body().datalist.size(); i++) {
                            getbcdata = new getMsgTutor();
                            getbcdata.setStudent_name(response.body().datalist.get(i).student_name);
                            getbcdata.setEmail(response.body().datalist.get(i).email);
                            getbcdata.setMobile_no(response.body().datalist.get(i).mobile_no);
                            getbcdata.setClasss(response.body().datalist.get(i).classs);

                            getbcdata.setCity(response.body().datalist.get(i).city);
                            getbcdata.setCurrent_location(response.body().datalist.get(i).current_location);
                            getbcdata.setStudent_id(response.body().datalist.get(i).student_id);
                            getbcdata.setNotes(response.body().datalist.get(i).notes);
                            getbcdata.setMsg(response.body().datalist.get(i).msg);
                            getbcdata.setSchool_name(response.body().datalist.get(i).school_name);

                            getbcdata.setUpload_pic(response.body().datalist.get(i).upload_pic);
                            getbcdata.setSuitable_timing(response.body().datalist.get(i).suitable_timing);

                            getbcdata.setWeak_subjects(response.body().datalist.get(i).weak_subjects);


                            getbcdata.setMsg(response.body().datalist.get(i).msg);





                            getbcdata.setReq_msg(response.body().datalist.get(i).req_msg);


                            getbcdata.setStatus_msg(response.body().datalist.get(i).status_msg);

                            getbcdata.setBoard(response.body().datalist.get(i).board);
                            getbcdata.setWallet(wallet_amount);



                            productList.add(getbcdata);

                        }
                        progressBar.dismiss();
                        adapter = new YourMessageListAdapter(getActivity(), productList);
                        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                        reyclerViewstudent.setLayoutManager(mLayoutManager);
                        reyclerViewstudent.setAdapter(adapter);


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
            public void onFailure(Call<TutorYourResponseListRequestJson> call, Throwable t) {
                t.printStackTrace();


                Log.e("System error:", t.getLocalizedMessage());

            }
        });


    }
    private void getTopMessageList() {
        progressBar.show();
        final TutorRequestListRequestJson request = new TutorRequestListRequestJson();
        request.setTutorid(Tutorid);
//        request.setTutorid("UT25826");

        Log.d("StatusLucckynewmslg","requst 1  ="+new Gson().toJson(request));

        UserService service = ServiceGenerator.createService(UserService.class, null, null);
        service.tutor_msg_listsss(request).enqueue(new Callback<TutorResponseListRequestJson>() {
            @Override
            public void onResponse(Call<TutorResponseListRequestJson> call, retrofit2.Response<TutorResponseListRequestJson> response) {
//                Log.d("StatusLuccky", "id Status =" + response.code());
                Log.d("StatusLucckynewmslg","requst response message 1  ="+new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
//                    Log.d("StatusLuccky", "id Status =" + response.code());
                    if (response.body().status.equalsIgnoreCase("0")) {
                        progressBar.dismiss();
                        Toast.makeText(getActivity(), "No Request Found", Toast.LENGTH_LONG).show();
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
                            getbcdatasec.setBoard(response.body().datalist.get(i).board);
                            getbcdatasec.setWallet(wallet_amount);



                            getbcdatasec.setTransation_status(sharedPreferences.getString("Transaction_status","0"));



                            productListSec.add(getbcdatasec);

                        }
                        progressBar.dismiss();
                        adaptersec = new MessageListAdapter(getActivity(), productListSec);
                        mLayoutManagerSec = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                        recyclerViewForTopCourses.setLayoutManager(mLayoutManagerSec);
                        recyclerViewForTopCourses.setAdapter(adaptersec);


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
