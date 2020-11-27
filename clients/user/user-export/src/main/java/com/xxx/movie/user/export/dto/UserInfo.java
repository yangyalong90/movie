package com.xxx.movie.user.export.dto;

import lombok.Data;

@Data
public class UserInfo {

    public static final Long ERROR_ID = 0L;

    private Long id;
    private String name;

}
