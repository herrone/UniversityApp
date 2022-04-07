package com.example.trydisslow;


public class Grade {

    String code;
    int target;
    int firstWeight;
    int firstObtained;
    int secondWeight;
    int secondObtained;
    int thirdWeight;
    int thirdObtained;
    int fourthWeight;
    int fourthObtained;
    int fifthWeight;
    int fifthObtained;
//empty constructor
    public Grade() {

    }
//populated constructor
    public Grade(String code, int target, int firstWeight, int firstObtained, int secondWeight, int secondObtained, int thirdWeight, int thirdObtained, int fourthWeight, int fourthObtained, int fifthWeight, int fifthObtained) {

        this.code = code;
        this.target = target;
        this.firstWeight = firstWeight;
        this.firstObtained = firstObtained;
        this.secondWeight = secondWeight;
        this.secondObtained = secondObtained;
        this.thirdWeight = thirdWeight;
        this.thirdObtained = thirdObtained;
        this.fourthWeight = fourthWeight;
        this.fourthObtained = fourthObtained;
        this.fifthWeight = fifthWeight;
        this.fifthObtained = fifthObtained;

    }
}