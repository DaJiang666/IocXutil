package com.jiang.iocxutil;

import android.content.Context;
import android.view.View;

import com.jiang.iocxutil.annotion.ContentView;
import com.jiang.iocxutil.annotion.EventBase;
import com.jiang.iocxutil.annotion.ViewInject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jiang on 2017/6/17.
 */

public class InjectUtils {

    /**
     *
     * @param context
     */
    public static void inject(Context context) {
        injectLayout(context);
        injectView(context);
        injectEvent(context);
    }

    /**
     * 依赖注入
     * @param context
     */
    public static void injectView(Context context) {
        Class<?> aClass = context.getClass();
        // 拿到成员变量 数组
        Field[] fields = aClass.getDeclaredFields();
        // 遍历所有的属性
        for (Field field: fields) {
            ViewInject viewInject = field.getAnnotation(ViewInject.class);

            if (viewInject != null) {
                int valueID = viewInject.value();
                try {
                    Method method = aClass.getMethod("findViewById", int.class);
                    View view = (View) method.invoke(context, valueID);
                    field.setAccessible(true);
                    field.set(context, view);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void injectLayout(Context context) {
        Class<?> clazz =  context.getClass();
        ContentView contentView = clazz.getAnnotation(ContentView.class);
        if (contentView != null) {
            int layoutId = contentView.value();
            //setContentView
            try {
                Method method = clazz.getMethod("setContentView", int.class);
                method.setAccessible(true);
                method.invoke(context, layoutId);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }

    }


    /**
     * 注入事件
     *
     * public Method[] getMethods()返回某个类的所有公用（public）方法包括其继承类的公用方法，当然也包括它所实现接口的方法。
     * public Method[] getDeclaredMethods()对象表示的类或接口声明的所有方法，
     * 包括公共、保护、默认（包）访问和私有方法，但不包括继承的方法。当然也包括它所实现接口的方法。
     * @param context
     */
    private static void injectEvent(Context context) {
        Class<?> clazz = context.getClass();
        Method[] methods = clazz.getMethods();

        for (Method method: methods) {
            /**
             * 扩展性
             */
            Annotation[] anns = method.getAnnotations();

            // 循环拿到方法类型注解
            for (Annotation ann: anns) {
                // 拿到注解的类  先拿到OnlickC注解
                Class<? extends Annotation> anntionType = ann.annotationType();
                //然后再拿到注解的注解EvenBase
                EventBase eventBase = anntionType.getAnnotation(EventBase.class);
                if (eventBase == null) {
                    continue;
                }
                //拿到事件的三要素
                // 设置事件 拿到 setOnclickListener
                String listenerSetter = eventBase.listenerSetter();
                // 事件类型 拿到 View.OnClickListener.class
                Class<?> listenerType = eventBase.listenerType();
                // 回调方法  拿到 callBackMethod onClick
                String callBackMethod = eventBase.callBackMethod();
                // 下一步 通过反射 给View 设置
                // 继续反射拿到 注解里面的id

                Map<String, Method> methodMap = new HashMap<>();
                // 得到当前callBackMethod 对应  Onclick  method -- clikText
                methodMap.put(callBackMethod, method);
                try {
                    //
                    Method declaredMethod = anntionType.getMethod("value");
                    // 注解上的方法  找到Id数组
                    int[] valuesId = (int[]) declaredMethod.invoke(ann);
                    for (int viewId: valuesId) {
                        Method findViewById = clazz.getMethod("findViewById", int.class);
                        View view = (View) findViewById.invoke(context, viewId);
                        //
                        if (view == null) {
                            continue;
                        }
                        //上面的事件三个要素全部拿到了  View的Class  setOnClickListener 对应  view的setOnClickListener
                        Method setOnClickListener = view.getClass().getMethod(listenerSetter, listenerType);

                        ListenerInvocationHandler handler = new ListenerInvocationHandler(context, methodMap);
                        // 拿到动态代理
                        Object proxyInstance = Proxy.newProxyInstance(listenerType.getClassLoader(), new Class[]{ listenerType }, handler);
                        // 将我们的方法执行
                        setOnClickListener.invoke(view, proxyInstance);
                    }
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    /**
     * 如何给其设置一个监听  onClick又不在InjectUtils回调 而是在MainActivity里面回调
     * 动态代理  今天的需求是要给按钮设置一个监听 我要执行的一个回调监听不能卸载InjectUtils里面
     * 而是想回调 MainActivity
     */





}
