package com.example.pavikhanna.moviedb;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieDescActivity extends AppCompatActivity {

    Bundle bundle;
    RecyclerView recyclerCast;
    RecyclerView recyclerSimilar;
    TextView seeall;
    CastRecyclerAdapter castRecyclerAdapter;
    MoviesRecyclerAdapter similarRecyclerAdapter;
    ArrayList<Movies.Cast> casts;
    ArrayList<Movies> similars;
    ImageView youtube;
    ImageView black;
    ImageView down;
    ImageView up;
    TextView overview;
    boolean isExpanded=false;
    ProgressBar progressBar;
    NestedScrollView constraintLayout;
    //Movies.VideoClass

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_desc);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Movies");
        setSupportActionBar(toolbar);


        constraintLayout = findViewById(R.id.moviedescact);
        progressBar = findViewById(R.id.moviedescpb);
        casts = new ArrayList<>();
        similars = new ArrayList<>();
        youtube = findViewById(R.id.youtube);
        black = findViewById(R.id.black);
        overview = findViewById(R.id.overview);

        Intent intent = getIntent();
        bundle=intent.getExtras();
        final int id=bundle.getInt("movieid");
            fetchInfo(id);

        recyclerCast = findViewById(R.id.CastRecycler);

        recyclerSimilar = findViewById(R.id.similarRecycler);

        castRecyclerAdapter = new CastRecyclerAdapter(casts, this, new CastRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent castIntent = new Intent(MovieDescActivity.this,CastActivity.class);
                castIntent.putExtra("id",casts.get(position).castId);
                startActivity(castIntent);
            }
        });

        similarRecyclerAdapter = new MoviesRecyclerAdapter(similars, this, new MoviesRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent movieIntent = new Intent(MovieDescActivity.this,MovieDescActivity.class);
                movieIntent.putExtra("movieid",similars.get(position).Id);
                startActivity(movieIntent);
            }
        });


        recyclerSimilar.setAdapter(similarRecyclerAdapter);
        recyclerCast.setAdapter(castRecyclerAdapter);

        recyclerCast.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        //recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        recyclerCast.setItemAnimator(new DefaultItemAnimator());

        recyclerSimilar.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        //recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        recyclerSimilar.setItemAnimator(new DefaultItemAnimator());

        down = findViewById(R.id.down);
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//               if(down.getDrawable()==getResources().getDrawable(R.drawable.ic_keyboard_arrow_down_black_24dp))

                if(!isExpanded) {
                    overview.setMaxLines(20);
                    isExpanded = true;
                    down.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                }
                else if(isExpanded)
                {
                    overview.setMaxLines(6);
                    isExpanded=false;
                    down.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                }
            }
        });

//        if(down.getDrawable()==getResources().getDrawable(R.drawable.ic_keyboard_arrow_up_black_24dp))
//        {
//            down.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                }
//            });
//
//        }

//        up = findViewById(R.id.);
//        up.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                overview.setMaxLines(4);
//                isExpanded=false;
//                up.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
//            }
//        });

        seeall = findViewById(R.id.reviewSEEALL);
        seeall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentReviews = new Intent(MovieDescActivity.this,ReviewActivity.class);
                intentReviews.putExtra("idReviews",id);
                startActivity(intentReviews);
            }
        });
    }

    private void fetchInfo(final int id) {

        progressBar.setVisibility(View.VISIBLE);
        constraintLayout.setVisibility(View.GONE);

        Retrofit retrofitDetail = new Retrofit.Builder()
                .baseUrl("http://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MoviesApi moviesApiDetail = retrofitDetail.create(MoviesApi.class);

        Call<Movies> detailCall = moviesApiDetail.getMovieDetails(id);

        detailCall.enqueue(new Callback<Movies>() {
            @Override
            public void onResponse(Call<Movies> call, Response<Movies> response) {
                final Movies movie = response.body();

                youtube.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intentYoutube = new Intent(Intent.ACTION_VIEW, Uri.parse("https://m.youtube.com/watch?v="+movie.videos.results.get(0).key));
                        startActivity(intentYoutube);
                    }
                });

                Picasso.get().load("http://img.youtube.com/vi/"+movie.videos.results.get(0).key+"/mqdefault.jpg").resize(2600,1400).into(black);

                //videokey = movie.videos.results.get(0).key;


                Holder holder = new Holder();
                holder.overview.setText(movie.overview);

                Picasso.get().load("http://image.tmdb.org/t/p/w185/"+movie.posterPath).resize(260,400).into(holder.poster);

                holder.Runtime.setText(movie.runtime+""+" minutes");

                holder.Tagline.setText(movie.tagline);

                if(movie.reviews.results.size()!=0) {
                    String reviewText = "Author: " + movie.reviews.results.get(0).author + "\n\nContent: " +
                            movie.reviews.results.get(0).content;
                    holder.reviews.setText(reviewText);
                }
                else
                {
                    seeall.setVisibility(View.INVISIBLE);
                    holder.reviews.setText("No Reviews Yet.");
                }

            }

            @Override
            public void onFailure(Call<Movies> call, Throwable t) {

            }
        });

        Call<CastResponse> castCall = moviesApiDetail.getCast(id);

        castCall.enqueue(new Callback<CastResponse>() {
            @Override
            public void onResponse(Call<CastResponse> call, Response<CastResponse> response) {
                CastResponse castResponse = response.body();


                    ArrayList<Movies.Cast> casts1 = castResponse.cast;

                    if (casts1 != null) {
                        casts.clear();
                        casts.addAll(casts1);
                        castRecyclerAdapter.notifyDataSetChanged();
                    }

            }

            @Override
            public void onFailure(Call<CastResponse> call, Throwable t) {
                Toast.makeText(MovieDescActivity.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
            }
        });

        Call<Movies> similarCall = moviesApiDetail.getMovieDetails(id);

        similarCall.enqueue(new Callback<Movies>() {
            @Override
            public void onResponse(Call<Movies> call, Response<Movies> response) {
                Movies similarResponse = response.body();
                ArrayList<Movies> similars1 = similarResponse.similar.results;


                if(similars1!=null)
                {
                    similars.clear();
                    similars.addAll(similars1);
                    similarRecyclerAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<Movies> call, Throwable t) {
                Toast.makeText(MovieDescActivity.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
            }
        });

        progressBar.setVisibility(View.GONE);
        constraintLayout.setVisibility(View.VISIBLE);

    }

    class Holder{
        ImageView poster;
        TextView overview;
        TextView Runtime;
        TextView reviews;
        TextView Tagline;

        Holder()
        {
            Tagline = findViewById(R.id.tagline);
            poster = findViewById(R.id.overviewPoster);
            overview = findViewById(R.id.overview);
            Runtime = findViewById(R.id.runtime);
            reviews = findViewById(R.id.reviews);
        }
    }

}
