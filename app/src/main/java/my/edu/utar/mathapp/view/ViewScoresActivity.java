package my.edu.utar.mathapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import my.edu.utar.mathapp.MyApplication;
import my.edu.utar.mathapp.R;
import my.edu.utar.mathapp.data.SharedPreferencesDataSource;

public class ViewScoresActivity extends BaseActivity {

    private TextView compareNumVal;
    private TextView orderNumVal;
    private TextView composeNumVal;

    private SharedPreferencesDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_scores);

        dataSource = ((MyApplication) getApplication()).getSharedPreferencesDataSource();

        toolbar = findViewById(R.id.toolbar);
        compareNumVal = findViewById(R.id.score_compare_num);
        orderNumVal = findViewById(R.id.score_order_num);
        composeNumVal = findViewById(R.id.score_compose_num);

        setupToolbarWithBackButton();
        loadHighScores();
    }

    private void loadHighScores() {
        compareNumVal.setText(String.valueOf(dataSource.getInt("compare_num", 0)));
        orderNumVal.setText(String.valueOf(dataSource.getInt("order_num", 0)));
        composeNumVal.setText(String.valueOf(dataSource.getInt("compose_num", 0)));
    }
}