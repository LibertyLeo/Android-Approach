 package com.libertyleo.geoquiz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

 public class CheatActivity extends AppCompatActivity {

    private static final String EXTRA_ANSWER_IS_TRUE = "com.libertyleo.geoquiz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN = "com.libertyleo.geoquiz.answer_shown";
    private static final String EXTRA_CHEAT_HAPPEN = "com.libertyleo.geoquiz.cheat_happen";

    private boolean mAnswerIsTrue;
    private String mCheatAnswer;

    private TextView mAnswerTextView;
    private Button mShowAnswerButton;

    public static Intent newIntent(Context packageContext, boolean answerIsTrue) {
        Intent intent = new Intent(packageContext, CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return intent;
    }

    public static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        if (savedInstanceState != null) {
            mCheatAnswer = savedInstanceState.getString(EXTRA_CHEAT_HAPPEN);
            // Since you have seen the answer, we should inform the QuizActivity known.
            setAnswerShownResult(true);
        }

        mAnswerTextView = (TextView) findViewById(R.id.answer_text_view);
        mAnswerTextView.setText(mCheatAnswer);
        mShowAnswerButton = (Button) findViewById(R.id.show_answer_button);
        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAnswerIsTrue) {
                    mAnswerTextView.setText(R.string.true_button);
                    mCheatAnswer = getString(R.string.true_button);
                } else {
                    mAnswerTextView.setText(R.string.false_button);
                    mCheatAnswer = getString(R.string.false_button);
                }
                setAnswerShownResult(true);
            }
        });
    }

    private void setAnswerShownResult(boolean isAnswerShown) {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(EXTRA_CHEAT_HAPPEN, mCheatAnswer);
    }
}
