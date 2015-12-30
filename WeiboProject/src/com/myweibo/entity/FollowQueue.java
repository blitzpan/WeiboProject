package com.myweibo.entity;

import java.util.concurrent.LinkedBlockingQueue;


public class FollowQueue {
	private static LinkedBlockingQueue followQueue = new LinkedBlockingQueue();//待关注用户queue
	private static LinkedBlockingQueue wbQueue = new LinkedBlockingQueue();//待转发微博queue
}
