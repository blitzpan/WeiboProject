package com.myweibo.utils;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.myweibo.service.WeiboService;

/**
 * 定时发送微博
 * @author Administrator
 */
@Component
@Scope("prototype")//每一个请求都有一个类来处理，避免线程安全问题。
public class WeiboTask {
	@Autowired
	private WeiboService weiboService; 
	/**
	 * @Description:转发 
	 * @param    
	 * @return void  
	 * @throws
	 * @author Panyk
	 * @date 2015年12月30日
	 */
	public void repost() {
		System.out.println("转发开始" + new Date()); 
		try{
			weiboService.repostIndexMostPopular();
		}catch(Exception e){
			
		}
		
	}
}