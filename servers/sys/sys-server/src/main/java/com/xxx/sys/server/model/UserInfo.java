package com.xxx.sys.server.model;

import lombok.Data;

import java.util.List;

@Data
public class UserInfo {
    private String username;
    private List<String> permissions;

}
