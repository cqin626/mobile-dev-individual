package my.edu.utar.mathapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.LocaleList;
import android.view.View;
import android.widget.Button;

import java.util.Locale;

import info.hoang8f.widget.FButton;
import my.edu.utar.mathapp.MyApplication;
import my.edu.utar.mathapp.R;
import my.edu.utar.mathapp.data.SharedPreferencesDataSource;

public class MainActivity extends AppCompatActivity {

    private FButton compareNumBtn;
    private FButton orderNumBtn;
    private FButton composeNumBtn;
    private FButton viewScoresBtn;
    private FButton toggleLocaleBtn;
    private SharedPreferencesDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);

        dataSource = ((MyApplication) getApplication()).getSharedPreferencesDataSource();

        if (savedInstanceState == null) {
            applySavedLocale();
        }

        setContentView(R.layout.activity_main);

        compareNumBtn = findViewById(R.id.compare_num_btn);
        orderNumBtn = findViewById(R.id.order_num_btn);
        composeNumBtn = findViewById(R.id.compose_num_btn);
        toggleLocaleBtn = findViewById(R.id.button_toggle_locale);
        viewScoresBtn = findViewById(R.id.view_scores_btn);

        applySavedLocale();

        setupNavigationButtons();
        styleMenuButtons();
        setupLocaleToggle();
    }

    private void setupNavigationButtons() {
        compareNumBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CompareNumActivity.class)));
        orderNumBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, OrderNumActivity.class)));
        composeNumBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ComposeNumActivity.class)));
        viewScoresBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ViewScoresActivity.class)));
    }

    private void styleMenuButtons() {
        final Typeface customFont = ResourcesCompat.getFont(this, R.font.noto_sans_semi_bold);
        FButton[] navButtons = new FButton[]{compareNumBtn, orderNumBtn, composeNumBtn, toggleLocaleBtn, viewScoresBtn};
        for (FButton currentNavButton : navButtons) {
            if (currentNavButton == toggleLocaleBtn || currentNavButton == viewScoresBtn) {
                currentNavButton.setButtonColor(0xFFECECEC);
            } else {
                currentNavButton.setButtonColor(0xFFFFFFFF);
            }
            currentNavButton.setTypeface(customFont);
            currentNavButton.setShadowHeight(32);
            currentNavButton.setCornerRadius(32);
        }
    }

    private void setupLocaleToggle() {
        toggleLocaleBtn.setOnClickListener(this::toggleLocale);
    }

    private void applySavedLocale() {
        String localeCode = dataSource.getString("locale", Locale.ENGLISH.getLanguage());
        Locale locale = new Locale(localeCode);

        updateLocale(locale);
    }

    private void updateLocale(Locale locale) {
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();

        // Update the configuration for resources
        configuration.setLocales(new LocaleList(locale));
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());

        // Save locale in shared preference
        dataSource.saveString("locale", locale.getLanguage());
    }

    private void toggleLocale(View view) {
        Locale currentLocale = getResources().getConfiguration().getLocales().get(0);

        Locale newLocale = currentLocale.getLanguage().equals(Locale.ENGLISH.getLanguage())
                ? Locale.SIMPLIFIED_CHINESE
                : Locale.ENGLISH;

        updateLocale(newLocale);
        recreate();
    }
}