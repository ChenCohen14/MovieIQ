package com.example.win10.movie_iq;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserTierInfo implements Serializable {
    private int tierNum;
    private ArrayList<Integer> answeredQuestions;
    private Map<Integer,Integer> hintTakedIndex;
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

    public Map<Integer, Integer> getHintTakedIndex() {
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

    public void setHintTakedIndex(Map<Integer, Integer> hintTakedIndex) {
        this.hintTakedIndex = hintTakedIndex;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
}
