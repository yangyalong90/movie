package com.xxx.sys.server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sys_user_url")
public class UserUrlEntity {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String username;
    private String url;
    private String appId;
}
