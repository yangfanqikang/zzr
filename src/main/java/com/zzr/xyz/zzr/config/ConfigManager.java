package com.zzr.xyz.zzr.config;

import java.net.URISyntaxException;

public class ConfigManager {
    private String getConfigPath() {
        //return this.parentPath + File.separator + ConfigManager.configFileName;
        try {
            //获取classpath下的config.json路径
            return this.getClass().getClassLoader().getResource("config.json").toURI().getPath();
        } catch (URISyntaxException e) {
            return null;
        }
    }
}
