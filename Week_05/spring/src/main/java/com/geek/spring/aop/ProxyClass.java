package com.geek.spring.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @program: JAVA-000
 * @description: 代理实现类
 * @author: yarne
 * @create: 2020-11-16 18:24
 * @see java.util.logging.Handler
 **/
public class ProxyClass implements InvocationHandler {

    public BaseInterface baseInterface;

    public final BlockingQueue blockingQueue=new LinkedBlockingQueue<InvocationHandler>();

    public ProxyClass(BaseInterface baseInterface) {
        this.baseInterface = baseInterface;
    }


    public void bind(Object object){
        try {
            blockingQueue.put(new AdviceAround(object));
            blockingQueue.put(new AdviceBefore(object));
            blockingQueue.put(new AdviceAfter(object));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object take = blockingQueue.take();
        if(take!=null){
            if(take instanceof  InvocationHandler){
                InvocationHandler take1 = (InvocationHandler) take;
               return take1.invoke(proxy,method,args);
            }
        }else{
            return method.invoke(baseInterface,args);
        }
        return null;
    }
}
