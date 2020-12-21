package com.xxx.common.rest.condition;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.mvc.condition.RequestCondition;

import javax.servlet.http.HttpServletRequest;

public class ApiVersionRequestCondition implements RequestCondition<ApiVersionRequestCondition> {

    public static final String API_VERSION_HEADER = "Api-Version";

    private String version;

    private boolean maxVersion = false;

    public ApiVersionRequestCondition(String version) {
        this.version = version;
    }

    /**
     * 合并条件
     * 如：类上指定了@RequestMapping的 url 为 root -
     * 而方法上指定的@RequestMapping的 url 为 method -
     * 那么在获取这个接口的 url 匹配规则时，类上扫描一次，方法上扫描一次，
     * 这个时候就需要把这两个合并成一个，表示这个接口匹配root/method
     *
     * @param other
     * @return
     */
    @Override
    public ApiVersionRequestCondition combine(ApiVersionRequestCondition other) {
//        this.version = other.version;
        return this;
    }

    @Override
    public ApiVersionRequestCondition getMatchingCondition(HttpServletRequest request) {
        String version = request.getHeader(API_VERSION_HEADER);

        if (StringUtils.isEmpty(version)) {

            // 默认取最新版本
            if (maxVersion) {
                return this;
            }

            return null;
        }

        if (version.compareTo(this.version) >= 0) {
            return this;
        }

        return null;
    }

    @Override
    public int compareTo(ApiVersionRequestCondition other, HttpServletRequest request) {
        return other.version.compareTo(version);
    }

    @Override
    public int hashCode() {
        return version.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ApiVersionRequestCondition)) {
            return false;
        }
        return version.equals(((ApiVersionRequestCondition) obj).version);
    }

    public String getVersion() {
        return version;
    }

    public void setMaxVersion(boolean maxVersion) {
        this.maxVersion = maxVersion;
    }
}
