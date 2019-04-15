package com.bubblehub.model.loader;

/**
 * @Author Fisher
 * @Date 2019/4/10 15:54
 **/

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * 资源加载器
 * 从配置文件中读取资源并加载
 */
public class ElementLoader {

    // 游戏全局配置
    private Map<String, String> globalConfig;

    // 游戏元素配置
    private Map<String, String> elementConfig;

    // 读取配置文件
    private Properties pro;

    private static ElementLoader loader;

    private ElementLoader() {
        this.globalConfig = new HashMap<>();
        this.elementConfig = new HashMap<>();
        this.pro = new Properties();
    }

    /**
     * 由于Loader不是经常使用，所以可以对该对象加锁
     * @return
     */
    public static synchronized ElementLoader getElementLoader() {
        if (loader == null) {
            loader = new ElementLoader();
        }
        return loader;
    }

    /**
     * 读取全局游戏配置
     */
    public void readGlobalConfig() {
        InputStream in = ElementLoader.class.getClassLoader().getResourceAsStream("pro/GlobalConfig.properties");
        try {
            // 先清除再加载
            pro.clear();
            pro.load(in);
            Set<?> set = pro.keySet();
            for (Object o:set) {
                String value = pro.getProperty(o.toString());
                globalConfig.put(o.toString(), value);
            }
            // 等待其他配置加载，最后加载元素，防止pro冲突
            this.readElementConfig(globalConfig.get("Elements"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取游戏元素配置
     * @param s
     */
    private void readElementConfig(String s) {
        String[] item = s.split(",");
        for (String x:item) {
            try {
                InputStream in = ElementLoader.class.getClassLoader().getResourceAsStream("pro/"+x+".properties");
                pro.clear();
                pro.load(in);
                Set<?> set = pro.keySet();
                for (Object o:set) {
                    this.elementConfig.put(o.toString(), pro.getProperty(o.toString()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println();
    }

    public static void main(String args[]) {
        getElementLoader().readGlobalConfig();
    }

    // 根据key获取全局配置信息
    public String getGlobalConfig(String key) {
        return globalConfig.get(key);
    }

    public Map<String, String> getGlobalConfigMap() {
        return globalConfig;
    }

    // 根据key获取元素配置信息
    public String getElementConfig(String key) {
        return elementConfig.get(key);
    }

    public Map<String, String> getElementConfigMap() {
        return elementConfig;
    }
}
