package com.beyond.cloud.alibaba.business.service.impl;

import com.beyond.cloud.alibaba.business.client.order.OrderClient;
import com.beyond.cloud.alibaba.business.client.storage.StorageClient;
import com.beyond.cloud.alibaba.business.service.BusinessService;
import com.beyond.cloud.alibaba.common.ApiResult;
import com.beyond.cloud.alibaba.exception.BusinessException;
import io.seata.spring.annotation.GlobalTransactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author beyond
 * @date 2020/7/27 15:32
 */
@Service
public class BusinessServiceImpl implements BusinessService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BusinessServiceImpl.class);

    private final OrderClient orderClient;
    private final StorageClient storageClient;

    public BusinessServiceImpl(final OrderClient orderClient,
                               final StorageClient storageClient) {
        this.orderClient = orderClient;
        this.storageClient = storageClient;
    }

    private void logExecutionTime(final String stage, final Runnable action) {
        final long start = System.currentTimeMillis();
        action.run();
        final long end = System.currentTimeMillis();
        LOGGER.info("步骤: {}, 耗时: {} ms", stage, end - start);
    }

    /**
     * 采购
     */
    @Override
    @GlobalTransactional(name = "purchase", rollbackFor = Exception.class)
    public ApiResult purchase(final int userId, final String commodityCode, final int orderCount) {
        logExecutionTime("减少库存", () -> storageClient.deduct(commodityCode, orderCount));
        logExecutionTime("生成订单", () -> orderClient.createOrder(userId, commodityCode, orderCount));
        if (orderCount > 1) {
            throw new BusinessException("每人限购一份");
        }
        return ApiResult.ok();
    }

}
