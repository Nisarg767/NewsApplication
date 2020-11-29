package com.tvreport18.newsapp;



import android.app.Activity;

import android.content.Intent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;


public class themeUtils extends AppCompatActivity

{

    private static int cTheme;



    public final static int BLACK = 0;

    public final static int BLUE = 1;

    public static void changeToTheme(View.OnClickListener activity, int theme)

    {

        cTheme = theme;

       // activity.finish();



        //activity.startActivity(new Intent(activity, activity.getClass()));


    }

    public static void onActivityCreateSetTheme(Activity activity)

    {

        switch (cTheme)

        {

            default:

            case BLACK:

                activity.setTheme(R.style.BlackTheme);

                break;

            case BLUE:

                activity.setTheme(R.style.BlueTheme);

                break;

        }

    }

}