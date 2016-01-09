package com.myweibo.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.myweibo.entity.WeiboEntity;
import com.myweibo.entity.WeiboQueue;

import weibo4j.Friendships;
import weibo4j.Timeline;
import weibo4j.model.Status;
import weibo4j.model.StatusWapper;
import weibo4j.model.User;
@Component
public class WeiboUtils {
	Logger log = Logger.getLogger(this.getClass());
	public static String at = "2.00Hp8ZeC1rOq2C7a23d0528aB4paQC";
	
	public void createFriendShip(String id) throws Exception{
		if(id!=null && id.length() > 0){
			Friendships fs = new Friendships(at);
			try{
				log.info("关注id=" + id);
				fs.createFriendshipsById(id);
			}catch(Exception e){
				log.error("关注friend。id="+id+"异常", e);
				WeiboQueue.followQueue.add(id);
			}
		}
	}
	/**
	 * @Description: 获取我的首页
	 * @param @return
	 * @param @throws Exception   
	 * @return List<Status>  
	 * @throws
	 * @author Panyk
	 * @date 2015年12月31日
	 */
	public List<Status> getIndex() throws Exception{
		List<Status> swl = null;
		try{
			Timeline tm = new Timeline(at);
			StatusWapper sw = tm.getFriendsTimeline();
			swl = sw.getStatuses();
		}catch(Exception e){
			log.error("getIndex", e);
			throw e;
		}
		return swl;
	}
	/**
	 * @Description:获取50最新公共微博 
	 * @param @return
	 * @param @throws Exception   
	 * @return List<Status>  
	 * @throws
	 * @author Panyk
	 * @date 2015年12月31日
	 */
	public List<Status> getPublic() throws Exception{
		List<Status> swl = null;
		try{
			Timeline tm = new Timeline(at);
			StatusWapper sw = tm.getPublicTimeline(50, 0);
			swl = sw.getStatuses();
		}catch(Exception e){
			log.error("getPublic", e);
			throw e;
		}
		return swl;
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
	public String getMaxRepostsAndcomments(List<Status> sl){
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
		if(max > 2000){//转发+评论 > 2000 返回
			//加入到转发队列中
			log.debug("最高评论加转发加入到转发队列。id=" + tempId);
			WeiboQueue.addrepostQueue(tempId);
		}else{
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
			WeiboQueue.addrepostQueue(id);
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
	public void doLikeAndNotLike(List<Status> swl) throws Exception{
		List<WeiboEntity> weL = new ArrayList<WeiboEntity>();
		String text = "";
		boolean save;
		User u = null;
		for(Status s:swl){
			save = false;
			text = s.getText();
			if(text.contains("转发抽奖") || text.contains("转发有奖")){
				save = true;
			}
			//过滤敏感微博
			if(s.getText().contains("转发抽奖平台") || s.getText().contains("瘦身神器")){
				save = false;
			}
			if(save){
				u = s.getUser();
				//粉丝数+关注数+收藏数
				int count = u.getFollowersCount() + u.getFriendsCount() + u.getFavouritesCount();
				if(count > 10000){
					WeiboQueue.addfollowQueue(s.getUser().getId());
				}
				WeiboQueue.addrepostQueue(s.getId());
			}
		}
	}
	/**
	 * @Description: 发一条
	 * @param @param str
	 * @param @throws Exception   
	 * @return void  
	 * @throws
	 * @author Panyk
	 * @date 2016年1月8日
	 */
	public void updateStatus(String str) throws Exception{
		Status s = null;
		try{
			Timeline tl = new Timeline(at);
			if(str.length()>140){
				str = str.substring(0, 139);
			}
			s = tl.updateStatus(str);
			log.debug("updateStatus status=" + s);
		}catch(Exception e){
			log.error("updateStatus发微博异常。", e);
			throw e;
		}
	}
	
	public static void main(String[] args) {
		WeiboUtils wu = new WeiboUtils();
		try {
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}