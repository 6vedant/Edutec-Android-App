package com.edutech.app.prototype;

/**
 * Created by vedant on 1/18/2018.
 */

public class FaqPrototype {
    public String question,answer;

    public FaqPrototype(){}
    public FaqPrototype(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {

        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
