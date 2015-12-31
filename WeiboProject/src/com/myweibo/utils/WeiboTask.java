package com.myweibo.utils;


import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.myweibo.service.RepostService;
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
	@Autowired
	private RepostService repostService;
	
	Logger log = Logger.getLogger(WeiboTask.class);
	/**
	 * @Description:开启转发线程 
	 * @param    
	 * @return void  
	 * @throws
	 * @author Panyk
	 * @date 2015年12月31日
	 */
	public void startRepostThread(){
		log.info("startRepostThread start.");
		try {
			repostService.start();
		} catch (Exception e) {
			log.error("startRepostThread error=", e);
		}
		log.info("startRepostThread end.");
	}
	
	
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
//			weiboService.repostIndexMostPopular();
		}catch(Exception e){
			
		}
	}
}