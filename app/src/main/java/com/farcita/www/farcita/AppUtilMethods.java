package com.farcita.www.farcita;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by gaurav on 13/01/17.
 */

public class AppUtilMethods {

    public static void errorLogger(String logMessage){
        Log.e("Farcita",logMessage);
    }

    public static void createShortToast(Context context,String toastMessage){
        Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();
    }

    public static void setRestaurantIdInSharedPref(Context context,String restaurantId){
        SharedPreferences preferences = context.getSharedPreferences("Farcita",0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("restaurantId",restaurantId);
        editor.commit();
    }

    public static String getRestaurantIdFromSharedPref(Context context){
        SharedPreferences preferences = context.getSharedPreferences("Farcita",0);
        return preferences.getString("restaurantId","");
    }

    public static boolean isRestaurantIdSavedInSharedPrefs(Context context){
        SharedPreferences preferences = context.getSharedPreferences("Farcita",0);
        if(preferences.getString("restaurantId",null) == null)return false;
        else return true;
    }
}
