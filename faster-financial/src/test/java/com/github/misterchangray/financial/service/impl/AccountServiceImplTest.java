package com.github.misterchangray.financial.service.impl;

import com.github.misterchangray.common.base.BaseResponse;
import com.github.misterchangray.financial.FasterFinancialApplication;
import com.github.misterchangray.financial.v001.intf.FinancialAccountService;
import com.github.misterchangray.financial.v001.pojo.request.FinancialChangesRecordRequest;
import com.github.misterchangray.financial.v001.pojo.response.FinancialChangesRecordResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = FasterFinancialApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class AccountServiceImplTest {
    @Autowired
    FinancialAccountService accountService;

    /**
     * 用户收入入库测试
     */
    @Test
    public void userIncome() {
        FinancialChangesRecordRequest req = new FinancialChangesRecordRequest();
        req.setAmount(new BigDecimal(5));
        req.setCreateTime(System.currentTimeMillis());
        req.setSourceId("12312312333333");
        req.setFinancialAccountId("123213");
        req.setRemark("测试订单");
        req.setSerialNumber("123123");

        BaseResponse<List<FinancialChangesRecordResponse>> income = accountService.income(req);
        System.out.println(11);

    }

}