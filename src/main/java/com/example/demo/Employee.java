package com.example.demo;

// Employee.java
import java.util.List;
import java.util.List;

public class Employee {
    private String FirstName;
    private String LastName;
    private int EmployeeID;
    private String Designation;
    private List<Language> KnownLanguages;

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public int getEmployeeID() {
        return EmployeeID;
    }

    public void setEmployeeID(int employeeID) {
        EmployeeID = employeeID;
    }

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String designation) {
        Designation = designation;
    }

    public List<Language> getKnownLanguages() {
        return KnownLanguages;
    }

    public void setKnownLanguages(List<Language> knownLanguages) {
        KnownLanguages = knownLanguages;
    }
// Getters and setters

    public static class Language {
        private String LanguageName;
        private int ScoreOutof100;

        public String getLanguageName() {
            return LanguageName;
        }

        public void setLanguageName(String languageName) {
            LanguageName = languageName;
        }

        public int getScoreOutof100() {
            return ScoreOutof100;
        }

        public void setScoreOutof100(int scoreOutof100) {
            ScoreOutof100 = scoreOutof100;
        }
// Getters and setters
    }
}
