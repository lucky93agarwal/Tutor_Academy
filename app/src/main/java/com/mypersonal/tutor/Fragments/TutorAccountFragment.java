package com.mypersonal.tutor.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;
import com.google.gson.Gson;
import com.mypersonal.tutor.Activities.ChangePasswordActivity;
import com.mypersonal.tutor.Activities.ContactUsActivity;
import com.mypersonal.tutor.Activities.SignInActivity;
import com.mypersonal.tutor.Activities.TutorEditProfileActivity;
import com.mypersonal.tutor.Activities.TutordetailsActivity;
import com.mypersonal.tutor.Activities.UserProfileActivity;
import com.mypersonal.tutor.Adapters.StudentListNewAdapter;
import com.mypersonal.tutor.JSONSchemas.UserSchema;
import com.mypersonal.tutor.Models.GetRequestList;
import com.mypersonal.tutor.Models.TutorRequestListRequestJson;
import com.mypersonal.tutor.Models.TutorResponseListRequestJson;
import com.mypersonal.tutor.Models.WalletResponseList;
import com.mypersonal.tutor.Network.Api;
import com.mypersonal.tutor.R;
import com.mypersonal.tutor.Razorpay.RazorpayActivity;
import com.mypersonal.tutor.Utils.Helpers;
import com.mypersonal.tutor.anurag.register.customadapter.RequestDemoClass;
import com.mypersonal.tutor.retrofit.ServiceGenerator;
import com.mypersonal.tutor.retrofit.UserService;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

public class TutorAccountFragment extends Fragment {
    Button signInButton;
    RelativeLayout signInPlaceholder, accountView;
    CircleImageView displayImageView;
    TextView userName;
    RelativeLayout editProfileRelativeLayout, changePasswordRelativeLayout, logOutRelativeLayout,changePasswordTitlerl;
    private ProgressBar progressBar;
    View view;
    private Context mContext;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor edits;

    ProgressDialog progressBars;
    public TextView tveditprofile, tvcontectTitle;
    public ImageView iveditprofile, ivcontectArrow;
    public CircleImageView civeditprofile,cicontectIcon;
    public RelativeLayout rleditprofile, rlcontectRelativeLayout;







    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tutor_account_fragment, container, false);

        progressBars= new ProgressDialog(getActivity());
        progressBars.setCancelable(true);
        progressBars.setMessage("Please wait ...");

        sharedPreferences = getActivity().getSharedPreferences("data", MODE_PRIVATE);
        edits = sharedPreferences.edit();
        init(view);
        initProgressBar(view);
//        if (isLoggedIn()){
        this.loggedInView();
//        }else{

        rleditprofile = (RelativeLayout) view.findViewById(R.id.editprofileRelativeLayout);
        rlcontectRelativeLayout = (RelativeLayout) view.findViewById(R.id.contectRelativeLayout);

        civeditprofile= (CircleImageView)view.findViewById(R.id.editprofileIcon);
        cicontectIcon= (CircleImageView)view.findViewById(R.id.contectIcon);

        iveditprofile = (ImageView) view.findViewById(R.id.editprofileArrow);
        ivcontectArrow = (ImageView) view.findViewById(R.id.contectArrow);

        tveditprofile = (TextView)view.findViewById(R.id.editproifleTitle);
        tvcontectTitle = (TextView)view.findViewById(R.id.contectTitle);


        rlcontectRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ContactUsActivity.class);
                startActivity(intent);
            }
        });


        cicontectIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ContactUsActivity.class);
                startActivity(intent);
            }
        });

        ivcontectArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ContactUsActivity.class);
                startActivity(intent);
            }
        });

        tvcontectTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ContactUsActivity.class);
                startActivity(intent);
            }
        });


        rleditprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TutorEditProfileActivity.class);
                startActivity(intent);
            }
        });
        civeditprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TutorEditProfileActivity.class);
                startActivity(intent);
            }
        });
        iveditprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TutorEditProfileActivity.class);
                startActivity(intent);
            }
        });
        tveditprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TutorEditProfileActivity.class);
                startActivity(intent);
            }
        });
