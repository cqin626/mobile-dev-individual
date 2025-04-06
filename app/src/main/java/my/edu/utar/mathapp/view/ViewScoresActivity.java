package my.edu.utar.mathapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import my.edu.utar.mathapp.R;

public class ViewScoresActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_scores);

        toolbar = findViewById(R.id.toolbar);
        setupToolbarWithBackButton();
    }
}