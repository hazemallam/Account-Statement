package com.nagarro.assignment.dto;

import lombok.Data;

@Data
public class QueryParamsDto {
    private String fromDate;
    private String toDate;
    private String fromAmount;
    private String toAmount;
}
