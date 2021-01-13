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
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mypersonal.tutor.Activities.StudentDetailsActivity;
import com.mypersonal.tutor.Models.GetTutorRequestList;
import com.mypersonal.tutor.R;
import com.mypersonal.tutor.Razorpay.RazorpayActivity;
import com.mypersonal.tutor.anurag.register.customadapter.RequestDemoClass;
import com.mypersonal.tutor.retrofit.Log;

import java.util.List;

public class TutorRequestLsitAdapter extends RecyclerView.Adapter<TutorRequestLsitAdapter.ViewHolder> {
    public static Context mcontext;

    public boolean run = false;
    public static List<GetTutorRequestList> productList;
    public static boolean runtimer = true;
    public Context context;
    ViewHolder view;

    public GetTutorRequestList getselfdata;
    String Contest = "";


    ViewHolder viewHolder;

    public TutorRequestLsitAdapter(Context context, List<GetTutorRequestList> productList) {
        super();
        this.context = context;
        this.productList = productList;
        mcontext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.top_new_course_cell_new_lucky, viewGroup, false);
        viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        getselfdata = productList.get(i);
        view = viewHolder;


        Log.d("WalletLuckyYUYU",productList.get(i).getStatus().toString());
        if(productList.get(i).getStatus().equalsIgnoreCase("0")){
            viewHolder.btndatabtn.setBackgroundColor(Color.parseColor("#1e91c7"));
            viewHolder.btndatabtn.setVisibility(View.GONE);
            viewHolder.btncancelbtn.setVisibility(View.GONE);
            Log.d("WalletLuckyYUYU"," 1 "+productList.get(i).getStatus().toString());

        }else  if(productList.get(i).getStatus().equalsIgnoreCase("2")){
            viewHolder.btncancelbtn.setBackgroundColor(Color.parseColor("#FF0000"));
            viewHolder.btncancelbtn.setVisibility(View.VISIBLE);
            viewHolder.btndatabtn.setVisibility(View.GONE);
            Log.d("WalletLuckyYUYU"," 2 "+productList.get(i).getStatus().toString());

        }else {
            viewHolder.btndatabtn.setBackgroundColor(Color.parseColor("#FF0000"));
            viewHolder.btndatabtn.setEnabled(false);
            viewHolder.btncancelbtn.setVisibility(View.GONE);
            viewHolder.btndatabtn.setVisibility(View.VISIBLE);
            Log.d("WalletLuckyYUYU"," 3 "+productList.get(i).getStatus().toString());
        }
        viewHolder.tvsubject.setText("Subject: "+productList.get(i).getSubjects());
        viewHolder.tvname.setText(""+productList.get(i).getName());
        viewHolder.tvquali.setText("Time: "+productList.get(i).getSuitable_timing());
        viewHolder.tvexpirence.setText("City: "+productList.get(i).getCity());

        viewHolder.tvrequest_subject.setText("Req Subject: "+productList.get(i).getReq_subjects());

