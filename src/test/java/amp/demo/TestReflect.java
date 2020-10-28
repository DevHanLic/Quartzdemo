package amp.demo;

import amp.demo.aop.TestRequest;
import amp.demo.aop.TestResponse;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestReflect {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
//        //1
//        Class<?> aClass = Class.forName("amp.demo.aop.TestRequest");
//        System.out.println(aClass);
//
//        Object o = aClass.newInstance();
//        System.out.println(o);
//        Method toString = aClass.getMethod("toString");
//
//        toString.invoke(o);
//
//        //2
//        Class<TestResponse> testRequestClass = TestResponse.class;
//        System.out.println(testRequestClass);
//        TestResponse testRequest = testRequestClass.newInstance();
//        System.out.println(testRequest);
//        Method toString1 = testRequestClass.getMethod("toString");
//        toString1.invoke(testRequest);


        TestRequest testRequest1=new TestRequest();
        Class<? extends TestRequest> aClass1 = testRequest1.getClass();
        TestRequest testRequest = aClass1.newInstance();
        Method toString = aClass1.getMethod("toString");
        Object invoke = toString.invoke(testRequest);
        System.out.println(invoke);

    }
}
