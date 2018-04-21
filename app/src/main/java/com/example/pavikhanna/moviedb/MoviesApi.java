package com.example.pavikhanna.moviedb;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Pavi Khanna on 3/24/2018.
 */

public interface MoviesApi {

    //http://api.themoviedb.org/3/movie/popular?api_key=61aea3cd039309b83d784fd7b1d71fd2
    //http://api.themoviedb.org/3/movie/popular?api_key=61aea3cd039309b83d784fd7b1d71fd2
    //http://image.tmdb.org/t/p/SIZE/IMAGE_URL_FROM_JSON_OBJECT
    //http://api.themoviedb.org/3/movie/MOVIE_ID/videos?api_key=61aea3cd039309b83d784fd7b1d71fd2
    //http://api.themoviedb.org/3/movie/MOVIE_ID/reviews?api_key=61aea3cd039309b83d784fd7b1d71fd2


    @GET("movie/popular?api_key=61aea3cd039309b83d784fd7b1d71fd2")
    Call<MovieResponse> getPopularMovies();

    @GET("movie/top_rated?api_key=61aea3cd039309b83d784fd7b1d71fd2")
    Call<MovieResponse> getTopRatedMovies();

    @GET("movie/upcoming?api_key=61aea3cd039309b83d784fd7b1d71fd2")
    Call<MovieResponse> getUpcomingMovies();

//    @GET("movie/{movie_id}/videos?api_key=61aea3cd039309b83d784fd7b1d71fd2")
//    Call<Movies> getTrailer(@Path("movie_id")int id);

//    @GET("movie/{movie_id}/reviews?api_key=61aea3cd039309b83d784fd7b1d71fd2")
//    Call<Movies> getReviews(@Path("movie_id")int id);

    //@GET("movie/{movie_id}?api_key=61aea3cd039309b83d784fd7b1d71fd2&append_to_response=videos,credits,images,similar,reviews")

   @GET("movie/{movie_id}/credits?api_key=61aea3cd039309b83d784fd7b1d71fd2")
    Call<CastResponse> getCast(@Path("movie_id")int id);

   @GET("movie/{movie_id}?api_key=61aea3cd039309b83d784fd7b1d71fd2&append_to_response=credits,videos,images,similar,reviews")
   Call<Movies> getMovieDetails(@Path("movie_id")int id);


   @GET("person/{person_id}?api_key=61aea3cd039309b83d784fd7b1d71fd2")
   Call<Movies.People> getPersonDetails(@Path("person_id")int id);

   @GET("https://api.themoviedb.org/3/person/{person_id}/movie_credits?api_key=61aea3cd039309b83d784fd7b1d71fd2")
   Call<MovieResponse> getPersonMovies(@Path("person_id")int id);

   @GET("tv/popular?api_key=61aea3cd039309b83d784fd7b1d71fd2")
   Call<MovieResponse> getTV();

   @GET("tv/top_rated?api_key=61aea3cd039309b83d784fd7b1d71fd2")
   Call<MovieResponse> getTVTop();

   @GET("person/popular?api_key=61aea3cd039309b83d784fd7b1d71fd2&append_to_response=credits,videos,images,similar,reviews")
   Call<MovieResponse> getPopularPeople();

   @GET("https://api.themoviedb.org/3/tv/{tv_id}?api_key=61aea3cd039309b83d784fd7b1d71fd2&append_to_response=credits,videos,images,similar,reviews,cast")
   Call<Movies> getTVDetails(@Path("tv_id")int id);

}
