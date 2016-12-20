package com.scanlibrary;

import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.Locale;

public class ScanActivity extends AppCompatActivity {

    public static final String EXTRA_BRAND_IMG_RES = "title_img_res";
    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_LANGUAGE = "language";
    public static final String EXTRA_ACTION_BAR_COLOR = "ab_color";
    public static final String RESULT_IMAGE_PATH = ScanFragment.RESULT_IMAGE_PATH;

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        int titleImgRes = getIntent().getExtras().getInt(EXTRA_BRAND_IMG_RES);
        int abColor = getIntent().getExtras().getInt(EXTRA_ACTION_BAR_COLOR);
        String title = getIntent().getExtras().getString(EXTRA_TITLE);
        String locale = getIntent().getExtras().getString(EXTRA_LANGUAGE);

        if (locale != null) {
            Locale l = new Locale(locale);
            Locale.setDefault(l);
            Configuration config = new Configuration();
            config.locale = l;
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        }

        if (title != null) getSupportActionBar().setTitle(title);
        if (titleImgRes != 0) getSupportActionBar().setLogo(titleImgRes);

        if (abColor != 0) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(abColor)));
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }

        if (savedInstanceState == null) {
            Bundle args = new Bundle();
            if (getIntent().getExtras() != null) {
                args.putAll(getIntent().getExtras());
            }

            FragmentManager fragMan = getSupportFragmentManager();
            Fragment f = new ScanFragment();
            f.setArguments(args);
            FragmentTransaction fragTransaction = fragMan.beginTransaction();
            fragTransaction.replace(R.id.contaner, f, "scan_frag").commit();
        }
    }

    @Override
    public void onBackPressed() {
        ScanFragment scanFragment = (ScanFragment) getSupportFragmentManager().findFragmentByTag("scan_frag");
        if (scanFragment != null) {
            boolean exit = scanFragment.onBackPressed();
            if (exit) {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }

}