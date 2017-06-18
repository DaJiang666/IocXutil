package com.jiang.iocxutil.annotion;

import android.app.Dialog;
import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by jiang on 2017/6/17.
 * 以EventBase做注解
 *
 * 目前需要View.OnClickListener.class
 * 为了扩展可能还需要Dialog.OnClickListener.class
 */
@EventBase(listenerSetter = "setOnClickListener"
        , listenerType = View.OnClickListener.class

        , callBackMethod = "onClick")
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OnClick {

    int[] value();
}
