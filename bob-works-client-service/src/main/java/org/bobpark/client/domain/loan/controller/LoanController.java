package org.bobpark.client.domain.loan.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import org.bobpark.client.domain.loan.feign.model.CreateLoanRequest;
import org.bobpark.client.domain.loan.feign.model.LoanRepaymentHistoryResponse;
import org.bobpark.client.domain.loan.feign.model.LoanResponse;
import org.bobpark.client.domain.loan.service.LoanService;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "loan")
public class LoanController {
    private final LoanService loanService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "")
    public LoanResponse createLoan(@RequestBody CreateLoanRequest createRequest) {
        return loanService.createLoan(createRequest);
    }

    @GetMapping(path = "all")
    public List<LoanResponse> getAll() {
        return loanService.getAll();
    }

    @GetMapping(path = "{loanId:\\d+}")
    public LoanResponse getDetail(@PathVariable long loanId) {
        return loanService.getDetail(loanId);
    }

    @PostMapping(path = "repay/{repayId:\\d+}")
    public LoanRepaymentHistoryResponse repay(@PathVariable long repayId) {
        return loanService.repay(repayId);
    }
}
