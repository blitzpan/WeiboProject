package com.myweibo.dao;

import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.myweibo.entity.Joke;

@Repository
public class JokeDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public Joke getJoke(Joke joke) throws Exception{
		String sql = "select * from joke where 1=1";
		Vector values = new Vector();
		if(joke!=null && joke.getType()!=null){
			sql += " and type=?";
			values.add(joke.getType());
		}
		sql += " ORDER BY sendc asc LIMIT 0,1";
		return jdbcTemplate.queryForObject(sql, values.toArray(), new BeanPropertyRowMapper(Joke.class));
	}
	public int upJokeSendC(Joke joke) throws Exception{
		String sql = "update joke set sendC=sendc+1 where id=?";
		return jdbcTemplate.update(sql,joke.getId());
	}
}
