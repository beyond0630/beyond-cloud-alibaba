package com.beyond.cloud.alibaba.account.controller;

import com.beyond.cloud.alibaba.account.serivce.AccountService;
import com.beyond.cloud.alibaba.common.ApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lucifer
 * @date 2020/9/1 14:26
 */
@RestController
@RequestMapping("/api/account")
public class AccountController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

    private final AccountService accountService;

    public AccountController(final AccountService accountService) {this.accountService = accountService;}

    @PostMapping("/debit")
    public ApiResult debit(@RequestParam(value = "userId") String userId,
                           @RequestParam(value = "money") int money) {
        final long start = System.currentTimeMillis();
        accountService.debit(userId, money);
        final long end = System.currentTimeMillis();
        LOGGER.info("扣费耗时: {} ms", (end - start));
        return ApiResult.ok();
    }

}
