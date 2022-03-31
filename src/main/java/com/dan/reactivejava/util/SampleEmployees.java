package com.dan.reactivejava.util;

import com.dan.reactivejava.model.entity.Employee;

import java.math.BigDecimal;
import java.util.List;

public class SampleEmployees {

    private SampleEmployees() {
        throw new UnsupportedOperationException("this is an Utility Class");
    }

    public static List<Employee> initEmployees(){
        return List.of(
                Employee.builder().id(1L).name("ADHE").salary(new BigDecimal(1_000_000)).build(),
                Employee.builder().id(2L).name("WAHYU").salary(new BigDecimal(1_000_000)).build(),
                Employee.builder().id(3L).name("ARDANTO").salary(new BigDecimal(1_000_000)).build()
        );
    }

}
