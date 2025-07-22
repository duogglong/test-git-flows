package com.ndl.trustviec.config.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

/**
 * The Interface Api.
 * Extend from RestController and RequestMapping of Spring
 * framework.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@RestController
@RequestMapping
@CrossOrigin(origins = "*", allowedHeaders = "*")
public @interface Api {

    /**
     * Path.
     *
     * @return the string
     */
    @AliasFor(attribute = "path", annotation = RequestMapping.class)
    String path();
}

