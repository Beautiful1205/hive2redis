package config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.yaml.snakeyaml.Yaml;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.io.InputStream;

/**
 * @author: Administrator$
 * @project: ltybdservice-root$
 * @date: 2017/12/1$ 16:14$
 * @description:
 */
public class RedisConfig {
    public static JedisCluster jc= new RedisConfig().getJedisCluster();
    private synchronized  JedisCluster  getJedisCluster(){
        if(jc!=null){
            return jc;
        }
        InputStream in = this.getClass().getResourceAsStream("/redis-site.yaml");
        RedisURI redisUri = new Yaml().loadAs(in,RedisURI.class);
        java.util.HashSet<HostAndPort> jedisClusterNodes = new java.util.HashSet<HostAndPort>();
        for (String u:
                redisUri.getUri()) {
            String[] addr=u.split(":");
            jedisClusterNodes.add(new HostAndPort(addr[0], Integer.parseInt(addr[1])));
        }
        return new JedisCluster(jedisClusterNodes, new GenericObjectPoolConfig());
    }

    public  static void main(String[] args){
        System.out.print(RedisConfig.jc);
    }
}
