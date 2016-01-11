package com.myweibo.service;

import java.io.File;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myweibo.dao.JokeDao;
import com.myweibo.entity.Constents;
import com.myweibo.entity.ImgJoke;
import com.myweibo.entity.Joke;
import com.myweibo.utils.JokeTypeUtils;
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
					int type = JokeTypeUtils.getJokeType();
					
					type = type==2?0:type;
					type = 1;
					
					switch(type){
					case 1://图片
						ImgJoke ij = null;
						boolean flag = true;
						while(flag){
							ij = jokeDao.getImgJoke(null);
							String fileName = ij.getFilename();
							File f = new File(Constents.imgPath + File.separator + fileName);
							if(!f.exists()){
								ij.setSendc(1000);
								jokeDao.upImgJokeSendC(ij);
								continue;
							}
							ij.setSendc(ij.getSendc() + 1);
							jokeDao.upImgJokeSendC(ij);
							break;
						}
						weiboUtils.updateStatus(ij.getTitle(), Constents.imgPath + File.separator + ij.getFilename());
						break;						
					default://文字
						Joke j = jokeDao.getJoke(null);
						jokeDao.upJokeSendC(j);
						msg = j.getContent();
						weiboUtils.updateStatus(msg);
						break;
					}
					
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