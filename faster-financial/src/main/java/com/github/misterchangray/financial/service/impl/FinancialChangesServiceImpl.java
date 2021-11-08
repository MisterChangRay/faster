package com.github.misterchangray.financial.service.impl;

import com.github.misterchangray.common.base.BaseResponse;
import com.github.misterchangray.financial.v001.intf.FinancialChangesService;
import com.github.misterchangray.financial.v001.mapper.intf.FinancialChangesMapper;
import com.github.misterchangray.financial.v001.mapper.po.FinancialChangesRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FinancialChangesServiceImpl implements FinancialChangesService {
    @Autowired
    private FinancialChangesMapper financialChangesMapper;


    @Override
    public BaseResponse<Boolean> addRecord(FinancialChangesRecord record) {
        int i = financialChangesMapper.insert(record);
        return BaseResponse.ofSuccess(true);
    }
}
