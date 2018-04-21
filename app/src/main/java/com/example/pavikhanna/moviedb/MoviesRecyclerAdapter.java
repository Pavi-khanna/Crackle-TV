package com.example.pavikhanna.moviedb;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Pavi Khanna on 3/24/2018.
 */

public class MoviesRecyclerAdapter extends RecyclerView.Adapter<MoviesRecyclerAdapter.MoviesViewHolder>{

    ArrayList<Movies> movies;
    Context context;
    OnItemClickListener listener;

    public MoviesRecyclerAdapter(ArrayList<Movies> movies, Context context, OnItemClickListener listener) {
        this.movies = movies;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public MoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.item_movie,parent,false);
        MoviesViewHolder holder = new MoviesViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MoviesViewHolder holder, int position) {

        Movies movie = movies.get(position);
        if(movie.nameTV==null)
        {holder.moviename.setText(movie.title);}
        else
        {
            holder.moviename.setText(movie.nameTV);
        }

        String s = String.valueOf(movie.voteAverage);
        holder.voteAverage.setText(s);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(holder.getAdapterPosition());
            }
        });

        if(movie.profile_path==null){
        Picasso.get().load("http://image.tmdb.org/t/p/w500/"+movie.posterPath).into(holder.avatar);}
        else
        {
            Picasso.get().load("http://image.tmdb.org/t/p/w500/"+movie.profile_path).into(holder.avatar);
        }

        //TODO OVERVIEW EXPANDABLE TEXT VIEW
        //TODO PROGRESS BAR
        //TODO CAST NAMES PROPER
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    interface OnItemClickListener {

        void onItemClick(int position);
    }



    class MoviesViewHolder extends RecyclerView.ViewHolder
    {
        TextView moviename;
        ImageView avatar;
        TextView voteAverage;
        View itemView;

        public MoviesViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            voteAverage = itemView.findViewById(R.id.voteAverage);
            moviename = itemView.findViewById(R.id.moviename);
            avatar = itemView.findViewById(R.id.avatar);
        }
    }

}
