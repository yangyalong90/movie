package com.xxx.movie.user.service;

public interface UserAuthService {

    boolean permission(String token, String url);

}
