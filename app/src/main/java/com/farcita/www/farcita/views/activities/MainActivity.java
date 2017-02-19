package com.farcita.www.farcita.views.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.farcita.www.farcita.R;
import com.farcita.www.farcita.views.fragments.FeedbackFragment;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout mMessageRelativeLayout;
    private RelativeLayout mEditRelativeLayout;
    private RelativeLayout mBillRelativeLayout;
    private RelativeLayout mStatsRelativeLayout;
    private RelativeLayout mAccountRelativeLayout;

    private ImageView mMessageImageView;
    private ImageView mEditImageView;
    private ImageView mBillImageView;
    private ImageView mStatsImageView;
    private ImageView mAccountImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindAllViewsToActivity();
        bringUpTheMenuBar();
        attachTheFeedbackFragment();
    }

    private void bindAllViewsToActivity(){
        mMessageRelativeLayout = (RelativeLayout) findViewById(R.id.menubar_message);
        mMessageRelativeLayout.setOnClickListener(this);
        mEditRelativeLayout = (RelativeLayout) findViewById(R.id.menubar_edit);
        mEditRelativeLayout.setOnClickListener(this);
        mBillRelativeLayout = (RelativeLayout) findViewById(R.id.menubar_bill);
        mBillRelativeLayout.setOnClickListener(this);
        mStatsRelativeLayout = (RelativeLayout) findViewById(R.id.menubar_stats);
        mStatsRelativeLayout.setOnClickListener(this);
        mAccountRelativeLayout = (RelativeLayout) findViewById(R.id.menubar_account);
        mAccountRelativeLayout.setOnClickListener(this);

        mMessageImageView = (ImageView) findViewById(R.id.menubar_message_iv);
        mEditImageView = (ImageView) findViewById(R.id.menubar_edit_iv);
        mBillImageView = (ImageView) findViewById(R.id.menubar_bill_iv);
        mStatsImageView = (ImageView) findViewById(R.id.menubar_stats_iv);
        mAccountImageView = (ImageView) findViewById(R.id.menubar_account_iv);
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){

            case R.id.menubar_message:
                break;
            case R.id.menubar_edit:
                break;
            case R.id.menubar_bill:
                break;
            case R.id.menubar_stats:
                break;
            case R.id.menubar_account:
                break;
        }

    }

    private void deSelectAllIcons(){

        mMessageRelativeLayout.setBackgroundColor(getColor(R.color.colorPrimary));
        mEditRelativeLayout.setBackgroundColor(getColor(R.color.colorPrimary));
        mBillRelativeLayout.setBackgroundColor(getColor(R.color.colorPrimary));
        mStatsRelativeLayout.setBackgroundColor(getColor(R.color.colorPrimary));
        mAccountRelativeLayout.setBackgroundColor(getColor(R.color.colorPrimary));

        mMessageImageView.setImageResource(R.drawable.message_white);
        mEditImageView.setImageResource(R.drawable.edit_white);
        mBillImageView.setImageResource(R.drawable.bill_white);
        mStatsImageView.setImageResource(R.drawable.stats_white);
        mAccountImageView.setImageResource(R.drawable.account_white);
    }

    public static Intent getNewIntent(Context context){
        return new Intent(context,MainActivity.class);
    }

    private void attachTheFeedbackFragment(){
        FeedbackFragment fragment = FeedbackFragment.feedbackFragmentFactory();
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.mainactivity_fragemnt_container,fragment);
        transaction.commit();
    }

    private void bringUpTheMenuBar(){
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.mainactivity_menubar);
        linearLayout.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.grow_from_bottom);
        animation.setDuration(1000);
        linearLayout.setAnimation(animation);
        linearLayout.animate();
        animation.start();
    }
}