//            this.loggedOutView();
//        }
        initViewElementsWithUserInfo();

        getTopTutorList();
        return view;
    }

    private boolean isLoggedIn() {
        SharedPreferences preferences = getContext().getSharedPreferences(Helpers.SHARED_PREF, 0);
        int userValidity = preferences.getInt("userValidity", 0);
        if (userValidity == 1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    void init(View view) {
        signInPlaceholder = view.findViewById(R.id.signInPlaceHolder);
        accountView = view.findViewById(R.id.accountView);
        displayImageView = view.findViewById(R.id.displayImageView);
        userName = view.findViewById(R.id.userName);
        userName.setText(sharedPreferences.getString("name", ""));
        editProfileRelativeLayout = view.findViewById(R.id.editProfileRelativeLayout);
        changePasswordRelativeLayout = view.findViewById(R.id.changePasswordRelativeLayout);

        if(sharedPreferences.getString("Transaction_status","0").equalsIgnoreCase("1")){
            view.findViewById(R.id.changePasswordTitle).setVisibility(View.GONE);
            view.findViewById(R.id.changePasswordRelativeLayout).setVisibility(View.GONE);
        }
        logOutRelativeLayout = view.findViewById(R.id.logOutRelativeLayout);
        view.findViewById(R.id.changePasswordTitle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, RazorpayActivity.class);
                intent.putExtra("wallet_amount",wallet_amount);
                startActivity(intent);
            }
        });

        logOutRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edits.clear();
                clearAllTheSharedPreferencesValues();
            }
        });

        editProfileRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, TutordetailsActivity.class);
                startActivity(intent);
            }
        });

        changePasswordRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(mContext, ChangePasswordActivity.class);
//                startActivity(intent);
            }
        });

        // SignIn button action
        signInButton = view.findViewById(R.id.signInButton);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edits.clear();
                edits.commit();
                Log.d("WalletLuckkyYuYu","typesdfs = ");
                Intent intent = new Intent(mContext, SignInActivity.class);
                startActivity(intent);

            }
        });

    }

    // Initialize the progress bar
    private void initProgressBar(View view) {
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        Sprite circularLoading = new Circle();
        progressBar.setIndeterminateDrawable(circularLoading);
    }


    // it will show the looged in view
    private void loggedInView() {
        getUserDetails();
        signInPlaceholder.setVisibility(View.GONE);
        accountView.setVisibility(View.VISIBLE);
    }

    // it will show the looged out view
    private void loggedOutView() {
        signInPlaceholder.setVisibility(View.VISIBLE);
        accountView.setVisibility(View.GONE);
    }

    // get user details
    private void getUserDetails() {
        if (isLoggedIn()) {
            getUserDataFromAPI();
        } else {
            Toast.makeText(getContext(), "Please Login First", Toast.LENGTH_SHORT).show();
        }
    }

    private void getUserDataFromAPI() {

        final SharedPreferences preferences = getContext().getSharedPreferences(Helpers.SHARED_PREF, 0);
        String authToken = preferences.getString("userToken", "loggedOut");
        progressBar.setVisibility(View.VISIBLE);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        Api api = retrofit.create(Api.class);
        Call<UserSchema> call = api.getUserProfileInfo(authToken);
        call.enqueue(new Callback<UserSchema>() {
            @Override
            public void onResponse(Call<UserSchema> call, Response<UserSchema> response) {
                progressBar.setVisibility(View.INVISIBLE);
                UserSchema userSchema = response.body();
                if (userSchema.getStatus().equals("success")) {

                } else {
                    Toast.makeText(getContext(), "An Error Occurred", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserSchema> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(), "An Error Occured", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initViewElementsWithUserInfo() {
        progressBar.setVisibility(View.VISIBLE);


        Glide.with(mContext).load(sharedPreferences.getString("image","")).placeholder(R.drawable.logo_light).into(displayImageView);
//        Glide.with(mContext)
//                .asBitmap()
//                .load(sharedPreferences.getString("image",""))
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .skipMemoryCache(true)
//                .into(displayImageView);
        userName.setText(sharedPreferences.getString("name", ""));
        progressBar.setVisibility(View.GONE);
    }

    private void clearAllTheSharedPreferencesValues() {
        edits.clear();
        edits.commit();
        Log.d("WalletLuckkyYuYu","typesdfs = ");

        Intent intent = new Intent(getContext(), SignInActivity.class);
        startActivity(intent);
    }

    public String wallet_amount;

    private void getTopTutorList() {
        progressBars.show();
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
                        progressBars.dismiss();
                        Toast.makeText(getActivity(), "No Request Found", Toast.LENGTH_LONG).show();
                    } else {

                        wallet_amount = response.body().price;


                        progressBars.dismiss();


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
}
