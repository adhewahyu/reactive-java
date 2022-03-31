package com.dan.reactivejava.controller;

import com.dan.reactivejava.model.request.FindByIdRequest;
import com.dan.reactivejava.model.response.EmployeeResponse;
import com.dan.reactivejava.service.GetEmployeeByIdService;
import com.dan.reactivejava.service.GetEmployeesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {

    private final GetEmployeeByIdService getEmployeeByIdService;
    private final GetEmployeesService getEmployeesService;

    @GetMapping("/v1/search/{id}")
    public Mono<EmployeeResponse> getEmployeeById(@PathVariable("id") Long id){
        return getEmployeeByIdService.execute(FindByIdRequest.builder().id(id).build());
    }

    @GetMapping("/v1/search")
    public Flux<EmployeeResponse> getAllEmployees(){
        return getEmployeesService.execute();
    }
}
