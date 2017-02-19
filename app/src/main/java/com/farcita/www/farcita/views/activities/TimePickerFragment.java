package com.farcita.www.farcita.views.activities;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Button;
import android.widget.TimePicker;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{

    private Button setButton;
    private ActivityCommunicator activityCommunicator;

    public TimePickerFragment() {}

    public TimePickerFragment(Button btn) {
        setButton = btn;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activityCommunicator = (ActivityCommunicator) context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(),this, hour, minute, DateFormat.is24HourFormat(getContext()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute){
        int time;

        if(hourOfDay == 0) {
            time = (24 * 60 * 60) + (minute * 60);
        } else {
            time = (hourOfDay * 60 * 60) + (minute * 60);
        }
       Log.d("Farcita", time+"time");
        activityCommunicator.passDataToActivity(time, hourOfDay, minute);

        setButton.setTextColor(Color.WHITE);
        boolean isPM = (hourOfDay >= 12);
        setButton.setText(String.format("%02d:%02d %s", (hourOfDay == 12 || hourOfDay == 0) ? 12 : hourOfDay % 12, minute, isPM ?
                          "PM" : "AM"));
   }
}