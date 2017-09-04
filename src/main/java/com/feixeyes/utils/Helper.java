package com.feixeyes.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

/**
 * Created by apple on 2017/9/4.
 */
public class Helper {
    public static String getProperty(String key){
        Properties properties  = new Properties();
        try{
            //读取属性文件a.properties
            InputStream in = new BufferedInputStream(new FileInputStream("conf/app.properties"));
            properties.load(in);     ///加载属性列表
            Iterator<String> it= properties.stringPropertyNames().iterator();
            System.out.println("loading properties");
//            while(it.hasNext()){
//                String k=it.next();
//                System.out.println(k+":"+properties.getProperty(k));
//            }
            in.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
        return properties.getProperty(key);
    }
}
