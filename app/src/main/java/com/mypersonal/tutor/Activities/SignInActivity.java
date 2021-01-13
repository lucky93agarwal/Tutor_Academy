package com.mypersonal.tutor.Activities;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.mypersonal.tutor.Models.LoginRequestJson;
import com.mypersonal.tutor.Models.LoginResponseJson;
import com.mypersonal.tutor.Models.Model_User;
import com.mypersonal.tutor.Models.StudentSignupRequestJson;
import com.mypersonal.tutor.Models.StudentSignupResponseJson;
import com.mypersonal.tutor.R;
import com.mypersonal.tutor.anurag.register.customadapter.RequestDemoClass;
import com.mypersonal.tutor.retrofit.ServiceGenerator;
import com.mypersonal.tutor.retrofit.UserService;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.Callable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignInActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    public TextView tvlogin;
    SignInButton signInButton;
    EditText editText;
    final int RC_SIGN_IN = 123;
    GoogleSignInClient googleSignInClient;
    FirebaseAuth firebaseAuth;
    DatabaseReference mUserDatabase;

    private static final String EMAIL = "email";

    private LoginButton mFacebookSignInButton;
    private CallbackManager mFacebookCallbackManager;
    private TextView txtemail;
    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor edits;
    ProgressDialog progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.signin);
        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        edits = sharedPreferences.edit();

        progressBar = new ProgressDialog(this);
        progressBar.setCancelable(true);
        progressBar.setMessage("Please wait ...");

//        RetrofitAPI("agarwal.lucky93@gmail.com");

