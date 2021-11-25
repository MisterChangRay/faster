package com.github.misterchangray.financial.v001.pojo.request;


import com.github.misterchangray.common.base.BaseEnum;
import com.github.misterchangray.common.exceptions.BizException;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.math.BigDecimal;


/**
 *
 * 财务账户表,
 *
 * 所有金额都使用 decimal, 计算精度保留 6 位
 *
 * **/
public class FinancialAccountRequest implements Serializable {
    // 金融账户ID
    private String id;

    // 账户用户ID
    private String userId;

    // 账户姓名
    @NotNull(message = "用户名,2-100个字符")
    @Pattern(regexp = "^.{2,100}$", message = "用户名,2-100个字符")
    private String name;

    // 账户手机号
    @NotNull(message = "手机号11位数字")
    @Pattern(regexp = "^1\\d{10}$", message = "手机号11位数字")
    private String phone;


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

    public boolean checkParams() {
        if(!StringUtils.hasLength(this.phone)) {
            throw new BizException(BaseEnum.INVALID_PARAM);
        }

        return true;
    }
}
