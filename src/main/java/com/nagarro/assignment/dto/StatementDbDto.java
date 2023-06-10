package com.nagarro.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatementDbDto {
    private long accountId;
    private String accountType;
    private String accountNumber;
    private LocalDate date;
    private Double amount;
}
