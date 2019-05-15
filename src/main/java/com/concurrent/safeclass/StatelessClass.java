package com.concurrent.safeclass;

/**
 * 无状态的类 -> UserVo是外部传入，不属于此类的持有属性
 */
public class StatelessClass {
	public int service(int a,int b){

	    return a+b;
    }

    public void serviceUser(UserVo user){
        //do sth user
    }


}
