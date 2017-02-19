package com.farcita.www.farcita.models;

<<<<<<< HEAD
=======
import com.farcita.www.farcita.AppUtilMethods;

>>>>>>> 6e5c7368d5ee1a1fcecb6227ab15f7ca9098aac2
import java.util.List;

/**
 * Created by gaurav on 23/01/17.
 */

public class Cuisine {
    private String name = "";
    private int id ;
    private List<Dish> dishList = null;

    public Cuisine(String name, int id, List<Dish> dishList) {
        this.name = name;
        this.id = id;
        this.dishList = dishList;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public List<Dish> getDishList() {
        return dishList;
    }
}
