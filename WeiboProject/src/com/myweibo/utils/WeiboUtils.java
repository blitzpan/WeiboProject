package com.myweibo.utils;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import weibo4j.Timeline;
import weibo4j.model.Status;
import weibo4j.model.StatusWapper;
@Component
public class WeiboUtils {
	Logger log = Logger.getLogger(this.getClass());
	public static String at = "2.00Hp8ZeC1rOq2C7a23d0528aB4paQC";
	
	/**
	 * @Description: 转发我的首页中评论转发最多的一条
	 * @param @throws Exception   
	 * @return void  
	 * @throws
	 * @author Panyk
	 * @date 2015年12月30日
	 */
	public String getIndexMostPopular() throws Exception{
		String id = "";
		try{
			Timeline tm = new Timeline(at);
			//获取最新的公共微博，并转发其中转发量最高的一条
//			StatusWapper sw = tm.getPublicTimeline(40, 0);
			StatusWapper sw = tm.getFriendsTimeline();
			List<Status> swl = sw.getStatuses();
			id = this.getMaxRepostsAndcomments(swl);
		}catch(Exception e){
			log.error("repostIndexMostPopular", e);
			throw e;
		}
		return id;
	}
	/**
	 * @Description:获取（转发+评论）最多的一条 
	 * @param @param sl
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author Panyk
	 * @date 2015年12月30日
	 */
	private String getMaxRepostsAndcomments(List<Status> sl){
		String tempId = "";
		int max = 0;
		for(Status s:sl){
			if((s.getRepostsCount() + s.getCommentsCount()) > max){
				tempId = s.getId();
				max = s.getRepostsCount() + s.getCommentsCount();
				
			}
			log.debug("转发=" + s.getRepostsCount() + ";评论=" + s.getCommentsCount());
			log.debug(s);
			log.debug("------------------------------------");
		}
		if(max < 0){//转发+评论 > 2000 返回
			tempId = "";
		}
		return tempId;
	}
	/**
	 * @Description:根据id转发一条 
	 * @param @param id
	 * @param @throws Exception   
	 * @return void  
	 * @throws
	 * @author Panyk
	 * @date 2015年12月30日
	 */
	public void repost(String id) throws Exception{
		try{
			Timeline tm = new Timeline(at);
			if(id!=null&& id.length()>0){
				tm.repost(id);
			}
		}catch(Exception e){
			log.error("repost",e);
			throw e;
		}
	}
	
	public static void main(String[] args) {
		WeiboUtils wu = new WeiboUtils();
		try {
			wu.getIndexMostPopular();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
