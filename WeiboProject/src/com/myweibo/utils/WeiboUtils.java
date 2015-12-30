package com.myweibo.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.myweibo.entity.WeiboEntity;

import weibo4j.Timeline;
import weibo4j.model.Status;
import weibo4j.model.StatusWapper;
import weibo4j.model.User;
@Component
public class WeiboUtils {
	Logger log = Logger.getLogger(this.getClass());
	public static String at = "2.00Hp8ZeC1rOq2C7a23d0528aB4paQC";
	
	/**
	 * @Description: 获取我的首页中评论转发最多的一条
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
			//过滤敏感微博
			if(s.getText().contains("转发抽奖平台")){
				continue;
			}
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
	
	public String getFavouriteWeiboAndUsers() throws Exception{
		String id = "";
		try{
			Timeline tm = new Timeline(at);
			//获取最新的公共微博
			StatusWapper sw = tm.getPublicTimeline(40, 0);
			List<Status> swl = sw.getStatuses();
		}catch(Exception e){
			log.error("doSearchInCur", e);
			throw e;
		}
		return id;
	}
	/**
	 * @Description: 过滤出喜欢的微博和作者，过滤掉敏感词
	 * @param @param swl
	 * @param @return
	 * @param @throws Exception   
	 * @return List<WeiboEntity>  
	 * @throws
	 * @author Panyk
	 * @date 2015年12月30日
	 */
	private List<WeiboEntity> doLikeAndNotLike(List<Status> swl) throws Exception{
		List<WeiboEntity> weL = new ArrayList<WeiboEntity>();
		String text = "";
		boolean save;
		WeiboEntity we = null;
		User u = null;
		for(Status s:swl){
			save = false;
			text = s.getText();
			if(text.contains("转发抽奖") || text.contains("转发有奖")){
				save = true;
			}
			//过滤敏感微博
			if(s.getText().contains("转发抽奖平台")){
				save = false;
			}
			if(save){
				we = new WeiboEntity();
				u = s.getUser();
				//粉丝数+关注数+收藏数
				int count = u.getFollowersCount() + u.getFriendsCount() + u.getFavouritesCount();
				if(count > 10000){
					we.setUserId(s.getUser().getId());
				}
				we.setWbId(s.getId());
				weL.add(we);
			}
		}
		return weL;
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