package com.mohit.vidhidarpan;


public class AdvocateModel {
    private String name;
    private String email;
    private String phoneNumber;
    private String barCouncilNumber;
    private String description;
    private int casesWon;
    private int casesTaken;
    private String password;
    private String firebaseKey;
    public AdvocateModel() {
        // Default constructor required for Firebase
    }

    public AdvocateModel(String name, String email, String phoneNumber, String barCouncilNumber,
                         String description, int casesWon, int casesTaken, String password) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.barCouncilNumber = barCouncilNumber;
        this.description = description;
        this.casesWon = casesWon;
        this.casesTaken = casesTaken;
        this.password = password;
    }

    // Getter and Setter methods for each attribute

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBarCouncilNumber() {
        return barCouncilNumber;
    }

    public void setBarCouncilNumber(String barCouncilNumber) {
        this.barCouncilNumber = barCouncilNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCasesWon() {
        return casesWon;
    }

    public void setCasesWon(int casesWon) {
        this.casesWon = casesWon;
    }

    public int getCasesTaken() {
        return casesTaken;
    }

    public void setCasesTaken(int casesTaken) {
        this.casesTaken = casesTaken;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    // Getter for Firebase UID
    public String getFirebaseKey() {
        return firebaseKey;
    }

    // Setter for Firebase key
    public void setFirebaseKey(String firebaseKey) {
        this.firebaseKey = firebaseKey;
    }
}

