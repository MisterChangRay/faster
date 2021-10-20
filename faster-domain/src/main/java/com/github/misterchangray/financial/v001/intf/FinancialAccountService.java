package com.github.misterchangray.financial.v001.intf;

import com.github.misterchangray.common.base.BaseResponse;
import com.github.misterchangray.financial.v001.mapper.po.FinancialAccount;

import java.math.BigDecimal;
import java.util.List;


/**
 * 提供账户操作的常用方法
 */
public interface FinancialAccountService {


    /**
     * 重构缓存 中的用户信息
     * @param financialId
     */
    BaseResponse<Boolean> reBuildCache(String financialId) ;


    /**
     * 批量获取账户信息
     * 每批不能大于 100
     * @param financialIds
     * @return
     */
    BaseResponse<List<FinancialAccount>> getFinancialAccount(List<String> financialIds);


    /**
     * 获取用户账户信息
     *
     * @param financialId
     * @return
     */
    BaseResponse<FinancialAccount> getFinancialAccount(String financialId);


    /**
     * 冻结账户金额
     * @param financialId
     * @param amount
     * @return freezeId 冻结ID, 解冻时使用
     */
    BaseResponse<String> freeze(String financialId, BigDecimal amount);


    /**
     * 解冻账户金额
     * @param freezeId  冻结金额时返回
     * @return
     */
    BaseResponse<Boolean> unFreeze(String freezeId);




    /**
     * 增加账户余额
     * @param financialId
     * @param amount
     * @return
     */
    BaseResponse<Boolean> increaseBalance(String financialId, BigDecimal amount);




    /**
     * 减少账户余额
     * @param financialId
     * @param amount
     * @return
     */
    BaseResponse<Boolean> decreaseBalance(String financialId, BigDecimal amount);


    /**
     * 增加账户冻结金额
     * @param financialId
     * @param amount
     * @return
     */
    BaseResponse<Boolean> increaseFreeze(String financialId, BigDecimal amount);




    /**
     * 减少账户冻结金额
     * @param financialId
     * @param amount
     * @return
     */
    BaseResponse<Boolean> decreaseFreeze(String financialId, BigDecimal amount);


}
