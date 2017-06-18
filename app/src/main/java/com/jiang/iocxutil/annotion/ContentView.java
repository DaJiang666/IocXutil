package com.jiang.iocxutil.annotion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by jiang on 2017/6/17.
 * 注入布局
 *
 * ElementType.TYPE 表示类的注解
 * RetentionPolicy.RUNTIME 运行时注解 一般我们编写的都是基于运行时的注解
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ContentView
{
    int value();
}
