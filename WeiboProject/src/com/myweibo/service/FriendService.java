package com.myweibo.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myweibo.dao.FriendshipsDao;
import com.myweibo.entity.WeiboQueue;
import com.myweibo.utils.WeiboUtils;
/**
 * ClassName: FriendService 
 * @Description: 关注好友
 * @author Panyk
 * @date 2015年12月31日
 */
@Service
@Transactional
public class FriendService {
	@Autowired
	private WeiboUtils weiboUtils;
	@Autowired
	private FriendshipsDao friendshipsDao;
	private Logger log = Logger.getLogger(this.getClass());
	private Thread rt = null;
	private boolean isRunning = true;
	private long sleepTime = 1000 * 60 * 40;
	
	public FriendService(){
		
	}
	
	public void start() throws Exception{
		if(rt == null || !rt.isAlive()){
			log.debug("FriendService.start()");
			rt = new Thread(new FriendThread());
			rt.start();
		}
	}
	public void stop() {
		isRunning = false;
	}
	class FriendThread implements Runnable {
		public void run() {
			log.info("FriendThread run.");
			while (isRunning) {
				while(true){
					try {
						if(!WeiboQueue.followQueue.isEmpty()){
							String uid = WeiboQueue.followQueue.poll();
							if(uid != null && friendshipsDao.getCountById(uid)<=0){
								friendshipsDao.addFriend(uid);
								weiboUtils.createFriendShip(uid);
							}
						}else{
							break;
						}
					} catch (Exception e) {
						log.error("FriendThread error.", e);
					}
				}
				try{
					Thread.sleep(sleepTime);
				}catch(Exception e){
					log.error("sleep error.", e);
				}
			}
			log.info("FriendThread end.");
		}
	}
}