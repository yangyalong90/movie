package com.xxx.movie.sys.server.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxx.movie.sys.server.entity.UrlEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UrlMapper extends BaseMapper<UrlEntity> {
    void batchInsert(@Param("entities") List<? extends UrlEntity> entities);
}
