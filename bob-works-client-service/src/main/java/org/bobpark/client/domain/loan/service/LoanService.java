package org.bobpark.client.domain.loan.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import org.bobpark.client.domain.loan.feign.client.LoanFeignClient;
import org.bobpark.client.domain.loan.feign.model.CreateLoanRequest;
import org.bobpark.client.domain.loan.feign.model.LoanRepaymentHistoryResponse;
import org.bobpark.client.domain.loan.feign.model.LoanResponse;

@Slf4j
@RequiredArgsConstructor
@Service
public class LoanService {

    private final LoanFeignClient loanClient;

    public LoanResponse createLoan(CreateLoanRequest createRequest) {
        return loanClient.createLoan(createRequest);
    }

    public List<LoanResponse> getAll() {
        return loanClient.getAll();
    }

    public LoanResponse getDetail(long loanId) {
        return loanClient.getDetail(loanId);
    }

    public LoanRepaymentHistoryResponse repay(long repayId) {
        return loanClient.repay(repayId);
    }

}
