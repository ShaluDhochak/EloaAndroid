package com.example.user.eloaandroid.View.Adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.eloaandroid.Beans.VideoDescriptionBean;
import com.example.user.eloaandroid.R;

import java.util.List;

/**
 * Created by User on 3/6/2018.
 */

public class VideoDescriptionAdapter extends RecyclerView.Adapter<VideoDescriptionAdapter.MyViewHolder> {
    private List<VideoDescriptionBean> moviesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nameVideoEditText_tv;

        @SuppressLint("CutPasteId")
        public MyViewHolder(View view) {
            super(view);
            nameVideoEditText_tv = (TextView) view.findViewById(R.id.nameVideoEditText_tv);
             }
    }

    public VideoDescriptionAdapter(List<VideoDescriptionBean> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public VideoDescriptionAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.plus_list_layout, parent, false);

        return new VideoDescriptionAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final VideoDescriptionAdapter.MyViewHolder holder, int position) {
        VideoDescriptionBean movie = moviesList.get(position);
        holder.nameVideoEditText_tv.setText(movie.getName());
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

}

