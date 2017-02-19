package com.farcita.www.farcita.views.adapters;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.farcita.www.farcita.AppUtilMethods;
import com.farcita.www.farcita.R;
import com.farcita.www.farcita.models.Dish;

import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by gaurav on 22/01/17.
 */

public class DishRecyclerViewAdapter extends RecyclerView.Adapter<DishRecyclerViewAdapter.DishRecyclerViewViewHolder> {

    private List<Dish> listOfDishes = null;

    public DishRecyclerViewAdapter(List<Dish> data){
        listOfDishes = data;
    }

    @Override
    public DishRecyclerViewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dish_view_holder,parent,false);
        return new DishRecyclerViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DishRecyclerViewViewHolder holder, int position) {
        holder.setData(listOfDishes.get(position).getName(),String.valueOf(listOfDishes.get(position).getRating()));

    }

    @Override
    public int getItemCount() {
        return listOfDishes.size();
    }

    public class DishRecyclerViewViewHolder extends RecyclerView.ViewHolder{

        private TextView dishNameTextView;
        private TextView dishRatingTextView;
        private ImageView dishTickCircle;
        private Context context;

        public DishRecyclerViewViewHolder(View itemView) {
            super(itemView);
            dishNameTextView = (TextView) itemView.findViewById(R.id.dish_name);
            dishRatingTextView = (TextView) itemView.findViewById(R.id.dish_raing);
            dishTickCircle = (ImageView)itemView.findViewById(R.id.dish_tick_circle);
            dishTickCircle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    flipView((ImageView) v);
                }
            });
            context = itemView.getContext();
        }

        public void setData(String dishName,String dishRating){
            dishNameTextView.setText(dishName);
            dishRatingTextView.setText(dishRating);
        }

        private void flipView(ImageView imageView){
            ObjectAnimator animator = (ObjectAnimator) AnimatorInflater.loadAnimator(context,R.animator.flipping);
            animator.setTarget(imageView);
            animator.setDuration(500);
            animator.start();
            imageView.setImageResource(R.drawable.green_tick);
        }
    }
}
