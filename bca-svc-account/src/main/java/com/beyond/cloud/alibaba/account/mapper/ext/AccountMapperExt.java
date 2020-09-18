package com.beyond.cloud.alibaba.account.mapper.ext;

import com.beyond.cloud.alibaba.account.domain.entity.Account;
import com.beyond.cloud.alibaba.account.mapper.AccountMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AccountMapperExt extends AccountMapper {

    Account selectAccountByUserId(@Param("userId") String userId);

}
