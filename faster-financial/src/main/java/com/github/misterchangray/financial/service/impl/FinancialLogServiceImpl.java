package com.github.misterchangray.financial.service.impl;

import com.github.misterchangray.common.base.BaseResponse;
import com.github.misterchangray.financial.service.FinancialLogService;
import com.github.misterchangray.financial.v001.mapper.po.FinancialChangesRecord;
import org.springframework.stereotype.Component;

@Component
public class FinancialLogServiceImpl implements FinancialLogService {
    @Override
    public BaseResponse log(FinancialChangesRecord changesRecord) {
        return null;
    }
}
