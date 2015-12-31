package com.myweibo.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myweibo.dao.WeiboDao;
import com.myweibo.utils.WeiboUtils;

@Service
@Transactional
public class WeiboService {
	@Autowired
	private WeiboDao weiboDao;
	@Autowired
	private WeiboUtils weiboUtils;
	Logger log = Logger.getLogger(WeiboService.class);
	/**
	 * @Description:转发首页最受欢迎微博 
	 * @param @throws Exception   
	 * @return void  
	 * @throws
	 * @author Panyk
	 * @date 2015年12月30日
	 */
	public void repostIndexMostPopular() throws Exception{
		try{
			/*String id = weiboUtils.getIndexMostPopular();
			if(id!=null && id.length()>0){
				if(weiboDao.addRepost(id)>0){
					weiboUtils.repost(id);
					log.info("转发微博id=" + id);
				}				
			}*/
		}catch(Exception e){
			log.error("repostIndexMostPopular=", e);
			throw e;
		}
	}
}
