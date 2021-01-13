package com.mypersonal.tutor.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mypersonal.tutor.Adapters.CourseOutcomesAdapter;
import com.mypersonal.tutor.Models.CourseDetails;
import com.mypersonal.tutor.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CourseOutcomeFragment extends Fragment {

    private CourseDetails mCourseDetails;
    private RecyclerView mCourseOutcomesRecyclerView;
    public CourseOutcomeFragment(CourseDetails courseDetails) {
        // Required empty public constructor
        this.mCourseDetails = courseDetails;
    }

    private void init(View view) {
        mCourseOutcomesRecyclerView = view.findViewById(R.id.courseOutcomeRecyclerView);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_course_outcome, container, false);
        init(view);
        getOutcomesData();
        return view;
    }

    private void getOutcomesData() {
        CourseOutcomesAdapter adapter = new CourseOutcomesAdapter(getActivity(), mCourseDetails.getCourseOutcomes());
        mCourseOutcomesRecyclerView.setAdapter(adapter);
        mCourseOutcomesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
