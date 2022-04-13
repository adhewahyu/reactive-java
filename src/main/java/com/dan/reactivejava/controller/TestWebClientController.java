package com.dan.reactivejava.controller;

import com.dan.reactivejava.model.response.EmployeeResponse;
import com.dan.reactivejava.model.response.RestResponse;
import com.dan.reactivejava.util.Constants;
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
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/employee")
public class TestWebClientController {

    private final WebClient webClientBasic;

    @GetMapping("/v1/test/search/{id}")
    public ResponseEntity<RestResponse> testGetEmployeeById(@PathVariable("id") Long id) throws ExecutionException, InterruptedException {
        Mono<EmployeeResponse> employeeResponseMono = webClientBasic.get()
                .uri("/employee/v1/search/" + id)
                .retrieve()
                .bodyToMono(EmployeeResponse.class);
        EmployeeResponse employeeResponse = employeeResponseMono
                .subscribeOn(Schedulers.boundedElastic())
                .toFuture().get();
        return new ResponseEntity<>(new RestResponse(employeeResponse, Constants.MSG_DATA_FOUND, true), HttpStatus.OK);
    }

    @GetMapping("/v1/test/search")
    public ResponseEntity<RestResponse> testGetEmployee() throws ExecutionException, InterruptedException {
        Flux<EmployeeResponse> employeeResponseFlux = webClientBasic.get()
                .uri("/employee/v1/search")
                .retrieve()
                .bodyToFlux(EmployeeResponse.class);
        List<EmployeeResponse> employeeResponseList = employeeResponseFlux.collectList()
                .subscribeOn(Schedulers.boundedElastic())
                .toFuture().get();
        return new ResponseEntity<>(new RestResponse(employeeResponseList, Constants.MSG_DATA_FOUND, true), HttpStatus.OK);
    }

}
