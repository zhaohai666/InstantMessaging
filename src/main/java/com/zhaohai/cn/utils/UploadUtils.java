package com.zhaohai.cn.utils;

import java.io.File;

public class UploadUtils {
    /**
     * 项目跟路径下的目录
     */
    private static final String IMG_PATH_PREFIX = "static/image";

    public static File getImageFile() {
        // 构建上传文件的存放"文件夹"路径
        String filePath = new String("src/main/resources/" + IMG_PATH_PREFIX);
        File file = new File(filePath);
        if (!file.exists()) {
            // 递归生成文件夹
            file.mkdirs();
        }
        return file;
    }
}
