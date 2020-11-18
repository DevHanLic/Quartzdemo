package amp.demo;

import amp.demo.aop.TestRequest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TestReflect {

    private List<Integer> list = new ArrayList<>();

    void demo() {
        System.out.println(list.getClass() + "----------" + list.stream().collect(Collectors.toList()));
        initDemo("1");
        System.out.println(list.getClass() + "----------" + list.stream().collect(Collectors.toList()));
        destoy();
        initDemo("2");
    }

    private void destoy() {
        list.clear();
    }

    private void initDemo(String str) {
        if ("1".equals("1")) {
            List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
            System.out.println(list.getClass() + "initDemo----------" + list.stream().collect(Collectors.toList()));
        } else {
            System.out.println(list.getClass() + "initDemo----------" + list.stream().collect(Collectors.toList()));
        }
    }


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

//
//        TestRequest testRequest1=new TestRequest();
//        Class<? extends TestRequest> aClass1 = testRequest1.getClass();
//        TestRequest testRequest = aClass1.newInstance();
//        Method toString = aClass1.getMethod("toString");
//        Object invoke = toString.invoke(testRequest);
//        System.out.println(invoke);

        TestReflect testReflect = new TestReflect();
        testReflect.demo();
    }
}
