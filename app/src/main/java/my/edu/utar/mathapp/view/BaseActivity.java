package my.edu.utar.mathapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.Toast;

import my.edu.utar.mathapp.R;

public class BaseActivity extends AppCompatActivity {
    protected Toast currentToast;
    protected Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Content view is set by child activity
        super.onCreate(savedInstanceState);
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
}