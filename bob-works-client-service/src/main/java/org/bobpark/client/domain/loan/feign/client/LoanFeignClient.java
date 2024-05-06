package org.bobpark.client.domain.loan.feign.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.bobpark.client.domain.loan.feign.model.CreateLoanRequest;
import org.bobpark.client.domain.loan.feign.model.LoanRepaymentHistoryResponse;
import org.bobpark.client.domain.loan.feign.model.LoanResponse;

@FeignClient(name = "finance-service")
public interface LoanFeignClient {

    @PostMapping(path = "v1/loan")
    LoanResponse createLoan(@RequestBody CreateLoanRequest createRequest);

    @GetMapping(path = "v1/loan/all")
    List<LoanResponse> getAll();

    @GetMapping(path = "v1/loan/{loanId:\\d+}")
    LoanResponse getDetail(@PathVariable long loanId);

    @PostMapping(path = "v1/loan/repay/{repayId:\\d+}")
    LoanRepaymentHistoryResponse repay(@PathVariable long repayId);
}
