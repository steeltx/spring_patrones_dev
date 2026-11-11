package com.atlas.bank.atlas_bank.transaction.service.fee;

import com.atlas.bank.atlas_bank.account.model.AccountType;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Order(0)
public class DefaultFeeCalculator implements FeeCalculator{
    @Override
    public boolean supports(AccountType accountType) {
        return !AccountType.SAVINGS.equals(accountType) && !AccountType.CHECKING.equals(accountType);
    }

    @Override
    public BigDecimal calculate(BigDecimal amount) {
        return BigDecimal.ZERO;
    }
}
