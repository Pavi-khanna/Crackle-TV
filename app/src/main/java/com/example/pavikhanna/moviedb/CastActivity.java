package com.example.pavikhanna.moviedb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CastActivity extends AppCompatActivity {

    MoviesRecyclerAdapter recyclerAdapter;
    RecyclerView recyclerView;
    ImageView imageView;
    TextView biography;
    TextView born;
    TextView popularity;
    TextView name;
    ArrayList<Movies> movieCredits=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cast);


        imageView = findViewById(R.id.picceleb);
        biography = findViewById(R.id.biography);
        born = findViewById(R.id.born);
        popularity = findViewById(R.id.popularity);
        name = findViewById(R.id.nameceleb);
        recyclerView = findViewById(R.id.moviecredits);

        recyclerAdapter = new MoviesRecyclerAdapter(movieCredits, this, new MoviesRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //
            }
        });


        recyclerView.setAdapter(recyclerAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        //recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        Intent intent = getIntent();
        int id = intent.getIntExtra("id",-1);
        fetchMovies(id);


    }

    private void fetchMovies(int id) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MoviesApi moviesApi = retrofit.create(MoviesApi.class);

        Call<Movies.People> call = moviesApi.getPersonDetails(id);
        call.enqueue(new Callback<Movies.People>() {
            @Override
            public void onResponse(Call<Movies.People> call, Response<Movies.People> response) {

                Movies.People people = response.body();

                born.setText(people.birthday);
                Picasso.get().load("http://image.tmdb.org/t/p/w342/"+people.profile_path).into(imageView);
                biography.setText(people.biography);
                DecimalFormat decimalFormat = new DecimalFormat("#.00");
                float pop = people.popularity;
                String popul = decimalFormat.format(pop);
                popularity.setText(popul);
                name.setText(people.name);

            }

            @Override
            public void onFailure(Call<Movies.People> call, Throwable t) {

            }
        });


        Call<MovieResponse> movieResponseCall = moviesApi.getPersonMovies(id);

        movieResponseCall.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {

                MovieResponse movieResponse = response.body();
                ArrayList<Movies> movies1 = movieResponse.cast;

                if(movies1!= null)
                {
                    movieCredits.clear();
                    movieCredits.addAll(movies1);
                    recyclerAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });
    }
}
