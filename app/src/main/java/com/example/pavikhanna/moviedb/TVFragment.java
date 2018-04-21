package com.example.pavikhanna.moviedb;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class TVFragment extends Fragment {



    MoviesRecyclerAdapter recyclerAdapter;
    MoviesRecyclerAdapter recyclerAdapter2;
    RecyclerView recyclerView;
    RecyclerView recyclerView2;
    ArrayList<Movies> popular;
    ArrayList<Movies> topRated;
    Intent intent;


    public TVFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tv, container, false);


        recyclerView = view.findViewById(R.id.tvPopular);
        recyclerView2 = view.findViewById(R.id.tvToprated);

        popular = new ArrayList<>();
        topRated = new ArrayList<>();

        recyclerAdapter = new MoviesRecyclerAdapter(popular, getContext(), new MoviesRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
//                intent = new Intent(getContext(),TVDescActivity.class);
//                intent.putExtra("tvid",popular.get(position).Id);
//                startActivity(intent);
            }
        });

        recyclerAdapter2 = new MoviesRecyclerAdapter(topRated, getContext(), new MoviesRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
//                intent = new Intent(getContext(),TVDescActivity.class);
//                intent.putExtra("tvid",popular.get(position).Id);
//                startActivity(intent);
            }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        //recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView2.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(recyclerAdapter);
        recyclerView2.setAdapter(recyclerAdapter2);


        fetchTV();

        return  view;
    }


    private void fetchTV() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MoviesApi moviesApi = retrofit.create(MoviesApi.class);

        Call<MovieResponse> call = moviesApi.getTV();

        Call<MovieResponse> call2 = moviesApi.getTVTop();

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse movieResponse = response.body();

                ArrayList<Movies> movies1 = movieResponse.results;

                if(movies1!= null)
                {
                    popular.clear();
                    popular.addAll(movies1);
                    recyclerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });

        call2.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse movieResponse = response.body();

                ArrayList<Movies> movies2 = movieResponse.results;

                if(movies2!= null)
                {
                    topRated.clear();
                    topRated.addAll(movies2);
                    recyclerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });

    }

}
