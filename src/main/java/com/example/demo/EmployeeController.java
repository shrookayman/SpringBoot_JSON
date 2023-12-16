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


    @GetMapping("/none")
    public String display(Model model) {

        return "none"; // Thymeleaf template name
    }
    @GetMapping("/display")
    public String displayJsonData(Model model) {
        try {
            String filePath = "Employee.json";
            List<Employee> employees = readJsonFile(filePath);
            model.addAttribute("employees", employees);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception
        }
        return "redirect:/display"; // Thymeleaf template name
    }
}
