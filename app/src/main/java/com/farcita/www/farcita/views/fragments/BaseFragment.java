package com.farcita.www.farcita.views.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.farcita.www.farcita.R;

/**
 * Created by gaurav on 04/02/17.
 */

public abstract class BaseFragment extends Fragment {

    protected ProgressDialog mProgressDialog;

    protected void showProgress(String message){
        if(mProgressDialog != null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();

        mProgressDialog = ProgressDialog.show(getContext(),getString(R.string.app_name),message);
    }

    protected void dismissProgress(){
        if(mProgressDialog != null)
            mProgressDialog.dismiss();

        mProgressDialog = null;
    }
}
