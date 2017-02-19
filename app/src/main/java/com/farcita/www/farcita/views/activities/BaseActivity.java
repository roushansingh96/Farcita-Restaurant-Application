package com.farcita.www.farcita.views.activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;

import com.farcita.www.farcita.R;


/**
 * Created by gaurav on 03/02/17.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected ProgressDialog mProgressDialog;

    protected void showProgress(String message){
        if(mProgressDialog != null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();

        mProgressDialog = ProgressDialog.show(this,getString((R.string.app_name)),message);
    }

    protected void dismissProgressDialog(){
        if(mProgressDialog != null)
            mProgressDialog.dismiss();

        mProgressDialog = null;
    }

}
