package com.com.jvm;

public class JvmTest {

    public static void test(int i, UserService userService) {
        System.out.println(i);
        i++;
        userService.sayHell("hello,world");
        System.out.println("============================");
    }
}

