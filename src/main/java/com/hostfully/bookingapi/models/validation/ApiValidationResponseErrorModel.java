package com.hostfully.bookingapi.models.validation;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiValidationResponseErrorModel {
    private String errorField;
    private String errorDescription;
}


