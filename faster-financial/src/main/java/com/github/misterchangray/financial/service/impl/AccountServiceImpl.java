package com.github.misterchangray.financial.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.github.misterchangray.common.base.BaseEnum;
import com.github.misterchangray.common.base.BaseResponse;
import com.github.misterchangray.common.exceptions.BizException;
import com.github.misterchangray.financial.v001.intf.CacheService;
import com.github.misterchangray.financial.v001.intf.FinancialAccountService;
import com.github.misterchangray.financial.v001.intf.FinancialChangesService;
import com.github.misterchangray.financial.v001.mapper.intf.FinancialAccountMapper;
import com.github.misterchangray.financial.v001.mapper.po.FinancialAccount;
import com.github.misterchangray.financial.v001.mapper.po.FinancialChangesRecord;
import com.github.misterchangray.financial.v001.pojo.request.*;
import com.github.misterchangray.financial.v001.pojo.response.FinancialChangesRecordResponse;
import com.github.misterchangray.idservice.IDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class AccountServiceImpl implements FinancialAccountService {
    @Autowired
    private FinancialChangesService financialLogService;
    @Autowired
    private IDService idService;
    @Autowired
    private CacheService cacheService;
    @Autowired
    private FinancialAccountMapper financialAccountMapper;



    /**
     * 从缓存或数据库中获取账户
     * @param id
     * @return
     */
    public FinancialAccount getUserFinancialAccount(String id) {
        if(Objects.isNull(id)) BaseResponse.ofFail(BaseEnum.INVALID_PARAM);

        FinancialAccount shadowAccount = cacheService.getShadowAccount(id);
        if(Objects.isNull(shadowAccount)) {
            shadowAccount = reBuildFinancialAccount(id);
        }

        return shadowAccount;
    }


    @Override
    public BaseResponse<List<FinancialAccount>> getUserFinancialAccount(String... ids) {
        if(Objects.isNull(ids)) BaseResponse.ofFail(BaseEnum.INVALID_PARAM);

        List<FinancialAccount> res = new ArrayList<>(ids.length);
        for (String id : ids) res.add(this.getUserFinancialAccount(id));

        return BaseResponse.ofSuccess(res);
    }

    @Override
    public void startInitShadowAccount() {

    }

    @Override
    public BaseResponse<List<FinancialChangesRecordResponse>> income(FinancialChangesRecordRequest... financialChangesRecords) {
        if(Objects.isNull(financialChangesRecords)) BaseResponse.ofFail(BaseEnum.INVALID_PARAM);

        List<FinancialChangesRecordResponse> res = new ArrayList<>();
        for (FinancialChangesRecordRequest request : financialChangesRecords) {
            FinancialChangesRecordResponse tmp = new FinancialChangesRecordResponse();
            FinancialAccount shadowAccount = getUserFinancialAccount(request.getFinancialAccountId());
            if (Objects.isNull(shadowAccount)) {
                tmp.fail(BaseEnum.INVALID_USER);
                res.add(tmp);
                continue;
            }

            FinancialChangesRecord financialChangesRecord = buildFinancialChangesRecord(request);
            financialLogService.addRecord(financialChangesRecord);
            shadowAccount.setBalance(shadowAccount.getBalance().add(request.getAmount()));

            tmp.setFinancialAccountId(request.getFinancialAccountId());
            tmp.setRequestSerialNumber(request.getSerialNumber());
            tmp.setSerialNumber(financialChangesRecord.getSerialNumber());
            tmp.success(null);
            res.add(tmp);
        }

        return BaseResponse.ofSuccess(res);
    }

    @Override
    public BaseResponse<List<String>> outlay(FinancialChangesRecordRequest... financialChangesRecord) {
        if(Objects.isNull(financialChangesRecord)) BaseResponse.ofFail(BaseEnum.INVALID_PARAM);


        return null;
    }

    @Override
    public BaseResponse<String> transfer(FinancialChangesRecordRequest... financialChangesRecord) {
        return null;
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
            return BaseResponse.<String>ofFail(BaseEnum.INVALID_PARAM).setMsg("accountId not found!");
        }

        FinancialChangesRecord financialChangesRecord = buildFinancialChangesRecord(request, shadowAccount);
        financialLogService.addRecord(financialChangesRecord);

        shadowAccount.setBalance(financialChangesRecord.getAmount());;
        shadowAccount.setFreeze(financialChangesRecord.getFreeze());

        return BaseResponse.ofSuccess(financialChangesRecord.getId());
    }

    @Override
    public BaseResponse<String> unfreeze(FinancialUnFreezeRequest financialUnFreezeRequest) {
        return null;
    }

    @Override
    public BaseResponse<String> done(FinancialUnFreezeRequest financialUnFreezeRequest, OperationUnFreeze operationUnFreeze) {
        return null;
    }

    @Override
    public BaseResponse<FinancialAccount> addOrEdit(FinancialAccountRequest financialAccountRequest) {
        if(Objects.isNull(financialAccountRequest) ) {
            return BaseResponse.ofFail(BaseEnum.INVALID_PARAM);
        }
        financialAccountRequest.checkParams();

        FinancialAccount financialAccount = FinancialAccount.valueOf(financialAccountRequest);
        BaseResponse<FinancialAccount> byPhone = this.getByPhone(financialAccount.getPhone());
        if(byPhone.isSuccess()) {
            return BaseResponse.ofFail(BaseEnum.ALREADY_EXISTS);
        }
        if(Objects.nonNull(financialAccount.getId())) {
            financialAccountMapper.updateById(financialAccount);
        } else {
            financialAccount.setPermissions(FinancialAccount.PERMISSION_ALL);
            financialAccount.setId(idService.getId());
            financialAccount.setStatus(FinancialAccount.STATUS_OF_ENABLE);
            financialAccountMapper.insert(financialAccount);
        }

        return BaseResponse.ofSuccess(financialAccount);
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
    public BaseResponse<FinancialAccount> getByPhone(String phone) {
        FinancialAccount financialAccount = financialAccountMapper.getByPhone(phone);
        if(Objects.isNull(financialAccount)) {
            return BaseResponse.ofFail(BaseEnum.NOT_FOUND);
        }
        return BaseResponse.ofSuccess(financialAccount);
    }


    @Override
    public BaseResponse<FinancialAccount> disabled(String id) {
        return changeStatus(id, FinancialAccount.STATUS_OF_DISABLE);
    }


    public BaseResponse<FinancialAccount> changeStatus(String id, int status) {
        if(!StringUtils.hasLength(id)) {
            throw new BizException(BaseEnum.INVALID_PARAM);
        }
        FinancialAccount financialAccount = financialAccountMapper.selectById(id);
        if(Objects.isNull(financialAccount)) {
            return BaseResponse.ofFail(BaseEnum.NOT_FOUND);
        }

        financialAccount.setStatus(status);
        financialAccountMapper.changeStatus(financialAccount);
        return BaseResponse.ofSuccess(financialAccount);
    }

    @Override
    public BaseResponse<FinancialAccount> enable(String id) {
        return changeStatus(id, FinancialAccount.STATUS_OF_ENABLE);
    }
}
