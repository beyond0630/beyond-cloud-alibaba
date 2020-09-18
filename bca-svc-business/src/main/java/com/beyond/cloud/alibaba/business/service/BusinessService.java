package com.beyond.cloud.alibaba.business.service;


import com.beyond.cloud.alibaba.common.ApiResult;

/**
 * @author beyond
 * @date 2020/7/27 15:32
 */
public interface BusinessService {

    ApiResult purchase(int userId, String commodityCode, int orderCount);
}
