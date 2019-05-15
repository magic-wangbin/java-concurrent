package com.concurrent.safeclass;

/**
 * 类不可变
 */
public class ImmutableClass {
    private final int a;
    private final UserVo user = new UserVo();//不安全

    public int getA() {
        return a;
    }

    // 返回的UserVo可能被修改
    public UserVo getUser() {
        return user;
    }


    public ImmutableClass(int a) {
        this.a = a;
    }

    public static class User {
        private int age;

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}
