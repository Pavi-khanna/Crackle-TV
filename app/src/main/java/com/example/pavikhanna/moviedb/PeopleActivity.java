package com.example.pavikhanna.moviedb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PeopleActivity extends AppCompatActivity {

    MoviesRecyclerAdapter castRecyclerAdapter;
    RecyclerView recyclerView;
    ArrayList<Movies> people;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people);

        recyclerView = findViewById(R.id.peoplegrid);
        castRecyclerAdapter = new MoviesRecyclerAdapter(people, this, new MoviesRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //
            }
        });

        people = new ArrayList<>();

        recyclerView.setAdapter(castRecyclerAdapter);

        //recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        fetchPeople();

    }

    private void fetchPeople() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MoviesApi moviesApi = retrofit.create(MoviesApi.class);

        Call<MovieResponse> call = moviesApi.getPopularPeople();

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse castResponse = response.body();

                ArrayList<Movies> movies1 = castResponse.results;

                if(movies1!= null)
                {
                    people.clear();
                    people.addAll(movies1);
                    castRecyclerAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });


    }


}
