package com.nagarro.assignment.service.impl;

import com.nagarro.assignment.dto.QueryParamsDto;
import com.nagarro.assignment.dto.StatementDbDto;
import com.nagarro.assignment.repository.AccountStatementRepository;
import com.nagarro.assignment.service.AccountStatementService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountStatementServiceImpl implements AccountStatementService {
    @Autowired
    private AccountStatementRepository accountStatementRepository;

    @Override
    public List<StatementDbDto> getAccountStatements(Long accountId, QueryParamsDto queryParamsDto) {
        Optional<List<StatementDbDto>> accountStatements = accountStatementRepository.getAccountStatements(accountId);
        if (accountStatements.isPresent()) {
            List<StatementDbDto> statementDbDtos = accountStatements.get();
            if (queryParamsDto.getFromDate() == null && queryParamsDto.getToDate() == null && queryParamsDto.getFromAmount() == null && queryParamsDto.getToAmount() == null) {
                   return handleNoParamsSent(statementDbDtos);
            } else if(queryParamsDto.getFromDate() != null && queryParamsDto.getToDate() != null && queryParamsDto.getFromAmount() != null && queryParamsDto.getToAmount() != null){
               return handleAllParamsSent(queryParamsDto, statementDbDtos);
            } else if(queryParamsDto.getFromDate() != null && queryParamsDto.getToDate() != null && queryParamsDto.getFromAmount() == null && queryParamsDto.getToAmount() == null){
                return handleDateParamsSent(queryParamsDto, statementDbDtos);
            } else if(queryParamsDto.getFromDate() == null && queryParamsDto.getToDate() == null && queryParamsDto.getFromAmount() != null && queryParamsDto.getToAmount() != null){
                return handleAmountParamsSent(queryParamsDto, statementDbDtos);
            }
        }
        return Collections.emptyList();
    }

    @Override
    public List<StatementDbDto> getAccountStatementsForUser(Long accountId) {
        Optional<List<StatementDbDto>> accountStatements = accountStatementRepository.getAccountStatements(accountId);
        if (accountStatements.isPresent()) {
            List<StatementDbDto> statementDbDtos = accountStatements.get();
            return handleNoParamsSent(statementDbDtos);
        }
        return Collections.emptyList();
    }

    private List<StatementDbDto> handleAmountParamsSent(QueryParamsDto queryParamsDto, List<StatementDbDto> statementDbDtos) {
        return statementDbDtos.stream().filter(statementDbDto ->
                (statementDbDto.getAmount() >= Double.parseDouble(queryParamsDto.getFromAmount())
                        && statementDbDto.getAmount() <= Double.parseDouble(queryParamsDto.getToAmount()))

        ).map(statementDbDto -> {
            StatementDbDto mapped = new StatementDbDto();
            mapped.setAccountId(statementDbDto.getAccountId());
            mapped.setAccountType(statementDbDto.getAccountType());
            mapped.setAccountNumber(DigestUtils.sha256Hex(statementDbDto.getAccountNumber()));
            mapped.setDate(statementDbDto.getDate());
            mapped.setAmount(statementDbDto.getAmount());
            return mapped;
        }).toList();
    }

    private List<StatementDbDto> handleDateParamsSent(QueryParamsDto queryParamsDto, List<StatementDbDto> statementDbDtos) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return statementDbDtos.stream().filter(statementDbDto ->
                ((statementDbDto.getDate().isEqual(LocalDate.parse(queryParamsDto.getFromDate(), formatter))
                        || statementDbDto.getDate().isAfter(LocalDate.parse(queryParamsDto.getFromDate(), formatter)))
                        && (statementDbDto.getDate().isEqual(LocalDate.parse(queryParamsDto.getToDate(), formatter))
                        || statementDbDto.getDate().isBefore(LocalDate.parse(queryParamsDto.getToDate(), formatter))))

        ).map(statementDbDto -> {
            StatementDbDto mapped = new StatementDbDto();
            mapped.setAccountId(statementDbDto.getAccountId());
            mapped.setAccountType(statementDbDto.getAccountType());
            mapped.setAccountNumber(DigestUtils.sha256Hex(statementDbDto.getAccountNumber()));
            mapped.setDate(statementDbDto.getDate());
            mapped.setAmount(statementDbDto.getAmount());
            return mapped;
        }).toList();
    }


     private List<StatementDbDto> handleNoParamsSent(List<StatementDbDto> statementDbDtos) {
        List<StatementDbDto> sortedList = statementDbDtos.stream().sorted(Comparator.comparing(StatementDbDto::getDate).reversed()).toList();
        if (!sortedList.isEmpty()) {
            StatementDbDto lastStatement = sortedList.get(0);
            LocalDate lastDate = lastStatement.getDate();
            LocalDate threeMonthsAgo = lastDate.minusMonths(3);

            return sortedList.stream().filter(statementDbDto -> statementDbDto.getDate().isAfter(threeMonthsAgo)
                    || statementDbDto.getDate().isEqual(threeMonthsAgo)).map(statementDbDto -> {
                StatementDbDto mapped = new StatementDbDto();
                mapped.setAccountId(statementDbDto.getAccountId());
                mapped.setAccountType(statementDbDto.getAccountType());
                mapped.setAccountNumber(DigestUtils.sha256Hex(statementDbDto.getAccountNumber()));
                mapped.setDate(statementDbDto.getDate());
                mapped.setAmount(statementDbDto.getAmount());
                return mapped;
            }).toList();
        }
        return Collections.emptyList();
    }

    private List<StatementDbDto> handleAllParamsSent(QueryParamsDto queryParamsDto, List<StatementDbDto> statementDbDtos){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return statementDbDtos.stream().filter(statementDbDto ->
                ((statementDbDto.getDate().isEqual(LocalDate.parse(queryParamsDto.getFromDate(), formatter))
                        || statementDbDto.getDate().isAfter(LocalDate.parse(queryParamsDto.getFromDate(), formatter)))
                        && (statementDbDto.getDate().isEqual(LocalDate.parse(queryParamsDto.getToDate(), formatter))
                        || statementDbDto.getDate().isBefore(LocalDate.parse(queryParamsDto.getToDate(), formatter)))
                && (statementDbDto.getAmount() >= Double.parseDouble(queryParamsDto.getFromAmount())
                        && statementDbDto.getAmount() <= Double.parseDouble(queryParamsDto.getToAmount())))

        ).map(statementDbDto -> {
            StatementDbDto mapped = new StatementDbDto();
            mapped.setAccountId(statementDbDto.getAccountId());
            mapped.setAccountType(statementDbDto.getAccountType());
            mapped.setAccountNumber(DigestUtils.sha256Hex(statementDbDto.getAccountNumber()));
            mapped.setDate(statementDbDto.getDate());
            mapped.setAmount(statementDbDto.getAmount());
            return mapped;
        }).toList();
    }
}

