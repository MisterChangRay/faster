package com.github.misterchangray.financial.v001.mapper.po;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.misterchangray.financial.v001.pojo.request.FinancialAccountRequest;

import java.math.BigDecimal;
import java.util.Date;


/**
 *
 * 财务账户表,
 *
 * 所有金额都使用 decimal, 计算精度保留 6 位
 *
 * **/
@TableName("faster_financial_account")
public class FinancialAccount {
    public final static int STATUS_OF_ENABLE = 1;  // 用户启动状态
    public final static int STATUS_OF_DISABLE = 2; //用户禁用状态

    public final static int PERMISSION_ALL = 0xFFFF;
    public final static int PERMISSION_WITHDRAW = 0x1;  // 提现权限
    public final static int PERMISSION_CHARGE = 0x3; // 充值权限
    public final static int PERMISSION_PAY = 0x7; // 支付权限


    // 金融账户ID
    @TableId
    private String id;

    // 账户用户ID
    private String userId;

    // 账户姓名
    private String name;

    // 账户手机号
    private String phone;

    // 账户余额
    private BigDecimal balance;

    // 冻结金额
    private BigDecimal freeze;

    // 权限
    private int permissions;

    // 1 启动 2禁用
    private int status;

    // 创建时间
    private Date createTime;

    // 更新时间
    private Date updateTime;

    public static FinancialAccount valueOf(FinancialAccountRequest financialAccountRequest) {
        FinancialAccount financialAccount = new FinancialAccount();
        financialAccount.setId(financialAccountRequest.getId());
        financialAccount.setFreeze(BigDecimal.ZERO);
        financialAccount.setBalance(BigDecimal.ZERO);
        financialAccount.setCreateTime(new Date());
        financialAccount.setName(financialAccountRequest.getName());
        financialAccount.setPhone(financialAccountRequest.getPhone());
        financialAccount.setUpdateTime(financialAccount.getCreateTime());
        financialAccount.setUserId(financialAccountRequest.getUserId());
        return financialAccount;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getFreeze() {
        return freeze;
    }

    public void setFreeze(BigDecimal freeze) {
        this.freeze = freeze;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public int getPermissions() {
        return permissions;
    }

    public void setPermissions(int permissions) {
        this.permissions = permissions;
    }
}
