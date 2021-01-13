package com.mypersonal.tutor.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mypersonal.tutor.Activities.CoursesActivity;
import com.mypersonal.tutor.JSONSchemas.CourseSchema;
import com.mypersonal.tutor.Models.Category;
import com.mypersonal.tutor.Models.Course;
import com.mypersonal.tutor.Models.TutorsList;
import com.mypersonal.tutor.Network.Api;
import com.mypersonal.tutor.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TutorsAdapter extends RecyclerView.Adapter<com.mypersonal.tutor.Adapters.CategoryAdapter.ViewHolder> implements Serializable {

        private static final String TAG = "CategoryAdapter";

        //vars
        private ArrayList<TutorsList> mCategory = new ArrayList<>();
        private ArrayList<Course> mCourses = new ArrayList<>();
        private Context mContext;

        private CardView categoryCardViewList;

        public TutorsAdapter(Context context, ArrayList<TutorsList> category) {
            mCategory = category;
            mContext = context;
        }

        @NonNull
        @Override
        public com.mypersonal.tutor.Adapters.CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_cell, parent, false);
            categoryCardViewList = view.findViewById(R.id.categoryCardView);

            final com.mypersonal.tutor.Adapters.CategoryAdapter.ViewHolder holder = new com.mypersonal.tutor.Adapters.CategoryAdapter.ViewHolder(view);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final TutorsList currentCategory = mCategory.get(holder.getAdapterPosition());
                    getCoursesByCategoryId(currentCategory.getId());
                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull com.mypersonal.tutor.Adapters.CategoryAdapter.ViewHolder holder, final int position) {
            final TutorsList currentCategory = mCategory.get(position);
//            Glide.with(mContext)
//                    .asBitmap()
//                    .load(currentCategory.getThumbnail())
//                    .into(holder.image);

            // Calling this function will help you to change the card view background color dynamically.
            applyCategoryCardViewBackgroundColor(position);
//            holder.name.setText(currentCategory.getTitle());
//            holder.numberOfCourses.setText(currentCategory.getNumberOfCourses() + " Courses");
        }

        private void getCoursesByCategoryId(int categoryId) {

            Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
            Api api = retrofit.create(Api.class);
            Call<List<CourseSchema>> call = api.getCourses(categoryId);
            call.enqueue(new Callback<List<CourseSchema>>() {
                @Override
                public void onResponse(Call<List<CourseSchema>> call, Response<List<CourseSchema>> response) {
                    mCourses = new ArrayList<>();
                    List<CourseSchema> courseSchemas = response.body();
                    for (CourseSchema m : courseSchemas) {
                        mCourses.add(new Course(m.getId(),m.getTitle(),m.getThumbnail(),m.getPrice(),m.getInstructorName(),m.getRating(),m.getNumberOfRatings(),m.getTotalEnrollment(), m.getShareableLink(), m.getCourseOverviewProvider(), m.getCourseOverviewUrl()));
                    }

                    Intent intent = new Intent(mContext, CoursesActivity.class);
                    intent.putExtra("Course", mCourses);
                    mContext.startActivity(intent);
                }

                @Override
                public void onFailure(Call<List<CourseSchema>> call, Throwable t) {
                    Toast.makeText(mContext, "Some error occurred", Toast.LENGTH_SHORT).show();

                }
            });
        }

        // Change the card background color dynamically
        private void applyCategoryCardViewBackgroundColor(int position) {
            int incrementalPosition = position + 1;
            if (incrementalPosition % 3 == 0) {
                categoryCardViewList.setCardBackgroundColor(Color.parseColor("#29D0A8"));
            } else if ((incrementalPosition - 2) % 3 == 0) {
                categoryCardViewList.setCardBackgroundColor(Color.parseColor("#F65053"));
            } else {
                categoryCardViewList.setCardBackgroundColor(Color.parseColor("#594CF5"));
            }
        }

        @Override
        public int getItemCount() {
            return mCategory.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView image;
            TextView name;
            TextView numberOfCourses;
            ImageView goToCategoryWiseCourseList;

            public ViewHolder(View itemView) {
                super(itemView);
                image = itemView.findViewById(R.id.categoryImageView);
                name = itemView.findViewById(R.id.categoryName);
                numberOfCourses = itemView.findViewById(R.id.numberOfCourses);
                goToCategoryWiseCourseList = itemView.findViewById(R.id.goToCategoryWiseCourseList);
            }


        }
    }

