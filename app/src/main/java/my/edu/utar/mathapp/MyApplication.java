package my.edu.utar.mathapp;

import android.app.Application;

import my.edu.utar.mathapp.data.SharedPreferencesDataSource;

public class MyApplication extends Application {
    private SharedPreferencesDataSource sharedPreferencesDataSource;

    @Override
    public void onCreate() {
        super.onCreate();
        sharedPreferencesDataSource = new SharedPreferencesDataSource(this);
    }

    public SharedPreferencesDataSource getSharedPreferencesDataSource() {
        return sharedPreferencesDataSource;
    }
}
