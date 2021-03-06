package com.beyond.cloud.alibaba.account.serivce.impl;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import com.beyond.cloud.alibaba.account.domain.entity.Account;
import com.beyond.cloud.alibaba.account.domain.entity.PayRecord;
import com.beyond.cloud.alibaba.account.mapper.ext.AccountMapperExt;
import com.beyond.cloud.alibaba.account.mapper.ext.PayRecordMapperExt;
import com.beyond.cloud.alibaba.account.serivce.AccountService;
import com.beyond.cloud.alibaba.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author lucifer
 * @date 2020/9/1 13:54
 */
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountMapperExt accountMapperExt;
    private final PayRecordMapperExt payRecordMapperExt;

    public AccountServiceImpl(final AccountMapperExt accountMapperExt,
                              final PayRecordMapperExt payRecordMapperExt) {
        this.accountMapperExt = accountMapperExt;
        this.payRecordMapperExt = payRecordMapperExt;
    }

    @Override
    @Transactional
    public void debit(final String userId, final int money) {

        Account account = accountMapperExt.selectAccountByUserId(userId);
        if (Objects.isNull(account) || account.getBalance() < money) {
            throw new BusinessException("余额不足");
        }
        if (account.getDisabled()) {
            throw new BusinessException("账号被禁用");
        }
        account.setBalance(account.getBalance() - money);
        accountMapperExt.updateByPrimaryKeySelective(account);
        PayRecord payRecord = new PayRecord();
        payRecord.setAccountId(account.getId());
        payRecord.setPayMoney(money);
        payRecordMapperExt.insertSelective(payRecord);
    }

}
