package com.github.misterchangray.financial.service;


import com.github.misterchangray.common.base.BaseResponse;
import com.github.misterchangray.financial.v001.mapper.po.FinancialChangesRecord;

/**
 * 日志记录服务
 * 在进行任何操作前必须先进行日志记录
 */
public interface FinancialLogService {

    /**
     * 账户变动记录
     * @param changesRecord
     * @return
     */
    BaseResponse log(FinancialChangesRecord changesRecord) ;
}
