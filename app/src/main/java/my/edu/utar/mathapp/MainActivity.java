package my.edu.utar.mathapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.LocaleList;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import java.util.Locale;

import info.hoang8f.widget.FButton;

public class MainActivity extends AppCompatActivity {

    private FButton compareNumBtn;
    private FButton orderNumBtn;
    private FButton composeNumBtn;
    private Button toggleLocaleBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        compareNumBtn = findViewById(R.id.compare_num_btn);
        orderNumBtn = findViewById(R.id.order_num_btn);
        composeNumBtn = findViewById(R.id.compose_num_btn);
        toggleLocaleBtn = findViewById(R.id.button_toggle_locale);

        setupNavigationButtons();
        styleNavigationButtons();
        setupLocaleToggle();
    }

    private void setupNavigationButtons() {
        compareNumBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CompareNumActivity.class)));
        orderNumBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, OrderNumActivity.class)));
        composeNumBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ComposeNumActivity.class)));
    }

    private void setupLocaleToggle() {
        toggleLocaleBtn.setOnClickListener(this::toggleLocale);
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

    private void styleNavigationButtons() {
        final Typeface customFont = ResourcesCompat.getFont(this, R.font.noto_sans_semi_bold);
        FButton[] navButtons = new FButton[]{compareNumBtn, orderNumBtn, composeNumBtn};
        for (FButton currentNavButton : navButtons) {
            currentNavButton.setTypeface(customFont);
            currentNavButton.setButtonColor(0xFFFFFFFF);
            currentNavButton.setShadowHeight(32);
            currentNavButton.setCornerRadius(32);
        }
    }
}