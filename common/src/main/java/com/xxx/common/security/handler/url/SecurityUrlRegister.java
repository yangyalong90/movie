package com.xxx.common.security.handler.url;

import com.xxx.common.thead.lock.RedisReentrantLock;
import com.xxx.common.version.handlers.RegisterHandlerMethodBefore;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

public abstract class SecurityUrlRegister implements RegisterHandlerMethodBefore, ApplicationListener<ApplicationStartedEvent> {

    private List<String> urls = new LinkedList<>();

    private RedisReentrantLock redisReentrantLock;

    public SecurityUrlRegister() {
    }

    public SecurityUrlRegister(RedisReentrantLock redisReentrantLock) {
        this.redisReentrantLock = redisReentrantLock;
    }

    public abstract void register(List<String> urls);

    @Override
    public void execute(Object handler, Method method, RequestMappingInfo mapping) {
        urls.add(mapping2Pattern(mapping));
    }

    String mapping2Pattern(RequestMappingInfo mappingInfo) {
        PatternsRequestCondition patternsCondition = mappingInfo.getPatternsCondition();
        RequestMethodsRequestCondition methodsCondition = mappingInfo.getMethodsCondition();
        String method = methodsCondition.getMethods().toArray()[0].toString();
        String pattern = patternsCondition.getPatterns().toArray()[0].toString();
        return method + "[::]" + pattern;
    }

    @Override
    public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {
        register(urls);
        urls.clear();
        urls = null;
    }
}
