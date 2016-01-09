package com.myweibo.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myweibo.dao.JokeDao;
import com.myweibo.entity.Joke;
import com.myweibo.utils.WeiboUtils;

@Service
@Transactional
public class JokeService {
	@Autowired 
	private JokeDao jokeDao;
	@Autowired
	private WeiboUtils weiboUtils;
	private Logger log = Logger.getLogger(this.getClass());
	private Thread rt = null;
	private boolean isRunning = true;
	private long sleepTime = 1000 * 60*40;
	
	public JokeService(){
		
	}
	
	public void start() throws Exception{
		if(rt == null || !rt.isAlive()){
			log.debug("JokeService.start()");
			rt = new Thread(new JokeThread());
			rt.start();
		}
	}
	public void stop() {
		isRunning = false;
	}
	class JokeThread implements Runnable {
		public void run() {
			log.info("JokeThread run.");
			String msg = "";
			while (isRunning) {
				try {
					Joke j = jokeDao.getJoke(null);
					jokeDao.upJokeSendC(j);
					msg = j.getContent();
					weiboUtils.updateStatus(msg);
				} catch (Exception e) {
					log.error("JokeThread error.", e);
				}
				try{
					Thread.sleep(sleepTime);
				}catch(Exception e){
					log.error("sleep error.", e);
				}
			}
			log.info("JokeThread end.");
		}
	}
}