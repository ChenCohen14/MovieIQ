package com.example.win10.movie_iq;

import java.io.Serializable;
import java.util.ArrayList;

public class Question implements Serializable {
    public String question;
    public String answer;
    public static final int POINTS = 10;
    public int currentPoints = POINTS;

    public int tier = 1;
    public ArrayList<String> hints = new ArrayList<>();
    public Question(){}

    public Question (String question, String answer){
        this.question = "In this animated coming of age classic, a lion cub will face family tragedy," +
                " exile and the burden of leading his pride as king.";
        this.answer = "The Lion King";

    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public static int getPOINTS() {
        return POINTS;
    }

    public int getCurrentPoints() {
        return currentPoints;
    }



    public int getTier() {
        return tier;
    }



    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setQuestion(String question) {
        this.question = question;
    }



    public void setCurrentPoints(int currentPoints) {
        this.currentPoints = currentPoints;
    }




    public void setTier(int tier) {
        this.tier = tier;
    }

    @Override
    public String toString() {
        return "Question = " + question + " answer = " + answer;
    }
}
