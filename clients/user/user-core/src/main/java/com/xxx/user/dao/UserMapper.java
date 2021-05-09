package com.xxx.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxx.user.common.entity.UserInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<UserInfo> {
}
