package com.jiang.iocxutil.annotion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by jiang on 2017/6/17.
 * 注解的注解 事件的三要素
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventBase {
    /**
     * 监听事件的方法
     * @return String
     */
    String listenerSetter();

    /**
     * 事件的类型
     * @return Class
     */
    Class<?> listenerType();

    /**
     * 事件被触发后的回调方法
     * @return
     */
    String callBackMethod();
}
