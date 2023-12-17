package com.example.demo;

import kotlinx.coroutines.channels.ActorKt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.List;

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
}
