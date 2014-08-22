package com.andima.billing.client.util;

import com.andima.billing.config.JpaConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by GHIBOUB Khalid  on 13/08/2014.
 */
public class SpringUtil {
    public static AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(JpaConfiguration.class);
    public static <T> T getBean(Class<T> aClass){
        return context.getBean(aClass);
    }
}
