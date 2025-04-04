package my.edu.utar.mathapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.LocaleList;
import android.view.View;
import android.widget.Button;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up listener for locale toggle
        Button toggleLocaleBtn = findViewById(R.id.button_toggle_locale);
        toggleLocaleBtn.setOnClickListener(this::toggleLocale);

        // Setup listeners for module navigation
        Button compareNumBtn = findViewById(R.id.compare_num_btn);
        Button orderNumBtn = findViewById(R.id.order_num_btn);
        Button composeNumBtn = findViewById(R.id.compose_num_btn);

        compareNumBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CompareNumActivity.class)));
        orderNumBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, OrderNumActivity.class)));
        composeNumBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ComposeNumActivity.class)));

    }

    private void toggleLocale(View view) {
        Configuration configuration = new Configuration();
        Locale currentLocale = getResources().getConfiguration().getLocales().get(0);

        Locale newLocale = currentLocale.getLanguage().equals(Locale.ENGLISH.getLanguage())
                ? Locale.SIMPLIFIED_CHINESE
                : Locale.ENGLISH;

        Locale.setDefault(newLocale);
        configuration.setLocales(new LocaleList(newLocale));
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());

        recreate();
    }

}