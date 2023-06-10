package com.nagarro.assignment.repository.impl;

import com.nagarro.assignment.dto.StatementDbDto;
import com.nagarro.assignment.repository.AccountStatementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Repository
public class AccountStatementRepositoryImpl implements AccountStatementRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public Optional<List<StatementDbDto>> getAccountStatements(Long accountId) {
        String sql = "SELECT st.account_id, st.datefield, st.amount, ac.account_type, ac.account_number FROM statement st JOIN account ac on st.account_id = ac.id " +
                " WHERE (st.account_id = :accountId)" ;

        MapSqlParameterSource mapParameters = new MapSqlParameterSource();
        mapParameters.addValue("accountId", accountId);
        List<StatementDbDto> result = (new NamedParameterJdbcTemplate(jdbcTemplate)).query(sql, mapParameters, mapResponse());
        return Optional.of(result);
    }

    private RowMapper<StatementDbDto> mapResponse(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
       return  ((rs, rowNum) -> {
           StatementDbDto statementDbDto = new StatementDbDto();
           statementDbDto.setAccountId(rs.getLong("account_id"));
           statementDbDto.setDate(LocalDate.parse(rs.getString("datefield"), formatter));
           statementDbDto.setAccountNumber(rs.getString("account_number"));
           statementDbDto.setAmount(Double.valueOf(rs.getString("amount")));
           statementDbDto.setAccountType(rs.getString("account_type"));
           return statementDbDto;
       });
    }
}
