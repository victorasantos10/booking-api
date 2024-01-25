package com.hostfully.bookingapi.models.dto;

import com.hostfully.bookingapi.models.validation.ApiResponseErrorModel;
import com.hostfully.bookingapi.models.validation.ApiValidationResponseErrorModel;
import lombok.Data;

import java.util.ArrayList;

@Data
public class ApiResponseDTO<T>  {
    private T data;
    private ArrayList<ApiResponseErrorModel> errorList;

    public ApiResponseDTO(T data){
        this.data = data;
    }

    public ApiResponseDTO(T data, ArrayList<ApiResponseErrorModel> errorList){
        this.data = data;
        this.errorList = errorList;
    }

}
