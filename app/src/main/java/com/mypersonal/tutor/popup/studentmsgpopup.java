package com.mypersonal.tutor.popup;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mypersonal.tutor.Models.RequestResponseJson;
import com.mypersonal.tutor.Models.messageRequestJson;
import com.mypersonal.tutor.R;
import com.mypersonal.tutor.retrofit.ServiceGenerator;
import com.mypersonal.tutor.retrofit.UserService;

import retrofit2.Call;
import retrofit2.Callback;

import static android.content.Context.MODE_PRIVATE;

public class studentmsgpopup {
    public Dialog epicDialog;
    private final Context _context;
    public EditText etmsg;
    public Button btnsubmit;
    public String Studentid;
    public String Mssage,Tutorid;

    public studentmsgpopup(Context context) {
        this._context = context;

        epicDialog = new Dialog(context); // for popup Dialog

    }

    public void addpopup(String tutor_id) {
        Studentid = tutor_id;
        epicDialog.setContentView(R.layout.messagelayout);
//        epicDialog.setCancelable(false);
        SharedPreferences prefs = _context.getSharedPreferences("data",MODE_PRIVATE);
        Tutorid = prefs.getString("id", null);

        etmsg = epicDialog.findViewById(R.id.messagete);
        btnsubmit =(Button) epicDialog.findViewById(R.id.sendbtn);


        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etmsg.getText().length()==0){
                    Toast.makeText(_context, "Enter your Message", Toast.LENGTH_SHORT).show();
                }else {
                    Mssage = etmsg.getText().toString();
                    sendrating();
                    epicDialog.dismiss();
                }

            }
        });
        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // popup background transparent
        epicDialog.show();

    }

    private void sendrating() {
        final messageRequestJson request = new messageRequestJson();
        request.setMsg(Mssage);
        request.setStudentid(Studentid);
        request.setTutorid(Tutorid);






        UserService service = ServiceGenerator.createService(UserService.class, null, null);
        service.get_student_message(request).enqueue(new Callback<RequestResponseJson>() {
            @Override
            public void onResponse(Call<RequestResponseJson> call, retrofit2.Response<RequestResponseJson> response) {
                Log.d("StatusLuccky", "id Status =" + response.code());
                if (response.isSuccessful()) {
                    Log.d("StatusLuccky", "id Status =" + response.code());
                    if (response.body().status.equalsIgnoreCase("0")) {
                        Toast.makeText(_context, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    } else {

                        Toast.makeText(_context, "your message has been submitted successfully.", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    switch (response.code()) {
                        case 401:
                            Toast.makeText(_context, "email and pass not check", Toast.LENGTH_SHORT).show();
                            break;
                        case 403:
                            Toast.makeText(_context, "ForbiddenException", Toast.LENGTH_SHORT).show();
                            break;
                        case 404:
                            Toast.makeText(_context, "not found", Toast.LENGTH_SHORT).show();
                            break;
                        case 500:
                            Toast.makeText(_context, "server broken", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(_context, "unknown error", Toast.LENGTH_SHORT).show();
                            break;

                    }
                    Toast.makeText(_context, "Something error please try again", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<RequestResponseJson> call, Throwable t) {
                t.printStackTrace();


                Log.e("System error:", t.getLocalizedMessage());

            }
        });
    }
}
