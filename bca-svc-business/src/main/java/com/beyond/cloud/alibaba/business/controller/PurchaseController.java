package com.beyond.cloud.alibaba.business.controller;

import com.beyond.cloud.alibaba.business.service.BusinessService;
import com.beyond.cloud.alibaba.common.ApiResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author beyond
 * @date 2020/7/27 15:42
 */
@RestController
@RequestMapping("/api/business")
public class PurchaseController {

    private final BusinessService businessService;

    public PurchaseController(final BusinessService businessService) {this.businessService = businessService;}

    @PostMapping("/purchase")
    public ApiResult purchase(@RequestParam("commodityCode") String commodityCode,
                              @RequestParam("count") int count) {
        return businessService.purchase(1, commodityCode, count);
    }

}
