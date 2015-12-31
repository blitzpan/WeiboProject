package com.myweibo.entity;

import java.util.concurrent.LinkedBlockingQueue;


public class WeiboQueue {
	public static LinkedBlockingQueue<String> followQueue = new LinkedBlockingQueue();//待关注用户queue
	public static LinkedBlockingQueue<String> repostQueue = new LinkedBlockingQueue();//待转发微博queue
}
