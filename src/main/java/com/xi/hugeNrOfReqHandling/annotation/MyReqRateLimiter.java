package com.xi.hugeNrOfReqHandling.annotation;

import java.lang.annotation.*;

/**
 * @author xichen created on 12/02/2020
 */

@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyReqRateLimiter {
    double rate(); // permits per second for acquiring token
    long timeout() default 0; // the timeout of acquiring token
}
