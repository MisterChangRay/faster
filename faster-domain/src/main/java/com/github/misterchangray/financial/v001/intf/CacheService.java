package com.github.misterchangray.financial.v001.intf;

import com.github.misterchangray.financial.v001.mapper.po.FinancialAccount;

import java.math.BigDecimal;
import java.util.List;

public interface CacheService {
    /**
     * 缓存
     * @param financialAccount
     * @return
     */
    int cache(FinancialAccount financialAccount);

    /**
     *  增加 原子操作
     * @param id
     * @return
     */
    boolean increment(String id, BigDecimal amount);

    /**
     * 减少 原子操作
     * @param id
     * @return
     */
    boolean decrement(String id, BigDecimal amount);


    FinancialAccount getShadowAccount(String id);

    List<FinancialAccount> getAccounts(String... ids);


}
