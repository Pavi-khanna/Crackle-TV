package com.example.pavikhanna.moviedb;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Pavi Khanna on 4/9/2018.
 */

public class CastResponse {

    @SerializedName("id")
    public int movieCastId;

    ArrayList<Movies.Cast> cast;

    //ArrayList<Movies> results;
}
