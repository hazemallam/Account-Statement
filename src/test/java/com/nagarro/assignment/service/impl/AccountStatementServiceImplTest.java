package com.nagarro.assignment.service.impl;

import com.nagarro.assignment.dto.QueryParamsDto;
import com.nagarro.assignment.dto.StatementDbDto;
import com.nagarro.assignment.repository.AccountStatementRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountStatementServiceImplTest {


    @Mock
    private AccountStatementRepository accountStatementRepository;

    @InjectMocks
    private AccountStatementServiceImpl underTest;



    @Test
    void should_get_account_statement_empty(){
        when(accountStatementRepository.getAccountStatements(anyLong())).thenReturn(Optional.of(Collections.emptyList()));
        QueryParamsDto queryParamsDto = new QueryParamsDto();
        List<StatementDbDto> result = underTest.getAccountStatements(0L, queryParamsDto);

        Assertions.assertThat(result).isEmpty();
    }

    @Test
     void testGetAccountStatements_NoParamsSent() {
        Long accountId = 1L;
        QueryParamsDto queryParamsDto = new QueryParamsDto();

        List<StatementDbDto> statementDbDtos = new ArrayList<>();
        statementDbDtos.add(new StatementDbDto(1L, "Savings", DigestUtils.sha256Hex("1234567890"), LocalDate.now(), 100.0));
        statementDbDtos.add(new StatementDbDto(1L, "Current", DigestUtils.sha256Hex("0987654321"), LocalDate.now().minusMonths(1), 200.0));

        when(accountStatementRepository.getAccountStatements(accountId)).thenReturn(Optional.of(statementDbDtos));

        List<StatementDbDto> result = underTest.getAccountStatements(accountId, queryParamsDto);

        assertEquals(2, result.size());
        verify(accountStatementRepository, times(1)).getAccountStatements(accountId);
    }

    @Test
    void testGetAccountStatements_AllParamsSent() {
        Long accountId = 1L;
        QueryParamsDto queryParamsDto = new QueryParamsDto();
        queryParamsDto.setFromDate("24.10.2020");
        queryParamsDto.setToDate("24.01.2021");
        queryParamsDto.setFromAmount("350.793682741483");
        queryParamsDto.setToAmount("564.982890505824");

        List<StatementDbDto> statementDbDtos = new ArrayList<>();
        statementDbDtos.add(new StatementDbDto(1L, "Savings", DigestUtils.sha256Hex("1234567890"), LocalDate.parse("2021-01-24"), 564.982890505824));
        statementDbDtos.add(new StatementDbDto(1L, "Current", DigestUtils.sha256Hex("0987654321"), LocalDate.parse("2020-11-29"), 350.793682741483));

        when(accountStatementRepository.getAccountStatements(accountId)).thenReturn(Optional.of(statementDbDtos));

        List<StatementDbDto> result = underTest.getAccountStatements(accountId, queryParamsDto);

        assertEquals(2, result.size());
        verify(accountStatementRepository, times(1)).getAccountStatements(accountId);
    }

    @Test
    void testGetAccountStatements_DateParamsSent() {
        Long accountId = 1L;
        QueryParamsDto queryParamsDto = new QueryParamsDto();
        queryParamsDto.setFromDate("24.10.2020");
        queryParamsDto.setToDate("24.01.2021");
        List<StatementDbDto> statementDbDtos = new ArrayList<>();
        statementDbDtos.add(new StatementDbDto(1L, "Savings", DigestUtils.sha256Hex("1234567890"), LocalDate.parse("2021-01-24"), 564.982890505824));
        statementDbDtos.add(new StatementDbDto(1L, "Current", DigestUtils.sha256Hex("0987654321"), LocalDate.parse("2020-11-29"), 350.793682741483));

        when(accountStatementRepository.getAccountStatements(accountId)).thenReturn(Optional.of(statementDbDtos));

        List<StatementDbDto> result = underTest.getAccountStatements(accountId, queryParamsDto);

        assertEquals(2, result.size());
        verify(accountStatementRepository, times(1)).getAccountStatements(accountId);
    }

    @Test
    void testGetAccountStatements_AmountParamsSent() {
        Long accountId = 1L;
        QueryParamsDto queryParamsDto = new QueryParamsDto();
        queryParamsDto.setFromAmount("350.793682741483");
        queryParamsDto.setToAmount("564.982890505824");
        List<StatementDbDto> statementDbDtos = new ArrayList<>();
        statementDbDtos.add(new StatementDbDto(1L, "Savings", DigestUtils.sha256Hex("1234567890"), LocalDate.parse("2021-01-24"), 564.982890505824));
        statementDbDtos.add(new StatementDbDto(1L, "Current", DigestUtils.sha256Hex("0987654321"), LocalDate.parse("2020-11-29"), 350.793682741483));

        when(accountStatementRepository.getAccountStatements(accountId)).thenReturn(Optional.of(statementDbDtos));

        List<StatementDbDto> result = underTest.getAccountStatements(accountId, queryParamsDto);

        assertEquals(2, result.size());
        verify(accountStatementRepository, times(1)).getAccountStatements(accountId);
    }

    @Test
    void testGetAccountStatementsForUser() {
        Long accountId = 1L;

        List<StatementDbDto> statementDbDtos = new ArrayList<>();
        statementDbDtos.add(new StatementDbDto(1L, "Savings", "1234567890", LocalDate.now(), 100.0));
        statementDbDtos.add(new StatementDbDto(1L, "Current","0987654321", LocalDate.now().minusMonths(1), 200.0));

        when(accountStatementRepository.getAccountStatements(accountId)).thenReturn(Optional.of(statementDbDtos));

        List<StatementDbDto> result = underTest.getAccountStatementsForUser(accountId);

        assertEquals(2, result.size());
        verify(accountStatementRepository, times(1)).getAccountStatements(accountId);
    }

    @Test
    void testGetAccountStatementsForUser_NoStatements() {
        Long accountId = 1L;

        when(accountStatementRepository.getAccountStatements(accountId)).thenReturn(Optional.empty());

        List<StatementDbDto> result = underTest.getAccountStatementsForUser(accountId);

        assertEquals(Collections.emptyList(), result);
        verify(accountStatementRepository, times(1)).getAccountStatements(accountId);
    }

}