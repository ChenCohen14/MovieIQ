package com.example.win10.movie_iq;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User implements Serializable {
    private String userEmail;
    private int totalPoints;
    private int pointsPerQuestion;

    public User() {
    }

    public User(String userEmail) {
        this.userEmail = userEmail;
        totalPoints = 0;
        pointsPerQuestion = 10;


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


}
