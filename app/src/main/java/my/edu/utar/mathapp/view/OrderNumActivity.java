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
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import my.edu.utar.mathapp.R;

public class OrderNumActivity extends AppCompatActivity {

    private LinearLayout cardContainer;
    private LinearLayout slotContainer;
    private Button submitButton;
    private TextView instruction;
    private Toast currentToast;
    int cardCount = 0;
    boolean isPlayingAscending = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_num);

        cardContainer = findViewById(R.id.order_cards_container);
        slotContainer = findViewById(R.id.order_slots_container);
        submitButton = findViewById(R.id.order_submit_btn);
        instruction = findViewById(R.id.order_instruction);

        // The number of cards is equal to the number of slots.
        cardCount = cardContainer.getChildCount();

        reloadGame();
        setupLongClickListenerForCardsAndSlots();
        setupDragAndDropOnCardContainer();
        setupDragAndDropOnSlots();
        setupOnClickListenerForSubmitButton();
    }

    private void reloadGame() {
        initGameMode();
        updateCardsWithRandomNumber();
    }

    private void initGameMode() {
        Random random = new Random();

        isPlayingAscending = random.nextBoolean();
        instruction.setText(isPlayingAscending ? getString(R.string.order_arrange_in_ascending) : getString(R.string.order_arrange_in_descending));
    }

    private void updateCardsWithRandomNumber() {
        Random random = new Random();

        for (int i = 0; i < cardCount; i++) {
            TextView card = (TextView) cardContainer.getChildAt(i);
            card.setText(String.valueOf(random.nextInt(1000)));
        }
    }

    private void setupLongClickListenerForCardsAndSlots() {
        // Ensuring that cards in slot and card container are draggable

        for (int i = 0; i < cardCount; i++) {
            View card = cardContainer.getChildAt(i);
            FrameLayout slot = (FrameLayout) slotContainer.getChildAt(i);

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
        for (int i = 0; i < cardCount; i++) {
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
            if (userSubmission.size() != cardCount) {
                showToast(getString(R.string.order_arrange_all_numbers));
            } else {
                String correctFeedback = isPlayingAscending ? getString(R.string.order_ascending_correct_answer) : getString(R.string.order_descending_correct_answer);
                String wrongFeedback = isPlayingAscending ? getString(R.string.order_ascending_wrong_answer) : getString(R.string.order_descending_wrong_answer);
                String feedback = isCorrectAnswer(userSubmission) ? correctFeedback : wrongFeedback;

                showToast(feedback);
                clearSlots();
                reloadGame();
            }
        });
    }

    private boolean isCorrectAnswer(List<Integer> submission) {
        return isPlayingAscending ?
                IntStream
                        .range(0, submission.size() - 1)
                        .allMatch(i -> submission.get(i) <= submission.get(i + 1))
                :
                IntStream
                        .range(0, submission.size() - 1)
                        .allMatch(i -> submission.get(i) >= submission.get(i + 1));

    }

    private void clearSlots() {
        for (int i = 0; i < cardCount; i++) {
            FrameLayout slot = (FrameLayout) slotContainer.getChildAt(i);

            if (slot.getChildCount() > 0) {
                View card = slot.getChildAt(0);
                slot.removeView(card);
                cardContainer.addView(card);
            }
        }
    }

    private List<Integer> getSlotValues() {
        List<Integer> submittedValues = new ArrayList<>();

        for (int i = 0; i < cardCount; i++) {
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

    private void showToast(String toastString) {
        if (currentToast != null) {
            currentToast.cancel();
        }
        currentToast = Toast.makeText(getApplicationContext(), toastString, Toast.LENGTH_SHORT);
        currentToast.show();
    }
}
