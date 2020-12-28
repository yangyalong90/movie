package com.xxx.common.model;

public class ApiResult<T> {

    public static final String SUCCESS_CODE = "1";
    public static final String ERROR_CODE = "0";

    private String code;
    private String message;
    private T data;

    public static <T> ApiResult<T> success(T data){
        return new ApiResult<T>(SUCCESS_CODE, null, data);
    }

    public static <T> ApiResult<T> error(){
        return new ApiResult<T>(ERROR_CODE, null, null);
    }

    public static <T> ApiResult<T> error(String message){
        return new ApiResult<T>(ERROR_CODE, message, null);
    }

    public ApiResult(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
