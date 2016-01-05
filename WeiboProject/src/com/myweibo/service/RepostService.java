package com.myweibo.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myweibo.dao.WeiboDao;
import com.myweibo.entity.WeiboQueue;
import com.myweibo.utils.WeiboUtils;

@Service
@Transactional
public class RepostService {
	@Autowired
	private WeiboDao weiboDao;
	@Autowired
	private WeiboUtils weiboUtils;
	private Logger log = Logger.getLogger(this.getClass());
	private Thread rt = null;
	private boolean isRunning = true;
	private long sleepTime = 1000 * 60*46;
	
	public RepostService(){
		
	}
	
	public void start() throws Exception{
		if(rt == null || !rt.isAlive()){
			log.debug("RepostService.start()");
			rt = new Thread(new RepostThread());
			rt.start();
		}
	}
	public void stop() {
		log.error("转发队列中还有" + WeiboQueue.repostQueue.size() + "条数数据未处理！");
		isRunning = false;
	}
	class RepostThread implements Runnable {
		public void run() {
			log.info("RepostThread run.");
			while (isRunning) {
				try {
					while(true){
						if(!WeiboQueue.repostQueue.isEmpty()){
							String wbid = WeiboQueue.repostQueue.poll();
							try{
								if(wbid != null && weiboDao.getCountById(wbid)<=0){
									weiboDao.addRepost(wbid);
									weiboUtils.repost(wbid);
									log.info("转发微博id=" + wbid);
									break;
								}
							}catch(Exception e){
								log.error("转发微博error。id=",e);
							}
						}else{
							break;
						}
					}
				} catch (Exception e) {
					log.error("RepostThread error.", e);
				}
				try{
					Thread.sleep(sleepTime);
				}catch(Exception e){
					log.error("sleep error.", e);
				}
			}
			log.info("RepostThread end.");
		}
	}
}