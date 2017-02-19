package com.farcita.www.farcita.views.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.farcita.www.farcita.R;

public class FragmentFour extends Fragment {

    private TextView time;

    public FragmentFour() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_four, container, false);

        time = (TextView) view.findViewById(R.id.time);

        SharedPreferences preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        String timeSet = preferences.getString("time",null);
        time.setText(timeSet);

        return view;
    }

}
