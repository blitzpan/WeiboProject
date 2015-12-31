package com.myweibo.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myweibo.dao.WeiboDao;
import com.myweibo.entity.WeiboQueue;
import com.myweibo.utils.WeiboUtils;
/**
 * ClassName: IndexAnalyzeService 
 * @Description: 我的首页内容分析
 * @author Panyk
 * @date 2015年12月31日
 */
@Service
@Transactional
public class IndexAnalyzeService {
	@Autowired
	private WeiboDao weiboDao;
	@Autowired
	private WeiboUtils weiboUtils;
	private Logger log = Logger.getLogger(IndexAnalyzeService.class);
	private Thread rt = null;
	private boolean isRunning = true;
	private long sleepTime = 1000 * 60;
	
	public IndexAnalyzeService(){
		
	}
	
	public void start() throws Exception{
		if(rt !=null && !rt.isAlive()){
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
			log.info("RepostThread start.");
			while (isRunning) {
				try {
					if(!WeiboQueue.repostQueue.isEmpty()){
						String wbid = WeiboQueue.repostQueue.poll();
						try{
							if(wbid != null && weiboDao.addRepost(wbid)>0){
								weiboUtils.repost(wbid);
								log.info("转发微博id=" + wbid);
							}
						}catch(Exception e){
							log.error("转发微博error。id=",e);
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