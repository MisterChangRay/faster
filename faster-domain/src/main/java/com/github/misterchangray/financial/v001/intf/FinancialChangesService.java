package com.github.misterchangray.financial.v001.intf;

import com.github.misterchangray.common.base.BaseResponse;
import com.github.misterchangray.financial.v001.mapper.po.FinancialAccount;
import com.github.misterchangray.financial.v001.mapper.po.FinancialChangesRecord;

import java.math.BigDecimal;
import java.util.List;


/**
 * 记录账户变动详情
 */
public interface FinancialChangesService {



    /**
     * 记录 账户变动详情
     * @param record
     * @return
     */
    BaseResponse<Boolean> addRecord(FinancialChangesRecord record);




}
