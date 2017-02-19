package com.farcita.www.farcita.views.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.farcita.www.farcita.AppUtilMethods;
import com.farcita.www.farcita.R;
import com.farcita.www.farcita.models.Cuisine;
import com.farcita.www.farcita.models.asynctasks.FetchCuisines;
import com.farcita.www.farcita.views.adapters.CuisinseRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class CuisineDishActivity extends BaseActivity {

    private RecyclerView mCuisineRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuisine_dish);

        bindAllViewsToActivity();
        getCuisinesFromBackEnd();
    }

    void bindAllViewsToActivity(){
        mCuisineRecyclerView = (RecyclerView) findViewById(R.id.cuisine_recycler_view);
    }

    public static Intent getMyIntent(Context context){
        Intent i = new Intent(context,CuisineDishActivity.class);
        return i;
    }

    private void initialiseRecyclerView(List<Cuisine> data){
        mCuisineRecyclerView.setLayoutManager(new LinearLayoutManager(CuisineDishActivity.this));
        mCuisineRecyclerView.setAdapter(new CuisinseRecyclerViewAdapter(data));
    }

    private void getCuisinesFromBackEnd(){
        String restaurantId = AppUtilMethods.getRestaurantIdFromSharedPref(CuisineDishActivity.this);
        new FetchCuisines(new FetchCuisines.CuisineFetchInterface() {
            @Override
            public void listOfCuisinesFetched(List<Cuisine> cuisines) {
                if(cuisines != null)
                initialiseRecyclerView(cuisines);
                else
                    AppUtilMethods.createShortToast(CuisineDishActivity.this,"Server Error !");
            }
        }).execute(getString(R.string.api_base_link),restaurantId);
    }

}
