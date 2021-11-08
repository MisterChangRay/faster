package com.github.misterchangray.financial.v001.pojo.request;

/**
 * 解冻操作
 */
public enum OperationUnFreeze {
    FREEZE_ROLLBACK(1, "冻结金额回滚到账户余额"),
    FREEZE_OUTLAY(2, "冻结金额转支出, 账户余额=账户余额-冻结余额"),
    FREEZE_INCOME(3, "冻结金额转收入, 账户余额=账户余额+冻结余额")
    ;

    int id;
    String desc;
    OperationUnFreeze(int id, String desc) {
        this.id = id;
        this.desc = desc;
    }

}
