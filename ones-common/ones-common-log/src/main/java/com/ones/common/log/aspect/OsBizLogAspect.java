package com.ones.common.log.aspect;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.ones.common.log.service.IBizLogService;
import com.ones.common.log.annotation.OsLog;
import com.ones.common.log.constant.OsLogConsts;
import com.ones.common.log.model.entity.BizLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * 业务日志Aspect 切面
 * @author Clark
 * @version 2022-03-03
 */
@Slf4j
@Aspect
public class OsBizLogAspect {

    @Autowired
    protected IBizLogService bizLogService;

    /**
     * 环绕通知
     */
    @Around("@annotation(com.ones.common.log.annotation.OsLog)")
    public Object arround(ProceedingJoinPoint joinPoint) throws Throwable {
        final Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        // 取注解
        OsLog osLog = method.getAnnotation(OsLog.class);
        // 开始时间
        long startTime = System.currentTimeMillis();
        String name = StrUtil.isNotBlank(osLog.value()) ? osLog.value() : method.getDeclaringClass().getName() + "." + method.getName();
        BizLog bizLog = BizLog.builder()
                .name(name)
                .type(OsLogConsts.TYPE_ANNOTATION)
                .status(OsLogConsts.STATUS_SUCCESS)
                .reqContent(getNameAndValue(joinPoint))
                .build();
        try {
            Object result = joinPoint.proceed();
            // 正常返回
            bizLog.setResContent(JSONUtil.toJsonStr(result));
            return result;
        } catch (Throwable e) {
            // 异常场合
            bizLog.setStatus(OsLogConsts.STATUS_FAIL);
            bizLog.setException(ExceptionUtil.stacktraceToString(e));
            throw e;
        } finally {
            // 调用耗时
            bizLog.setTimeCost(System.currentTimeMillis() - startTime);
            // 记录日志
            this.bizLogService.save(bizLog);
        }
    }

    /**
     *  获取方法参数名和参数值
     * @param joinPoint
     * @return String
     */
    protected String getNameAndValue(ProceedingJoinPoint joinPoint) {
        final Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        final String[] names = methodSignature.getParameterNames();
        final Object[] args = joinPoint.getArgs();

        if (ArrayUtil.isEmpty(names) || ArrayUtil.isEmpty(args)) {
            return StrUtil.EMPTY;
        }
        if (names.length != args.length) {
            log.warn("{}方法参数名和参数值数量不一致", methodSignature.getName());
            return names.toString() + StrUtil.UNDERLINE + args.toString();
        }
        Map<String, Object> map = MapUtil.newHashMap();
        for (int i = 0; i < names.length; i++) {
            map.put(names[i], args[i]);
        }

        return JSONUtil.toJsonStr(map);
    }
}

