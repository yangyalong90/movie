package com.xxx.movie.sys.server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("sys_url")
@Data
public class UrlEntity {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String application;
    private String url;
    private String type;
}
