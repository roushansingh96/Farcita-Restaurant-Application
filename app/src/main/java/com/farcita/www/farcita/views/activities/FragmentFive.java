package com.farcita.www.farcita.views.activities;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.farcita.www.farcita.R;

public class FragmentFive extends Fragment {

    public FragmentFive() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_five, container, false);

        return view;
    }

}
