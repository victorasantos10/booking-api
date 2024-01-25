package com.hostfully.bookingapi.models.validation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class ApiResponseErrorModel {
    private String errorField;
    private String errorDescription;

    public ApiResponseErrorModel(String errorDescription){
        this.errorDescription = errorDescription;
    }
}