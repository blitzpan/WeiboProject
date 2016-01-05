package com.myweibo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class FriendshipsDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	public int addFriend(String id) throws Exception{
		String sql = "insert into friendships(id,createtime) values(?,now())";
		return jdbcTemplate.update(sql, id);
	}
	public int getCountById(String id) throws Exception{
		String sql = "select count(*) from friendships where id=?";
		return jdbcTemplate.queryForInt(sql, id);
	}
}
