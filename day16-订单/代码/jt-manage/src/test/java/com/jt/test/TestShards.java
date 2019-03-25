package com.jt.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;

public class TestShards {
	
	@Test
	public void test01() {
		List<JedisShardInfo> shards =
					new ArrayList<JedisShardInfo>();
		JedisShardInfo info1 = 
				new JedisShardInfo("192.168.126.174",6379);
		JedisShardInfo info2 = 
				new JedisShardInfo("192.168.126.174",6380);
		JedisShardInfo info3 = 
				new JedisShardInfo("192.168.126.174",6381);
		shards.add(info1);
		shards.add(info2);
		shards.add(info3);
		ShardedJedis jedis = new ShardedJedis(shards);
		jedis.set("1811","分片测试");
		System.out.println(jedis.get("1811"));
		//问:数据保存到了哪个redis节点??如何存储??
	}
}
