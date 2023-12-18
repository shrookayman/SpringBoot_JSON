package com.example.demo;

// Employee.java
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.List;

public class Employee {
//    @JsonProperty("FirstName")
    private String FirstName;
//    @JsonProperty("LastName")
    private String LastName;
//    @JsonProperty("EmployeeID")
    private int EmployeeID;
//    @JsonProperty("Designation")
    private String Designation;
//    @JsonProperty("KnownLanguages")
    private List<Language> KnownLanguages;
    public Employee(){};
    public Employee(String firstName, String lastName, int employeeID, String designation,
                    List<Language> KnownLanguages) {
        this.FirstName = firstName;
        this.LastName = lastName;
        this.EmployeeID = employeeID;
        this.Designation = designation;
        this.KnownLanguages = KnownLanguages;
    }


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

    public static class Language {
//        @JsonProperty("LanguageName")
        private String LanguageName;
//        @JsonProperty("ScoreOutof100")
        private int ScoreOutof100;
        public Language() {}

        public Language(String languageName, int scoreOutof100) {
            LanguageName = languageName;
            ScoreOutof100 = scoreOutof100;
        }

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

}
}
