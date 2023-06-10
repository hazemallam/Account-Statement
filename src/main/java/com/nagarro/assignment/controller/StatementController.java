package com.nagarro.assignment.controller;

import com.nagarro.assignment.dto.QueryParamsDto;
import com.nagarro.assignment.dto.StatementDbDto;
import com.nagarro.assignment.exceptions.UnauthorizedException;
import com.nagarro.assignment.service.AccountStatementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class StatementController {

    @Autowired
    private AccountStatementService accountStatementService;
    @GetMapping("/admin/statement/{accountId}")
    ResponseEntity<List<StatementDbDto>> getStatement(
            @PathVariable Long accountId,
            @ModelAttribute QueryParamsDto queryParamsDto
            ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))){
            return ResponseEntity.ok(accountStatementService.getAccountStatements(accountId, queryParamsDto));

        }
        else{
            throw new UnauthorizedException("Unauthorized");
        }
    }

    @GetMapping("/user/statement/{accountId}")
    ResponseEntity<List<StatementDbDto>> getStatementUser(
            @PathVariable Long accountId
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER") || a.getAuthority().equals("ROLE_ADMIN"))) {
            return ResponseEntity.ok(accountStatementService.getAccountStatementsForUser(accountId));
        }
        else{
            throw new UnauthorizedException("Unauthorized");
        }
    }

}
