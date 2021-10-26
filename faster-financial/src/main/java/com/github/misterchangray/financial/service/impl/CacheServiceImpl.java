package com.github.misterchangray.financial.service.impl;

import com.github.misterchangray.financial.service.CacheService;
import com.github.misterchangray.financial.v001.mapper.po.FinancialAccount;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CacheServiceImpl implements CacheService {
    @Autowired()
    RedissonClient redissonClient;

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

    public boolean enableRedis () {
        return Objects.nonNull(redissonClient);
    }
}
