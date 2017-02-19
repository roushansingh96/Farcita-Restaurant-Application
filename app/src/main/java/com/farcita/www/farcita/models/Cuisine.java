package com.farcita.www.farcita.models;

import com.farcita.www.farcita.AppUtilMethods;

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
