package com.jiang.iocxutil;

import android.content.Context;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by jiang on 2017/6/17.
 */

public class ListenerInvocationHandler implements InvocationHandler {

    private Context context;



    private Map<String, Method> methodMap;

    public ListenerInvocationHandler(Context context, Map<String, Method> methodMap) {
        this.context = context;
        this.methodMap = methodMap;
    }

    /**
     * 处理代理对象的代理方法
     * 我们要代理谁  MainActivity OnClickListener
     * 持有一个真正的对象引用就是MainActivity
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String name = method.getName();
        Method mtd = methodMap.get(name);
        if (mtd == null) {
            //不需要代理
            return method.invoke(proxy, args);
        } else {
            //真正的代理方法
            return mtd.invoke(context, args);
        }
    }
}
