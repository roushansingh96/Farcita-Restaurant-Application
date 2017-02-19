package com.farcita.www.farcita.views.adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.farcita.www.farcita.AppUtilMethods;
import com.farcita.www.farcita.R;
import com.farcita.www.farcita.models.Cuisine;
import com.farcita.www.farcita.models.Dish;

import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by gaurav on 22/01/17.
 */

public class CuisinseRecyclerViewAdapter extends RecyclerView.Adapter<CuisinseRecyclerViewAdapter.CuisineRecyclerViewViewHolder> {


    private List<Cuisine> listOfCuisine = null;

    public CuisinseRecyclerViewAdapter(List<Cuisine> data) {
        listOfCuisine = data;
    }

    @Override
    public CuisineRecyclerViewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cuisine_view_holder, parent, false);
        return new CuisineRecyclerViewViewHolder(view, parent.getContext());
    }

    @Override
    public void onBindViewHolder(CuisineRecyclerViewViewHolder holder, int position) {
        holder.setData(listOfCuisine.get(position));
    }

    @Override
    public int getItemCount() {
        return listOfCuisine.size();
    }


    public class CuisineRecyclerViewViewHolder extends RecyclerView.ViewHolder {

        private ImageView addDishImageView;
        private TextView cuisineNameTextView;
        private RecyclerView dishRecyclerView;

        private Cuisine dataForViewHolder;
        private Context context;

        public CuisineRecyclerViewViewHolder(View itemView, Context context) {
            super(itemView);
            addDishImageView = (ImageView) itemView.findViewById(R.id.add_dish_iv);
            cuisineNameTextView = (TextView) itemView.findViewById(R.id.cuisine_name);
            dishRecyclerView = (RecyclerView) itemView.findViewById(R.id.dish_recycler_view);
            this.context = context;
        }

        public void setData(Cuisine data) {
            dataForViewHolder = data;
            cuisineNameTextView.setText(dataForViewHolder.getName());
            initialiseDishRecyclerView();

        }

        private void initialiseDishRecyclerView() {
            dishRecyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
            dishRecyclerView.setAdapter(new DishRecyclerViewAdapter(dataForViewHolder.getDishList()));
        }
    }
}
