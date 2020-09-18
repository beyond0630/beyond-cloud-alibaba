package com.beyond.cloud.alibaba.order.serivce;


import com.beyond.cloud.alibaba.common.ApiResult;
import com.beyond.cloud.alibaba.order.domain.entity.Order;

/**
 * @author beyond
 * @date 2020/7/27 13:35
 */
public interface OrderService {

    ApiResult<Order> createOrder(String userId, String commodityCode, int count);

}
