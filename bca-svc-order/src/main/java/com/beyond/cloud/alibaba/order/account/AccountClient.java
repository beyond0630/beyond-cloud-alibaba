package com.beyond.cloud.alibaba.order.account;

import com.beyond.cloud.alibaba.common.ApiResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author beyond
 * @date 2020/7/27 15:07
 */
@FeignClient(value = "bca-svc-account")
public interface AccountClient {

    @PostMapping(path = "/api/account/debit")
    ApiResult debit(@RequestParam(value = "userId") String userId,
                    @RequestParam(value = "money") int money);

}
