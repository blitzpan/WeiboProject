package com.myweibo.utils;


import java.util.Date;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 定时发送微博
 * @author Administrator
 */
@Component
@Scope("prototype")//每一个请求都有一个类来处理，避免线程安全问题。
public class WeiboTask {
	/**
	 * @Description:转发 
	 * @param    
	 * @return void  
	 * @throws
	 * @author Panyk
	 * @date 2015年12月30日
	 */
	public void transmit() {
		System.out.println("转发开始" + new Date());  
	}
}