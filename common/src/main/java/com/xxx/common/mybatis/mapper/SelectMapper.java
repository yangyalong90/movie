package com.xxx.common.mybatis.mapper;

import java.io.Serializable;
import java.util.List;

public interface SelectMapper<T> {

    List<T> selectList(T entity);

    T selectById(Serializable id);

}
