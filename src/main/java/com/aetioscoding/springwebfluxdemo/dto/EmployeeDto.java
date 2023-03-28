package com.aetioscoding.springwebfluxdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {

    private String id;
    private String firstName;
    private String lastName;
    private String email;


}
