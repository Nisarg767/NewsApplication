package com.tvreport18.newsapp;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.webkit.WebSettingsCompat;
import androidx.webkit.WebViewFeature;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.navigation.NavigationView;
import com.onesignal.OneSignal;

import java.security.KeyStore;

import es.dmoral.toasty.Toasty;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ProgressDialog progressBar;
    private WebView wv;
    private Button button;

    private static final String TAG = "Main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getWindow().getDecorView().setBackgroundColor(Color.RED);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        oneSignalInit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        wv = (WebView) findViewById(R.id.wv);
        wv.getSettings();
        wv.setBackgroundColor(0x00FFFFFF);


        WebSettings webSettings = wv.getSettings();
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setSupportZoom(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setBuiltInZoomControls(false);
        //webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setDomStorageEnabled(true);
        //webSettings.setSupportZoom(true);
        wv.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        wv.setScrollbarFadingEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setDomStorageEnabled(true);
        webSettings.setJavaScriptEnabled(true);



        wv.setWebViewClient(new WebViewClient());
        wv.loadUrl("http://www.tvreport18.com/wp-json/wp/v2/posts?fields=id,title,media");
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
       // progressBar = new ProgressDialog(MainActivity.this);
        //progressBar.setMessage("Please Wait...");
       // progressBar.setCancelable(false);
       // progressBar.setCanceledOnTouchOutside(false);
       // progressBar.show();

        final SwipeRefreshLayout finalMySwipeRefreshLayout1;
        finalMySwipeRefreshLayout1 = findViewById(R.id.swiperefresh);
        finalMySwipeRefreshLayout1.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                wv.loadUrl("https://www.tvreport18.com");
            }

        });

        wv.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                finalMySwipeRefreshLayout1.setRefreshing(false);
         /*       Log.i(TAG, "Done loading " + url);
                if (progressBar.isShowing()) {
                    progressBar.dismiss();
                }*/

            }
            public void onReceivedError(WebView wv, int i, String s, String d1)
            {
                Toasty.error(getApplicationContext(),"No Internet Connection!").show();
                wv.loadUrl("file:///android_asset/net_error.html");
            }

        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
                 super.onBackPressed();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id == R.id.menu_about_us)
        {
            Intent i = new Intent(this,AboutUs.class);
            startActivity(i);
            return true;
        }




        return super.onOptionsItemSelected(item);
    }




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id==R.id.darkt){
            if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
                // Включаем тёмный режим
                WebSettingsCompat.setForceDark(wv.getSettings(), WebSettingsCompat.FORCE_DARK_ON);
            }


        }else if(id==R.id.lightt){
            if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
                // Включаем светлый режим
                WebSettingsCompat.setForceDark(wv.getSettings(), WebSettingsCompat.FORCE_DARK_OFF);
            }
        }else
        if (id == R.id.Gujarat) {
            wv.loadUrl("https://tvreport18.com/category/gujarat/");
        } else if (id == R.id.India) {
            wv.loadUrl("https://tvreport18.com/category/india/");
        } else if (id == R.id.Crime) {
            wv.loadUrl("https://tvreport18.com/category/crime/");
        } else if (id == R.id.World){
            wv.loadUrl("https://tvreport18.com/category/international/");
        } else if (id == R.id.Health){
            wv.loadUrl("https://tvreport18.com/category/Health/");
        } else if (id == R.id.Business){
            wv.loadUrl("https://tvreport18.com/category/business/");
        } else if (id == R.id.Entertainment){
            wv.loadUrl("https://tvreport18.com/category/entertainment/");
        } else if (id == R.id.Sports){
            wv.loadUrl("https://tvreport18.com/category/sports/");
        } else if(id == R.id.Technology){
            wv.loadUrl("https://tvreport18.com/category/Technology/");
        } else if (id == R.id.LifeFashion){
            wv.loadUrl("https://tvreport18.com/category/lifestyle-fashion/");
        } else if (id == R.id.Home){
            wv.loadUrl("https://tvreport18.com");
        }else if(id == R.id.Twitter){
            wv.loadUrl("https://twitter.com/tvreport181");
        }else if(id == R.id.Facebook){
            wv.loadUrl("https://www.facebook.com/TVReport18/");
        }else if(id == R.id.Instagram){
            wv.loadUrl("https://www.instagram.com/tvreport18/");
        }else if(id == R.id.contactus){
            wv.loadUrl("https://tvreport18.com/contact-us/");
        }else if(id == R.id.privacy){
            wv.loadUrl("https://tvreport18.com/privacy-policy/");
        }else if(id == R.id.termsndcond){
            wv.loadUrl("https://tvreport18.com/terms-and-conditions/");
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    final void oneSignalInit() {
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && wv.canGoBack()) {
            wv.goBack();
            return true;
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }
}