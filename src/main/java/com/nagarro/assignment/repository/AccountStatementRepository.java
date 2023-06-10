package com.nagarro.assignment.repository;

import com.nagarro.assignment.dto.StatementDbDto;

import java.util.List;
import java.util.Optional;

public interface AccountStatementRepository {
    Optional<List<StatementDbDto>> getAccountStatements(Long accountId);
}
