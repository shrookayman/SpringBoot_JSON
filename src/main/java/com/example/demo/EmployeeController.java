package com.example.demo;

//import kotlinx.coroutines.channels.ActorKt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Controller
public class EmployeeController {

    private  final JsonEmployeeService jsonEmployeeService ;

    public EmployeeController(JsonEmployeeService jsonEmployeeService) {
        this.jsonEmployeeService = jsonEmployeeService;
    }


    @GetMapping("/display")
    public String displayJsonData(Model model) throws IOException {
        try {
            List<Employee> employees ;
            employees = jsonEmployeeService.readJsonFile("Employee.json");
            model.addAttribute("employees", employees);
            for (Employee st : employees) {
            }

            return "display";
        }
        catch (Exception e){
            System.out.println("error");
//            return "add-employee";
        }
        return "display";

    }
    int num = 0;
    @GetMapping("/add-employee")
    public String showAddForm(Model model, @RequestParam(required = false) Integer numOfLanguages) {
        model.addAttribute("numOfLanguages", numOfLanguages);
        if(numOfLanguages != null){
            num = numOfLanguages;
            List<Integer> additionalInputs = new ArrayList<>();
            for (int i = 0; i < numOfLanguages; i++) {
                additionalInputs.add((i + 1));
            }
            model.addAttribute("additionalInputs", additionalInputs);
    return "add-employee";
        }
        else{
            return "add-employee";
        }
    }

    @PostMapping("/add-employee")
    public String addEmployee(Model model, @RequestParam Map<String, String> formData
    ) throws IOException {
        try {
            List<String> languages = new ArrayList<>();
            List<Integer> Scores = new ArrayList<>();
            for (int i = 1; i <= num; i++) {
                languages.add(formData.get("language"+i));
                Scores.add(Integer.parseInt(formData.get("langScore"+i)));
            }
            jsonEmployeeService.addEmployeeService(formData.get("firstName"), formData.get("lastName"), Integer.parseInt(formData.get("employeeId")),formData.get("designation") , languages,Scores,"Employee.json");
            return "redirect:/display";
        }
        catch (Exception e){
            System.out.println("error");
        }
        return "redirect:/display";


    }

    @GetMapping("/search-data")
    public String showSearchForm() {
        return "search-data";
    }
    @PostMapping("/search-data")
    public String searchData(Model model, @RequestParam String searchField, @RequestParam String searchTerm) {

        try {
            List<Employee> employees = jsonEmployeeService.readJsonFile("Employee.json");
            int numOfFoundEmployees = 0;
            List<Employee> searchResults = new ArrayList<>();
            if ("ID".equals(searchField)) {
                for (Employee employee : employees) {
                    if (employee.getEmployeeID() == Integer.parseInt(searchTerm)) {
                        searchResults.add(employee);
                        numOfFoundEmployees++;
                    }
                }
            } else if ("Designation".equals(searchField)) {
                for (Employee employee : employees) {
                    if (employee.getDesignation().equalsIgnoreCase(searchTerm)) {
                        searchResults.add(employee);
                        numOfFoundEmployees++;

                    }
                }
            }


            model.addAttribute("searchedEmployees", searchResults);
            model.addAttribute("numOfFoundEmployees", numOfFoundEmployees);


        } catch (Exception e) {
            System.out.println("error");
        }
        return "search-data";
    }

    @PostMapping("/delete")
    public String deleteEmployee(@RequestParam("employeeID") int employeeID) throws IOException {

        jsonEmployeeService.deleteEmployee(employeeID, "Employee.json");

        return "redirect:/display"; // Redirect to the display page after deletion
    }
    int ID ;
    @GetMapping("/update")
    public String updateStudent(Model model) throws Exception {
        try {
            ID=2000;
            List<Employee> employees = jsonEmployeeService.readJsonFile("Employee.json");
            List<Employee> updateEmployee = new ArrayList<>();
            for (Employee employee : employees) {
                if (employee.getEmployeeID() == 2000 && Objects.equals(employee.getDesignation(), "Developer")) {
                    updateEmployee.add(employee);
                }
            }
            model.addAttribute("updateEmployee", updateEmployee);
        } catch (Exception e) {
            System.out.println("error");
        }
        return "display";
    }

    @PostMapping("/update")
    public String updateEmployee() {
        try {
            jsonEmployeeService.updateEmployee("Employee.json");
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        return "redirect:/display";
    }

    @GetMapping("/java-experts")
    public String getJavaExperts(Model model) throws IOException {
        try {
            List<Employee> javaExperts = jsonEmployeeService.getJavaExperts(jsonEmployeeService.readJsonFile("Employee.json"));
            model.addAttribute("javaExperts", javaExperts);
            return "java-experts"; // Thymeleaf template name
        }
        catch (Exception e){
            System.out.println("error");
        }
        return "java-experts";
    }

}
