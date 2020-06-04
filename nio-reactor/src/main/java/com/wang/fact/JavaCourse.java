package com.wang.fact;

/**
 * @Author: wangzhichao
 * @Date: 2020/6/4 13:46
 */
/**
 * simple factory
 */
public class JavaCourse implements ICourse {

	@Override
	public void speack() {
		System.out.println("this is java course");
	}

	public static void main(String[] args) {
		ICourse course = CourseFactory.create(JavaCourse.class);
		course.speack();
	}


	static class CourseFactory {
		public static ICourse create(Class<? extends ICourse> clazz) {
			if(null != clazz) {
				try {
					return clazz.newInstance();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			return null;
		}
	}
}