//        RetrofitAPI("ashraf.abul@gmail.com");
        tvlogin = (TextView) findViewById(R.id.tvlogintv);
        tvlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        txtemail = (TextView) findViewById(R.id.txtemail);

        // Google signin Action


        signInButton = findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        configureGoogleClient();
        // Facebook Signn Action

        mFacebookCallbackManager = CallbackManager.Factory.create();
        mFacebookSignInButton = (LoginButton) findViewById(R.id.login_button);

        Log.d("Walletnewvjvjvjdkdd", "1");

        mFacebookSignInButton.setReadPermissions("email", "public_profile", "user_friends");
        mFacebookSignInButton.registerCallback(mFacebookCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(final LoginResult loginResult) {
                        Log.d("Walletnewvjvjvjdkdd", "1 = " + loginResult.toString());
                        String accessToken = loginResult.getAccessToken().getToken();
                        Log.d("Walletnewvjvjvjdkdd", "accessToken = " + accessToken.toString());
                        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.d("Walletnewvjvjvjdkdd", "response = = = "+response.toString());
                                // Get facebook data from login
                                Bundle bFacebookData = getFacebookData(object);
                            }
                        });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location"); // Parámetros que pedimos a facebook
                        request.setParameters(parameters);
                        request.executeAsync();
                        handleSignInResult(new Callable<Void>() {
                            @Override
                            public Void call() throws Exception {

                                Log.d("Walletnewvjvjvjdkdd", "1");
                                return null;
                            }
                        });
                    }

                    @Override
                    public void onCancel() {
                        Log.d("Walletnewvjvjvjdkdd", "2");
                        handleSignInResult(null);

                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.d("Walletnewvjvjvjdkdd", "3 error = " + error.getMessage());
                        Log.d(MainActivity.class.getCanonicalName(), error.getMessage());
//                        handleSignInResult(null);
                    }
                }

        );
    }

    private Bundle getFacebookData(JSONObject object) {

        try {
            Bundle bundle = new Bundle();
            String id = object.getString("id");

            try {
                URL profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?width=200&height=150");
                Log.i("profile_pic", profile_pic + "");
                bundle.putString("profile_pic", profile_pic.toString());

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }

            bundle.putString("idFacebook", id);
            if (object.has("first_name"))
                bundle.putString("first_name", object.getString("first_name"));
            if (object.has("last_name"))
                bundle.putString("last_name", object.getString("last_name"));
            if (object.has("email")) {
                Log.d("Walletnewvjvjvjdkdd", "email 1 = = = " + object.getString("email").toString());
                bundle.putString("email", object.getString("email"));
                RetrofitAPI(object.getString("email"));
            }
            if (object.has("gender"))
                bundle.putString("gender", object.getString("gender"));
            if (object.has("birthday"))
                bundle.putString("birthday", object.getString("birthday"));
            if (object.has("location"))
                bundle.putString("location", object.getJSONObject("location").getString("name"));

            return bundle;
        } catch (JSONException e) {

        }
        return null;
    }

    private void configureGoogleClient() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        firebaseAuth = FirebaseAuth.getInstance();


    }


    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    private void handleSignInResult(Object o) {
        Log.d("Walletnewvjvjvjdkdd", "Object ==  " + o.toString());

    }



    /*private void signInWithGoogle() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        final Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signOut() {

        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {

                    }
                });
    }*/


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mFacebookCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("Walletnewvjvjvjdkdd", "2 data = " + data.toString());
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                showToastMessage("Google Sign in Succeeded");
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("TAG", "Google sign in failed", e);
                showToastMessage("Google Sign in Failed " + e);
            }
        }

    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        Log.d("TAG", "firebaseAuthWithGoogle:" + account.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = firebaseAuth.getCurrentUser();

                            Log.d("TAG", "signInWithCredential:success: currentUser: " + user.getEmail());

                            showToastMessage("Firebase Authentication Succeeded ");
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.

                            Log.w("TAG", "signInWithCredential:failure", task.getException());

                            showToastMessage("Firebase Authentication failed:" + task.getException());
                        }
                    }
                });
    }

    private void showToastMessage(String message) {
        Toast.makeText(SignInActivity.this, message, Toast.LENGTH_LONG).show();
    }

    void updateUI(FirebaseUser user) {
        if (user != null) {
            transferdata(user);
            /**/
        }
    }

    public String cemail;

    private void transferdata(FirebaseUser user) {
        String email = user.getEmail();
        txtemail.setText(email);
        cemail = email;

        RetrofitAPI(email);

//        finish();
    }

    private void RetrofitAPI(final String email) {
        final LoginRequestJson request = new LoginRequestJson();
        request.setEmail(email);
        progressBar.show();

        Log.d("StatusLucckydfjnew", "requst  =" + new Gson().toJson(request));
        UserService service = ServiceGenerator.createService(UserService.class, null, null);
        service.login_data(request).enqueue(new Callback<LoginResponseJson>() {
            @Override
            public void onResponse(Call<LoginResponseJson> call, Response<LoginResponseJson> response) {
                if (response.isSuccessful()) {
                    Log.d("StatusLucckydfjnew", "response  =" + new Gson().toJson(response.body()));
                    if (response.body().status.equalsIgnoreCase("0")) { ////New App
                        progressBar.dismiss();
                        Intent intent = new Intent(SignInActivity.this, Signup.class);
                        intent.putExtra("email", email);
                        startActivity(intent);


                    } else if (response.body().status.equalsIgnoreCase("1")) {//// All Okay

                        if (response.body().type.equalsIgnoreCase("Tutor")) {

                            for (int i = 0; i < response.body().datalist.size(); i++) {

                                edits.putString("email", cemail);
                                edits.putString("id", response.body().datalist.get(i).id);
                                Log.d("StatusLuccky", "id  =" + response.body().datalist.get(i).id);
                                edits.putString("name", response.body().datalist.get(i).name);
                                edits.putString("mobile", response.body().datalist.get(i).mobile_no);
                                edits.putString("age", response.body().datalist.get(i).age);
                                edits.putString("qualification", response.body().datalist.get(i).qualification);
                                edits.putString("tutor_exp", response.body().datalist.get(i).experience);
                                edits.putString("Subjectid", response.body().datalist.get(i).id);
                                edits.putString("email", response.body().datalist.get(i).email);
                                edits.putString("fee", response.body().datalist.get(i).fees);
                                edits.putString("type", "tutor");

                                edits.putString("specialization", response.body().datalist.get(i).any_specialization);

                                edits.putString("Tutercurrent_city", response.body().datalist.get(i).current_city);
                                edits.putString("subject_offerd", response.body().datalist.get(i).name);

                                edits.putString("tutor_area", response.body().datalist.get(i).area_of_teaching);
                                edits.putString("timeDuration", response.body().datalist.get(i).time_duration_alloted_to_students);
                                edits.putString("image", response.body().datalist.get(i).upload_pic);
                                edits.putString("transaction_status", response.body().datalist.get(i).transaction_status);
                                Log.d("StatusLucckydfjnew", "transaction_status  =" + response.body().datalist.get(i).transaction_status);
                                edits.putString("TutorTyple", response.body().datalist.get(i).tuition_type);

                                edits.putString("board",response.body().datalist.get(i).board);
                                edits.putString("board_date",response.body().datalist.get(i).board_date);

                                edits.putString("Willing_to_travel", response.body().datalist.get(i).willing_to_traveling);
                                edits.putString("day", response.body().datalist.get(i).day);





                                edits.putString("schoolname",response.body().datalist.get(i).school_name);
                                edits.putString("school_date",response.body().datalist.get(i).school_date);

                                edits.putString("collage", response.body().datalist.get(i).clg_name);
                                edits.putString("collage_date", response.body().datalist.get(i).clg_date);
                                edits.putString("approved_doc",response.body().datalist.get(i).approved_doc);
                                edits.apply();


                            }
                            progressBar.dismiss();
                            Intent intent = new Intent(SignInActivity.this, TutorActivity.class);
                            startActivity(intent);
                            finish();

                        } else if (response.body().type.equalsIgnoreCase("Student")) {
                            for (int i = 0; i < response.body().datalist.size(); i++) {
                                edits.putString("id", response.body().datalist.get(i).id);
                                edits.putString("type", "student");
                                edits.putString("image", response.body().datalist.get(i).upload_pic);
                                edits.putString("email", response.body().datalist.get(i).email);
                                edits.putString("name", response.body().datalist.get(i).name);
                                edits.putString("mobile", response.body().datalist.get(i).mobile_no);
                                edits.putString("student_class", response.body().datalist.get(i).classs);
                                edits.putString("student_weak_subject", response.body().datalist.get(i).weak_subjects);
                                edits.putString("student_timing", response.body().datalist.get(i).suitable_timing);
                                edits.putString("subject", response.body().datalist.get(i).subjects);
                                edits.putString("student_sortDesc", response.body().datalist.get(i).notes);
                                edits.putString("student_school", response.body().datalist.get(i).school_name);
                                edits.putString("student_City", response.body().datalist.get(i).city);
                                edits.putString("student_Location", response.body().datalist.get(i).current_location);
                                edits.putString("student_board", response.body().datalist.get(i).board);

                                edits.apply();
                            }
                            progressBar.dismiss();
                            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }


                    }


                } else {
                    Log.d("walletwallets", "System code:= " + response.code());
                    switch (response.code()) {
                        case 401:
                            Toast.makeText(SignInActivity.this, "email and pass not check", Toast.LENGTH_SHORT).show();
                            break;
                        case 403:
                            Toast.makeText(SignInActivity.this, "ForbiddenException", Toast.LENGTH_SHORT).show();
                            break;
                        case 404:
                            Toast.makeText(SignInActivity.this, "not found", Toast.LENGTH_SHORT).show();
                            break;
                        case 500:
                            Toast.makeText(SignInActivity.this, "server broken", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(SignInActivity.this, "unknown error", Toast.LENGTH_SHORT).show();
                            break;
                    }
                    Toast.makeText(SignInActivity.this, "Something error please try again", Toast.LENGTH_SHORT).show();


                }
            }

            @Override
            public void onFailure(Call<LoginResponseJson> call, Throwable t) {
                t.printStackTrace();
                Log.d("walletwallets", "System error:= " + t.getLocalizedMessage());


                Log.e("System error:", t.getLocalizedMessage());

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth = FirebaseAuth.getInstance();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
//        if(currentUser != null){
//            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
//            startActivity(intent);
//        }else{
//            updateUI(currentUser);
//        }

    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}


