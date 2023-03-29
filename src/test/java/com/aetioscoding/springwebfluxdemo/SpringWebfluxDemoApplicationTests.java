package com.aetioscoding.springwebfluxdemo;

import com.aetioscoding.springwebfluxdemo.dto.EmployeeDto;
import com.aetioscoding.springwebfluxdemo.repository.EmployeeRepository;
import com.aetioscoding.springwebfluxdemo.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Collections;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringWebfluxDemoApplicationTests {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private WebTestClient webTestClient;

	@Autowired
	private EmployeeRepository employeeRepository;

	@BeforeEach
	public  void before(){
		System.out.println("BEFORE EACH TEST");
		employeeRepository.deleteAll().subscribe();
	}

	@Test
	public void testSaveEmployee() {

		EmployeeDto employeeDto = new EmployeeDto();
		employeeDto.setFirstName("Aécio");
		employeeDto.setLastName("Barros");
		employeeDto.setEmail("aeciob.guterres@gmail.com");

		webTestClient.post().uri("api/employees")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.body(Mono.just(employeeDto), EmployeeDto.class)
				.exchange()
				.expectStatus().isCreated()
				.expectBody()
				.consumeWith(System.out::println)
				.jsonPath("$.firstName").isEqualTo(employeeDto.getFirstName())
				.jsonPath("$.lastName").isEqualTo(employeeDto.getLastName())
				.jsonPath("$.email").isEqualTo(employeeDto.getEmail());


	}

	@Test
	public void testGetSingleEmployee(){


		EmployeeDto employeeDto = new EmployeeDto();
		employeeDto.setFirstName("Aécio");
		employeeDto.setLastName("Barros");
		employeeDto.setEmail("aeciob.guterres@gmail.com");

		EmployeeDto savedEmployee = employeeService.saveEmployee(employeeDto).block();

		webTestClient.get().uri("/api/employees/{id}", Collections.singletonMap("id",savedEmployee.getId()))
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.consumeWith(System.out::println)
				.jsonPath("$.id").isEqualTo(savedEmployee.getId())
				.jsonPath("$.firstName").isEqualTo(employeeDto.getFirstName())
				.jsonPath("$.lastName").isEqualTo(employeeDto.getLastName())
				.jsonPath("$.email").isEqualTo(employeeDto.getEmail());
	}

}
