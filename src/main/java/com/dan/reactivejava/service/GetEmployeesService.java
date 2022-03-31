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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
public class GetEmployeesService {

    private final EmployeeResponseTransformer employeeResponseTransformer;

    public Flux<EmployeeResponse> execute(){
        List<EmployeeResponse> allEmployees = SampleEmployees.initEmployees()
                .stream()
                .map(employeeResponseTransformer::transform).collect(Collectors.toList());
        return Flux.fromIterable(allEmployees);
    }

}
