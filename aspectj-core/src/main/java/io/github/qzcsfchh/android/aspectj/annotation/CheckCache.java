package io.github.qzcsfchh.android.aspectj.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.github.qzcsfchh.android.aspectj.enums.CacheMode;

@Retention(RetentionPolicy.CLASS)
@Target({ElementType.METHOD,ElementType.FIELD})
public @interface CheckCache {
    String key();
    CacheMode mode();
}
