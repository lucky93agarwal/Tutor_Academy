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
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mypersonal.tutor.Activities.TutorListActivity;
import com.mypersonal.tutor.Models.GetSubjectData;
import com.mypersonal.tutor.R;
import com.mypersonal.tutor.anurag.register.customadapter.TutorDetailsActivity;

import java.util.List;

public class SubjectListAdapter extends RecyclerView.Adapter<SubjectListAdapter.ViewHolder> {
    public static Context mcontext;

    public boolean run = false;
    public static List<GetSubjectData> productList;
    public static boolean runtimer = true;
    public Context context;
    ViewHolder view;

    public GetSubjectData getselfdata;
    String Contest = "";


    ViewHolder viewHolder;

    public SubjectListAdapter(Context context, List<GetSubjectData> productList) {
        super();
        this.context = context;
        this.productList = productList;
        mcontext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.category_cell, viewGroup, false);
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

        viewHolder.tvcount.setText(productList.get(i).count+" Tutor");
        viewHolder.tvsubject.setText(productList.get(i).getName());




    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder  {
        public TextView tvproductname, tvsubject, tvcount, tvcheck, tvuploaded;

        CardView tvicon, tvarrow;
        public CheckBox mcheckbox;
        public SharedPreferences sharedPreferences;
        public LinearLayout lljoinll;


        public ViewHolder(final View itemView) {
            super(itemView);


            tvsubject= (TextView) itemView.findViewById(R.id.categoryName);
            tvcount = (TextView) itemView.findViewById(R.id.numberOfCourses);
//

//            tvproductqty = (TextView) itemView.findViewById(R.id.produnit);
//
//            tvprice = (TextView) itemView.findViewById(R.id.price);
            tvicon = (CardView) itemView.findViewById(R.id.categoryCardView);
//            tvarrow = (ImageView) itemView.findViewById(R.id.uploadi);
//            mcheckbox = (CheckBox) itemView.findViewById(R.id.checkbox);


//            mcheckbox.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                }
//            });


            tvicon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mcontext, TutorListActivity.class);
                    intent.putExtra("id",productList.get(getAdapterPosition()).id);
//                    intent.putExtra("name",productList.get(getAdapterPosition()).name);
//                    intent.putExtra("fee",productList.get(getAdapterPosition()).fee);
//
//                    intent.putExtra("exp",productList.get(getAdapterPosition()).experience);
//                    intent.putExtra("city",productList.get(getAdapterPosition()).city);
//                    intent.putExtra("subject",productList.get(getAdapterPosition()).subject);
//
//
//                    intent.putExtra("time",productList.get(getAdapterPosition()).time);
//                    intent.putExtra("mobile",productList.get(getAdapterPosition()).mobile);

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


