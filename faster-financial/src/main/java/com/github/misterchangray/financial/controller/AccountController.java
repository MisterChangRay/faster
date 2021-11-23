package com.github.misterchangray.financial.controller;

import com.github.misterchangray.common.base.BaseEnum;
import com.github.misterchangray.common.base.BaseResponse;
import com.github.misterchangray.common.base.ResEnum;
import com.github.misterchangray.financial.v001.intf.FinancialAccountService;
import com.github.misterchangray.financial.v001.mapper.po.FinancialAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private FinancialAccountService accountService;


    /**
     * 新增或者修改
     * 带ID就是修改
     * @return
     */
    @PostMapping()
    public BaseResponse<FinancialAccount> add(@RequestBody FinancialAccount financialAccount) {
        return accountService.addOrEdit(financialAccount);
    }

    @GetMapping("/{id}")
    public BaseResponse<FinancialAccount> getUserFinancialAccount(@PathVariable("id") String id) {

        return null;
    }

    @GetMapping("/list")
    public BaseResponse<List<FinancialAccount>> getUserFinancialAccount() {
        return null;

    }


    @PostMapping("/changeBalance")
    public BaseResponse<List<FinancialAccount>> changeBalanceUserFinancialAccount() {

        return null;

    }

    @PostMapping("/changeFreeze")
    public BaseResponse<List<FinancialAccount>> changeFreezeUserFinancialAccount() {
        return null;
    }

}
