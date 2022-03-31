package com.dan.reactivejava.service;

import com.dan.reactivejava.model.entity.Employee;
import com.dan.reactivejava.model.request.FindByIdRequest;
import com.dan.reactivejava.model.response.EmployeeResponse;
import com.dan.reactivejava.model.transform.EmployeeResponseTransformer;
import com.dan.reactivejava.util.SampleEmployees;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class GetEmployeeByIdService {

    private final EmployeeResponseTransformer employeeResponseTransformer;

    public Mono<EmployeeResponse> execute (FindByIdRequest request){
        doValidateRequest(request);
        List<Employee> employees = SampleEmployees.initEmployees();
        Employee selectedEmployee = employees.stream().filter(employee -> employee.getId().equals(request.getId())).findAny().orElseThrow(()->{
            log.error("Employee not found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found");
        });
        return Mono.just(employeeResponseTransformer.transform(selectedEmployee));
    }

    private void doValidateRequest(FindByIdRequest request){
        if(ObjectUtils.isEmpty(request.getId())){
            log.error("Id is required");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id is required");
        }
    }

}
