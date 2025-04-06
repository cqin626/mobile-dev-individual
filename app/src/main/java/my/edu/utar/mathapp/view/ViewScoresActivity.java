package my.edu.utar.mathapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import info.hoang8f.widget.FButton;
import my.edu.utar.mathapp.MyApplication;
import my.edu.utar.mathapp.R;
import my.edu.utar.mathapp.data.SharedPreferencesDataSource;

public class ViewScoresActivity extends BaseActivity {

    private TextView compareNumVal;
    private TextView orderNumVal;
    private TextView composeNumVal;

    private SharedPreferencesDataSource dataSource;
    private FButton deleteScoreBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_scores);

        dataSource = ((MyApplication) getApplication()).getSharedPreferencesDataSource();

        toolbar = findViewById(R.id.toolbar);
        compareNumVal = findViewById(R.id.score_compare_num);
        orderNumVal = findViewById(R.id.score_order_num);
        composeNumVal = findViewById(R.id.score_compose_num);
        deleteScoreBtn = findViewById(R.id.score_delete_btn);

        setupToolbarWithBackButton();
        loadHighScores();
        initDeleteScoreBtn();
    }

    private void loadHighScores() {
        compareNumVal.setText(String.valueOf(dataSource.getInt("compare_num", 0)));
        orderNumVal.setText(String.valueOf(dataSource.getInt("order_num", 0)));
        composeNumVal.setText(String.valueOf(dataSource.getInt("compose_num", 0)));
    }

    private void initDeleteScoreBtn() {
        applyRetryButtonStyle(deleteScoreBtn);
        deleteScoreBtn.setOnClickListener(v -> {
            dataSource.saveInt("compare_num", 0);
            dataSource.saveInt("order_num", 0);
            dataSource.saveInt("compose_num", 0);
            loadHighScores();
        });
    }
}