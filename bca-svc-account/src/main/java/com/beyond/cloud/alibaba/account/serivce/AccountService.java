package com.beyond.cloud.alibaba.account.serivce;

/**
 * @author lucifer
 * @date 2020/9/1 13:45
 */
public interface AccountService {

    void debit(String userId, int money);

}
