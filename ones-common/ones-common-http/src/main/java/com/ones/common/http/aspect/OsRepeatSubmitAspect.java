package com.ones.common.http.aspect;

import cn.hutool.core.util.StrUtil;
import com.ones.common.core.exception.OsBizException;
import com.ones.common.http.annotation.OsRepeatSubmit;
import com.ones.common.http.constant.OsHttpConsts;
import com.ones.common.redis.util.OsRedisUtils;
import com.ones.common.web.constant.OsWebConsts;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;

/**
 * @author Clark
 * @version 2023/3/14
 */
@Aspect
public class OsRepeatSubmitAspect {
    @Autowired(required = false)
    private OsRedisUtils osRedisUtils;

    @Around("@annotation(com.ones.common.http.annotation.OsRepeatSubmit)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader(OsWebConsts.HEADER_TOKEN);
        if (StrUtil.isEmpty(token)) {
            throw new OsBizException(OsHttpConsts.BIZ_EXCEPTION_CODE_ACCESS_LIMIT, "osToken不能为空");
        }
        String path = request.getServletPath();
        String key = "repeatSubmit:" + token + ":" + path;
        boolean isExist = this.osRedisUtils.hasKey(key);
        if (isExist) {
            throw new OsBizException(OsHttpConsts.BIZ_EXCEPTION_CODE_REPEAT_SUBMIT, "请勿重复提交");
        }

        this.osRedisUtils.set(key, "0", this.expireTime(joinPoint));

        Object result = joinPoint.proceed();
        return result;
    }

    /**
     * 获取注解中的过期时间
     */
    private int expireTime(ProceedingJoinPoint joinPoint) {
        final Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        // 取注解
        OsRepeatSubmit annotation = method.getAnnotation(OsRepeatSubmit.class);
        return annotation.expireTime();
    }
}