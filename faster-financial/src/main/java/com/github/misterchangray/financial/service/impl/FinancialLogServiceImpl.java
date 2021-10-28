package com.github.misterchangray.financial.service.impl;

import com.github.misterchangray.common.base.BaseResponse;
import com.github.misterchangray.financial.dao.FinancialLogMapper;
import com.github.misterchangray.financial.service.FinancialLogService;
import com.github.misterchangray.financial.v001.mapper.po.FinancialChangesRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FinancialLogServiceImpl implements FinancialLogService {
    @Autowired
    private FinancialLogMapper financialLogMapper;

    @Override
    public BaseResponse log(FinancialChangesRecord changesRecord) {
        financialLogMapper.insert(changesRecord);
        return BaseResponse.ofSuccess(changesRecord);
    }
}
