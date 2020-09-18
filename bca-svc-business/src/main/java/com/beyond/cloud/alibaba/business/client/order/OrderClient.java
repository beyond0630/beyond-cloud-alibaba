package com.beyond.cloud.alibaba.business.client.order;

import com.beyond.cloud.alibaba.common.ApiResult;
import com.beyond.cloud.alibaba.order.domain.entity.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author beyond
 * @date 2020/7/27 15:07
 */
@FeignClient(value = "bca-svc-order")
public interface OrderClient {

    @PostMapping(path = "/api/order")
    ApiResult<Order> createOrder(@RequestParam(value = "userId") int userId,
                                 @RequestParam(value = "commodityCode") String commodityCode,
                                 @RequestParam(value = "count")  int count);

}
