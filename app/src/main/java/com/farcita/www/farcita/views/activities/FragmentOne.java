package com.farcita.www.farcita.views.activities;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.farcita.www.farcita.R;

import java.util.List;
import java.util.Locale;

public class FragmentOne extends Fragment {

    private TextView restaurant;
    private TextView address;

    public FragmentOne() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_one, container, false);
        view.setClipToOutline(true);

        SharedPreferences preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        String restaurantName = preferences.getString("restaurantName",null);
        restaurant = (TextView)view.findViewById(R.id.restaurant_name);
        restaurant.setText(restaurantName);

        String lat = preferences.getString("latitude",null);
        Double latitude = Double.parseDouble(lat);
        String lng = preferences.getString("longitude",null);
        Double longitude = Double.parseDouble(lng);
        address = (TextView) view.findViewById(R.id.address);

        Geocoder geocoder = new Geocoder(getContext(), Locale.ENGLISH);

        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);

            if(addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("Address:\n");
                for(int i=0; i<returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                address.setText(strReturnedAddress.toString());
                Log.d("Farcita",strReturnedAddress.toString());
            }
            else{
                address.setText("No Address returned!");
                Log.d("Farcita","No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            address.setText("Cannot get Address!");
            Log.d("Farcita","Cannot get Address!");
        }
        return view;
    }

}
