package com.github.misterchangray.financial.v001.pojo.request;

public enum  OperationType {
    INCOME(1, "收入"),
    OUTLAY(2, "支出");

    int id;
    String desc;
    OperationType(int id, String desc) {
        this.id = id;
        this.desc = desc;
    }

}