        viewHolder.tvrequest_time.setText("Req Time: "+productList.get(i).getReq_timing());
        viewHolder.ratingBarbtn.setVisibility(View.GONE);
        viewHolder.tvmsg.setText("Message: "+productList.get(i).getMsg());
        Glide.with(context).load(productList.get(i).getUpload_pic()).placeholder(R.drawable.logo_light).into(viewHolder.tvicon);


    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder  {
        public TextView tvexpirence, tvsubject, tvname, tvquali, tvrequest_time,tvrequest_subject, tvmsg;

        ImageView tvicon, tvarrow;
        public CheckBox mcheckbox;
        public SharedPreferences sharedPreferences;
        public LinearLayout lljoinll;
        public CardView mcardview;
        public RatingBar ratingBarbtn;
        public Button btndatabtn,btncancelbtn;


        public ViewHolder(final View itemView) {
            super(itemView);

            mcardview = (CardView)itemView.findViewById(R.id.top_course_card);


            btndatabtn = (Button)itemView.findViewById(R.id.databtn);


            btncancelbtn = (Button)itemView.findViewById(R.id.cancelbtn);

            ratingBarbtn = (RatingBar)itemView.findViewById(R.id.topCourseRating);


            tvmsg = (TextView)itemView.findViewById(R.id.msg_of_tutor_new);

            tvexpirence = (TextView)itemView.findViewById(R.id.subject_of_ex);
            tvquali = (TextView)itemView.findViewById(R.id.subject_of_quali);
            tvname= (TextView) itemView.findViewById(R.id.name);
            tvsubject = (TextView) itemView.findViewById(R.id.subject_of_tutor);
//
            tvrequest_subject = (TextView) itemView.findViewById(R.id.subject_of_tutor_new);

            tvrequest_time = (TextView) itemView.findViewById(R.id.time_of_tutor_new);
//            tvproductqty = (TextView) itemView.findViewById(R.id.produnit);
//
//            tvprice = (TextView) itemView.findViewById(R.id.price);
            tvicon = (ImageView) itemView.findViewById(R.id.image_view);


            btndatabtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (productList.get(getAdapterPosition()).getTransation_status().equalsIgnoreCase("0")){
                        Intent intent = new Intent(mcontext, RazorpayActivity.class);
                        intent.putExtra("wallet_amount",productList.get(getAdapterPosition()).getWallet());
                        mcontext.startActivity(intent);
                        Toast.makeText(mcontext, "Please pay payment ", Toast.LENGTH_SHORT).show();
                    }else {
                        if (productList.get(getAdapterPosition()).getStatus().equalsIgnoreCase("0")) {
                            btndatabtn.setBackgroundColor(Color.parseColor("#FF0000"));
                            btndatabtn.setEnabled(false);
                            ((RequestDemoClass) mcontext).doAddition(productList.get(getAdapterPosition()).status, productList.get(getAdapterPosition()).request_id);
                        }
                    }
                }
            });
            btncancelbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (productList.get(getAdapterPosition()).getTransation_status().equalsIgnoreCase("0")){
                        Intent intent = new Intent(mcontext, RazorpayActivity.class);
                        intent.putExtra("wallet_amount",productList.get(getAdapterPosition()).getWallet());
                        mcontext.startActivity(intent);
                        Toast.makeText(mcontext, "Please pay payment ", Toast.LENGTH_SHORT).show();
                    }else {
                        if (productList.get(getAdapterPosition()).getStatus().equalsIgnoreCase("0")) {
                            btncancelbtn.setBackgroundColor(Color.parseColor("#FF0000"));
                            btncancelbtn.setEnabled(false);
                            ((RequestDemoClass) mcontext).cancel(productList.get(getAdapterPosition()).status, productList.get(getAdapterPosition()).request_id);
                        }
                    }
                }
            });
//            tvarrow = (ImageView) itemView.findViewById(R.id.uploadi);
//            mcheckbox = (CheckBox) itemView.findViewById(R.id.checkbox);


//            mcheckbox.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                }
//            });



            mcardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mcontext, StudentDetailsActivity.class);
                    intent.putExtra("image",productList.get(getAdapterPosition()).upload_pic);
                    intent.putExtra("name",productList.get(getAdapterPosition()).name);
                    intent.putExtra("fee","");
                    intent.putExtra("weak_subjects",productList.get(getAdapterPosition()).weak_subjects);

                    intent.putExtra("suitable_timing",productList.get(getAdapterPosition()).suitable_timing);

                    intent.putExtra("email",productList.get(getAdapterPosition()).email);
                    intent.putExtra("classs",productList.get(getAdapterPosition()).classs);

                    intent.putExtra("id",productList.get(getAdapterPosition()).student_id);
                    intent.putExtra("school_name",productList.get(getAdapterPosition()).school_name);
                    intent.putExtra("city",productList.get(getAdapterPosition()).city);
                    intent.putExtra("subject",productList.get(getAdapterPosition()).subjects);
                    intent.putExtra("current_location",productList.get(getAdapterPosition()).current_location);

                    intent.putExtra("board",productList.get(getAdapterPosition()).board);
                    intent.putExtra("time",productList.get(getAdapterPosition()).suitable_timing);
                    intent.putExtra("mobile",productList.get(getAdapterPosition()).mobile_no);

                    mcontext.startActivity(intent);
                }
            });
            tvicon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mcontext, StudentDetailsActivity.class);
                    intent.putExtra("image",productList.get(getAdapterPosition()).upload_pic);
                    intent.putExtra("name",productList.get(getAdapterPosition()).name);
                    intent.putExtra("fee","");
                    intent.putExtra("current_location",productList.get(getAdapterPosition()).current_location);

                    intent.putExtra("suitable_timing",productList.get(getAdapterPosition()).suitable_timing);


                    intent.putExtra("weak_subjects",productList.get(getAdapterPosition()).weak_subjects);

                    intent.putExtra("email",productList.get(getAdapterPosition()).email);
                    intent.putExtra("classs",productList.get(getAdapterPosition()).classs);

                    intent.putExtra("id",productList.get(getAdapterPosition()).student_id);
                    intent.putExtra("school_name",productList.get(getAdapterPosition()).school_name);
                    intent.putExtra("city",productList.get(getAdapterPosition()).city);
                    intent.putExtra("subject",productList.get(getAdapterPosition()).subjects);

                    intent.putExtra("board",productList.get(getAdapterPosition()).board);
                    intent.putExtra("time",productList.get(getAdapterPosition()).suitable_timing);
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


