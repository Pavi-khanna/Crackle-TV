package com.example.pavikhanna.moviedb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Pavi Khanna on 4/10/2018.
 */

public class ReviewAdapter extends BaseAdapter {

    Context context;
    ArrayList<Movies.ReviewClass> reviews3;

    public ReviewAdapter(Context context, ArrayList<Movies.ReviewClass> reviews3) {
        this.context = context;
        this.reviews3 = reviews3;
    }

    @Override
    public int getCount() {
        return reviews3.size();
    }

    @Override
    public Movies.ReviewClass getItem(int i) {
        return reviews3.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View output= view;
        if(output==null)
        {
            //TODO LAYOUT ADD STAR AND RATING
            output = inflater.inflate(R.layout.item_review,viewGroup,false);
            ReviewViewHolder holder = new ReviewViewHolder(output);
            output.setTag(holder);
        }

        ReviewViewHolder holder= (ReviewViewHolder) output.getTag();
        Movies.ReviewClass reviewClass = getItem(i);

        holder.authorTextView.setText("Author : "+reviewClass.author);
        holder.contentTextView.setText(reviewClass.content);
        return output;
    }


    class ReviewViewHolder {

        TextView authorTextView;
        TextView contentTextView;

        ReviewViewHolder(View rowLayout) {
            authorTextView = rowLayout.findViewById(R.id.authoritem);
            contentTextView = rowLayout.findViewById(R.id.contentitem);
        }
    }

}
