package my.edu.utar.mathapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import my.edu.utar.mathapp.R;

public class ComposeNumActivity extends BaseActivity {
    private LinearLayout slotContainer;
    private LinearLayout cardContainer;
    private TextView targetNumCard;
    private Button submitButton;
    private int cardCount = 0;
    private int targetNum = 0;
    private final int COMBINATION_NUM = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_num);

        toolbar = findViewById(R.id.toolbar);
        setupToolbarWithBackButton();

        slotContainer = findViewById(R.id.compose_slots_container);
        cardContainer = findViewById(R.id.compose_cards_container);
        targetNumCard = findViewById(R.id.compose_target_num_card);
        submitButton = findViewById(R.id.compose_submit_btn);
        cardCount = cardContainer.getChildCount();

        reloadGame();
        setupLongClickListenerForCards();
        setupLongClickListenerForSlots();
        setupDragAndDropOnCardContainer();
        setupDragAndDropOnSlots();
        setupOnClickListenerForSubmitButton();
    }

    private void reloadGame() {
        initTargetNumber();
        initCardNumbers();
    }

    private void initTargetNumber() {
        Random random = new Random();
        targetNum = random.nextInt(1000);
        targetNumCard.setText(String.valueOf(targetNum));
    }

    private void initCardNumbers() {
        List<Integer> numbers = new ArrayList<>();
        Random random = new Random();

        // Generate combination that can make up target num
        int x = random.nextInt(targetNum);
        int y = targetNum - x;

        numbers.add(x);
        numbers.add(y);
        numbers.add(random.nextInt(1000));
        numbers.add(random.nextInt(1000));
        Collections.shuffle(numbers);

        for (int i = 0; i < cardCount; i++) {
            TextView card = (TextView) cardContainer.getChildAt(i);
            card.setText(String.valueOf(numbers.get(i)));
        }
    }

    private void setupLongClickListenerForCards() {
        for (int i = 0; i < cardCount; i++) {
            View card = cardContainer.getChildAt(i);

            card.setOnLongClickListener(view -> {
                        view.startDragAndDrop(
                                ClipData.newPlainText("", ""),
                                new View.DragShadowBuilder(view),
                                view,
                                0
                        );
                        return true;
                    }
            );
        }
    }

    private void setupLongClickListenerForSlots() {
        for (int i = 0; i < COMBINATION_NUM; i++) {
            FrameLayout slot = (FrameLayout) slotContainer.getChildAt(i);

            slot.setOnLongClickListener(view -> {
                if (slot.getChildCount() > 0) {
                    View existingCard = slot.getChildAt(0);
                    existingCard.startDragAndDrop(
                            ClipData.newPlainText("", ""),
                            new View.DragShadowBuilder(existingCard),
                            existingCard,
                            0
                    );
                    return true;
                }
                return false;
            });
        }
    }

    private void setupDragAndDropOnCardContainer() {
        cardContainer.setOnDragListener((v, event) -> {
            if (event.getAction() == DragEvent.ACTION_DROP) {
                View dragged = (View) event.getLocalState();
                ViewGroup originalParent = (ViewGroup) dragged.getParent();
                if (originalParent != null) {
                    originalParent.removeView(dragged);
                }
                cardContainer.addView(dragged);
                return true;
            }
            return true;
        });
    }

    private void setupDragAndDropOnSlots() {
        for (int i = 0; i < COMBINATION_NUM; i++) {
            FrameLayout slot = (FrameLayout) slotContainer.getChildAt(i);

            slot.setOnDragListener((v, event) -> {
                if (event.getAction() == DragEvent.ACTION_DROP) {
                    View draggedView = (View) event.getLocalState();
                    ViewGroup originalParent = (ViewGroup) draggedView.getParent();
                    FrameLayout targetSlot = (FrameLayout) v;

                    if (originalParent != null) {
                        originalParent.removeView(draggedView);
                    }
                    // If slot already has a card, move it back to card container
                    if (targetSlot.getChildCount() > 0) {
                        View existingCard = targetSlot.getChildAt(0);
                        targetSlot.removeView(existingCard);
                        cardContainer.addView(existingCard);
                    }
                    targetSlot.addView(draggedView);
                    return true;
                }
                return true;
            });
        }
    }

    private void setupOnClickListenerForSubmitButton() {
        submitButton.setOnClickListener(v -> {
            List<Integer> userSubmission = getSlotValues();
            if (userSubmission.size() != COMBINATION_NUM) {
                showToast(getString(R.string.compose_pick_two_nums));
            } else {
                String correctFeedback = getString(R.string.compose_correct_answer);
                String wrongFeedback = getString(R.string.compose_wrong_answer);
                String feedback = isCorrectAnswer(userSubmission) ? correctFeedback : wrongFeedback;

                showToast(feedback);
                clearSlots();
                reloadGame();
            }
        });
    }

    private boolean isCorrectAnswer(List<Integer> submission) {
        int sum = 0;
        for (int i = 0; i < COMBINATION_NUM; i++) {
            sum += submission.get(i);
        }
        return targetNum == sum;
    }

    private List<Integer> getSlotValues() {
        List<Integer> submittedValues = new ArrayList<>();

        for (int i = 0; i < COMBINATION_NUM; i++) {
            FrameLayout currentSlot = (FrameLayout) slotContainer.getChildAt(i);
            if (currentSlot.getChildCount() > 0) {
                View currentCard = currentSlot.getChildAt(0);
                if (currentCard instanceof TextView) {
                    try {
                        String currentCardValue = ((TextView) currentCard).getText().toString();
                        submittedValues.add(Integer.parseInt(currentCardValue));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return submittedValues;
    }

    private void clearSlots() {
        for (int i = 0; i < COMBINATION_NUM; i++) {
            FrameLayout slot = (FrameLayout) slotContainer.getChildAt(i);

            if (slot.getChildCount() > 0) {
                View card = slot.getChildAt(0);
                slot.removeView(card);
                cardContainer.addView(card);
            }
        }
    }
}