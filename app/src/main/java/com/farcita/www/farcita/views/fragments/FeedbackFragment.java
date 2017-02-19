package com.farcita.www.farcita.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.farcita.www.farcita.AppUtilMethods;
import com.farcita.www.farcita.R;
import com.farcita.www.farcita.models.Cuisine;
import com.farcita.www.farcita.models.asynctasks.FetchCuisines;
import com.farcita.www.farcita.views.adapters.CuisinseRecyclerViewAdapter;

import java.util.List;

/**
 * Created by gaurav on 04/02/17.
 */

public class FeedbackFragment extends BaseFragment {

    private RecyclerView mCuisineRecyclerView;
    private ImageView mErrorImageView;

    public static FeedbackFragment feedbackFragmentFactory() {
        return new FeedbackFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.feedback_fragment, container, false);
        bindAllViewsToFragment(view);
        getCuisinesFromBackEnd();
        return view;
    }

    private void bindAllViewsToFragment(View view) {
        mCuisineRecyclerView = (RecyclerView) view.findViewById(R.id.cuisine_recycler_view);
        mErrorImageView = (ImageView) view.findViewById(R.id.feedback_fragment_error_imageview);
    }

    private void initialiseRecyclerView(List<Cuisine> data) {
        mCuisineRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mCuisineRecyclerView.setAdapter(new CuisinseRecyclerViewAdapter(data));
    }

    private void getCuisinesFromBackEnd() {
        showProgress("Fetching Dishes");
        String restaurantId = AppUtilMethods.getRestaurantIdFromSharedPref(getContext());
        new FetchCuisines(new FetchCuisines.CuisineFetchInterface() {
            @Override
            public void listOfCuisinesFetched(List<Cuisine> cuisines) {
                dismissProgress();

                if (cuisines != null)
                    initialiseRecyclerView(cuisines);
                else
                    showErrorImage();
            }
        }).execute(getString(R.string.api_base_link), restaurantId);
    }

    private void showErrorImage() {
        mErrorImageView.setVisibility(View.VISIBLE);
        mCuisineRecyclerView.setVisibility(View.GONE);
    }
}