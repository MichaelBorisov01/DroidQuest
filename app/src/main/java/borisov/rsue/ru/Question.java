package borisov.rsue.ru;

class Question {
    private int mTextResId;
    private boolean mAnswerTrue;

    int getTextResId() {
        return mTextResId;
    }

    boolean isAnswerTrue() {
        return mAnswerTrue;
    }
    
    Question(int textResId, boolean answerTrue) {
        mTextResId = textResId;
        mAnswerTrue = answerTrue;
    }
}
