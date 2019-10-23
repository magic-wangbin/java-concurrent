package com.com.jvm.ch04.deencrpt;

public class DemoRun {

    public static void main(String[] args) throws Exception {
        //new出自定义类加载器
        CustomClassLoader demoCustomClassLoader = new CustomClassLoader("My ClassLoader");
        //设置加载类的路径
        demoCustomClassLoader.setBasePath("D:\\work\\ref-jvm\\bin\\");
        Class<?> clazz = demoCustomClassLoader.findClass("com.jvm.ch04.deencrpt.DemoUser");
        System.out.println(clazz.getClassLoader());
        Object o = clazz.newInstance();
        System.out.println(o);
    }
}
