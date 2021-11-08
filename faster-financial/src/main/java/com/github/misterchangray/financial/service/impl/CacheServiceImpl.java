package com.github.misterchangray.financial.service.impl;

import com.github.misterchangray.financial.v001.intf.CacheService;
import com.github.misterchangray.financial.v001.mapper.po.FinancialAccount;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
public class CacheServiceImpl implements CacheService {
    @Autowired()
    RedissonClient redissonClient;


    @Override
    public int cache(FinancialAccount financialAccount) {
        return 0;
    }

    @Override
    public boolean increment(String id, BigDecimal amount) {
        return false;
    }

    @Override
    public boolean decrement(String id, BigDecimal amount) {
        return false;
    }

    @Override
    public FinancialAccount getShadowAccount(String id) {
        return null;
    }

    @Override
    public List<FinancialAccount> getAccounts(String... ids) {
        return null;
    }

    public boolean enableRedis () {
        return Objects.nonNull(redissonClient);
    }
}
