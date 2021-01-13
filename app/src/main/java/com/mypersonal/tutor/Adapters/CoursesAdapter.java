package com.mypersonal.tutor.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mypersonal.tutor.Models.Course;
import com.mypersonal.tutor.Models.User;
import com.mypersonal.tutor.R;
import com.mypersonal.tutor.Activities.CourseDetailsActivity;
import com.mypersonal.tutor.anurag.register.customadapter.TutorDetailsActivity;

import java.util.ArrayList;

public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.ViewHolder> {
    private static final String TAG = "Courses List Adapter";
    private static final String TAG2 = "Checker";

    //vars
    private Context mContext;
    private ArrayList<User> mCourses = new ArrayList<>();
    private ArrayList<User> mtoptutor = new ArrayList<User>();

    public CoursesAdapter(Context context, ArrayList<User> courses) {
      //  mCourses = courses;
        mtoptutor = courses;
        mContext = context;

    }

    @NonNull
    @Override
    public CoursesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_cell, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           //     final User currentCourse = mCourses.get(holder.getAdapterPosition());
                final User currentCourse = mtoptutor.get(holder.getAdapterPosition());
                switchToCourseDetailsActivity(currentCourse);
            }
        });
        return holder;
    }

    private void switchToCourseDetailsActivity(User currentCourse) {
        Intent intent = new Intent(mContext, TutorDetailsActivity.class);
        intent.putExtra("User" ,currentCourse);
        mContext.startActivity(intent);
    }


    @Override
    public void onBindViewHolder(@NonNull CoursesAdapter.ViewHolder holder, final int position) {
     //   final User currentCourse = mCourses.get(position);
        final User currentCourse = mtoptutor.get(position);
        Glide.with(mContext)
                .asBitmap()
                .load(currentCourse.getPicture())
                .into(holder.image);

        holder.name.setText(currentCourse.getName());
        holder.coursePrice.setText(currentCourse.getSubject());
       // holder.totalNumberOfRating.setText("( "+currentCourse.getTotalNumberRating()+" )");
       // holder.instructorName.setText(currentCourse.getInstructor());
       // holder.starRating.setRating(currentCourse.getRating());
       // holder.rating.setText((" "+currentCourse.getRating()+" "));

    }

    @Override
    public int getItemCount() {
        return mtoptutor.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView name;
        TextView coursePrice;
        TextView instructorName;
        TextView rating;
        TextView totalNumberOfRating;
        RatingBar starRating;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.courseThumbnail);
            name = itemView.findViewById(R.id.courseTitle);
            coursePrice = itemView.findViewById(R.id.coursePrice);
            instructorName = itemView.findViewById(R.id.instructorName);
            rating = itemView.findViewById(R.id.numericRating);
            totalNumberOfRating = itemView.findViewById(R.id.totalNumberOfRatingByUsers);
            starRating = itemView.findViewById(R.id.starRating);
        }
    }
}
