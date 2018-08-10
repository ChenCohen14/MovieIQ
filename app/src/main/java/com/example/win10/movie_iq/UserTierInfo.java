package com.example.win10.movie_iq;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserTierInfo implements Serializable {
    private String tier;
    private ArrayList<Question> answeredQuestions;
    private Map<String, Integer> hintTakedIndex;
    private Map<String, Question> currentPointsForQuestion;
    private boolean isOpen;

    public UserTierInfo(String tier) {
        this.tier = tier;
        answeredQuestions = new ArrayList<>();
        hintTakedIndex = new HashMap<>();
        isOpen = true;

        currentPointsForQuestion = new HashMap<>();
    }

    public String getTier() {
        return tier;
    }

    public ArrayList<Question> getAnsweredQuestions() {
        return answeredQuestions;
    }

    public Map<String, Integer> getHintTakedIndex() {
        return hintTakedIndex;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    public void setAnsweredQuestions(ArrayList<Question> answeredQuestions) {
        this.answeredQuestions = answeredQuestions;
    }

    public void setHintTakedIndex(Map<String, Integer> hintTakedIndex) {
        this.hintTakedIndex = hintTakedIndex;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public void addAnsweredQuestion(Question question) {
        answeredQuestions.add(question);
    }

    public void addHintTakedIndexed(String answerOfQuestion) {
        int count = getNumOfHintsTaked(answerOfQuestion);
        hintTakedIndex.put(answerOfQuestion, count + 1);
    }

    public int getNumOfHintsTaked(String answerOfQuestion){
        if(hintTakedIndex.size() == 0)
            return 0;
        return hintTakedIndex.get(answerOfQuestion);
    }

    public Map<String, Question> getCurrentPointsForQuestion() {
        return currentPointsForQuestion;
    }

    public void setCurrentPointsForQuestion(Map<String, Question> currentPointsForQuestion) {
        this.currentPointsForQuestion = currentPointsForQuestion;
    }
//    public void saveCurrentPointsPerQuestion(String questionAnswer, int currentPoints){
//        currentPointsForQuestion.put(questionAnswer, currentPoints);
//    }

    public Question getQuestionByAnswer(String questionAnswer){
        return currentPointsForQuestion.get(questionAnswer);
    }

    @Override
    public String toString() {
        return "UserTierInfo{" +
                "tier='" + tier + '\'' +
                ", answeredQuestions=" + answeredQuestions +
                ", hintTakedIndex=" + hintTakedIndex +
                ", currentPointsForQuestion=" + currentPointsForQuestion +
                ", isOpen=" + isOpen +
                '}';
    }
}
