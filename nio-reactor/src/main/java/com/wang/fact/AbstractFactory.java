package com.wang.fact;

/**
 * @Author: wangzhichao
 * @Date: 2020/6/4 13:57
 */

public class AbstractFactory {

	interface IVideo {
		void record();
	}

	interface INote {
		void edit();
	}

	class JavaVideo implements IVideo {
		@Override
		public void record() {
			System.out.println("java record");
		}
	}

	class JavaNote implements INote {
		@Override
		public void edit() {
			System.out.println("java note");
		}
	}



	abstract class AbsFactory {
		public void init() {
			System.out.println("init data");
		}

		public abstract INote createNote();
		public abstract IVideo createVideo();

	}


	class JavaFactory extends AbsFactory {
		@Override
		public INote createNote() {
			return new JavaNote();
		}

		@Override
		public IVideo createVideo() {
			return new JavaVideo();
		}
	}

	public static void main(String[] args) {
//		AbsFactory factory = new JavaFactory();
//		factory.createNote().edit();
//		factory.createVideo().record();
	}

}
