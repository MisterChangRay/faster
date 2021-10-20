package com.github.misterchangray.financial.controller;

import com.github.misterchangray.common.base.BaseResponse;
import com.github.misterchangray.financial.v001.mapper.po.FinancialAccount;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/account")
public class AccountController {

    @GetMapping("/{id}")
    public BaseResponse<FinancialAccount> getUserFinancialAccount(@PathVariable("id") String id) {
        return BaseResponse.ofSuccess(null);
    }

    @GetMapping("/list")
    public BaseResponse<List<FinancialAccount>> getUserFinancialAccount() {
        return BaseResponse.ofSuccess(null);

    }

    @PostMapping("/update")
    public BaseResponse<List<FinancialAccount>> updateUserFinancialAccount() {
        return BaseResponse.ofSuccess(null);

    }

    @PostMapping("/changeBalance")
    public BaseResponse<List<FinancialAccount>> changeBalanceUserFinancialAccount() {
        return BaseResponse.ofSuccess(null);

    }

    @PostMapping("/changeFreeze")
    public BaseResponse<List<FinancialAccount>> changeFreezeUserFinancialAccount() {
        return BaseResponse.ofSuccess(null);
    }

}
