package com.example.justinharsono.week2labflashcards;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class AddCardActivity extends AppCompatActivity {

    Intent data;
    EditText questionField;
    EditText answerField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        data = new Intent();
        questionField = (EditText)findViewById(R.id.newQuestion);
        answerField = (EditText)findViewById(R.id.newAnswer);

        findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.putExtra("question", questionField.getText().toString());
                data.putExtra("answer", answerField.getText().toString());
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }


}
