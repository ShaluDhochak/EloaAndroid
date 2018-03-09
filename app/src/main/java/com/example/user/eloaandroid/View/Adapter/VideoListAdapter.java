package com.example.user.eloaandroid.View.Adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.user.eloaandroid.Beans.VideoListBean;
import com.example.user.eloaandroid.R;

import java.util.List;

/**
 * Created by Shalu Dhochak on 3/5/2018.
 */

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.MyViewHolder> {
    private List<VideoListBean> moviesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView VideoHeading_txtView, VideoSubHeading_txtView;
        ImageView profileImg_iv, hideDetail_iv, showDetail_iv;
        RelativeLayout videoDescription;

        //Video Desciprion
        TextView titleDetail_tv,descriptionDetail_tv,keywordsDetail_tv;


        @SuppressLint("CutPasteId")
        public MyViewHolder(View view) {
            super(view);
            VideoHeading_txtView = (TextView) view.findViewById(R.id.VideoHeading_txtView);
            VideoSubHeading_txtView = (TextView) view.findViewById(R.id.VideoSubHeading_txtView);
            profileImg_iv = (ImageView) view.findViewById(R.id.profileImg_iv);
            showDetail_iv = (ImageView) view.findViewById(R.id.showDetail_iv);
            hideDetail_iv = (ImageView) view.findViewById(R.id.hideDetail_iv);

            videoDescription = (RelativeLayout) view.findViewById(R.id.videoDescription);

            titleDetail_tv = (TextView) view.findViewById(R.id.titleDetail_tv);
            descriptionDetail_tv = (TextView) view.findViewById(R.id.descriptionDetail_tv);
            keywordsDetail_tv = (TextView) view.findViewById(R.id.keywordsDetail_tv);
        }
    }

    public VideoListAdapter(List<VideoListBean> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_list_recyclerview, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        VideoListBean movie = moviesList.get(position);
        holder.VideoHeading_txtView.setText(movie.getVideoHeading());
        holder.VideoSubHeading_txtView.setText(movie.getTime() + " " + movie.getDay());

        holder.titleDetail_tv.setText(movie.getTitle());
        holder.descriptionDetail_tv.setText(movie.getDescription());
        holder.keywordsDetail_tv.setText(movie.getKeywords());

        holder.videoDescription.setVisibility(View.VISIBLE);
        holder.hideDetail_iv.setVisibility(View.VISIBLE);
        holder.showDetail_iv.setVisibility(View.GONE);

        holder.showDetail_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.videoDescription.setVisibility(View.VISIBLE);
                holder.hideDetail_iv.setVisibility(View.VISIBLE);
                holder.showDetail_iv.setVisibility(View.GONE);
            }
        });
        holder.hideDetail_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.videoDescription.setVisibility(View.GONE);
                holder.showDetail_iv.setVisibility(View.VISIBLE);
                holder.hideDetail_iv.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

}
