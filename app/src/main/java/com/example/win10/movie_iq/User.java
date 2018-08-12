package com.example.win10.movie_iq;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User implements Serializable {
    private String userEmail;
    private String name;
    private String userID;
    private int totalPoints;
    //private int pointsPerQuestion;
    private ArrayList<UserTierInfo> userTierInfos;
    private int currentOpenTiers;


    public User() {
    }

    public User(String userEmail, String name, String userID) {
        this.userEmail = userEmail;
        this.name = name;
        this.userID = userID;
        totalPoints = 0;
        currentOpenTiers = 1;
        //  pointsPerQuestion = 10;
        userTierInfos = new ArrayList<>();


    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints += totalPoints;
    }

//    public void setPointsPerQuestion(int pointsPerQuestion) {
//        this.pointsPerQuestion = pointsPerQuestion;
//    }


    public int getTotalPoints() {
        return totalPoints;
    }

    // public int getPointsPerQuestion() {
    //return pointsPerQuestion;
    // }

    public ArrayList<UserTierInfo> getUserTierInfos() {
        return userTierInfos;
    }

    public void setUserTierInfos(ArrayList<UserTierInfo> userTierInfos) {
        this.userTierInfos = userTierInfos;
    }

    public boolean isExistUserTierInfo(UserTierInfo userTierInfo) {
        for (int i = 0; i < userTierInfos.size(); i++) {
            if (userTierInfo.getTier().equals(userTierInfos.get(i).getTier()))
                return true;
        }
        return false;
    }

    public void addUserTierInfo(UserTierInfo newUserTierInfo) {
        if (isExistUserTierInfo(newUserTierInfo))
            return;
        userTierInfos.add(newUserTierInfo);
    }
    public UserTierInfo getUserTierInfoByTierName(String tierName){
        for(int i=0 ;i<getUserTierInfos().size();i++){
            if(getUserTierInfos().get(i).getTier().equals(tierName))
                return getUserTierInfos().get(i);
        }
        return null;
    }

    public int getCurrentOpenTiers() {
        return currentOpenTiers;
    }

    public void setCurrentOpenTiers(int currentOpenTiers) {
        this.currentOpenTiers = currentOpenTiers;
    }

    @Override
    public String toString() {
        return "User{" +
                "userEmail='" + userEmail + '\'' +
                ", name='" + name + '\'' +
                ", userID='" + userID + '\'' +
                ", totalPoints=" + totalPoints +
                ", userTierInfos=" + userTierInfos +
                '}';
    }
}
