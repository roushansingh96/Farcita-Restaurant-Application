package com.farcita.www.farcita.views.activities;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.farcita.www.farcita.R;

public class ReviewFragment extends Fragment {

    public ReviewFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_review, container, false);

        FragmentOne one = new FragmentOne();
        FragmentTwo two = new FragmentTwo();
        FragmentThree three = new FragmentThree();
        FragmentFour four = new FragmentFour();
        FragmentFive five = new FragmentFive();

        getFragmentManager().beginTransaction().add(R.id.fragment_one, one)
                                               .add(R.id.fragment_two, two)
                                               .add(R.id.fragment_three, three)
                                               .add(R.id.fragment_four, four)
                                               .add(R.id.fragment_five, five)
                                               .commit();

        return view;
    }
}
