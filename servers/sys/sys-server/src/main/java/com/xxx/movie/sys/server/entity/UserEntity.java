package com.xxx.movie.sys.server.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sys_user")
public class UserEntity {

    public static final Long ERROR_ID = 0L;

    private Long id;
    private String username;
    private String password;

}
