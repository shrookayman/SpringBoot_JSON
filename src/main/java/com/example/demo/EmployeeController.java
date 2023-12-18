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

import static com.example.demo.JsonEmployeeService.readJsonFile;


@Controller
public class EmployeeController {

    private  final JsonEmployeeService jsonEmployeeService ;

    public EmployeeController(JsonEmployeeService jsonEmployeeService) {
        this.jsonEmployeeService = jsonEmployeeService;
    }


    @GetMapping("/display")
    public String displayJsonData(Model model) throws IOException {
            List<Employee> employees ;
            employees = jsonEmployeeService.readJsonFile("Employee.json");
            model.addAttribute("employees", employees);
            for (Employee st : employees) {
                System.out.println(st.getFirstName());
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
            System.out.println(additionalInputs);
    return "add-employee";
        }
        else{
            return "add-employee";
        }
    }

    @PostMapping("/add-employee")
    public String addEmployee(
            Model model,
            @RequestParam Map<String, String> formData
    ) throws IOException {
        List<String> languages = new ArrayList<>();
        List<Integer> Scores = new ArrayList<>();
        for (int i = 1; i <= num; i++) {
            languages.add(formData.get("language"+i));
            Scores.add(Integer.parseInt(formData.get("langScore"+i)));
        }
            jsonEmployeeService.addEmployee(formData.get("firstName"), formData.get("lastName"), Integer.parseInt(formData.get("employeeId")),formData.get("designation") , languages,Scores,"Employee.json");
        return "redirect:/display";
    }


}
