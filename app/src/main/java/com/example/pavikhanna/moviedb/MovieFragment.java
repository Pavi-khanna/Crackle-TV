package com.example.pavikhanna.moviedb;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {


    ProgressBar progressBar;
    MoviesRecyclerAdapter recyclerAdapter;
    MoviesRecyclerAdapter recyclerAdapter2;
    MoviesRecyclerAdapter recyclerAdapter3;
    ArrayList<Movies> movies = new ArrayList<>();
    ArrayList<Movies> moviest = new ArrayList<>();
    ArrayList<Movies> moviesu = new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerView recyclerView2;
    RecyclerView recyclerView3;
    ImageView twitter;
    ImageView insta;
    ImageView textView3;
    Intent intent;


    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_movie, container, false);
        recyclerView = view.findViewById(R.id.popularRecycler);
        recyclerView2 = view.findViewById(R.id.topratedRecycler);
        recyclerView3 = view.findViewById(R.id.upcomingRecycler);
        twitter = view.findViewById(R.id.twitter);
        insta = view.findViewById(R.id.insta);
        textView3 = view.findViewById(R.id.imageView3);

        recyclerAdapter = new MoviesRecyclerAdapter(movies, getContext(), new MoviesRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final int position) {
                intent = new Intent(getContext(),MovieDescActivity.class);
                int id=movies.get(position).Id;
                Bundle bundle=new Bundle();
                bundle.putInt("movieid",id);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        recyclerAdapter2 = new MoviesRecyclerAdapter(moviest, getContext(), new MoviesRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                intent = new Intent(getContext(),MovieDescActivity.class);
                intent.putExtra("movieid",moviest.get(position).Id);
                startActivity(intent);
            }
        });

        recyclerAdapter3 = new MoviesRecyclerAdapter(moviesu, getContext(), new MoviesRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                intent = new Intent(getContext(),MovieDescActivity.class);
                intent.putExtra("movieid",moviesu.get(position).Id);
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(recyclerAdapter);
        recyclerView2.setAdapter(recyclerAdapter2);
        recyclerView3.setAdapter(recyclerAdapter3);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        //recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView2.setItemAnimator(new DefaultItemAnimator());

        recyclerView3.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView3.setItemAnimator(new DefaultItemAnimator());

        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                String url = "https://twitter.com/tmdb";
                intent.setData(Uri.parse(url));
                //intent.setType("text/plain");
                startActivity(intent);
            }
        });

        insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                String url = "https://www.instagram.com/tmdb/";
                intent.setData(Uri.parse(url));
                //intent.setType("text/plain");
                startActivity(intent);
            }
        });

        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                String url = "https://www.facebook.com/themoviedb/";
                intent.setData(Uri.parse(url));
                //intent.setType("text/plain");
                startActivity(intent);
            }
        });


        fetchMovies();

        return view;
    }


    private void fetchMovies() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MoviesApi moviesApi = retrofit.create(MoviesApi.class);

        Call<MovieResponse> call = moviesApi.getPopularMovies();

        Call<MovieResponse> call2 = moviesApi.getTopRatedMovies();

        Call<MovieResponse> call3 = moviesApi.getUpcomingMovies();

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse movieResponse = response.body();

                ArrayList<Movies> movies1 = movieResponse.results;

                if(movies1!= null)
                {
                    movies.clear();
                    movies.addAll(movies1);
                    recyclerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

                Toast.makeText(getContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
            }
        });

        call2.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse movieResponse = response.body();

                ArrayList<Movies> movies2 = movieResponse.results;

                if(movies2!= null)
                {
                    moviest.clear();
                    moviest.addAll(movies2);
                    recyclerAdapter2.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

                //Toast.makeText(MainActivity.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
            }
        });

        call3.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {

                MovieResponse movieResponse = response.body();

                ArrayList<Movies> movies3 = movieResponse.results;

                if(movies3!= null)
                {
                    moviesu.clear();
                    moviesu.addAll(movies3);
                    recyclerAdapter3.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

                //Toast.makeText(MainActivity.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
            }
        });

    }



}
