package com.example.pavikhanna.moviedb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TVDescActivity extends AppCompatActivity {

    ImageView youtube;
    ImageView poster;
    TextView review;
    TextView overview;
    MoviesRecyclerAdapter moviesRecyclerAdapter;
    ArrayList<Movies> movietv;
    RecyclerView recyclerView;
    Intent intent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvdesc);

        youtube = findViewById(R.id.youtubetv);
        poster = findViewById(R.id.postertv);
        review = findViewById(R.id.reviews);
        overview = findViewById(R.id.overviewtv);

        movietv = new ArrayList<>();

        intent=getIntent();
        int id = intent.getIntExtra("tvid",-1);

        moviesRecyclerAdapter = new MoviesRecyclerAdapter(movietv, this, new MoviesRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        //recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(moviesRecyclerAdapter);

        fetchInfo(id);

    }

    private void fetchInfo(int id) {

        Retrofit retrofitDetail = new Retrofit.Builder()
                .baseUrl("http://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MoviesApi moviesApiDetail = retrofitDetail.create(MoviesApi.class);

        Call<Movies> detailCall = moviesApiDetail.getTVDetails(id);

        detailCall.enqueue(new Callback<Movies>() {
            @Override
            public void onResponse(Call<Movies> call, Response<Movies> response) {

                Movies movie = response.body();


                Picasso.get().load("http://img.youtube.com/vi/"+movie.videos.results.get(0).key+"/mqdefault.jpg").resize(2000,900).into(youtube);


            }

            @Override
            public void onFailure(Call<Movies> call, Throwable t) {

            }
        });

    }
}
