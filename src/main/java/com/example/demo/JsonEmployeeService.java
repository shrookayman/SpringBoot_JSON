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
                String firstName = employeeMap.get("firstName").toString();
                String lastName =  employeeMap.get("lastName").toString();
                int employeeID = (int) employeeMap.get("employeeID");
                String designation =  employeeMap.get("designation").toString();
//                System.out.println(lastName);
                List<Map<String, Integer>> knownLanguagesData = (List<Map<String, Integer>>) employeeMap.get("knownLanguages");
                List<Employee.Language> knownLanguages = new ArrayList<>();
                for (Map<String, Integer> languageData : knownLanguagesData) {
                    String languageName = String.valueOf(languageData.get("languageName"));
                    int scoreOutof100 = languageData.get("scoreOutof100");
                    knownLanguages.add(new Employee.Language(languageName, scoreOutof100));
                }
                employees.add(new Employee(firstName, lastName, employeeID, designation , knownLanguages));
            }
                return employees;
    }


    public void addEmployeeService(String FirstName, String LastName, int employeeId, String Designation,
                            List<String> languages,List<Integer> langScores,String filePath) throws IOException {
        List<Employee> employees ;

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        // Read existing employees from the file or create a new list if the file doesn't exist
        File jsonFile = new File(filePath);
        if (jsonFile.exists()) {
            employees = readJsonFile(filePath);
        } else {
            employees = new ArrayList<>();
            jsonFile.createNewFile(); // Create a new file if it doesn't exist

        }

        // Create a new employee
        List<Employee.Language> languages1 = new ArrayList<>();
        for (int i = 0; i < languages.size(); i++) {
            languages1.add(new Employee.Language(languages.get(i), langScores.get(i)));
        }

        Employee newEmployee = new Employee(FirstName, LastName, employeeId, Designation, languages1);
        newEmployee.setKnownLanguages(languages1);

        // Add the new employee to the existing list
        employees.add(newEmployee);


            // Write data to the JSON file
        objectMapper.writeValue(new File(filePath), employees);



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

    public void deleteEmployee(int employeeID, String filePath) throws IOException {
        List<Employee> employees = readJsonFile(filePath);

        employees.removeIf(employee -> employee.getEmployeeID()== (employeeID));

       writeJsonFile(employees , filePath);
    }
    public List<Employee> getJavaExperts(List<Employee> employees) {
        return employees.stream()
                .filter(employee -> employee.getKnownLanguages().stream()
                        .anyMatch(language -> isJava(language) && language.getScoreOutof100() > 50))
                .sorted(Comparator.comparingInt(employee -> getJavaScore(employee)))
                .collect(Collectors.toList());
    }

    private boolean isJava(Employee.Language language) {
        return "java".equalsIgnoreCase(language.getLanguageName());
    }

    private int getJavaScore(Employee employee) {
        return employee.getKnownLanguages().stream()
                .filter(this::isJava)
                .mapToInt(Employee.Language::getScoreOutof100)
                .findFirst()
                .orElse(0);
    }

    private static void writeJsonFile(List<Employee> employees , String filePath) throws IOException {


        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            objectMapper.writeValue(new File(filePath), employees);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
