package com.dan.reactivejava.model.transform;

import com.dan.reactivejava.model.entity.Employee;
import com.dan.reactivejava.model.response.EmployeeResponse;
import org.springframework.stereotype.Component;

@Component
public class EmployeeResponseTransformer {

    public EmployeeResponse transform(Employee employee){
        return EmployeeResponse.builder()
                .id(employee.getId())
                .name(employee.getName())
                .salary(employee.getSalary())
                .build();
    }

}
