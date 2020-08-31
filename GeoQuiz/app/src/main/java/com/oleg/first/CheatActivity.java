package com.oleg.first;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private boolean mAnswerIsTrue;
    private Button mShowAnswerButton;
    private TextView mShowAnswerTextView;
    private static final String EXTRA_ANSWER_IS_TRUE =
            "com.oleg.first.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN =
            "com.oleg.first.answer_shown";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        mShowAnswerTextView = (TextView) findViewById(R.id.answer_text_view);
        mShowAnswerButton = (Button) findViewById(R.id.show_answer_button);
        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAnswerIsTrue){
                    mShowAnswerTextView.setText(R.string.correct_value);
                }else {
                    mShowAnswerTextView.setText(R.string.incorrect_value);
                }
                setAnswerShownResult();
            }
        });
    }

    private void setAnswerShownResult() {
        Intent intent =new Intent();
        intent.putExtra(EXTRA_ANSWER_SHOWN, true);
    }

    public static Intent newIntent(Context context, boolean answerIsTrue){
        Intent intent = new Intent(context, CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return intent;
    }

    public static boolean wasAnswerIsShown(Intent result){
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }
}