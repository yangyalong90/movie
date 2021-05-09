package com.xxx.sys.server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("sys_url")
@Data
public class UrlEntity {
    private String appId;
    private String url;
    private String isIgnore = "0";
}
