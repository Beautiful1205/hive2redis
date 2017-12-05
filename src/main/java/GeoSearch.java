import config.RedisConfig;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.GeoRadiusResponse;
import redis.clients.jedis.GeoUnit;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.params.geo.GeoRadiusParam;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author: Administrator$
 * @project: hive2redis$
 * @date: 2017/11/27$ 16:17$
 * @description:
 */
public class GeoSearch {
    public static void main(String[] args) throws  IOException {
        JedisCluster jc = RedisConfig.jc;
        List<GeoRadiusResponse> listRes =jc.georadius("130400",114.479278,36.612833,5, GeoUnit.KM,  GeoRadiusParam.geoRadiusParam().sortAscending().count(1).withCoord());
        GeoRadiusResponse res= listRes.get(0);
        String[] a = res.getMemberByString().split("-");
        System.out.println(a[0]);
        jc.close();
    }
}
