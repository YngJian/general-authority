package com.example.authority.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.authority.domain.entity.LoginLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author yangj
 */
@Mapper
public interface LoginLogMapper extends BaseMapper<LoginLog> {
}