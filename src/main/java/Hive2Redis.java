import config.RedisConfig;
import redis.clients.jedis.JedisCluster;

import java.io.IOException;
import java.sql.*;


/**
 * @author: Administrator$
 * @project: jedis$
 * @date: 2017/11/27$ 13:58$
 * @description:
 */
public class Hive2Redis {
    private static String driverName = "org.apache.hive.jdbc.HiveDriver";
    private static String jdbcConnUrl = "jdbc:hive2://master:10000/";
    private static String hiveUser = "hdfs";
    private static String hivePassword = "hdfs";
    private static String databaseName = "app";
    private static String tableName = "base_station";

    public static void main(String[] args) throws SQLException, IOException {

        JedisCluster jc = RedisConfig.jc;
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        Connection conn = DriverManager.getConnection(jdbcConnUrl + databaseName, hiveUser, hivePassword);
        String sql = "select stationid,name,longitude,latitude,stationflag,stationstatus,citycode from " + tableName;
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            long stationid = rs.getLong("stationid");
            String name = rs.getString("name");
            double longitude = Double.parseDouble(rs.getString("longitude"));
            double latitude = Double.parseDouble(rs.getString("latitude"));
            int stationflag = rs.getInt("stationflag");
            int stationstatus = rs.getInt("stationstatus");
            String citycode = rs.getString("citycode");
            jc.geoadd(citycode, longitude, latitude, String.valueOf(stationid) + "-" + name + "-" + stationflag + "-" + stationstatus);
        }
        rs.close();
        conn.close();
    }
}
