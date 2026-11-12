package com.atlas.bank.atlas_bank.account.service;

import com.atlas.bank.atlas_bank.account.dto.DashboardResponse;
import com.atlas.bank.atlas_bank.account.model.Account;
import com.atlas.bank.atlas_bank.transaction.dto.TransactionMapper;
import com.atlas.bank.atlas_bank.transaction.dto.TransactionResponse;
import com.atlas.bank.atlas_bank.transaction.service.ITransactionQueryService;
import com.atlas.bank.atlas_bank.transaction.service.fraud.FraudCheckResult;
import com.atlas.bank.atlas_bank.transaction.service.fraud.FraudChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountDashboardFacade {

    private final IAccountService accountService;
    private final ITransactionQueryService transactionQueryService;
    private final FraudChecker fraudChecker;
    private final TransactionMapper transactionMapper;

    public DashboardResponse getDashboard(Long accountId){
        Account account = accountService.findById(accountId);

        List<TransactionResponse> transactions = transactionQueryService
                .getByAccountId(accountId)
                .stream()
                .map(transactionMapper::toResponse)
                .toList();

        FraudCheckResult fraudCheckResult = fraudChecker.check(accountId, account.getBalance());

        return DashboardResponse.builder()
                .accountId(accountId)
                .accountNumber(account.getAccountNumber())
                .ownerName(account.getOwnerName())
                .type(account.getType().name())
                .balance(account.getBalance())
                .status(account.getStatus().name())
                .recentTransactions(transactions)
                .build();
    }
}
