 package com.example.justinharsono.week2labflashcards;

 import android.content.Intent;
 import android.os.Bundle;
 import android.support.annotation.Nullable;
 import android.support.v7.app.AppCompatActivity;
 import android.view.View;
 import android.widget.ImageView;
 import android.widget.TextView;

 import java.util.List;

 public class MainActivity extends AppCompatActivity {

    Intent addCard;
    TextView flashcardQuestion;
    TextView flashcardAnswer;
    ImageView addButton;
    ImageView nextButton;
    ImageView deleteButton;
    FlashcardDatabase flashcardDatabase;
    int ADD_CARD_REQUEST_CODE = 0;
    List<Flashcard> allFlashcards;
    int currentCardDisplayedIndex;
    String DEFAULT_QUESTION = "Who was the 44th president of the United States?";
    String DEFAULT_ANSWER = "Barack Obama";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addCard = new Intent(MainActivity.this, AddCardActivity.class );
        flashcardQuestion = findViewById(R.id.flashcard_question);
        flashcardAnswer = findViewById(R.id.flashcard_answer);
        addButton = findViewById(R.id.add);
        nextButton = findViewById(R.id.next_button);
        deleteButton = findViewById(R.id.delete);
        currentCardDisplayedIndex = 0;

        flashcardDatabase = new FlashcardDatabase(getApplicationContext());
        allFlashcards = flashcardDatabase.getAllCards();

        if (allFlashcards != null && allFlashcards.size() > 0) {
            flashcardQuestion.setText(allFlashcards.get(0).getQuestion());
            flashcardAnswer.setText(allFlashcards.get(0).getAnswer());
        } else {
            flashcardQuestion.setText(DEFAULT_QUESTION);
            flashcardAnswer.setText(DEFAULT_ANSWER);
        }

        flashcardQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashcardQuestion.setVisibility(View.INVISIBLE);
                flashcardAnswer.setVisibility(View.VISIBLE);
            }
        });

        flashcardAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashcardQuestion.setVisibility(View.VISIBLE);
                flashcardAnswer.setVisibility(View.INVISIBLE);
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(addCard, ADD_CARD_REQUEST_CODE);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (allFlashcards.size() > 0) {
                    // advance our pointer index so we can show the next card
                    currentCardDisplayedIndex++;

                    // make sure we don't get an IndexOutOfBoundsError if we are viewing the last indexed card in our list
                    if (currentCardDisplayedIndex > allFlashcards.size() - 1) {
                        currentCardDisplayedIndex = 0;
                    }

                    // set the question and answer TextViews with data from the database
                    flashcardQuestion.setText(allFlashcards.get(currentCardDisplayedIndex).getQuestion());
                    flashcardAnswer.setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
                    allFlashcards = flashcardDatabase.getAllCards();

                    flashcardQuestion.setVisibility(View.VISIBLE);
                    flashcardAnswer.setVisibility(View.INVISIBLE);
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (allFlashcards.size() > 0) {

                    flashcardDatabase.deleteCard(flashcardQuestion.getText().toString());
                    allFlashcards = flashcardDatabase.getAllCards();

                    currentCardDisplayedIndex--;

                    // make sure we don't get an IndexOutOfBoundsError if we are viewing the last indexed card in our list
                    if (currentCardDisplayedIndex < 0) {
                        currentCardDisplayedIndex = allFlashcards.size() - 1;

                    }

                    if (currentCardDisplayedIndex != -1) {
                        // set the question and answer TextViews with data from the database
                        flashcardQuestion.setText(allFlashcards.get(currentCardDisplayedIndex).getQuestion());
                        flashcardAnswer.setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
                    } else {
                        flashcardQuestion.setText("");
                        flashcardAnswer.setText("");
                    }
                }
            }
        });

    }

     @Override
     protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
         if (resultCode == RESULT_OK && requestCode == ADD_CARD_REQUEST_CODE) {
             String newQuestion = data.getExtras().getString("question");
             String newAnswer = data.getExtras().getString("answer");

             flashcardQuestion.setText(newQuestion);
             flashcardAnswer.setText(newAnswer);

             flashcardQuestion.setVisibility(View.VISIBLE);
             flashcardAnswer.setVisibility(View.INVISIBLE);

             flashcardDatabase.insertCard(new Flashcard(newQuestion, newAnswer));
             allFlashcards = flashcardDatabase.getAllCards();
             currentCardDisplayedIndex++;
         }
     }

 }
