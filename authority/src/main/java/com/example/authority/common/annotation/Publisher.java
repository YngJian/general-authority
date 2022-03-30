package com.example.authority.common.annotation;

import com.example.authority.common.entity.Strings;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author MrBird
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Publisher {
    @AliasFor(annotation = Component.class)
    String value() default Strings.EMPTY;
}
