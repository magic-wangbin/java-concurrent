package com.com.jvm.ch04.dispatch;
/**
 * @author 【享学课堂】 King老师
 * 动态分派
 */
public class DynamicDispatch {
	static abstract class Human{
		protected abstract void sayHello();
	}
	static class Man extends Human{
		@Override
		protected void sayHello() {
			System.out.println("hello,gentleman！");
		}
	}
	static class Woman extends Human{
		@Override
		protected void sayHello() {
			System.out.println("hello,lady！");
		}
	}
	public static void main(String[]args){
		Human h1 = new Man();
		Human h2 = new Woman();
		h1.sayHello();
		h2.sayHello();

		h1 = new Woman();
		h1.sayHello();
	}
}
