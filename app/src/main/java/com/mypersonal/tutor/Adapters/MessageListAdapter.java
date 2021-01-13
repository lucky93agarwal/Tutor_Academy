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
import com.mypersonal.tutor.Activities.TutorActivity;
import com.mypersonal.tutor.Models.GetMessage;
import com.mypersonal.tutor.R;
import com.mypersonal.tutor.Razorpay.RazorpayActivity;
import com.mypersonal.tutor.anurag.register.customadapter.RequestDemoClass;

import java.util.List;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.ViewHolder> {
    public static Context mcontext;

    public boolean run = false;
    public static List<GetMessage> productList;
    public static boolean runtimer = true;
    public Context context;
    ViewHolder view;

    public GetMessage getselfdata;
    String Contest = "";


    ViewHolder viewHolder;

    public MessageListAdapter(Context context, List<GetMessage> productList) {
        super();
        this.context = context;
        this.productList = productList;
        mcontext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.top_new_course_cell_new, viewGroup, false);
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

        }else {
            viewHolder.btndatabtn.setBackgroundColor(Color.parseColor("#FF0000"));
            viewHolder.btndatabtn.setEnabled(false);
        }

        viewHolder.tvsubject.setText("Subject: "+productList.get(i).getWeak_subjects());
        viewHolder.tvname.setText(""+productList.get(i).getName());
        viewHolder.tvquali.setText("Time: "+productList.get(i).getSuitable_timing());
        viewHolder.tvexpirence.setText("City: "+productList.get(i).getCity());

        viewHolder.tvrequest_subject.setText("Message: "+productList.get(i).getMsg());

        if(productList.get(i).getTransation_status().equalsIgnoreCase("0")){
            viewHolder.tvrequest_time.setText("");
        }else {
            viewHolder.tvrequest_time.setText("Mobile : " + productList.get(i).getMobile_no());
        }
        viewHolder.ratingBarbtn.setVisibility(View.GONE);
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
        public RatingBar ratingBarbtn;
        public Button btndatabtn;

        public ViewHolder(final View itemView) {
            super(itemView);

            mcardview = (CardView)itemView.findViewById(R.id.top_course_card);
            btndatabtn = (Button)itemView.findViewById(R.id.databtn);
            ratingBarbtn = (RatingBar)itemView.findViewById(R.id.topCourseRating);

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
//            tvarrow = (ImageView) itemView.findViewById(R.id.uploadi);
//            mcheckbox = (CheckBox) itemView.findViewById(R.id.checkbox);
            btndatabtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (productList.get(getAdapterPosition()).getTransation_status().equalsIgnoreCase("0")){
                        Toast.makeText(mcontext, "Please pay payment ", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(mcontext, RazorpayActivity.class);
                        intent.putExtra("wallet_amount",productList.get(getAdapterPosition()).getWallet());
                        mcontext.startActivity(intent);
                    }else {
                        if (productList.get(getAdapterPosition()).getStatus().equalsIgnoreCase("0")) {
                            btndatabtn.setBackgroundColor(Color.parseColor("#FF0000"));
                            btndatabtn.setEnabled(false);
                            ((TutorActivity) mcontext).msgreply(productList.get(getAdapterPosition()).status, productList.get(getAdapterPosition()).msg_id,productList.get(getAdapterPosition()).getMsg());
                        }
                    }
                }
            });

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
                    intent.putExtra("subject",productList.get(getAdapterPosition()).weak_subjects);
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

                    intent.putExtra("board",productList.get(getAdapterPosition()).board);
                    intent.putExtra("weak_subjects",productList.get(getAdapterPosition()).weak_subjects);

                    intent.putExtra("email",productList.get(getAdapterPosition()).email);
                    intent.putExtra("classs",productList.get(getAdapterPosition()).classs);

                    intent.putExtra("id",productList.get(getAdapterPosition()).student_id);
                    intent.putExtra("school_name",productList.get(getAdapterPosition()).school_name);
                    intent.putExtra("city",productList.get(getAdapterPosition()).city);
                    intent.putExtra("subject",productList.get(getAdapterPosition()).weak_subjects);


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


