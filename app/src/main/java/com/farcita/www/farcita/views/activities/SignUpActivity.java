package com.farcita.www.farcita.views.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.farcita.www.farcita.AppUtilMethods;
import com.farcita.www.farcita.R;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.hbb20.CountryCodePicker;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class SignUpActivity extends AppCompatActivity implements ActivityCommunicator{
    private static final int LOCATION_REQUEST=1340;
    private static final String[] LOCATION_PERMS={Manifest.permission.ACCESS_FINE_LOCATION};

    ArrayList<String> franchise_array = new ArrayList<String>();


    @InjectView(R.id.input_name) EditText nameText;
    @InjectView(R.id.input_manager) EditText nameTextManager;
    @InjectView(R.id.ccp) CountryCodePicker ccp;
    @InjectView(R.id.input_email) EditText emailText;
    @InjectView(R.id.input_website) EditText websiteText;
    @InjectView(R.id.input_password) EditText passwordText;
    @InjectView(R.id.retype_password) EditText retypePasswordText;
    @InjectView(R.id.btn_signup) Button signupButton;
    @InjectView(R.id.input_mobile) EditText mobileText;
    @InjectView(R.id.franchise_button) Button franchiseButton;
    @InjectView(R.id.link_login) TextView loginLink;
    @InjectView(R.id.input_time1)Button openingTime;
    @InjectView(R.id.input_time2) Button closingTime;
    @InjectView(R.id.lat) TextView lat;
    @InjectView(R.id.lng) TextView lng;
    @InjectView(R.id.openTime) TextView openTime;
    @InjectView(R.id.closeTime) TextView closeTime;
    int counter = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.inject(this);

        if (canAccessLocation()) {
            doLocationThing();
        } else {
            requestPermissions(LOCATION_PERMS, LOCATION_REQUEST);
        }

        new RetrieveFranchiseList().execute(getString(R.string.api_base_link) + "franchises");

        nameText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {

                }else {
                    String name = nameText.getText().toString().trim();
                    if (name.length() > 0 && name.length() < 3) {
                        nameText.setError("Enter a valid restaurant name");
                    } else {
                        nameText.setError(null);
                    }
                }
            }
        });

        nameTextManager.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {

                }else {
                    String nameManager = nameTextManager.getText().toString().trim();
                    if (nameManager.length() > 0 && nameManager.length() < 3) {
                        nameTextManager.setError("Enter valid manager name");
                    } else {
                        nameTextManager.setError(null);
                    }
                }
            }
        });

        emailText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){

                }else {
                    String email = emailText.getText().toString().trim();
                    if (email.isEmpty()) {
                        emailText.setError(null);
                    } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        emailText.setError("Enter a valid email address.");
                    }
                }
            }
        });

        websiteText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){

                }else {
                    String website = websiteText.getText().toString().trim();
                    if(website.isEmpty()) {
                        websiteText.setError(null);
                    } else if (!Patterns.WEB_URL.matcher(website).matches()) {
                        websiteText.setError("Enter a valid website.");
                    }
                }

            }
        });

        passwordText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){

                }else {
                    String password = passwordText.getText().toString();
                    if(password.length() == 0)
                        passwordText.setError(null);
                    if ((passwordText.length() > 0 && password.length() < 4) || password.length() > 10) {
                        passwordText.setError("Between 4 and 10 alpha-numeric characters");
                    } else {
                        passwordText.setError(null);
                    }
                }
            }
        });

        retypePasswordText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){

                }else {
                    String password = passwordText.getText().toString();
                    String retypePassword = retypePasswordText.getText().toString();
                    if (retypePassword.equals(password)) {
                        retypePasswordText.setError(null);
                    } else {
                        retypePasswordText.setError("Password did not match");
                    }
                }
            }
        });

        mobileText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){

                }else {
                    String countryCode = ccp.getSelectedCountryCodeWithPlus();
                    String mobile = mobileText.getText().toString().trim();
                    if (countryCode.length() > 0 && mobile.length() > 0) {
                        boolean status = validateUsing_libphonenumber(countryCode, mobile);
                        if (status) {
                        } else {
                            mobileText.setError("Enter valid phone number");
                        }
                        }else if(mobile.length() == 0){
                        mobileText.setError(null);
                    }

                }
            }
        });

        franchiseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                FranchiseListFragment fragObj = new FranchiseListFragment();
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("franchise_array", franchise_array);
                fragObj.setArguments(bundle);

                fragObj.show(fragmentManager,"Franchise");
            }
        });

        openingTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new TimePickerFragment(openingTime);
                newFragment.show(getFragmentManager(), "Select Time");
            }
        });

        closingTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new TimePickerFragment(closingTime);
                newFragment.show(getFragmentManager(), "Select Time");
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkConnectivity();

                String userMobileNumber = ccp.getSelectedCountryCodeWithPlus().toString() + mobileText.getText().toString().trim();
                new SignUpAsyncTask().execute(nameText.getText().toString(), nameTextManager.getText().toString(), userMobileNumber,
                        emailText.getText().toString(), passwordText.getText().toString(), openTime.getText().toString(),
                        closeTime.getText().toString(), franchiseButton.getText().toString(), lat.getText().toString(),
                        lng.getText().toString(),websiteText.getText().toString());
            }
        });

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void doLocationThing() {
        LocationFetcher locationFetcher = new LocationFetcher(SignUpActivity.this);
        Location networkLocation = locationFetcher.getLocation(LocationManager.NETWORK_PROVIDER);

        if (networkLocation != null) {
            double la = networkLocation.getLatitude();
            double lo = networkLocation.getLongitude();
            String getLat = Double.toString(la);
            String getLng = Double.toString(lo);
            lat.setText(getLat);
            lat.setVisibility(View.INVISIBLE);
            lng.setText(getLng);
            lng.setVisibility(View.INVISIBLE);

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("latitude", getLat);
            editor.putString("longitude", getLng);
            editor.apply();

            Toast.makeText(getApplicationContext(), "Mobile Location (NW): \nLatitude: " + getLat + "\nLongitude: " + getLng, Toast.LENGTH_LONG).show();
        } else {
            showSettingsAlert("NETWORK");
        }
    }

    public void showSettingsAlert(String provider) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(SignUpActivity.this);

        alertDialog.setTitle(provider + " SETTINGS");
        alertDialog.setMessage(provider + " is not enabled! Please enable it in the settings menu?");

        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        SignUpActivity.this.startActivity(intent);
                    }
                });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }

    private void checkConnectivity() {
        if (isNetworkAvailable()) {
            signup();
        } else {
            Toast.makeText(getApplicationContext(), "Network is not available", Toast.LENGTH_SHORT).show();
        }
    }

    public void signup() {
        if (!validate()) {
            onSignupFailed();
            return;
        } else {
            onSignupSuccess();
        }
        signupButton.setEnabled(true);
    }

    private boolean canAccessLocation() {
        return(hasPermission(Manifest.permission.ACCESS_FINE_LOCATION));
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    private boolean hasPermission(String perm) {
        return(PackageManager.PERMISSION_GRANTED==checkSelfPermission(perm));
    }


    public void onSignupSuccess() {
        Toast.makeText(getBaseContext(), "SignUp Successful", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "SignUp failed", Toast.LENGTH_LONG).show();
        signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = nameText.getText().toString().trim();
        String nameManager = nameTextManager.getText().toString().trim();
        String email = emailText.getText().toString().trim();
        String website = websiteText.getText().toString().trim();
        String password = passwordText.getText().toString();
        String retypePassword = retypePasswordText.getText().toString();
        String mobile = mobileText.getText().toString().trim();
        String countryCode = ccp.getSelectedCountryCodeWithPlus();
        String time = openingTime.getText().toString()+"-"+closingTime.getText().toString();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("restaurantName", name);
        editor.putString("time",time);
        editor.apply();

        if (name.isEmpty() || name.length() < 3) {
            nameText.setError("Enter a valid Restaurant name");
            valid = false;
        } else {
            nameText.setError(null);
        }

        if(nameManager.isEmpty()) {
            nameTextManager.setError(null);
        } else if(nameManager.length() > 0 && nameManager.length() < 3) {
            nameTextManager.setError("Enter  valid Manager name");
            valid = false;
        }

        if(openingTime.getText().toString().equals("Opening Time")) {
            Toast.makeText(getApplicationContext(),"Set restaurant opening time",Toast.LENGTH_SHORT).show();
            valid = false;
        } else {
            openingTime.setError(null);
        }

        if(closingTime.getText().toString().equals("Closing Time")) {
            Toast.makeText(getApplicationContext(),"Set restaurant closing time",Toast.LENGTH_SHORT).show();
            valid = false;
        } else {
            closingTime.setError(null);
        }

        if (email.isEmpty()) {
            emailText.setError(null);
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("Enter a valid email address.");
            valid = false;
        }

        if(website.isEmpty()) {
            websiteText.setError(null);
        } else if (!Patterns.WEB_URL.matcher(website).matches()) {
            websiteText.setError("Enter a valid website.");
            valid = false;
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordText.setError("Between 4 and 10 alpha-numeric characters");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        if (retypePassword.equals(password)) {
            retypePasswordText.setError(null);
        } else {
            retypePasswordText.setError("Password did not match");
            valid = false;
        }

        if (countryCode.length() > 0 && mobile.length() > 0) {
            boolean status = validateUsing_libphonenumber(countryCode, mobile);
            if (status) {
                Log.d("Farcita", "Valid mobile number");
            } else {
                Log.d("Farcita", "Invalid mobile number");
                valid = false;
            }
        } else {
            Toast.makeText(getApplicationContext(), "Country Code and Phone Number is required", Toast.LENGTH_SHORT).show();
        }
        return valid;
    }

    private boolean validateUsing_libphonenumber(String countryCode, String phNumber) {
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        String isoCode = phoneNumberUtil.getRegionCodeForCountryCode(Integer.parseInt(countryCode));
        Phonenumber.PhoneNumber phoneNumber = null;
        try {
            phoneNumber = phoneNumberUtil.parse(phNumber, isoCode);
        } catch (NumberParseException e) {
            System.err.println(e);
        }

        boolean isValid = phoneNumberUtil.isValidNumber(phoneNumber);
        if (isValid) {
            return true;
        } else {
            Toast.makeText(this, "Phone Number is Invalid " + phoneNumber, Toast.LENGTH_LONG).show();
            return false;
        }
    }

    @Override
    public void passDataToActivity(String s) {
        franchiseButton.setText(s);
        franchiseButton.setTextColor(Color.WHITE);
    }

    @Override
    public void passDataToActivity(int time, int hourOfDay, int minute) {
        String setTime = String.valueOf(time);
        if(counter == 0) {
            openTime.setText(setTime);
            openTime.setVisibility(View.INVISIBLE);
            counter++;
        } else {
            closeTime.setText(setTime);
            closeTime.setVisibility(View.INVISIBLE);
        }
    }

    private class RetrieveFranchiseList extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String urlName = strings[0];
            String franchiseList = "";

            try {
                URL url = new URL(urlName);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    franchiseList += line;
                }
                AppUtilMethods.errorLogger("Fran Data : " + franchiseList);
            } catch (Exception e) {
                AppUtilMethods.errorLogger(e.getMessage());
                e.printStackTrace();
            }
            return franchiseList;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            String response = s.toString();
            try {
                JSONArray new_array = new JSONArray(response);

                for (int i = 0, count = new_array.length(); i < count; i++) {
                    try {
                        JSONObject jsonObject = new_array.getJSONObject(i);
                        franchise_array.add(jsonObject.getString("name").toString());
                        Log.d("Farcita",franchise_array.get(i));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class SignUpAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String username = strings[0];
            String managerName = strings[1];
            String mobileNumber = strings[2];
            String emailText = strings[3];
            String passwordText = strings[4];
            String openTime = strings[5];
            String closeTime = strings[6];
            String franchiseName = strings[7];
            String latitude = strings[8];
            String longitude = strings[9];
            String website = strings[10];

//            String urlString = "http://172.31.3.6:3000/restaurants" + "signup?username=" + username + "&managerName=" + managerName + "&mobileNumber=" +
//                         mobileNumber + "&email=" + emailText + "&password=" + passwordText + "&openingTime=" + openTime +
//                         "&closingTime=" + closeTime + "&franchise=" + franchiseName + "&latitude=" + latitude + "&longitude=" +
//                          longitude + "&website=" + website;
            String apiResponse = "";

            try {
                URL url = new URL(getString(R.string.api_base_link) + "restaurants");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");

                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("rest_name", username)
                        .appendQueryParameter("manager_name", managerName)
                        .appendQueryParameter("phone", mobileNumber)
                        .appendQueryParameter("email", emailText)
                        .appendQueryParameter("password", passwordText)
                        .appendQueryParameter("opening_time", openTime)
                        .appendQueryParameter("closing_time", closeTime)
                        .appendQueryParameter("franchise", franchiseName)
                        .appendQueryParameter("lat", latitude)
                        .appendQueryParameter("long", longitude)
                        .appendQueryParameter("website", website);
                String query = builder.build().getEncodedQuery();
                OutputStream os = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();

//                int responseCode = connection.getResponseCode();
//                AppUtilMethods.errorLogger(responseCode+"gfdfgd");
//                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String responseLine;
                    while ((responseLine = bufferedReader.readLine()) != null) {
                        apiResponse += responseLine;
                    }
//                } else {
//                    apiResponse = "";
//                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return apiResponse;
        }

        @Override
        protected void onPostExecute(String s) {
            AppUtilMethods.errorLogger(s);
            super.onPostExecute(s);
            Toast.makeText(SignUpActivity.this, s, Toast.LENGTH_SHORT).show();
        }
    }
}

