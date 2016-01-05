package com.myweibo.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myweibo.utils.WeiboUtils;

import weibo4j.model.Status;
/**
 * ClassName: PublicAnalyzeService 
 * @Description: 公共最新内容分析
 * @author Panyk
 * @date 2015年12月31日
 */
@Service
@Transactional
public class PublicAnalyzeService {
	@Autowired
	private WeiboUtils weiboUtils;
	private Logger log = Logger.getLogger(this.getClass());
	private Thread rt = null;
	private boolean isRunning = true;
	private long sleepTime = 1000 * 60 * 35;
	
	public PublicAnalyzeService(){
		
	}
	
	public void start() throws Exception{
		if(rt == null || !rt.isAlive()){
			log.debug("PublicAnalyzeService.start()");
			rt = new Thread(new PublicAnalyzeThread());
			rt.start();
		}
	}
	public void stop() {
		isRunning = false;
	}
	class PublicAnalyzeThread implements Runnable {
		public void run() {
			log.info("PublicAnalyzeService run.");
			while (isRunning) {
				try {
					List<Status> sl = weiboUtils.getPublic();
					weiboUtils.doLikeAndNotLike(sl);
				} catch (Exception e) {
					log.error("PublicAnalyzeService error.", e);
				}
				try{
					Thread.sleep(sleepTime);
				}catch(Exception e){
					log.error("sleep error.", e);
				}
			}
			log.info("PublicAnalyzeService end.");
		}
	}
}