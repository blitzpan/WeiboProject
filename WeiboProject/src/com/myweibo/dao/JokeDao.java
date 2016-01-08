package com.myweibo.dao;

import java.util.Vector;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.myweibo.entity.Joke;

@Repository
public class JokeDao {
	private JdbcTemplate jdbcTemplate;
	
	public Joke getJoke(Joke joke) throws Exception{
		String sql = "select * from joke where 1=1";
		Vector values = new Vector();
		if(joke.getType()!=null){
			sql += " and type=?";
			values.add(joke.getType());
		}
		sql += " limit 0,1";
		return jdbcTemplate.queryForObject(sql, values.toArray(), new BeanPropertyRowMapper(Joke.class));
	}
}
