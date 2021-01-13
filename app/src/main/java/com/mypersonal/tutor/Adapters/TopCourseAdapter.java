package com.mypersonal.tutor.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.mypersonal.tutor.Activities.CourseDetailsActivity;
import com.mypersonal.tutor.Models.Course;
import com.mypersonal.tutor.Models.User;
import com.mypersonal.tutor.R;
import com.mypersonal.tutor.anurag.register.customadapter.TutorDetailsActivity;

import java.io.Serializable;
import java.util.ArrayList;

public class TopCourseAdapter extends RecyclerView.Adapter<TopCourseAdapter.ViewHolder> {

    //vars
    private Context mContext;
    private ArrayList<User> mTopCourses;


    public TopCourseAdapter(Context context, ArrayList<User> topCourses) {
        mContext = context;
        mTopCourses = topCourses;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.top_course_cell, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final User currentTopCourse = mTopCourses.get(holder.getAdapterPosition());
                switchToCourseDetailsView(currentTopCourse);
            }
        });
        return holder;
    }

    private void switchToCourseDetailsView(User currentTopCourse) {
        Intent intent = new Intent(mContext, TutorDetailsActivity.class);
        intent.putExtra("User", (Serializable) currentTopCourse);
        mContext.startActivity(intent);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final User currentTopCourse = mTopCourses.get(position);
        Glide.with(mContext)
                .load("https://beedesignsitsolution.in/mpt/uploads/user_image/"+currentTopCourse.getPicture()).centerCrop()
                .into(holder.image);

        holder.name.setText(currentTopCourse.getName());

        holder.tutorSubject.setText((String) currentTopCourse.getSubject());
    //    holder.topCourseRating.setRating(Float.parseFloat(String.valueOf(currentTopCourse.getFirstName())));

    }

    @Override
    public int getItemCount() {
        return mTopCourses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        int id;
        ImageView image;
        TextView name;
        TextView tutorSubject;
        RatingBar topCourseRating;
        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_view);
            name = itemView.findViewById(R.id.name);
           tutorSubject = itemView.findViewById(R.id.subject_of_tutor);
            topCourseRating = itemView.findViewById(R.id.topCourseRating);


        }
    }


}
