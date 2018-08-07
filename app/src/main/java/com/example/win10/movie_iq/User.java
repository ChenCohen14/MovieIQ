package com.example.win10.movie_iq;

import java.util.ArrayList;

public class User {
    private String userName;
    private String uID;
    private int totalPoints;
    private int pointsPerQuestion;
    private ArrayList<UserTierInfo> currentState;

    public User(){

    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public void setPointsPerQuestion(int pointsPerQuestion) {
        this.pointsPerQuestion = pointsPerQuestion;
    }

    public void setCurrentState(ArrayList<UserTierInfo> currentState) {
        this.currentState = currentState;
    }

    public String getUserName() {
        return userName;
    }

    public String getuID() {
        return uID;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public int getPointsPerQuestion() {
        return pointsPerQuestion;
    }

    public ArrayList<UserTierInfo> getCurrentState() {
        return currentState;
    }
}
