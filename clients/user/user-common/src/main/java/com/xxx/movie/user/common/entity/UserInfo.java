package com.xxx.movie.user.common.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user_info")
public class UserInfo {

    public static final Long ERROR_ID = 0L;

    private Long id;
    private String username;
    private String password;

}
