package com.myweibo.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myweibo.utils.WeiboUtils;

import weibo4j.model.Status;
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
	private WeiboUtils weiboUtils;
	private Logger log = Logger.getLogger(this.getClass());
	private Thread rt = null;
	private boolean isRunning = true;
	private long sleepTime = 1000 * 60*40;//40分钟
	
	public IndexAnalyzeService(){
		
	}
	
	public void start() throws Exception{
		if(rt == null || !rt.isAlive()){
			rt = new Thread(new IndexAnalyzeThread());
			rt.start();
		}
	}
	public void stop() {
		isRunning = false;
	}
	class IndexAnalyzeThread implements Runnable {
		public void run() {
			log.info("IndexAnalyzeThread start.");
			while (isRunning) {
				try {
					List<Status> sl = weiboUtils.getIndex();//我的首页
					weiboUtils.getMaxRepostsAndcomments(sl);//转发评论最多的一条
				} catch (Exception e) {
					log.error("IndexAnalyzeThread error.", e);
				}
				try{
					Thread.sleep(sleepTime);
				}catch(Exception e){
					log.error("sleep error.", e);
				}
			}
			log.info("IndexAnalyzeThread end.");
		}
	}
}