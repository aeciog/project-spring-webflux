package com.aetioscoding.springwebfluxdemo.repository;

import com.aetioscoding.springwebfluxdemo.entity.Employee;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface EmployeeRepository extends ReactiveCrudRepository <Employee, String>{
}
