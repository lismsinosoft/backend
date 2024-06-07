package com.gfk.framework.aspect.operationlog;

import com.gfk.framework.jwt.JwtUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 操作日志记录处理
 * <p>
 * refer ruoyi
 */
@Aspect
@Component
public class OperationLogAspect {

    private static final Logger log = LoggerFactory.getLogger(OperationLogAspect.class);

    /**
     * 配置织入点
     */
    @Pointcut("@annotation(com.gfk.framework.aspect.operationlog.Log)")
    public void operationLogPointCut() {
    }

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "operationLogPointCut()", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Object jsonResult) {
        handleLog(joinPoint, null, jsonResult);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param e         异常
     */
    @AfterThrowing(value = "operationLogPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        handleLog(joinPoint, e, null);
    }

    /**
     * 记录日志
     * @param joinPoint
     * @param e
     * @param jsonResult
     */
    protected void handleLog(final JoinPoint joinPoint, final Exception e, Object jsonResult) {
        try {
            // 获得注解
            Log controllerLog = getAnnotationLog(joinPoint);
            if (controllerLog == null) {
                return;
            }

            // 获取当前的用户
            String userId = JwtUtil.getUserId();

            // *========数据库日志=========*//
//            SysOperationLog sysOperationLog = new SysOperationLog();
//            sysOperationLog.setId(UUID.randomUUID().toString());
//            sysOperationLog.setVisitTime(TimeUtils.nowDate());
//            sysOperationLog.setMuid(userId);
//
//            // 设置方法名称
//            String className = joinPoint.getTarget().getClass().getName();
//            sysOperationLog.setModule(className);
//            String methodName = controllerLog.title();
//            sysOperationLog.setFunction(methodName);
//
//            // 保存数据库
//            AsyncManager.me().execute(AsyncFactory.asyncSaveOperationLog(sysOperationLog));
        } catch (Exception exp) {
            // 记录本地异常日志
            log.error("==前置通知异常==");
            log.error("异常信息:{}", exp.getMessage());
        }
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private Log getAnnotationLog(JoinPoint joinPoint) throws Exception {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null) {
            return method.getAnnotation(Log.class);
        }
        return null;
    }
}
