package com.github.misterchangray.financial.v001.intf;

import com.github.misterchangray.common.base.BaseResponse;
import com.github.misterchangray.financial.v001.mapper.po.FinancialAccount;
import com.github.misterchangray.financial.v001.pojo.request.*;
import com.github.misterchangray.financial.v001.pojo.response.FinancialChangesRecordResponse;

import java.util.List;

public interface FinancialAccountService {




    /**
     * 开始初始化影子账号
     * 这里将会自动初始化影子账户
     * 根据账户活跃度 优先级进行初始化
     */
    void startInitShadowAccount();


    /**
     * 账户收入
     * 收入将会立即入账
     *
     * 账户收入可以使用同步或者异步进行入账操作
     *
     * 需要注意的是同一笔入账需要使用同一个流水号
     *
     * @param financialChangesRecord
     * @return
     */
    BaseResponse<List<FinancialChangesRecordResponse>> income(FinancialChangesRecordRequest ...financialChangesRecord);

    /**
     * 账户支出
     * 支出将会立即从账户扣减
     * @param financialChangesRecord
     * @return
     */
    BaseResponse<List<String>> outlay(FinancialChangesRecordRequest ...financialChangesRecord);

    /**
     * 转账
     * @param financialChangesRecord
     * @return
     */
    BaseResponse<String> transfer(FinancialChangesRecordRequest ...financialChangesRecord);

    /**
     * 账户金额冻结
     * @return
     */
    BaseResponse<String> freeze(FinancialFreezeRequest financialFreezeRequest);

    /**
     * 账户解冻
     * @param financialUnFreezeRequest
     * @return
     */
    BaseResponse<String> unfreeze(FinancialUnFreezeRequest financialUnFreezeRequest);

    /**
     * 账户冻结完成
     * @param financialUnFreezeRequest
     * @return
     */
    BaseResponse<String> done(FinancialUnFreezeRequest financialUnFreezeRequest, OperationUnFreeze operationUnFreeze);


    /**
     * 批量获取账户
     * @param ids
     * @return
     */
    BaseResponse<List<FinancialAccount>> getUserFinancialAccount(String ...ids) ;


    BaseResponse<FinancialAccount> addOrEdit(FinancialAccountRequest financialAccount);

    BaseResponse<FinancialAccount> getByPhone(String phone);

    BaseResponse<FinancialAccount> disabled(String id);

    BaseResponse<FinancialAccount> enable(String id);
}
