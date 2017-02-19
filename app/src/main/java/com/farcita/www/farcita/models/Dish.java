package com.farcita.www.farcita.models;

<<<<<<< HEAD
=======
import com.farcita.www.farcita.AppUtilMethods;

>>>>>>> 6e5c7368d5ee1a1fcecb6227ab15f7ca9098aac2
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
