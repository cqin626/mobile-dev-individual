package my.edu.utar.mathapp.data;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesDataSource {

    private static final String PREF_NAME = "app_preferences";
    private final SharedPreferences sharedPreferences;

    public SharedPreferencesDataSource(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveString(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }

    public void saveInt(String key, int value) {
        sharedPreferences.edit().putInt(key, value).apply();
    }

    public String getString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }


    public int getInt(String key, int defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }

    public void remove(String key) {
        sharedPreferences.edit().remove(key).apply();
    }

    public void clearAll() {
        sharedPreferences.edit().clear().apply();
    }

    public boolean contains(String key) {
        return sharedPreferences.contains(key);
    }
}
