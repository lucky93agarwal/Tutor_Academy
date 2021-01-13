package com.mypersonal.tutor.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mypersonal.tutor.Activities.MainActivity;
import com.mypersonal.tutor.Activities.PersonalTutorActivity;
import com.mypersonal.tutor.Models.GetTutorStudentRequestList;
import com.mypersonal.tutor.R;
import com.mypersonal.tutor.anurag.register.customadapter.RequestDemoClass;

import java.util.List;

public class RequestTutorStudentLsitAdapter extends RecyclerView.Adapter<RequestTutorStudentLsitAdapter.ViewHolder> {
    public static Context mcontext;

    public boolean run = false;
    public static List<GetTutorStudentRequestList> productList;
    public static boolean runtimer = true;
    public Context context;
    ViewHolder view;

    public GetTutorStudentRequestList getselfdata;
    String Contest = "";


    ViewHolder viewHolder;

    public RequestTutorStudentLsitAdapter(Context context, List<GetTutorStudentRequestList> productList) {
        super();
        this.context = context;
        this.productList = productList;
        mcontext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.top_new_course_new_new_lucky, viewGroup, false);
        viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        getselfdata = productList.get(i);
        view = viewHolder;

//        try{
//            viewHolder.tvproductname.setText(productList.get(i).getProduct_Name());
//            viewHolder.tvproductqty.setText(productList.get(i).getVer_capacity()+productList.get(i).getVer_unit() + " X "+productList.get(i).getQuantity() );
//            int totalprice =0;
//            totalprice = Integer.valueOf(productList.get(i).getQuantity())*(int)Double.parseDouble(productList.get(i).getPrice());
//            viewHolder.tvprice.setText(" ₹ "+String.valueOf(totalprice)+".00");
//
//
//            if (productList.get(i).getCheckImage().equalsIgnoreCase("1")){
//                viewHolder.tvuploaded.setVisibility(View.VISIBLE);
//            }else {
//                viewHolder.tvuploaded.setVisibility(View.GONE);
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }

        if(productList.get(i).getStatus().equalsIgnoreCase("0")){
            viewHolder.btndatabtn.setBackgroundColor(Color.parseColor("#1e91c7"));
            viewHolder.btndatabtn.setEnabled(true);
            viewHolder.btndatabtn.setText("Accepte");

            viewHolder.btndatacancelbtn.setBackgroundColor(Color.parseColor("#1e91c7"));
            viewHolder.btndatacancelbtn.setEnabled(true);
            viewHolder.btndatacancelbtn.setText("Denied");
        }else  if(productList.get(i).getStatus().equalsIgnoreCase("2")){
            viewHolder.btndatabtn.setBackgroundColor(Color.parseColor("#FF0000"));
            viewHolder.btndatabtn.setEnabled(false);
            viewHolder.btndatacancelbtn.setVisibility(View.GONE);
            viewHolder.btndatabtn.setText("Request Denied");
        }else {
            viewHolder.btndatabtn.setBackgroundColor(Color.parseColor("#0ea22a"));
            viewHolder.btndatabtn.setEnabled(false);
            viewHolder.btndatacancelbtn.setVisibility(View.GONE);
            viewHolder.btndatabtn.setText("Request Accepted");
        }
        viewHolder.tvsubject.setText("Subject: "+productList.get(i).getSubjects_offered());

        viewHolder.tvrequest_subject.setText("Req Subject: "+productList.get(i).getReq_subjects());

        viewHolder.tvrequest_time.setText("Req Time: "+productList.get(i).getReq_timing());
        viewHolder.tvname.setText(""+productList.get(i).getName());
        viewHolder.tvquali.setText("Time: "+productList.get(i).getTime_duration_alloted_to_students());
        viewHolder.tvexpirence.setText("City: "+productList.get(i).getCurrent_city());

        Glide.with(context).load(productList.get(i).getUpload_pic()).placeholder(R.drawable.logo_light).into(viewHolder.tvicon);


    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder  {
        public TextView tvexpirence, tvsubject, tvname, tvquali, tvrequest_time,tvrequest_subject;

        ImageView tvicon, tvarrow;
        public CheckBox mcheckbox;
        public SharedPreferences sharedPreferences;
        public LinearLayout lljoinll;
        public CardView mcardview;
        public Button btndatabtn,btndatacancelbtn;

        public ViewHolder(final View itemView) {
            super(itemView);

            mcardview = (CardView)itemView.findViewById(R.id.top_course_card);
            btndatabtn = (Button)itemView.findViewById(R.id.databtn);

            btndatacancelbtn = (Button)itemView.findViewById(R.id.datacancelbtn);
            tvexpirence = (TextView)itemView.findViewById(R.id.subject_of_ex);
            tvquali = (TextView)itemView.findViewById(R.id.subject_of_quali);
            tvname= (TextView) itemView.findViewById(R.id.name);
            tvsubject = (TextView) itemView.findViewById(R.id.subject_of_tutor);
//

            tvrequest_subject = (TextView) itemView.findViewById(R.id.subject_of_tutor_new);

            tvrequest_time = (TextView) itemView.findViewById(R.id.time_of_tutor_new);
            tvicon = (ImageView) itemView.findViewById(R.id.image_view);
//            tvarrow = (ImageView) itemView.findViewById(R.id.uploadi);
//            mcheckbox = (CheckBox) itemView.findViewById(R.id.checkbox);


//            mcheckbox.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                }
//            });



            btndatabtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        if (productList.get(getAdapterPosition()).getStatus().equalsIgnoreCase("0")) {
                            btndatabtn.setBackgroundColor(Color.parseColor("#FF0000"));
                            btndatabtn.setEnabled(false);
                            ((MainActivity) mcontext).doAddition(productList.get(getAdapterPosition()).status, productList.get(getAdapterPosition()).request_id);
                        }

                }
            });


            btndatacancelbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        if (productList.get(getAdapterPosition()).getStatus().equalsIgnoreCase("0")) {
                            btndatacancelbtn.setBackgroundColor(Color.parseColor("#FF0000"));
                            btndatacancelbtn.setEnabled(false);
                            ((MainActivity) mcontext).cancel(productList.get(getAdapterPosition()).status, productList.get(getAdapterPosition()).request_id);
                        }

                }
            });
            mcardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mcontext, PersonalTutorActivity.class);
                    intent.putExtra("image",productList.get(getAdapterPosition()).upload_pic);
                    intent.putExtra("name",productList.get(getAdapterPosition()).name);
                    intent.putExtra("fee","");


                    intent.putExtra("email",productList.get(getAdapterPosition()).email);
                    intent.putExtra("qty",productList.get(getAdapterPosition()).qualification);
                    intent.putExtra("rating",productList.get(getAdapterPosition()).rating);

                    intent.putExtra("id",productList.get(getAdapterPosition()).tutor_id);
                    intent.putExtra("exp",productList.get(getAdapterPosition()).experience_in_teaching);
                    intent.putExtra("city",productList.get(getAdapterPosition()).current_city);
                    intent.putExtra("subject",productList.get(getAdapterPosition()).subjects_offered);

                    intent.putExtra("transaction_status",productList.get(getAdapterPosition()).transaction_status);
                    intent.putExtra("time",productList.get(getAdapterPosition()).time_duration_alloted_to_students);
                    intent.putExtra("mobile",productList.get(getAdapterPosition()).mobile_no);

                    mcontext.startActivity(intent);
                }
            });
            tvicon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mcontext, PersonalTutorActivity.class);
                    intent.putExtra("image",productList.get(getAdapterPosition()).upload_pic);
                    intent.putExtra("name",productList.get(getAdapterPosition()).name);
                    intent.putExtra("fee","");
                    intent.putExtra("rating",productList.get(getAdapterPosition()).rating);

                    intent.putExtra("email",productList.get(getAdapterPosition()).email);
                    intent.putExtra("qty",productList.get(getAdapterPosition()).qualification);

                    intent.putExtra("id",productList.get(getAdapterPosition()).tutor_id);
                    intent.putExtra("exp",productList.get(getAdapterPosition()).experience_in_teaching);
                    intent.putExtra("city",productList.get(getAdapterPosition()).current_city);
                    intent.putExtra("subject",productList.get(getAdapterPosition()).subjects_offered);
                    intent.putExtra("transaction_status",productList.get(getAdapterPosition()).transaction_status);

                    intent.putExtra("time",productList.get(getAdapterPosition()).time_duration_alloted_to_students);
                    intent.putExtra("mobile",productList.get(getAdapterPosition()).mobile_no);

                    mcontext.startActivity(intent);
                }
            });
//
//            lljoinll.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(mcontext, TutorDetailsActivity.class);
//                    intent.putExtra("User", (Serializable) currentTopCourse);
//                    mcontext.startActivity(intent);
//                }
//            });





        }


    }

}



