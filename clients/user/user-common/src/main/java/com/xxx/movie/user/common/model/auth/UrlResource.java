package com.xxx.movie.user.common.model.auth;

import lombok.Data;

import java.util.List;

@Data
public class UrlResource {

    private String application;
    private List<String> urls;

}
