package my.edu.utar.mathapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.Typeface;
import android.os.Bundle;
import android.service.autofill.TextValueSanitizer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import info.hoang8f.widget.FButton;
import my.edu.utar.mathapp.R;

public class CompareNumActivity extends BaseActivity {
    private Button leftBtn;
    private Button rightBtn;
    private FButton retryBtn;
    private TextView instruction;
    private TextView scoreLabel;
    private boolean isPlayingGreater = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_num);

        toolbar = findViewById(R.id.toolbar);
        setupToolbarWithBackButton();

        leftBtn = findViewById(R.id.compare_left_num);
        rightBtn = findViewById(R.id.compare_right_num);
        retryBtn = findViewById(R.id.retry_btn);
        instruction = findViewById(R.id.compare_instruction);
        scoreLabel = findViewById(R.id.score_value);

        reloadGame();
        initNumButtonListener(leftBtn, rightBtn);
        initNumButtonListener(rightBtn, leftBtn);
        initRetryBtn();
    }

    private void reloadGame() {
        initGameMode();
        scoreLabel.setText(String.valueOf(score));
        updateButtonsWithRandomNumbers();
    }

    private void initGameMode() {
        Random random = new Random();

        isPlayingGreater = random.nextBoolean();
        instruction.setText(isPlayingGreater ? getString(R.string.compare_tap_bigger_num) : getString(R.string.compare_tap_smaller_num));
    }

    private void updateButtonsWithRandomNumbers() {
        Random random = new Random();
        boolean found = false;
        int leftNum = random.nextInt(1000);
        int rightNum = 0;

        while (!found) {
            rightNum = random.nextInt(1000);
            found = rightNum != leftNum;
        }

        leftBtn.setText(String.valueOf(leftNum));
        rightBtn.setText(String.valueOf(rightNum));
    }

    private void initNumButtonListener(Button currentButton, Button oppositeButton) {
        currentButton.setOnClickListener(v -> {
            String correctFeedback = isPlayingGreater ? getString(R.string.compare_num_bigger_correct_answer) : getString(R.string.compare_num_smaller_correct_answer);
            String wrongFeedback = isPlayingGreater ? getString(R.string.compare_num_bigger_wrong_answer) : getString(R.string.compare_num_smaller_wrong_answer);

            try {
                int currentButtonVal = Integer.parseInt(currentButton.getText().toString());
                int oppositeButtonVal = Integer.parseInt(oppositeButton.getText().toString());
                String feedback;
                boolean isCorrectAnswer = isCorrect(currentButtonVal, oppositeButtonVal);

                feedback = isCorrectAnswer ? correctFeedback : wrongFeedback;
                showToast(feedback);
                if (isCorrectAnswer) {
                    incrementScore(1, "compare_num");
                    reloadGame();
                } else {
                    leftBtn.setEnabled(false);
                    rightBtn.setEnabled(false);
                    retryBtn.setVisibility(View.VISIBLE);
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        });
    }

    private boolean isCorrect(int selectedNum, int oppositeNum) {
        return isPlayingGreater
                ? (selectedNum > oppositeNum)
                : (selectedNum < oppositeNum);
    }

    private void initRetryBtn() {
        applyRetryButtonStyle(retryBtn);
        setupOnClickListenerForRetryBtn();
    }

    private void setupOnClickListenerForRetryBtn() {
        retryBtn.setOnClickListener(v -> {
            score = 0;
            scoreLabel.setText(String.valueOf(score));
            leftBtn.setEnabled(true);
            rightBtn.setEnabled(true);
            retryBtn.setVisibility(View.GONE);
            reloadGame();
        });
    }
}