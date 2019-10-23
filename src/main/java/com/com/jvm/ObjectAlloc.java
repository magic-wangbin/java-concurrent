package com.com.jvm;
/**
 * JVM中对象的分配
 */
public class ObjectAlloc {

	public static class User{
		public int id = 0;
		public String name = "";
		User(){

		}
	}


	public static void alloc() {
		User u = new User();  //Object  在堆上分配的

		u.id = 5;
		u.name = "King";
	}

	public static void main(String[] args) {
		alloc();
	}



}
