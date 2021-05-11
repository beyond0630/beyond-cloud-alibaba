package com.beyond.cloud.alibaba.order.serivce.impl;

import com.beyond.cloud.alibaba.common.ApiResult;
import com.beyond.cloud.alibaba.order.account.AccountClient;
import com.beyond.cloud.alibaba.order.domain.entity.Order;
import com.beyond.cloud.alibaba.order.mapper.OrderMapper;
import com.beyond.cloud.alibaba.order.serivce.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author beyond
 * @date 2020/7/27 14:41
 */
@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final AccountClient accountClient;
    private final OrderMapper orderMapper;

    public OrderServiceImpl(final AccountClient accountClient, final OrderMapper orderMapper) {
        this.accountClient = accountClient;
        this.orderMapper = orderMapper;
    }

    @Override
    @Transactional
    public ApiResult<Order> createOrder(final String userId, final String commodityCode, final int count) {
        final long start = System.currentTimeMillis();
        Order order = new Order();
        order.setUserId(userId);
        order.setCommodityCode(commodityCode);
        order.setCount(count);
        order.setMoney(calculate(commodityCode, count));
        orderMapper.insertSelective(order);

        accountClient.debit(userId, order.getMoney());

        final long end = System.currentTimeMillis();
        LOGGER.info("生成订单耗时: {} ms", (end - start));
        return ApiResult.ok(order);
    }

    private Integer calculate(final String commodityCode, final int count) {
        // 演示数据，直接返回 count
        return count;
    }

}
