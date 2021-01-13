package com.mypersonal.tutor.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Adapter;

import com.mypersonal.tutor.Models.User;
import com.mypersonal.tutor.R;
import com.mypersonal.tutor.anurag.register.customadapter.RequestDemoClass;
import com.mypersonal.tutor.anurag.register.customadapter.StudentRequestAdapter;

import java.util.ArrayList;


public class ChatActivity extends AppCompatActivity {

private RecyclerView chatRecycler;
ArrayList<User> mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        chatRecycler = (RecyclerView) findViewById(R.id.recyclerViewForChating);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);

        StudentRequestAdapter adapter = new StudentRequestAdapter(ChatActivity.this, mUser);
        chatRecycler.setAdapter(adapter);
        chatRecycler.setLayoutManager(layoutManager);

        fetchStudentRequest();

    }

    private void fetchStudentRequest() {


    }


}