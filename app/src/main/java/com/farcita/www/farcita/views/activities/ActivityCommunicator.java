package com.farcita.www.farcita.views.activities;

/**
 * Created by Roushan on 18-01-2017.
 */

public interface ActivityCommunicator {

    public void passDataToActivity(String s);
    public void passDataToActivity(int time, int hourOfDay, int minute);
}
