package com.diakonia.diakonapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.DisplayMetrics;
import android.util.Log;

public abstract class DiakoUtils {


    public static String readSharedSetting(Context ctx, String settingName, String defaultValue) {
        SharedPreferences sharedPref = ctx.getSharedPreferences(ctx.getResources().getString(R.string.shared_pref_file_name), Context.MODE_PRIVATE);
        return sharedPref.getString(settingName, defaultValue);
    }

    public static void saveSharedSetting(Context ctx, String settingName, String settingValue) {
        SharedPreferences sharedPref = ctx.getSharedPreferences(ctx.getResources().getString(R.string.shared_pref_file_name), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(settingName, settingValue);
        editor.apply();
    }

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        Log.d("COLUMNAAAA", "displaymetrics: "+ displayMetrics);
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        Log.d("COLUMNAAAA", "dpWidth: "+ dpWidth);
        int noOfColumns = (int) (dpWidth / 360);
        Log.d("COLUMNAAAA", "calculateNoOfColumns: "+ noOfColumns);

        return noOfColumns;
    }

}
