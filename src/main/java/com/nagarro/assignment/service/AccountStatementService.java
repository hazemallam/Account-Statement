package com.nagarro.assignment.service;

import com.nagarro.assignment.dto.QueryParamsDto;
import com.nagarro.assignment.dto.StatementDbDto;

import java.util.List;

public interface AccountStatementService {
    List<StatementDbDto> getAccountStatements(Long accountId, QueryParamsDto queryParamsDto);

    List<StatementDbDto> getAccountStatementsForUser(Long accountId);
}
