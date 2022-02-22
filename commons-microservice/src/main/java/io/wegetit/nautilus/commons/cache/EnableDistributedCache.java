package io.wegetit.nautilus.commons.cache;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({DistributedCacheConfiguration.class})
public @interface EnableDistributedCache {

}