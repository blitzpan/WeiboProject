package com.myweibo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class WeiboDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	/**
	 * @Description:转发历史表新增一条记录 
	 * @param @param id
	 * @param @return
	 * @param @throws Exception   
	 * @return int  
	 * @throws
	 * @author Panyk
	 * @date 2015年12月30日
	 */
	public int addRepost(String id) throws Exception{
		String sql = "insert into repost(id,reposttime) values(?,now())";
		return jdbcTemplate.update(sql, id);
	}
}
