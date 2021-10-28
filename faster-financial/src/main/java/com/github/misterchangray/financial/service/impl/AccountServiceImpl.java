package com.github.misterchangray.financial.service.impl;

import com.github.misterchangray.common.base.BaseEnum;
import com.github.misterchangray.common.base.BaseResponse;
import com.github.misterchangray.financial.service.AccountService;
import com.github.misterchangray.financial.service.FinancialLogService;
import com.github.misterchangray.financial.v001.consts.FinancialRedisKeys;
import com.github.misterchangray.financial.v001.mapper.po.FinancialAccount;
import com.github.misterchangray.financial.v001.mapper.po.FinancialChangesRecord;
import com.github.misterchangray.financial.v001.pojo.request.FinancialChangesRecordRequest;
import com.github.misterchangray.financial.v001.pojo.request.FinancialFreezeRequest;
import com.github.misterchangray.idservice.IDService;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    RedissonClient redissonClient;
    @Autowired
    FinancialLogService financialLogService;
    @Autowired
    IDService idService;

    public FinancialAccount getShadowAccount(String id) {
        // 构建Key对象
        RBucket<FinancialAccount> bucket = redissonClient.getBucket(FinancialRedisKeys.FINANCIAL_ACCOUNT);
        if(bucket.isExists()) {
            return bucket.get();
        }

        return null;
    }

    public FinancialAccount getUserFinancialAccount(String id) {
        if(Objects.isNull(id)) BaseResponse.ofFail(BaseEnum.INVALID_PARAM);

        FinancialAccount shadowAccount = getShadowAccount(id);
        if(Objects.isNull(shadowAccount)) {
            shadowAccount = reBuildFinancialAccount(id);
        }

        return shadowAccount;
    }


    @Override
    public BaseResponse<List<FinancialAccount>> getUserFinancialAccount(String... ids) {
        if(Objects.isNull(ids)) BaseResponse.ofFail(BaseEnum.INVALID_PARAM);

        List<FinancialAccount> res = new ArrayList<>(ids.length);
        for (String id : ids) res.add(getShadowAccount(id));

        return BaseResponse.ofSuccess(res);
    }

    @Override
    public void startInitShadowAccount() {

    }

    @Override
    public BaseResponse<Boolean> record(FinancialChangesRecordRequest... financialChangesRecord) {
        if(Objects.isNull(financialChangesRecord)) BaseResponse.ofFail(BaseEnum.INVALID_PARAM);

        for (FinancialChangesRecordRequest financialChangesRecordRequest : financialChangesRecord) {
            FinancialChangesRecord financialChangesRecord1 = buildFinancialChangesRecord(financialChangesRecordRequest);
            if(Objects.isNull(financialChangesRecord1)) BaseResponse.ofFail(BaseEnum.INVALID_PARAM);

            financialLogService.log(financialChangesRecord1);
            FinancialAccount shadowAccount = getShadowAccount(financialChangesRecordRequest.getFinancialAccountId());
            shadowAccount.setBalance(shadowAccount.getBalance().add(financialChangesRecordRequest.getAmount()));
        }

        return BaseResponse.ofSuccess(true);
    }

    @Override
    public BaseResponse<String> freeze(FinancialFreezeRequest request) {
        if(Objects.isNull(request)) {
            return BaseResponse.ofFail(BaseEnum.INVALID_PARAM);
        }
        if(request.getAmount().doubleValue() <= 0) {
            return BaseResponse.ofFail(BaseEnum.INVALID_PARAM);
        }
        FinancialAccount shadowAccount = getUserFinancialAccount(request.getFinancialAccountId());
        if(Objects.isNull(shadowAccount)) {
            return BaseResponse.ofFail(BaseEnum.INVALID_PARAM).setMsg("accountId not found!");
        }

        FinancialChangesRecord financialChangesRecord = buildFinancialChangesRecord(request, shadowAccount);
        financialLogService.log(financialChangesRecord);

        shadowAccount.setBalance(financialChangesRecord.getAmount());;
        shadowAccount.setFreeze(financialChangesRecord.getFreeze());

        return BaseResponse.ofSuccess(financialChangesRecord.getId());
    }

    @Override
    public BaseResponse<Boolean> unFreeze(String freezeId) {


        return null;
    }

    private FinancialChangesRecord buildFinancialChangesRecord(FinancialFreezeRequest request, FinancialAccount shadowAccount) {
        FinancialChangesRecord financialChangesRecord = new FinancialChangesRecord();
        financialChangesRecord.setBeforeBalance(shadowAccount.getBalance());
        financialChangesRecord.setBeforeFreeze(shadowAccount.getFreeze());
        financialChangesRecord.setAmount(shadowAccount.getBalance().subtract(request.getAmount()));
        financialChangesRecord.setFreeze(shadowAccount.getFreeze().add(request.getAmount()));
        financialChangesRecord.setSerialNumber(request.getSerialNumber());
        financialChangesRecord.setBooksId(1);
        financialChangesRecord.setCreateTime(request.getCreateTime());
        financialChangesRecord.setUpdateTime(request.getCreateTime());
        financialChangesRecord.setName(shadowAccount.getName());
        financialChangesRecord.setPhone(shadowAccount.getPhone());
        financialChangesRecord.setFinancialAccountId(request.getFinancialAccountId());
        financialChangesRecord.setRemark(request.getRemark());
        financialChangesRecord.setId(idService.getId());
        financialChangesRecord.setStatus(1);
        return financialChangesRecord;
    }

    private FinancialChangesRecord buildFinancialChangesRecord(FinancialChangesRecordRequest request) {
        FinancialAccount shadowAccount = getUserFinancialAccount(request.getFinancialAccountId());

        FinancialChangesRecord financialChangesRecord = new FinancialChangesRecord();
        financialChangesRecord.setBeforeBalance(shadowAccount.getBalance());
        financialChangesRecord.setBeforeFreeze(shadowAccount.getFreeze());
        financialChangesRecord.setAmount(request.getAmount());
        financialChangesRecord.setSerialNumber(request.getSerialNumber());
        financialChangesRecord.setBooksId(request.getBooksId());
        financialChangesRecord.setCreateTime(request.getCreateTime());
        financialChangesRecord.setUpdateTime(request.getCreateTime());
        financialChangesRecord.setName(shadowAccount.getName());
        financialChangesRecord.setPhone(shadowAccount.getPhone());
        financialChangesRecord.setFinancialAccountId(request.getFinancialAccountId());
        financialChangesRecord.setRemark(request.getRemark());
        financialChangesRecord.setId(idService.getId());
        financialChangesRecord.setStatus(1);

        return financialChangesRecord;
    }

    /**
     * 重建财务账户缓存,
     * 财务账户数据是实时查询缓存的
     * 如果缓存没有,则需要根据财务变动记录表重建财务账户数据
     *
     * @param id
     * @return
     */
    public FinancialAccount reBuildFinancialAccount(String id) {
        return null;
    }


    @Override
    public BaseResponse<List<FinancialAccount>> updateUserFinancialAccount() {
        return null;
    }

    @Override
    public BaseResponse<List<FinancialAccount>> changeBalanceUserFinancialAccount() {
        return null;
    }

    @Override
    public BaseResponse<List<FinancialAccount>> changeFreezeUserFinancialAccount() {
        return null;
    }
}
