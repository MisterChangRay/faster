package com.github.misterchangray.financial.service.impl;

import com.github.misterchangray.financial.service.CacheService;
import com.github.misterchangray.financial.v001.mapper.po.FinancialAccount;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CacheServiceImpl implements CacheService {
    @Override
    public int save(FinancialAccount financialAccount) {
        return 0;
    }

    @Override
    public int increment(String id) {
        return 0;
    }

    @Override
    public int decrement(String id) {
        return 0;
    }

    @Override
    public FinancialAccount getAccount(String id) {
        return null;
    }

    @Override
    public List<FinancialAccount> getAccount(String... ids) {
        return null;
    }
}
