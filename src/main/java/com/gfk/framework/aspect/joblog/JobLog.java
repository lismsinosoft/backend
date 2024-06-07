package com.gfk.framework.aspect.joblog;

import java.lang.annotation.*;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JobLog {
    String value() default "";
}
