package com.jiang.iocxutil.annotion;

import android.view.View;

import java.lang.annotation.Target;

/**
 * Created by jiang on 2017/6/17.
 */
@EventBase(listenerSetter = "setOnClickListener"
        , listenerType = View.OnClickListener.class
        , callBackMethod = "")
public @interface OnLongClick {
    int[] value();
}
