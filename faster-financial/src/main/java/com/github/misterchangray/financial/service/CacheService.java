package com.github.misterchangray.financial.service;

import com.github.misterchangray.financial.v001.mapper.po.FinancialAccount;

import java.util.List;

public interface CacheService {
    /**
     * 缓存
     * @param financialAccount
     * @return
     */
    int save(FinancialAccount financialAccount);

    /**
     *  增加 原子操作
     * @param id
     * @return
     */
    int increment(String id);

    /**
     * 减少 原子操作
     * @param id
     * @return
     */
    int decrement(String id);


    FinancialAccount getAccount(String id);

    List<FinancialAccount> getAccount(String ...ids);


}
