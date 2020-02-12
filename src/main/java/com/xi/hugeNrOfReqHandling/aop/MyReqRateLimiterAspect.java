package com.xi.hugeNrOfReqHandling.aop;

import com.google.common.util.concurrent.RateLimiter;
import com.xi.hugeNrOfReqHandling.annotation.MyReqRateLimiter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

/**
 * @author xichen created on 12/02/2020
 */

@Aspect
@Component
public class MyReqRateLimiterAspect {
    @Autowired
    private HttpServletResponse response; // only for fallback() function

    private RateLimiter rateLimiter = RateLimiter.create(2);

    @Pointcut("execution(public * com.xi.hugeNrOfReqHandling.api.*.*(..))")
    public void pointcut() {

    }

    @Around("pointcut()")
    public Object process(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();

        // use reflection to check whether the method is annotated by @MyReqRateLimiter
        MyReqRateLimiter myReqRateLimiter = signature.getMethod().getDeclaredAnnotation(MyReqRateLimiter.class);

        // if no such annotation, we continue the business logic of that method
        if (myReqRateLimiter == null) {
            return proceedingJoinPoint.proceed();
        }

        // otherwise check if we should limit the request
        if (shouldLimitRequest(myReqRateLimiter)) {
            fallback();
            return null;
        }

        // successfully got token from bucket, we proceed forward with the business logic of the method
        return proceedingJoinPoint.proceed();

    }

    private boolean shouldLimitRequest(MyReqRateLimiter myReqRateLimiter) {
        double rate = myReqRateLimiter.rate();
        long timeout = myReqRateLimiter.timeout();
        rateLimiter.setRate(rate);

        // check if timeout when acquiring a token
        boolean tryAcquiring = rateLimiter.tryAcquire(timeout, TimeUnit.MILLISECONDS);
        if (!tryAcquiring) {
            // timeout! we should limit this request (i.e. no token available in bucket)
            return true;
        }
        return false;
    }

    private void fallback() {
        response.setHeader("content-type", "text/html:charset=UTF-8");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.println("Server is busy, please try again later.");
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}
