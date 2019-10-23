package com.com.jvm.type;

import java.util.ArrayList;

/**
 * 泛型的注意事项
 * JDK的编译器是可以通过（方法的特征： 返回类型+ 方法名+ param）
 */
public class Conflict {
//    public String method(List<String> stringList) {
//        System.out.println("List");
//        return "OK";
//    }
//
//    public User method(List<Integer> integerList) {
//        System.out.println("List");
//        return new User();
//    }

    ArrayList<String> list1 = new ArrayList(); //第一种 情况
    ArrayList list2 = new ArrayList<String>(); //第二种 情况
}

class User {

}
