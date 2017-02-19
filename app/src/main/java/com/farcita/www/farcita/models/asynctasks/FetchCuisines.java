package com.farcita.www.farcita.models.asynctasks;

import android.os.AsyncTask;

import com.farcita.www.farcita.AppUtilMethods;
import com.farcita.www.farcita.models.Cuisine;
import com.farcita.www.farcita.models.Dish;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaurav on 24/01/17.
 */

public class FetchCuisines extends AsyncTask<String, Void, ArrayList<Cuisine>> {


    private CuisineFetchInterface interfaceImplementer;

    public FetchCuisines(CuisineFetchInterface fetchInterface){
        interfaceImplementer = fetchInterface;
    }

    @Override
    protected ArrayList<Cuisine> doInBackground(String... params) {
        String url = params[0];
        String rest_id = params[1];
        url = url + "cuisines/" + rest_id;

        ArrayList<Cuisine> listOfCuisines = null;
        ArrayList<Dish> listOfDishes = null;
        JSONArray dishJSONArray ;

        try {
            URL finalUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) finalUrl.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String response = "";
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                response += line;
            }
            AppUtilMethods.errorLogger(response);
            listOfCuisines = new ArrayList<>();
            listOfDishes = new ArrayList<>();
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                listOfDishes = new ArrayList<>();
                dishJSONArray = jsonArray.getJSONObject(i).getJSONArray("dishArray");
                for(int j = 0;j<dishJSONArray.length();j++){
                    listOfDishes.add(new Dish(dishJSONArray.getJSONObject(j).getString("dish_name"),dishJSONArray.getJSONObject(j).getInt("rating"),dishJSONArray.getJSONObject(j).getInt("dish_id")));
                }
                listOfCuisines.add(new Cuisine(jsonArray.getJSONObject(i).getString("cuis_name"),jsonArray.getJSONObject(i).getInt("cuis_id"),listOfDishes));
            }

        } catch (Exception e) {
            e.printStackTrace();
            AppUtilMethods.errorLogger(e.getMessage());
        }
        return listOfCuisines;
    }

    @Override
    protected void onPostExecute(ArrayList<Cuisine> cuisines) {
        super.onPostExecute(cuisines);
        interfaceImplementer.listOfCuisinesFetched(cuisines);
    }

    public interface CuisineFetchInterface{
        void listOfCuisinesFetched(List<Cuisine> cuisines);
    }

}
