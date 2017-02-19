package com.farcita.www.farcita.views.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.farcita.www.farcita.AppUtilMethods;
import com.farcita.www.farcita.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends BaseActivity {

    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private ImageView mUsernameCancelImageView;
    private ImageView mPasswordCancelImageView;
    private Button mLoginButton;
    private TextView mCreateAccountTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindViewsToActivity();
        AppUtilMethods.setRestaurantIdInSharedPref(this,"2");
        checkIfLoggedIn();
    }

    private void bindViewsToActivity() {
        mUsernameEditText = (EditText) findViewById(R.id.login_username_et);
        mUsernameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().equals("")){
                    mUsernameCancelImageView.setVisibility(View.VISIBLE);
                }else if(s.toString().equals("")){
                    mUsernameCancelImageView.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mPasswordEditText = (EditText) findViewById(R.id.login_password_et);
        mPasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().equals("")){
                    mPasswordCancelImageView.setVisibility(View.VISIBLE);
                }else if(s.toString().equals("")){
                    mPasswordCancelImageView.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mUsernameCancelImageView = (ImageView) findViewById(R.id.login_username_cancel_iv);
        mUsernameCancelImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mUsernameCancelImageView.getVisibility() == View.VISIBLE){
                    mUsernameEditText.setText("");
                }
            }
        });
        mPasswordCancelImageView = (ImageView) findViewById(R.id.login_password_cancel_iv);
        mPasswordCancelImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mPasswordCancelImageView.getVisibility() == View.VISIBLE){
                    mPasswordEditText.setText("");
                }
            }
        });
        mLoginButton = (Button) findViewById(R.id.login_button);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginButtonPressed();
            }
        });
        mCreateAccountTextView = (TextView) findViewById(R.id.login_create_account_tv);

        mCreateAccountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccountTextClicked();
            }
        });

    }

    public static Intent getMyIntent(Context context){
        Intent i = new Intent(context,LoginActivity.class);
        return i;
    }

    private void loginButtonPressed() {
        if (isNetworkAvailable()) {
            if (!mUsernameEditText.getText().toString().equals("") && !mPasswordEditText.getText().toString().equals("")) {
                login();
            } else {
                Toast.makeText(this, "Username or Password left Blank!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Network not Available!", Toast.LENGTH_SHORT).show();
        }
    }

    private void login() {

        showProgress("Logging In");

        new LoginAsyncTask(new LoginAsyncInterface() {
            @Override
            public void restaurantIdReceived(String restaurantId) {
                dismissProgressDialog();
                AppUtilMethods.setRestaurantIdInSharedPref(LoginActivity.this,restaurantId);
                startActivity(MainActivity.getNewIntent(LoginActivity.this));
            }

            @Override
            public void wrongUsernameOrPassword() {
                dismissProgressDialog();
                AppUtilMethods.createShortToast(LoginActivity.this,"Username or Password is Incorrect");
                AppUtilMethods.errorLogger("Wrong Credentials");
            }

            @Override
            public void serverConnectionError() {
                dismissProgressDialog();
                AppUtilMethods.errorLogger("Server Error");
            }
        }).execute(mUsernameEditText.getText().toString(), mPasswordEditText.getText().toString());
    }

    private void createAccountTextClicked() {
        Intent i = new Intent(this,SignUpActivity.class);
        startActivity(i);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    private void checkIfLoggedIn(){
        if(AppUtilMethods.isRestaurantIdSavedInSharedPrefs(LoginActivity.this)){
            startActivity(MainActivity.getNewIntent(LoginActivity.this));
        }
    }


    private class LoginAsyncTask extends AsyncTask<String, Void, String> {

        public LoginAsyncInterface listener = null;

        public LoginAsyncTask(LoginAsyncInterface loginAsyncInterfaceImplementer){
            listener = loginAsyncInterfaceImplementer;
        }

        @Override
        protected String doInBackground(String... strings) {
            String username = strings[0];
            String password = strings[1];
            String urlString = getString(R.string.api_base_link) + "login?username=" + username + "&password=" + password;
            String apiResponse = "";
            String responseString = "";

            try {

                URL url = new URL(urlString);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String responseLine = "";
                while ((responseLine = bufferedReader.readLine()) != null) {
                    apiResponse = apiResponse + responseLine;
                }
                AppUtilMethods.errorLogger(apiResponse);
                JSONObject baseObject = new JSONObject(apiResponse);

                try {
                    String checkIfDatabaseErrorReceived = baseObject.getString("errno");
                    responseString = "Database Error";
                } catch (Exception e) {
                    try {
                        responseString = baseObject.getString("error");
                    } catch (Exception e1) {
                        try {
                            responseString = String.valueOf(baseObject.getInt("rest_id"));
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                }

            } catch (Exception e) {
                AppUtilMethods.errorLogger("Exception");
                e.printStackTrace();
            }
            return responseString;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            AppUtilMethods.errorLogger("Message : " + s);

            if(s.equals("Username or Password is Incorrect")){
                listener.wrongUsernameOrPassword();
            }else if(s.equals("Database Error") || s.equals("")){
                listener.serverConnectionError();
            }else{
                listener.restaurantIdReceived(s);
            }
        }
    }

    public interface LoginAsyncInterface{
        void restaurantIdReceived(String restaurantId);
        void wrongUsernameOrPassword();
        void serverConnectionError();
    }

}
