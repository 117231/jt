package com.jt.test;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.Transaction;

public class RedisTest {
	
	/**
	 * 1.通过IP和端口可以连接Redis
	 * 2.操作方法就是命令
	 * @throws InterruptedException 
	 * **/
	@Test
	public void StringTest() throws InterruptedException {
		Jedis jedis = 
				new Jedis("192.168.126.174", 6379);
		String result = jedis.set("1811","今天周四");
		//指定超时时间
		jedis.expire("1811",20);
		long time = jedis.ttl("1811");
		jedis.persist("1811");
		System.out.println("还能存活"+time+"秒");
		System.out.println("保存后的返回值为:"+result);
		System.out.println("获取数据:"+jedis.get("1811"));
	}
	
	//操作hash
	@Test
	public void testHash() {
		Jedis jedis = new Jedis("192.168.126.174", 6379);
		jedis.hset("dog", "name","二哈");
		jedis.hset("dog", "age", "7");
		Map<String,String> map = 
				jedis.hgetAll("dog");
		System.out.println(map);
	}
	
	/**操作List集合,改数据类型,不能长期保存数据,
	数据最终会被消费.*/
	@Test
	public void testList() {
		Jedis jedis = new Jedis("192.168.126.174", 6379);
		jedis.rpush("list","1","2","3");
		String a = jedis.rpop("list");
		System.out.println(a);
	}
	
	//控制redis事务问题
	@Test
	public void tx() {
		Jedis jedis = new Jedis("192.168.126.174", 6379);
		//开启事务
		Transaction transaction = jedis.multi();
		try {
			transaction.set("kk", "kk");
			transaction.set("ww", "ww");
			int a = 1/0;
			transaction.exec();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.discard();
		}
	}
	
	/**
	 * 参数说明
	 * 	1.masterName  主机的变量名称
	 *  2.sentinels   哨兵的信息
	 *  String=IP:端口
	 */
	@Test
	public void testSentinel() {
		Set<String> sentinels = new HashSet<>();
		sentinels.add("192.168.126.174:26379");
		JedisSentinelPool pool = 
		new JedisSentinelPool("mymaster", sentinels);
		Jedis jedis = pool.getResource();
		jedis.set("aa", "abc");
		System.out.println("获取redis数据:"+jedis.get("aa"));
		jedis.close();
	}
	
	@Test
	public void testRedisCluster() {
		String host = "192.168.126.174";
		Set<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort(host, 7000));
		nodes.add(new HostAndPort(host, 7001));
		nodes.add(new HostAndPort(host, 7002));
		nodes.add(new HostAndPort(host, 7003));
		nodes.add(new HostAndPort(host, 7004));
		nodes.add(new HostAndPort(host, 7005));
		nodes.add(new HostAndPort(host, 7006));
		nodes.add(new HostAndPort(host, 7007));
		nodes.add(new HostAndPort(host, 7008));
		JedisCluster jedisCluster = new JedisCluster(nodes);
		jedisCluster.set("1811", "redis集群搭建完成!!!");
		System.out.println("获取数据:"+jedisCluster.get("1811"));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
