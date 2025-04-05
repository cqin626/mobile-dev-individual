package my.edu.utar.mathapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Random;

public class CompareNumActivity extends AppCompatActivity {
    private Button leftBtn;
    private Button rightBtn;
    private ImageButton nextBtn;
    private TextView instruction;
    private TextView feedback;
    private boolean isPlayingGreater = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_num);

        leftBtn = findViewById(R.id.compare_left_num);
        rightBtn = findViewById(R.id.compare_right_num);
        nextBtn = findViewById(R.id.compare_next_button);
        feedback = findViewById(R.id.compare_feedback);
        instruction = findViewById(R.id.compare_instruction);

        reloadGame();
        initNumButtonListener(leftBtn, rightBtn);
        initNumButtonListener(rightBtn, leftBtn);
        initNextButtonListener();
    }

    private void reloadGame() {
        initGameMode();
        updateButtonsWithRandomNumbers();
    }

    private void initGameMode() {
        Random random = new Random();

        isPlayingGreater = random.nextBoolean();
        instruction.setText(isPlayingGreater ? getString(R.string.compare_tap_bigger_num) : getString(R.string.compare_tap_smaller_num));
    }

    private void updateButtonsWithRandomNumbers() {
        Random random = new Random();
        int leftNum = random.nextInt(1000) + 1;
        int rightNum = random.nextInt(1000) + 1;

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

                if (isPlayingGreater) {
                    feedback.setText(currentButtonVal > oppositeButtonVal ? correctFeedback : wrongFeedback);
                } else {
                    feedback.setText(currentButtonVal < oppositeButtonVal ? correctFeedback : wrongFeedback);
                }
                feedback.setVisibility((View.VISIBLE));

                if (nextBtn.getVisibility() == View.GONE) {
                    nextBtn.setVisibility(View.VISIBLE);
                }

                currentButton.setEnabled(false);
                oppositeButton.setEnabled(false);
                nextBtn.setEnabled(true);
            } catch (NumberFormatException e) {
                feedback.setText(getString(R.string.common_unexpected_error));
                feedback.setVisibility((View.VISIBLE));
                e.printStackTrace();
            }
        });
    }

    private void initNextButtonListener() {
        nextBtn.setOnClickListener(v -> {
            feedback.setText("");
            reloadGame();
            leftBtn.setEnabled(true);
            rightBtn.setEnabled(true);
            nextBtn.setEnabled(false);
        });
    }
}