package com.llchyan.retrofitdemo.model;

import android.graphics.Movie;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by LinLin on 2015/12/17.
 */
public class BoxOfficeMovieResponse
{
    @SerializedName("movies")
    List<Movie> movieList;

    public List<Movie> getMovieList()
    {
        return movieList;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder("----");
        for (Movie movie:movieList)
        {
            builder.append("width==").append(movie.width())
                    .append(",height==").append(movie.height())
                    .append(",duration==").append(movie.duration());
        }
        builder.append("----");
        return "BoxOfficeMovieResponse{" +
                "movieList=" + builder.toString() +
                '}';
    }
}
