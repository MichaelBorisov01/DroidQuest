package borisov.rsue.ru;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DeceitActivity extends AppCompatActivity {
    public static final String EXTRA_ANSWER_IS_TRUE = "borisov.rsue.ru.driodquest.answer_is_true";
    public static final String EXTRA_ANSWER_SHOWN = "borisov.rsue.ru.driodquest.answer_is_shown";
    private boolean mAnswerIsTrue;
    private TextView mAnswerTextView;
    private static final String KEY_INDEX = "index";
    private boolean mResponseReviewed = false;

    public static Intent newIntent(Context packageContext, boolean answerIsTrue) {
        Intent i = new Intent(packageContext, DeceitActivity.class);
        i.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return i;
    }

    public static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deceit);
        mAnswerTextView = findViewById(R.id.answer_text_view);
        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
        if (savedInstanceState != null)
            mResponseReviewed = savedInstanceState.getBoolean(KEY_INDEX);
        if (mResponseReviewed) {
            if (mAnswerIsTrue) mAnswerTextView.setText(R.string.true_button);
            else mAnswerTextView.setText(R.string.false_button);
            setAnswerShownResult();
        }

        Button showAnswer = findViewById(R.id.show_answer_button);
        showAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAnswerIsTrue) {
                    mAnswerTextView.setText(R.string.true_button);
                } else {
                    mAnswerTextView.setText(R.string.false_button);
                }
                setAnswerShownResult();
                mResponseReviewed = true;
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle saveInstanceState) {
        super.onSaveInstanceState(saveInstanceState);
        saveInstanceState.putBoolean(KEY_INDEX, mResponseReviewed);
    }

    private void setAnswerShownResult() {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, true);
        setResult(RESULT_OK, data);
    }
}
