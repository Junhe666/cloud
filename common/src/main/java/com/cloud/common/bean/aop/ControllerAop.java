package com.cloud.common.bean.aop;

import com.cloud.common.constant.SecurityConstants;
import com.cloud.common.util.UserUtils;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * Created by Junhe on 2018/7/31
 */
@Aspect
@Component
public class ControllerAop {
    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    @Pointcut("execution(public com.cloud.common.util.R *(..))")
    public void pointCutR() {
    }

    /**
     * 拦截器具体实现
     *
     * @param pjp 切点 所有返回对象R
     * @return R  结果包装
     */
    @Around("pointCutR()")
    public Object methodRHandler(ProceedingJoinPoint pjp) throws Throwable {
        return methodHandler(pjp);
    }


    @Pointcut("execution(public com.cloud.domain *(..))")
    public void pointCutPage() {
    }

    /**
     * 拦截器具体实现
     *
     * @param pjp 切点 所有返回对象Page
     * @return R  结果包装
     */
    @Around("pointCutPage()")
    public Object methodPageHandler(ProceedingJoinPoint pjp) throws Throwable {
        return methodHandler(pjp);
    }

    private Object methodHandler(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String username = request.getHeader(SecurityConstants.USER_HEADER);
        if (StringUtils.isNotBlank(username)) {
            LOGGER.info("Controller AOP get username:{}", username);
            UserUtils.setUser(username);
        }

        LOGGER.info("URL : " + request.getRequestURL().toString());
        LOGGER.info("HTTP_METHOD : " + request.getMethod());
        LOGGER.info("IP : " + request.getRemoteAddr());
        LOGGER.info("CLASS_METHOD : " + pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName());
        LOGGER.info("ARGS : " + Arrays.toString(pjp.getArgs()));

        Object result;

        result = pjp.proceed();
        LOGGER.info(pjp.getSignature() + "use time:" + (System.currentTimeMillis() - startTime));

        if (StringUtils.isNotEmpty(username)) {
            UserUtils.clearAllUserInfo();
        }

        return result;
    }
}
