package com.zhaohai.cn;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GenMain {

    public static void main(String[] args) {
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        //如果这里出现空指针，直接写绝对路径即可。
        String genCfg = "/Users/zhaozhaohai/work/zhaohai_chat/src/main/resources/mybatis-generator.xml";
//        File configFile = new File(GenMain.class.getResource(genCfg).getFile()); //获取路径出错
        try {

            File configFile = ResourceUtils.getFile(genCfg);
            ConfigurationParser cp = new ConfigurationParser(warnings);
            Configuration config = null;
            config = cp.parseConfiguration(configFile);
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            MyBatisGenerator myBatisGenerator = null;
            myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
            myBatisGenerator.generate(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
