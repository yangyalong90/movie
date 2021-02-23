package com.xxx.movie.user.dao;

import com.xxx.common.mybatis.mapper.BaseMapper;
import com.xxx.movie.user.common.entity.UserInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<UserInfo> {
}
