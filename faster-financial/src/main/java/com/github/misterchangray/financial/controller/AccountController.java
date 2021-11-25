package com.github.misterchangray.financial.controller;

import com.github.misterchangray.common.base.BaseEnum;
import com.github.misterchangray.common.base.BaseResponse;
import com.github.misterchangray.common.base.ResEnum;
import com.github.misterchangray.financial.v001.intf.FinancialAccountService;
import com.github.misterchangray.financial.v001.mapper.po.FinancialAccount;
import com.github.misterchangray.financial.v001.pojo.request.FinancialAccountRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
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
    @PostMapping("/register")
    public BaseResponse<FinancialAccount> register(@RequestBody @Validated FinancialAccountRequest financialAccount) {
        return accountService.addOrEdit(financialAccount);
    }

    @PostMapping("/disable/{id}")
    public BaseResponse<FinancialAccount> disabled(@PathVariable String id) {
        return accountService.disabled(id);
    }

    @PostMapping("/update")
    public BaseResponse<FinancialAccount> update(@RequestBody FinancialAccount financialAccount) {
        return accountService.addOrEdit(null);
    }

    @PostMapping("/permission")
    public BaseResponse<FinancialAccount> permission(@RequestBody FinancialAccount financialAccount) {
        return accountService.addOrEdit(null);
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
