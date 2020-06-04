package com.wang.fact;

/**
 * @Author: wangzhichao
 * @Date: 2020/6/4 13:51
 */

/**
 * 工厂方法
 */
public class FactoryMethod {

	public interface ICourse {
		void speak();
	}

	public static class  JavaCourse implements ICourse {

		@Override
		public void speak() {
			System.out.println("this is factory");
		}
	}

	public interface ICourseFactroy {
		ICourse create();
	}

	public static class JavaCourseFactory implements  ICourseFactroy {

		@Override
		public ICourse create() {
			return new JavaCourse();
		}
	}

	public static void main(String[] args) {
		ICourseFactroy factroy = new JavaCourseFactory();
		ICourse course = factroy.create();
		course.speak();
	}

}
