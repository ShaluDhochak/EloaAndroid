package com.example.user.eloaandroid.View.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.eloaandroid.Beans.VideoListBean;
import com.example.user.eloaandroid.R;
import com.example.user.eloaandroid.View.Adapter.VideoListAdapter;

import java.util.ArrayList;
import java.util.List;


public class DashboardFragment extends Fragment {
    RecyclerView videoList_RecyclerView;
    VideoListAdapter faqsAdapter;
    private List<VideoListBean> movieList = new ArrayList<>();

    View view;

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        // Inflate the layout for this fragment
        initialiseUI();
        return view;

    }

    private void initialiseUI() {
        videoList_RecyclerView = (RecyclerView) view.findViewById(R.id.videoList_RecyclerView);

        faqsAdapter = new VideoListAdapter(movieList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        videoList_RecyclerView.setLayoutManager(mLayoutManager);
        videoList_RecyclerView.setItemAnimator(new DefaultItemAnimator());
        videoList_RecyclerView.setAdapter(faqsAdapter);

        prepareMovieData();
    }

    private void prepareMovieData() {
        VideoListBean movie = new VideoListBean("Video1", "11:00", "Monday", "Lorem Ipsum I ssimply dummy", "lorem ipsum is simply dummy text of the printing an dtypesetting industry.", " Lorem 1, LOREM 2");
        movieList.add(movie);

        movie = new VideoListBean("Video2", "13:00", "Tuesday", "Lorem Ipsum 2 ssimply dummy", "lorem ipsum is simply dummy text of the printing an dtypesetting industry.", " Lorem 3, LOREM 4");
        movieList.add(movie);
    }


}
