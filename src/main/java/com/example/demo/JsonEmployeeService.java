package com.example.demo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.thymeleaf.util.StringUtils.length;

@Service
public class JsonEmployeeService {

        public static List<Employee> readJsonFile(String filePath) throws IOException {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Map<String, Object>> jsonData = objectMapper.readValue(new File(filePath), List.class);
            List<Employee> employees = new ArrayList<>();
            for (Map<String, Object> employeeMap : jsonData) {
                String firstName = employeeMap.get("FirstName").toString();
                String lastName =  employeeMap.get("LastName").toString();
                int employeeID = (int) employeeMap.get("EmployeeID");
                String designation =  employeeMap.get("Designation").toString();
//                System.out.println(lastName);
                List<Map<String, Integer>> knownLanguagesData = (List<Map<String, Integer>>) employeeMap.get("KnownLanguages");
                List<Employee.Language> knownLanguages = new ArrayList<>();
                for (Map<String, Integer> languageData : knownLanguagesData) {
                    String languageName = String.valueOf(languageData.get("LanguageName"));
                    int scoreOutof100 = languageData.get("ScoreOutof100");
                    knownLanguages.add(new Employee.Language(languageName, scoreOutof100));
                    System.out.println(languageData);
                }
                employees.add(new Employee(firstName, lastName, employeeID, designation , knownLanguages));
                System.out.println();
            }
                return employees;
    }


    public void addEmployee(String FirstName, String LastName, int employeeId, String Designation, List<String> languages,List<Integer> langScores,String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, Object>> jsonData = objectMapper.readValue(new File(filePath), List.class);
        List<Employee> employees = new ArrayList<>();
        File jsonFile = new File(filePath); // Your JSON file name

        for (Map<String, Object> employeeMap : jsonData) {
            String firstName = employeeMap.get("FirstName").toString();
            String lastName =  employeeMap.get("LastName").toString();
            int employeeID = (int) employeeMap.get("EmployeeID");
            String designation =  employeeMap.get("Designation").toString();
//                System.out.println(lastName);
            List<Map<String, Integer>> knownLanguagesData = (List<Map<String, Integer>>) employeeMap.get("KnownLanguages");
            List<Employee.Language> knownLanguages = new ArrayList<>();
            for (Map<String, Integer> languageData : knownLanguagesData) {
                String languageName = String.valueOf(languageData.get("LanguageName"));
                int ScoreOutof100 = languageData.get("ScoreOutof100");
                knownLanguages.add(new Employee.Language(languageName, ScoreOutof100));
                System.out.println(languageData);
            }
            employees.add(new Employee(firstName, lastName, employeeID, designation , knownLanguages));
            System.out.println();
        }


            List<Employee.Language> languages1 = new ArrayList<>();
            for (int i =0;i<languages.size();i++){
                languages1.add(new Employee.Language(languages.get(i), langScores.get(i)));
            }
        Employee employee1 = new Employee(FirstName,LastName,employeeId,Designation,languages1);

            employee1.setKnownLanguages(languages1);

            employees.add(employee1);

            // Write data to the JSON file
            objectMapper.writeValue(jsonFile, employees);

            System.out.println("Data written to JSON file successfully!");


    }
}
