package com.mypersonal.tutor.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mypersonal.tutor.Activities.PersonalTutorActivity;
import com.mypersonal.tutor.Models.GetTutorData;
import com.mypersonal.tutor.R;
import com.mypersonal.tutor.anurag.register.customadapter.TutorDetailsActivity;

import java.io.Serializable;
import java.util.List;

public class tutorlsitAdapter extends RecyclerView.Adapter<tutorlsitAdapter.ViewHolder> {
    public static Context mcontext;

    public boolean run = false;
    public static List<GetTutorData> productList;
    public static boolean runtimer = true;
    public Context context;
    ViewHolder view;

    public GetTutorData getselfdata;
    String Contest = "";


    ViewHolder viewHolder;

    public tutorlsitAdapter(Context context, List<GetTutorData> productList) {
        super();
        this.context = context;
        this.productList = productList;
        mcontext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.top_course_cell, viewGroup, false);
        viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        getselfdata = productList.get(i);
        view = viewHolder;


        viewHolder.tvsubject.setText("Subject: "+productList.get(i).getSubject());
        viewHolder.tvname.setText(""+productList.get(i).getName());
        viewHolder.tvquali.setText("Time: "+productList.get(i).getTime());
        viewHolder.tvexpirence.setText("Experience: "+productList.get(i).getExperience()+" Year");
        viewHolder.tvlocation.setText("City :"+productList.get(i).getCity());
        viewHolder.tvcity.setText("Location :"+productList.get(i).getArea());

        Glide.with(context).load(productList.get(i).getImage()).placeholder(R.drawable.logo_light).into(viewHolder.tvicon);
        viewHolder.mratingbar.setRating(Float.parseFloat(productList.get(i).getRating()));


    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder  {
        public TextView tvexpirence, tvsubject, tvname, tvquali, tvlocation,tvcity;

        ImageView tvicon, tvarrow;
        public CheckBox mcheckbox;
        public SharedPreferences sharedPreferences;
        public LinearLayout lljoinll;
        public CardView mcardview;
        public RatingBar mratingbar;


        public ViewHolder(final View itemView) {
            super(itemView);

            tvlocation = (TextView)itemView.findViewById(R.id.subject_of_tutor_location);
            tvcity = (TextView)itemView.findViewById(R.id.subject_of_ex_city);

            mcardview = (CardView)itemView.findViewById(R.id.top_course_card);


            mratingbar = (RatingBar)itemView.findViewById(R.id.topCourseRating);

            tvexpirence = (TextView)itemView.findViewById(R.id.subject_of_ex);
            tvquali = (TextView)itemView.findViewById(R.id.subject_of_quali);
            tvname= (TextView) itemView.findViewById(R.id.name);
            tvsubject = (TextView) itemView.findViewById(R.id.subject_of_tutor);
//

//            tvproductqty = (TextView) itemView.findViewById(R.id.produnit);
//
//            tvprice = (TextView) itemView.findViewById(R.id.price);
            tvicon = (ImageView) itemView.findViewById(R.id.image_view);
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
                    Intent intent = new Intent(mcontext, PersonalTutorActivity.class);
                    intent.putExtra("image",productList.get(getAdapterPosition()).image);
                    intent.putExtra("name",productList.get(getAdapterPosition()).name);


                    intent.putExtra("rating",productList.get(getAdapterPosition()).rating);
                    intent.putExtra("fee",productList.get(getAdapterPosition()).fee);


                    intent.putExtra("email",productList.get(getAdapterPosition()).email);
                    intent.putExtra("qty",productList.get(getAdapterPosition()).qty);

                    intent.putExtra("day",productList.get(getAdapterPosition()).day);



                    intent.putExtra("id",productList.get(getAdapterPosition()).tutorid);
                    intent.putExtra("exp",productList.get(getAdapterPosition()).experience);
                    intent.putExtra("city",productList.get(getAdapterPosition()).city);
                    intent.putExtra("subject",productList.get(getAdapterPosition()).subject);
                    intent.putExtra("tuition_type",productList.get(getAdapterPosition()).tuition_type);
                    intent.putExtra("willing_to_traveling",productList.get(getAdapterPosition()).willing_to_traveling);

                    intent.putExtra("time",productList.get(getAdapterPosition()).time);
                    intent.putExtra("mobile",productList.get(getAdapterPosition()).mobile);

                    intent.putExtra("transaction_status",productList.get(getAdapterPosition()).transaction_status);

                    mcontext.startActivity(intent);
                }
            });
            tvicon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mcontext, PersonalTutorActivity.class);
                    intent.putExtra("image",productList.get(getAdapterPosition()).image);

                    intent.putExtra("transaction_status",productList.get(getAdapterPosition()).transaction_status);

                    intent.putExtra("name",productList.get(getAdapterPosition()).name);
                    intent.putExtra("fee",productList.get(getAdapterPosition()).fee);

                    intent.putExtra("email",productList.get(getAdapterPosition()).email);
                    intent.putExtra("id",productList.get(getAdapterPosition()).tutorid);
                    intent.putExtra("qty",productList.get(getAdapterPosition()).qty);

                    intent.putExtra("rating",productList.get(getAdapterPosition()).rating);

                    intent.putExtra("day",productList.get(getAdapterPosition()).day);
                    intent.putExtra("tuition_type",productList.get(getAdapterPosition()).tuition_type);
                    intent.putExtra("willing_to_traveling",productList.get(getAdapterPosition()).willing_to_traveling);

                    intent.putExtra("exp",productList.get(getAdapterPosition()).experience);
                    intent.putExtra("city",productList.get(getAdapterPosition()).city);
                    intent.putExtra("subject",productList.get(getAdapterPosition()).subject);


                    intent.putExtra("time",productList.get(getAdapterPosition()).time);
                    intent.putExtra("mobile",productList.get(getAdapterPosition()).mobile);

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


