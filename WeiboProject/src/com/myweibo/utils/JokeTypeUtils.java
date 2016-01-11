package com.myweibo.utils;

import java.util.Random;

public class JokeTypeUtils {
	/**
	 * @Description:0文字；1图片；2视频 
	 * @param @return   
	 * @return int  
	 * @throws
	 * @author Panyk
	 * @date 2016年1月11日
	 */
	public static int getJokeType(){
		int rand = new Random().nextInt(3);
		return rand;
	}
}
