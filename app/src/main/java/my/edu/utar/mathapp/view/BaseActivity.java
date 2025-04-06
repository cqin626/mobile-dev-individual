package my.edu.utar.mathapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.Toast;

import info.hoang8f.widget.FButton;
import my.edu.utar.mathapp.MyApplication;
import my.edu.utar.mathapp.R;
import my.edu.utar.mathapp.data.SharedPreferencesDataSource;

public class BaseActivity extends AppCompatActivity {
    protected Toast currentToast;
    protected Toolbar toolbar;
    protected SharedPreferencesDataSource dataSource;
    protected int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Content view is set by child activity
        super.onCreate(savedInstanceState);
        dataSource = ((MyApplication) getApplication()).getSharedPreferencesDataSource();
    }

    protected void showToast(String toastString) {
        if (currentToast != null) {
            currentToast.cancel();
        }
        currentToast = Toast.makeText(getApplicationContext(), toastString, Toast.LENGTH_SHORT);
        currentToast.show();
    }

    protected void setupToolbarWithBackButton() {
        toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);

            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayShowTitleEnabled(false);

                ImageButton backButton = findViewById(R.id.back_button);

                if (backButton != null) {
                    backButton.setOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getOnBackPressedDispatcher().onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void incrementScore(int by, String module) {
        int currentHighScore = dataSource.getInt(module, 0);

        score += by;
        if (score > currentHighScore) {
            dataSource.saveInt(module, score);
        }
    }

    protected void applyRetryButtonStyle(FButton btn) {
        final Typeface customFont = ResourcesCompat.getFont(this, R.font.noto_sans_semi_bold);

        btn.setTypeface(customFont);
        btn.setButtonColor(0xFFEE4B2B);
        btn.setShadowHeight(32);
        btn.setCornerRadius(32);
    }

    protected void applySubmitButtonStyle(FButton btn) {
        final Typeface customFont = ResourcesCompat.getFont(this, R.font.noto_sans_semi_bold);

        btn.setTypeface(customFont);
        btn.setButtonColor(0xFF2E8BC0);
        btn.setShadowHeight(32);
        btn.setCornerRadius(32);
    }
}