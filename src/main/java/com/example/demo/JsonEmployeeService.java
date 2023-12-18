package com.example.demo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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
                }
                employees.add(new Employee(firstName, lastName, employeeID, designation , knownLanguages));
            }
                return employees;
    }


    public void addEmployeeService(String FirstName, String LastName, int employeeId, String Designation,
                            List<String> languages,List<Integer> langScores,String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

//        List<Map<String, Object>> jsonData = objectMapper.readValue(new File(filePath), List.class);

        // Read existing employees from the file
        File jsonFile = new File(filePath);

        // Read existing employees from the file
        List<Employee> employees = readJsonFile(filePath);

        // Create a new employee
        List<Employee.Language> languages1 = new ArrayList<>();
        for (int i = 0; i < languages.size(); i++) {
            languages1.add(new Employee.Language(languages.get(i), langScores.get(i)));
        }

        Employee newEmployee = new Employee(FirstName, LastName, employeeId, Designation, languages1);
        newEmployee.setKnownLanguages(languages1);

        // Add the new employee to the existing list
        employees.add(newEmployee);
        // Clear the file before writing the updated list
        try (FileWriter fileWriter = new FileWriter(jsonFile, false)) {
            fileWriter.write(""); // Clears the file content
        }

            // Write data to the JSON file
            objectMapper.writeValue(jsonFile, employees);



    }
    public void updateEmployee(String filePath) throws IOException {
        List<Employee> employees = readJsonFile(filePath);

        for (Employee employee : employees) {
            if (employee.getEmployeeID() == 2000 && Objects.equals(employee.getDesignation(), "Developer")) {
                employee.setDesignation("Team Leader");
            }
        }

        writeJsonFile( employees, filePath);
    }

    public static List<Employee> search(String filePath,String searchAttribute,String searchWord) throws IOException {

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
            }
            employees.add(new Employee(firstName, lastName, employeeID, designation , knownLanguages));
        }        int searchEmployeeID = 1000;
        for (Employee employee : employees) {
            if (employee.getEmployeeID() == searchEmployeeID) {
                break; // Exit loop if found (assuming IDs are unique)
            }
        }

        return employees;
    }
    public void deleteEmployee(int employeeID, String filePath) throws IOException {
        List<Employee> employees = readJsonFile(filePath);

        employees.removeIf(employee -> employee.getEmployeeID()== (employeeID));

       writeJsonFile(employees , filePath);
    }
    public List<Employee> getJavaExperts(List<Employee> employees) {
        return employees.stream()
                .filter(employee -> employee.getKnownLanguages().stream()
                        .anyMatch(language -> "Java".equals(language.getLanguageName()) && language.getScoreOutof100() > 50))
                .sorted(Comparator.comparing(Employee::getFirstName))
                .collect(Collectors.toList());
    }

    private static void writeJsonFile(List<Employee> employees , String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            objectMapper.writeValue(new File(filePath), employees);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
