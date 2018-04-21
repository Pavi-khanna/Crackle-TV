package com.example.pavikhanna.moviedb;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Pavi Khanna on 4/9/2018.
 */

public class CastRecyclerAdapter extends RecyclerView.Adapter<CastRecyclerAdapter.CastViewHolder>{

    ArrayList<Movies.Cast> casts;
    Context context;
    OnItemClickListener onItemClickListener;

    public CastRecyclerAdapter(ArrayList<Movies.Cast> casts, Context context, OnItemClickListener onItemClickListener) {
        this.casts = casts;
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    interface OnItemClickListener {

        void onItemClick(int position);
    }

    @Override
    public CastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.item_cast,parent,false);
        CastRecyclerAdapter.CastViewHolder holder = new CastRecyclerAdapter.CastViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(final CastViewHolder holder, int position) {

        Movies.Cast cast = casts.get(position);
        holder.character.setText(cast.character);
        holder.name.setText(cast.characterName);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(holder.getAdapterPosition());
            }
        });

        if(cast.profilePath!=null)
        {Picasso.get().load("http://image.tmdb.org/t/p/w342/"+cast.profilePath).resize(280,420).into(holder.avatar);}
        else
        { Picasso.get().load(R.drawable.nopicture).resize(280,420).into(holder.avatar); }
    }

    @Override
    public int getItemCount() {

        return casts.size();

    }

    class CastViewHolder extends RecyclerView.ViewHolder{

        ImageView avatar;
        TextView character;
        TextView name;
        View itemView;

        public CastViewHolder(View itemView) {
            super(itemView);

            this.itemView = itemView;
            avatar = itemView.findViewById(R.id.castImage);
            character = itemView.findViewById(R.id.character);
            name = itemView.findViewById(R.id.name);

        }
    }

}
