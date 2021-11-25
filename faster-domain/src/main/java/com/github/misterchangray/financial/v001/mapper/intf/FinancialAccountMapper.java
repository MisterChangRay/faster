package com.github.misterchangray.financial.v001.mapper.intf;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.misterchangray.financial.v001.mapper.po.FinancialAccount;

public interface FinancialAccountMapper extends BaseMapper<FinancialAccount> {
    FinancialAccount getByPhone(String phone);

    void changeStatus(FinancialAccount financialAccount);
}
