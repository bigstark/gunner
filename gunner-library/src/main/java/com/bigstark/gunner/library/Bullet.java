package com.bigstark.gunner.library;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(METHOD) @Retention(RUNTIME)
public @interface Bullet {
    int sequence() default 0;
    long delay() default 0;
}
