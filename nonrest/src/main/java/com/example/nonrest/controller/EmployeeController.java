package com.example.nonrest.controller;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.nonrest.controller.assembler.EmployeeModelAssembler;
import com.example.nonrest.controller.exception.EmployeeNotFoundException;
import com.example.nonrest.entity.Employee;
import com.example.nonrest.repository.EmployeeRepository;

@RestController
public class EmployeeController {

	@Autowired
	@Qualifier("EmployeeRepository")
	private EmployeeRepository repository;

	@Autowired
	private EmployeeModelAssembler assembler;

	// Aggregate root

//	@GetMapping("/employees")
//	List<Employee> all() {
//		return repository.findAll();
//	}

	@GetMapping("/employees")
	public CollectionModel<EntityModel<Employee>> all() {

		List<EntityModel<Employee>> employees = repository.findAll().stream()
				.map(employee -> EntityModel.of(employee,
						linkTo(methodOn(EmployeeController.class).one(employee.getId())).withSelfRel(),
						linkTo(methodOn(EmployeeController.class).all()).withRel("employees")))
				.collect(Collectors.toList());

		return CollectionModel.of(employees, linkTo(methodOn(EmployeeController.class).all()).withSelfRel());
	}

	@PostMapping("/employees")
	ResponseEntity<?> newEmployee(@RequestBody Employee newEmployee) {

		EntityModel<Employee> entityModel = assembler.toModel(repository.save(newEmployee));

		return ResponseEntity //
				.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
				.body(entityModel);
	}

	// Single item

//	@GetMapping("/employees/{id}")
//	Employee one(@PathVariable Long id) {
//		return repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
//	}

	@GetMapping("/employees/{id}")
	public EntityModel<Employee> one(@PathVariable Long id) {

		Employee employee = repository.findById(id) //
				.orElseThrow(() -> new EmployeeNotFoundException(id));

		return assembler.toModel(employee);
	}

	@PutMapping("/employees/{id}")
	Employee replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {

		return repository.findById(id).map(employee -> {
			employee.setName(newEmployee.getName());
			employee.setRole(newEmployee.getRole());
			return repository.save(employee);
		}).orElseGet(() -> {
			newEmployee.setId(id);
			return repository.save(newEmployee);
		});
	}

	@DeleteMapping("/employees/{id}")
	void deleteEmployee(@PathVariable Long id) {
		repository.deleteById(id);

	}
}
