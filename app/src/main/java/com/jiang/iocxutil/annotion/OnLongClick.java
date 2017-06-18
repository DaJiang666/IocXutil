package com.jiang.iocxutil.annotion;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by jiang on 2017/6/17.
 * 长按事件的注解
 */
@EventBase(listenerSetter = "setOnLongClickListener"
        , listenerType = View.OnLongClickListener.class
        , callBackMethod = "onLongClick")
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OnLongClick {
    int[] value();
}
