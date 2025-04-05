package my.edu.utar.mathapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class CompareNumActivity extends AppCompatActivity {
    private Button leftBtn;
    private Button rightBtn;
    private TextView instruction;
    private Toast currentToast;
    private boolean isPlayingGreater = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_num);

        leftBtn = findViewById(R.id.compare_left_num);
        rightBtn = findViewById(R.id.compare_right_num);
        instruction = findViewById(R.id.compare_instruction);

        reloadGame();
        initNumButtonListener(leftBtn, rightBtn);
        initNumButtonListener(rightBtn, leftBtn);
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
        int leftNum = random.nextInt(1000);
        int rightNum = random.nextInt(1000);

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

                if (isPlayingGreater) {
                    feedback = currentButtonVal > oppositeButtonVal ? correctFeedback : wrongFeedback;
                } else {
                    feedback = currentButtonVal < oppositeButtonVal ? correctFeedback : wrongFeedback;
                }
                showToast(feedback);
                reloadGame();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        });
    }

    private void showToast(String toastString) {
        if (currentToast != null) {
            currentToast.cancel();
        }
        currentToast = Toast.makeText(getApplicationContext(), toastString, Toast.LENGTH_SHORT);
        currentToast.show();
    }
}