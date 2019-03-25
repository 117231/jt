package com.jt.order.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

@Configuration	//我是配置类
@PropertySource("classpath:/properties/redis.properties")
public class JedisClusterConfig {
	@Value("${redis.nodes}")
	private String redisNodes;
	
	@Bean //标识对象
	public JedisCluster getJedisCluster() {
		Set<HostAndPort> nodes = new HashSet<>();
		String[] rNodes = redisNodes.split(",");
		for (String node : rNodes) {
			String[] args = node.split(":");
			String ip = args[0];
			int port = Integer.parseInt(args[1]);
			HostAndPort hostAndPort = 
					new HostAndPort(ip, port);
			nodes.add(hostAndPort);
		}
		return new JedisCluster(nodes);
	}
	
}
