package com.farcita.www.farcita.models;

/**
 * Created by gaurav on 23/01/17.
 */

public class Dish {

    private String name = "";
    private int rating = 0;
    private int id ;

    public Dish(String dishName,int dishRating,int dishId){
        name = dishName;
        rating = dishRating;
        id = dishId;
    }

    public String getName() {
        return name;
    }

    public int getRating() {
        return rating;
    }

    public int getId() {
        return id;
    }
}
