package com.beyond.cloud.alibaba.business.client.storage;

import com.beyond.cloud.alibaba.common.ApiResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author beyond
 * @date 2020/7/27 15:07
 */
@FeignClient(value = "bca-svc-storage")
public interface StorageClient {

    @PostMapping(path = "/api/storage")
    ApiResult<Boolean> deduct(@RequestParam(value = "commodityCode") String commodityCode,
                              @RequestParam(value = "count") int count);

}
