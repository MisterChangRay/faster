package com.github.misterchangray.financial.v001.mapper.intf;

import com.github.misterchangray.financial.v001.mapper.po.FinancialAccount;

public interface FinancialAccountMapper {
    boolean insert(FinancialAccount financialAccount);
    boolean udpate(FinancialAccount financialAccount);
}
