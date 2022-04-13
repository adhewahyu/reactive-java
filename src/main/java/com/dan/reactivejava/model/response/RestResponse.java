package com.dan.reactivejava.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestResponse {

    private Object data;
    private String message;
    private Boolean result;

}
