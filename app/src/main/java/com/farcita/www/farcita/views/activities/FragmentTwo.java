package com.farcita.www.farcita.views.activities;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.farcita.www.farcita.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FragmentTwo extends Fragment {

    private ProgressBar progressBar;

    public FragmentTwo() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

       View view = inflater.inflate(R.layout.fragment_two, container, false);

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        new RetrieveRating().execute(getString(R.string.api_base_link) + "rating");

        return view;
    }

    class RetrieveRating extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String urlName = strings[0];
            String rating = "";
            try {
                URL url = new URL(urlName);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    rating += line;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.d("Farcita",rating);
            return rating;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            int progress = Integer.parseInt(s);
            progressBar.setProgress(progress*10);
        }
    }

}
