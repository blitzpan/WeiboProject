package com.myweibo.entity;

import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;


public class WeiboQueue {
	private static Logger log = Logger.getLogger(WeiboQueue.class);
	
	public static LinkedBlockingQueue<String> followQueue = new LinkedBlockingQueue();//待关注用户queue
	public static LinkedBlockingQueue<String> repostQueue = new LinkedBlockingQueue();//待转发微博queue

	public static void addrepostQueue(String id){
		try{
			if(id != null && id.length() > 0)
			repostQueue.add(id);
		}catch(Exception e){
			log.error("addrepostQueue",e);
		}
	}
	public static void addfollowQueue(String id){
		try{
			if(id != null && id.length() > 0)
				followQueue.add(id);
		}catch(Exception e){
			log.error("addfollowQueue",e);
		}
	}
}
