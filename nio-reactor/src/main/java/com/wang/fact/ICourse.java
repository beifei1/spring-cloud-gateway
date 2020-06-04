package com.wang.fact;

/**
 * @Author: wangzhichao
 * @Date: 2020/6/4 13:45
 */

public interface ICourse {

	default void speack() {
		System.out.println("this is icourse");
	}

}
