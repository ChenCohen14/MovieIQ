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
    private int pointsPerQuestion;

    public User() {
    }

    public User(String userEmail, String name, String userID) {
        this.userEmail = userEmail;
        this.name = name;
        this.userID = userID;
        totalPoints = 0;
        pointsPerQuestion = 10;


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
        this.totalPoints = totalPoints;
    }

    public void setPointsPerQuestion(int pointsPerQuestion) {
        this.pointsPerQuestion = pointsPerQuestion;
    }


    public int getTotalPoints() {
        return totalPoints;
    }

    public int getPointsPerQuestion() {
        return pointsPerQuestion;
    }


    @Override
    public String toString() {
        return "User{" +
                "userEmail='" + userEmail + '\'' +
                ", name='" + name + '\'' +
                ", userID='" + userID + '\'' +
                ", totalPoints=" + totalPoints +
                ", pointsPerQuestion=" + pointsPerQuestion +
                '}';
    }
}
