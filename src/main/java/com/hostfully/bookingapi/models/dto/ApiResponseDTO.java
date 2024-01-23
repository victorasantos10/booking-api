package com.hostfully.bookingapi.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
public class ApiResponseDTO<T>  {
    private T data;
    private ArrayList<Object> errorList;

    public ApiResponseDTO(T data){
        this.data = data;
    }

    public ApiResponseDTO(T data, ArrayList<Object> errorList){
        this.data = data;
        this.errorList = errorList;
    }
}
