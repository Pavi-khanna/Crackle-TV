package com.example.pavikhanna.moviedb;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Pavi Khanna on 4/12/2018.
 */

public class TV {

    public String poster_path;

    @SerializedName("id")
    public int Tvid;

    public float popularity;

    @SerializedName("name")
    public String nameTV;

    public String overview;

    public float vote_average;

    Credits credits;

    Reviews reviews;

    Similar similar;

    public class Reviews{



    }

    public class Credits{
        ArrayList<Cast> cast;

    }

    public class Similar{
        ArrayList<TV> results;
    }

    public class Cast{

        String name;

       String profile_path;
    }


}
