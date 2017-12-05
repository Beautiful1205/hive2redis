package config;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;

/**
 * @author: Administrator$
 * @project: hive2redis$
 * @date: 2017/11/28$ 16:25$
 * @description:
 */
public class RedisConfig {
    private String uri;
    public String getConfig(){
        InputStream in = this.getClass().getResourceAsStream("/redis-site.yaml");
        Yaml yaml = new Yaml();
        return yaml.load(in).toString().split("=")[1].replace("}","");
    }
}
