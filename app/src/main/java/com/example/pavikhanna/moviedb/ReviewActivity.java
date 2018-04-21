package com.example.pavikhanna.moviedb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReviewActivity extends AppCompatActivity {

    ReviewAdapter reviewAdapter;
    ArrayList<Movies.ReviewClass> reviewClasses;
    ListView reviewList;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        reviewList = findViewById(R.id.reviewList);

        reviewClasses = new ArrayList<>();
        intent = getIntent();
        int id = intent.getIntExtra("idReviews",-1);
        if(id!=-1)
        {fetchReviews(id);}

        reviewAdapter = new ReviewAdapter(this,reviewClasses);

        reviewList.setAdapter(reviewAdapter);

    }

    private void fetchReviews(int id) {

        Retrofit retrofitDetail = new Retrofit.Builder()
                .baseUrl("http://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MoviesApi moviesApiDetail = retrofitDetail.create(MoviesApi.class);

        Call<Movies> reviewCall = moviesApiDetail.getMovieDetails(id);

        reviewCall.enqueue(new Callback<Movies>() {
            @Override
            public void onResponse(Call<Movies> call, Response<Movies> response) {

                Movies movie = response.body();

                ArrayList<Movies.ReviewClass> reviews = movie.reviews.results;

                if(reviews!=null)
                {
                    reviewClasses.clear();
                    reviewClasses.addAll(reviews);
                    reviewAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<Movies> call, Throwable t) {
                //
            }
        });


    }


}
