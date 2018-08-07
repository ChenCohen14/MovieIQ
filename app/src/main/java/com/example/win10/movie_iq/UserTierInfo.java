package com.example.win10.movie_iq;

import java.util.ArrayList;
import java.util.HashMap;

public class UserTierInfo {
    private int tierNum;
    private ArrayList<Integer> answeredQuestions;
    private HashMap<Integer,Integer> hintTakedIndex;
    private boolean isOpen;

    public UserTierInfo(){
        tierNum = 1;
        answeredQuestions = new ArrayList<>();
        hintTakedIndex = new HashMap<>();
        isOpen = true;
    }

    public int getTierNum() {
        return tierNum;
    }

    public ArrayList<Integer> getAnsweredQuestions() {
        return answeredQuestions;
    }

    public HashMap<Integer, Integer> getHintTakedIndex() {
        return hintTakedIndex;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setTierNum(int tierNum) {
        this.tierNum = tierNum;
    }

    public void setAnsweredQuestions(ArrayList<Integer> answeredQuestions) {
        this.answeredQuestions = answeredQuestions;
    }

    public void setHintTakedIndex(HashMap<Integer, Integer> hintTakedIndex) {
        this.hintTakedIndex = hintTakedIndex;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
}
