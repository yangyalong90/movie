package com.xxx.common.mybatis.aspect;

import com.xxx.common.mybatis.aspect.executor.SelectMapperExecutor;
import org.apache.ibatis.binding.MapperProxy;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Aspect
public class MapperAspect {

    private static final List<Method> SELECT_METHODS =
            Arrays.stream(SelectMapperExecutor.class.getMethods()).filter(method -> {
                Class<?> declaringClass = method.getDeclaringClass();
                return declaringClass.isAssignableFrom(SelectMapperExecutor.class);
            }).collect(Collectors.toList());

    public static final ThreadLocal<Class<?>> MAPPER_T_LOCAL = new ThreadLocal<>();

    private SelectMapperExecutor<?> selectMapperExecutor;

    public MapperAspect(SelectMapperExecutor<?> selectMapperExecutor) {
        this.selectMapperExecutor = selectMapperExecutor;
    }

    @Pointcut("execution(* com.xxx.common.mybatis.mapper.SelectMapper.*(..)) " +
            "&& " +
            "!execution(* com.xxx.common.mybatis.aspect..*.*(..)) ")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        InvocationHandler invocationHandler = Proxy.getInvocationHandler(joinPoint.getTarget());

        MapperProxy mapperProxy;
        if (invocationHandler instanceof MapperProxy) {
            mapperProxy = (MapperProxy) invocationHandler;
        } else {
            return joinPoint.proceed();
        }

        Class<?> mapperT = mapperT(mapperProxy);
        MAPPER_T_LOCAL.set(mapperT);
        try {
            return execute(joinPoint);
        }finally {
            MAPPER_T_LOCAL.remove();
        }
    }

    Class<?> mapperT(MapperProxy mapperProxy) throws Exception {
        Class<? extends MapperProxy> mapperProxyClass = mapperProxy.getClass();

        Field mapperInterfaceField = mapperProxyClass.getDeclaredField("mapperInterface");
        mapperInterfaceField.setAccessible(true);
        // 得到自定义 mapper
        Class<?> mapperInterface = (Class<?>) mapperInterfaceField.get(mapperProxy);

        Type[] genericInterfaces = mapperInterface.getGenericInterfaces();
        if (genericInterfaces == null || genericInterfaces.length == 0) {
            return null;
        }
        return (Class<?>) ((ParameterizedTypeImpl)genericInterfaces[0]).getActualTypeArguments()[0];
    }

    Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        Signature signature = joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        String methodName = signature.getName();

        Optional<Method> optional = SELECT_METHODS.stream()
                .filter(method -> methodName.equals(method.getName()))
                .findFirst();
        if (!optional.isPresent()) {
            return joinPoint.proceed();
        }
        return optional.get().invoke(selectMapperExecutor, args);
    }

}
