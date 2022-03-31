package com.dan.reactivejava.controller;

import com.alibaba.fastjson.JSON;
import com.dan.reactivejava.model.response.EmployeeResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestWebClientController {

    private final WebClient client;

    @GetMapping("/v1/employee/search/{id}")
    public ResponseEntity<Object> testGetEmployeeById(@PathVariable("id") Long id){
        Mono<EmployeeResponse> employeeResponseMono = client.get()
                .uri("/employee/v1/search/"+id)
                .retrieve()
                .bodyToMono(EmployeeResponse.class);
        employeeResponseMono.subscribe(data-> log.info("Data = {}", JSON.toJSONString(data)));
        return new ResponseEntity<>(employeeResponseMono, HttpStatus.OK);
    }

    @GetMapping("/v1/employee/search")
    public ResponseEntity<Object> testGetEmployee(){
        Flux<EmployeeResponse> employeeResponseFlux = client.get()
                .uri("/employee/v1/search")
                .retrieve()
                .bodyToFlux(EmployeeResponse.class);
        employeeResponseFlux.subscribe(data-> log.info("Data = {}", JSON.toJSONString(data)));
        return new ResponseEntity<>(employeeResponseFlux, HttpStatus.OK);
    }

}
