package borisov.rsue.ru;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "QuestActivity";
    private static final String KEY_INDEX_ONE = "index";
    private static final String KEY_INDEX_TWO = "index";
    private static final String KEY_INDEX_QUESTION = "index";
    private TextView mQuestionTextView;
    private static final int REQUEST_CODE_DECEIT = 0;

    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_android, true),
            new Question(R.string.question_linear, false),
            new Question(R.string.question_service, false),
            new Question(R.string.question_res, true),
            new Question(R.string.question_manifest, true),

            new Question(R.string.question_yon, false),
            new Question(R.string.question_onSaveInstanceState, true),
            new Question(R.string.question_theBest, true),
            new Question(R.string.question_home, false),
            new Question(R.string.question_ussr, true),
    };
    private int mCurrentIndex = 0;
    private boolean mIsDeceiter;
    private boolean[] mUsedHint =new boolean[mQuestionBank.length];

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_DECEIT) {
            if (data == null) {
                return;
            }
            mIsDeceiter = DeceitActivity.wasAnswerShown(data);
            mUsedHint[mCurrentIndex]=true;
        }
    }

    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId;
        if (mIsDeceiter||mUsedHint[mCurrentIndex]) {
            messageResId = R.string.jugment_toast;
        } else {
            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
            } else {
                messageResId = R.string.incorrect_toast;
            }
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate(Bundle) was called");

        if(savedInstanceState!=null){
            mCurrentIndex=savedInstanceState.getInt(KEY_INDEX_ONE,0);
            mIsDeceiter=savedInstanceState.getBoolean(KEY_INDEX_TWO,false);
            mUsedHint=savedInstanceState.getBooleanArray(KEY_INDEX_QUESTION);
        }
        setContentView(R.layout.activity_main);
        mQuestionTextView = findViewById(R.id.question_text_view);
        Button mTrueButton = findViewById(R.id.true_button);
        Button mFalseButton = findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
            }
        });
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
            }
        });

        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex + 9) % mQuestionBank.length;
                updateQuestion();
            }
        });

        ImageButton nextButton = findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                mIsDeceiter = false;
                updateQuestion();
            }
        });

        mQuestionTextView = findViewById(R.id.question_text_view);
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });

        Button deceitButton = findViewById(R.id.deceit_button);
        deceitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent i = DeceitActivity.newIntent(MainActivity.this, answerIsTrue);
                startActivityForResult(i, REQUEST_CODE_DECEIT);
            }
        });

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX_ONE, 0);
        }
        updateQuestion();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle saveInstanceState) {
        super.onSaveInstanceState(saveInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        saveInstanceState.putInt(KEY_INDEX_ONE, mCurrentIndex);
        saveInstanceState.putBoolean(KEY_INDEX_TWO,mIsDeceiter);
        saveInstanceState.putBooleanArray(KEY_INDEX_QUESTION,mUsedHint);

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() was called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() was called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() was called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() was called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() was called");
    }
}
