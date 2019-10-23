package com.com.jvm.ch04.dispatch;
/**
 * @author 【享学课堂】 King老师
 * 静态分派
 */
public class StaticDispatch{

	static abstract class Human{}
	static class Man extends Human{	}
	static class Woman extends Human{}

	public void sayHello(Human guy){
		System.out.println("hello,guy！");
	}
	public void sayHello(Man guy){
		System.out.println("hello,gentleman！");
	}
	public void sayHello(Woman guy){
		System.out.println("hello,lady！");
	}
	public static void main(String[]args){
		Human h1 = new Man();
		Human h2 = new Woman();
		StaticDispatch sr = new StaticDispatch();
		sr.sayHello(h1);
		sr.sayHello(h2);

		//实际类型变化
		Human man=new Man();
		//静态类型变化
		sr.sayHello((Man)man);
		man=new Woman();
		sr.sayHello((Woman)man);


	}
}
