package com.oleg.first;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final int REQUEST_CODE_CHEAT = 0;
    private boolean mIsCheater;
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mCheatButton;
    private TextView mQuestionTextView;
    private TextView mTestResultTextView;
    private int mCurrentIndex = 0;
    private int mCorrectAnswersCount = 0;
    private int mChooseAnswerCount = 0;
    private Question[] mQuestions = new Question[]{
            new Question(R.string.questions_capital_Russia, true),
            new Question(R.string.questions_capital_Ukraine, true),
            new Question(R.string.questions_capital_England, false),
            new Question(R.string.questions_oceans, true),
            new Question(R.string.questions_asia, true),
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }

        mQuestionTextView = (TextView) findViewById(R.id.question_list);
        updateQuestionsIndexList();

        mFalseButton = (Button) findViewById(R.id.false_button);
        mTrueButton = (Button) findViewById(R.id.true_button);
        mNextButton = (Button) findViewById(R.id.next_button);
        mCheatButton = (Button) findViewById(R.id.cheat_button);


        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
                mFalseButton.setEnabled(false);
                mTrueButton.setEnabled(false);
            }
        });
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
                mFalseButton.setEnabled(false);
                mTrueButton.setEnabled(false);
            }
        });
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentIndex == (mQuestions.length - 1)) {
                    mQuestionTextView.setEnabled(false);
                    mNextButton.setEnabled(false);
                } else {
                    mCurrentIndex = (mCurrentIndex + 1);
                    updateQuestionsIndexList();
                }
                mFalseButton.setEnabled(true);
                mTrueButton.setEnabled(true);
                mIsCheater = false;
            }
        });
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(QuizActivity.this, CheatActivity.class);
                boolean answerIsTrue = mQuestions[mCurrentIndex].ismAnswerTrue();
                Intent intent = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);
//                startActivity(intent);
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
            }
        });
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentIndex == (mQuestions.length - 1)) {
                    mCurrentIndex = (mCurrentIndex + 1);
                    updateQuestionsIndexList();
                } else {
                    mQuestionTextView.setEnabled(false);
                    mNextButton.setEnabled(false);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            }
            mIsCheater = CheatActivity.wasAnswerIsShown(data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "savedInstanceState");
        outState.putInt(KEY_INDEX, mCurrentIndex);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    private void updateQuestionsIndexList() {
        int question = mQuestions[mCurrentIndex].getmTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestions[mCurrentIndex].ismAnswerTrue();
        int messageResId = 0;
        if (userPressedTrue == answerIsTrue) {
            messageResId = R.string.correct_value;
            mCorrectAnswersCount++;
        } else {
            messageResId = R.string.incorrect_value;
        }
        Toast toast = Toast.makeText(
                getApplicationContext(),
                messageResId,
                Toast.LENGTH_SHORT
        );
        mChooseAnswerCount++;
        toast.setGravity(Gravity.TOP, 0, 400);
        toast.show();
        displayTestResult();
    }

    private void displayTestResult() {
        if (mChooseAnswerCount == mQuestions.length) {
            mTestResultTextView = (TextView) findViewById(R.id.test_result);
            Toast.makeText(getApplicationContext(),
                    "Correct answers:" + mCorrectAnswersCount,
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }
}