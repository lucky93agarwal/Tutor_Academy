package com.mypersonal.tutor.anurag.register.customadapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mypersonal.tutor.Models.User;
import com.mypersonal.tutor.R;

import java.util.ArrayList;


public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<User> mStudent;

    public ChatAdapter(Context context, ArrayList<User> student) {
        mContext = context;
        mStudent = student;

    }

    @NonNull
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_demo_tutor, parent, false);
        final ChatAdapter.ViewHolder holder = new ChatAdapter.ViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final User studentDetails = mStudent.get(holder.getAdapterPosition());
                switchToStudentDetailsView(studentDetails);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ViewHolder holder, int position) {

        final User studentDetails = mStudent.get(position);
        Glide.with(mContext)
                .asBitmap()
                .load(studentDetails.getPicture()).centerCrop()
                .into(holder.image);

        holder.name.setText(studentDetails.getName());

        holder.studentClass.setText((String) studentDetails.getSubject());


    }

    @Override
    public int getItemCount() {
        return mStudent.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;
        TextView studentClass;
        TextView studentCity;
        Button viewDetail;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.student_pic);
            name = itemView.findViewById(R.id.requested_student_name);
            studentClass = itemView.findViewById(R.id.requested_student_class);
            studentCity = itemView.findViewById(R.id.requested_student_address);
            viewDetail = itemView.findViewById(R.id.view_details_student);

            viewDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent in = new Intent(mContext,StudentDetailActivity.class);
                    mContext.startActivity(in);
                }
            });

        }
    }
    private void switchToStudentDetailsView(User studentDetails) {
        Intent intent = new Intent(mContext, StudentDetailActivity.class);
        intent.putExtra("id", String.valueOf(studentDetails));
        mContext.startActivity(intent);
    }

}
