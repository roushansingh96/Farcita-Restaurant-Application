package com.farcita.www.farcita.views.activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.farcita.www.farcita.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

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
    }

    private void bindViewsToActivity() {
        mUsernameEditText = (EditText) findViewById(R.id.login_username_et);
        mPasswordEditText = (EditText) findViewById(R.id.login_password_et);
        mUsernameCancelImageView = (ImageView) findViewById(R.id.login_username_cancel_iv);
        mPasswordCancelImageView = (ImageView) findViewById(R.id.login_password_cancel_iv);
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

    private void loginButtonPressed(){
        if(isNetworkAvailable()){
            if(!mUsernameEditText.getText().toString().equals("") && !mPasswordEditText.getText().toString().equals("")){
                login();
            }
            else{
                Toast.makeText(this,"Username or Password left Blank!",Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this,"Network not Available!",Toast.LENGTH_SHORT).show();
        }
    }

    private void login(){
        new LoginAsyncTask().execute(mUsernameEditText.getText().toString(),mPasswordEditText.getText().toString());
    }

    private void createAccountTextClicked(){

    }

    private boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo!=null && networkInfo.isConnectedOrConnecting();
    }

    private class LoginAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String username = strings[0];
            String password = strings[1];
            String urlString = getString(R.string.api_base_link) + "login?username=" + username + "&password=" + password;
            String apiResponse = "";

            try {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String responseLine;
                while ((responseLine = bufferedReader.readLine()) != null) {
                    apiResponse += responseLine;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return apiResponse;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(LoginActivity.this, s, Toast.LENGTH_SHORT).show();
        }
    }

}
