package com.example.user.eloaandroid.View.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.user.eloaandroid.Beans.VideoListBean;
import com.example.user.eloaandroid.R;
import com.example.user.eloaandroid.View.Activity.VideoPlayActivity;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Shalu Dhochak on 3/5/2018.
 */

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.MyViewHolder> {
    private List<VideoListBean.Data> moviesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView VideoHeading_txtView, VideoSubHeading_txtView;
        ImageView profileImg_iv, hideDetail_iv, showDetail_iv,Video_imgView;

        RelativeLayout videoDescription;

        //Video Desciprion
        TextView titleDetail_tv, descriptionDetail_tv, keywordsDetail_tv;


        @SuppressLint("CutPasteId")
        public MyViewHolder(View view) {
            super(view);
            VideoHeading_txtView = (TextView) view.findViewById(R.id.VideoHeading_txtView);
            VideoSubHeading_txtView = (TextView) view.findViewById(R.id.VideoSubHeading_txtView);
            profileImg_iv = (ImageView) view.findViewById(R.id.profileImg_iv);
            showDetail_iv = (ImageView) view.findViewById(R.id.showDetail_iv);
            hideDetail_iv = (ImageView) view.findViewById(R.id.hideDetail_iv);
            Video_imgView = view.findViewById(R.id.Video_imgView);

            videoDescription = (RelativeLayout) view.findViewById(R.id.videoDescription);

            titleDetail_tv = (TextView) view.findViewById(R.id.titleDetail_tv);
            descriptionDetail_tv = (TextView) view.findViewById(R.id.descriptionDetail_tv);
            keywordsDetail_tv = (TextView) view.findViewById(R.id.keywordsDetail_tv);
        }
    }

    Context context;

    public VideoListAdapter(Context context, List<VideoListBean.Data> moviesList) {
        this.context = context;
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
        final VideoListBean.Data movie = moviesList.get(position);
        holder.VideoHeading_txtView.setText(movie.getTitle());
        holder.VideoSubHeading_txtView.setText(movie.getDate());

        holder.titleDetail_tv.setText(movie.getTitle());
        holder.descriptionDetail_tv.setText(movie.getDescription());
        holder.keywordsDetail_tv.setText(movie.getKeyword());
        //Picasso.get().load(movie.getVideo()).into(holder.Video_imgView);

        try {
           // Uri uri = Uri.parse(movie.getVideo());
           // String scheme = uri.getScheme();
           // holder.Video_imgView.setVideoURI(movie.getVideoUri());
           // holder.Video_imgView.seekTo(500);

            holder.Video_imgView.setImageBitmap(movie.getBitmap());
        } catch (Throwable e) {
            e.printStackTrace();
        }

        holder.Video_imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, VideoPlayActivity.class);
                intent.putExtra("path", movie.getVideo());
                context.startActivity(intent);
            }
        });

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
