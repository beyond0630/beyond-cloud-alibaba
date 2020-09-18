package com.beyond.cloud.alibaba.storage.mapper.ext;

import com.beyond.cloud.alibaba.storage.domain.entity.Storage;
import com.beyond.cloud.alibaba.storage.mapper.StorageMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface StorageMapperExt extends StorageMapper {

    Storage getByCommodityCode(@Param("commodityCode") String commodityCode);

}
