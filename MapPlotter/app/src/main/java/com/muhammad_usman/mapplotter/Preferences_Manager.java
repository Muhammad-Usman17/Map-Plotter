package com.muhammad_usman.mapplotter; /**
 * Created by Muhammad_Usman on 9/8/2017.
 */


import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;


public class Preferences_Manager {
    SharedPreferences preferences;
    SharedPreferences.Editor spEditor;
    int count;


    private static Preferences_Manager instance = null;

    private Preferences_Manager() {
    }


    public static Preferences_Manager getInstance() {
        if (instance == null)
            instance = new Preferences_Manager();

        return instance;
    }



    public String Data_String(String name, Context context) {
        List<String> list = new ArrayList<>();
        preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        spEditor = preferences.edit();

        String s = preferences.getString("Value[0]", "");


        return s;
    }


    public void Add_String(String name, String value, Context context) {

        preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        spEditor = preferences.edit();
        count = preferences.getInt("count", 0);
        spEditor.putString("Value[" + count + "]", value);
        spEditor.putInt("count", count);
        spEditor.apply();

    }

    public int Get_Length(String name, Context context) {

        preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        count = preferences.getInt("count", 0);
        return count;

    }

    public void remove_Data(String name, String key, Context context) {

        preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        spEditor = preferences.edit();
        spEditor.remove(key);
        spEditor.apply();
    }



}



