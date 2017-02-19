package com.farcita.www.farcita.views.activities;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.farcita.www.farcita.R;

public class FragmentThree extends Fragment {


    public FragmentThree() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_three, container, false);

        return view;
    }

}
