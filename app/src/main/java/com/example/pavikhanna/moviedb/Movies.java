package com.example.pavikhanna.moviedb;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Pavi Khanna on 3/24/2018.
 */

public class Movies {

    public boolean adult;

    @SerializedName("id")
    public int Id;

    @SerializedName("name")
    public String nameTV;

    public String overview;

    @SerializedName("poster_path")
    public String posterPath;

    public String profile_path;

    @SerializedName("vote_average")
    public float voteAverage;

    public String title;

    public float popularity;

    @SerializedName("release_date")
    public String releaseDate;

    public int runtime;

    public String tagline;

    Similar similar;

    Videos videos;

    Reviews reviews;

    Cast cast;


    public class Cast {

        @SerializedName("id")
        public int castId;

        @SerializedName("name")
        public String characterName;

        @SerializedName("profile_path")
        public String profilePath;

        public String character;
    }

    public class Videos{

        ArrayList<VideoClass> results;

    }

    public class MovieCredits{
        ArrayList<Movies> cast;

        @SerializedName("id")
        int personId;
    }


    public class VideoClass{

        @SerializedName("id")
        String videoId;
        String key;
        String type;
    }

    public class Similar{

        ArrayList<Movies> results;

    }

    public class Reviews{

        ArrayList<ReviewClass> results;

    }

    public class ReviewClass{

        String author;
        String content;

    }

    public class People{
        String birthday;
        int id;
        String name;
        String biography;
        String place_of_birth;
        String profile_path;
        String imdb_id;
        float popularity;
    }


    public class Credits{

        ArrayList<Cast> cast;

    }

}
