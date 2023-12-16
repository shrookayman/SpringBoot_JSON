package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes  = {Employee.class , EmployeeController.class , JsonEmployeeService.class ,
		Demo1Application.class
})
class Demo1ApplicationTests {

	@Test
	void contextLoads() {
	}

}
