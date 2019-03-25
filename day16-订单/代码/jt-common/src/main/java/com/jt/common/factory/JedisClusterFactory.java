package com.jt.common.factory;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Value;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class JedisClusterFactory implements FactoryBean<JedisCluster>{
	
	@Value("${redis.nodes}")
	private String redisNodes;
	
	@Override
	public JedisCluster getObject() throws Exception {
		Set<HostAndPort> nodes = getNodes();
		return new JedisCluster(nodes);
	}
	
	//获取set集合方法   IP:端口,IP:端口
	public Set<HostAndPort> getNodes(){
		Set<HostAndPort> nodes = new HashSet<>();
		String[] ipPorts = redisNodes.split(",");
		for (String node : ipPorts) {
			String[] args = node.split(":");
			String host = args[0];
			int port = Integer.parseInt(args[1]);
			HostAndPort hostAndPort = 
					new HostAndPort(host, port);
			nodes.add(hostAndPort);
		}
		return nodes;
	}

	@Override
	public Class<?> getObjectType() {
		
		return JedisCluster.class;
	}

	@Override
	public boolean isSingleton() {
		//表示单例
		return true;
	}

}
