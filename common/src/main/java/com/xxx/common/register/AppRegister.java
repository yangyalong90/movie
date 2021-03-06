package com.xxx.common.register;

import com.xxx.common.security.handler.url.IgnoreSecurityUrl;
import com.xxx.common.thead.lock.RedisReentrantLock;
import com.xxx.common.version.handlers.RegisterHandlerMethodBefore;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

public abstract class AppRegister implements RegisterHandlerMethodBefore, ApplicationListener<ApplicationStartedEvent> {

    private static final String REGISTER_LOCK_KEY = "REGISTER_LOCK_KEY";
    public static final String SIGN = "#&#";
    private List<RegisterBodyUrl> urls = new LinkedList<>();

    @Autowired
    private RedisReentrantLock redisReentrantLock;

    @Value("${spring.application.name}")
    private String application;

    public abstract void register(RegisterBody registerBody);

    @Override
    public void execute(Object handler, Method method, RequestMappingInfo mapping) {
        String pattern = mapping2Pattern(mapping);
        if (StringUtils.isEmpty(pattern)) {
            return;
        }

        IgnoreSecurityUrl ignoreSecurityUrl = AnnotationUtils.findAnnotation(method, IgnoreSecurityUrl.class);
        RegisterBodyUrl url = new RegisterBodyUrl();
        if (ignoreSecurityUrl != null) {
            url.setIgnore("1");
        }
        url.setUrl(pattern);
        urls.add(url);
    }

    String mapping2Pattern(RequestMappingInfo mappingInfo) {
        PatternsRequestCondition patternsCondition = mappingInfo.getPatternsCondition();
        RequestMethodsRequestCondition methodsCondition = mappingInfo.getMethodsCondition();
        if (patternsCondition.isEmpty() || methodsCondition.isEmpty()) {
            return null;
        }
        String method = methodsCondition.getMethods().toArray()[0].toString();
        String pattern = patternsCondition.getPatterns().toArray()[0].toString();
        return method + SIGN + pattern;
    }

    @Override
    public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {
        if (!urls.isEmpty() && redisReentrantLock.lock(REGISTER_LOCK_KEY)) {
            RegisterBody registerBody = new RegisterBody();
            registerBody.setUrls(urls);
            registerBody.setAppId(application);
            register(registerBody);
        }
        urls.clear();
        urls = null;
    }

    @Data
    public static class RegisterBody {
        private String appId;
        private String ip;
        private String activeUrl = "/heart";
        private List<RegisterBodyUrl> urls = new LinkedList<>();
    }

    @Data
    public static class RegisterBodyUrl {
        private String url;
        private String ignore = "0";
    }

}
