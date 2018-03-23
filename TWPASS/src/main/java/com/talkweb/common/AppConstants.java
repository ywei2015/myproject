package com.talkweb.common;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 

/**
 * TODO(描述这个类的作用)
 * 
 * @author: 2012-8-28，LB，（描述修改内容）
 */

@Service("appConstants")
@Transactional
public class AppConstants
{
    private static final Logger logger = LoggerFactory.getLogger(AppConstants.class);
    private static String fileurl = "";
    private static void getfileurl(){
    	 Properties prop = new Properties();
         InputStream is = AppConstants.class.getClassLoader().getResourceAsStream("application.properties");
		try {
			prop.load(is);
			fileurl = prop.getProperty("IMG_URL");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
    }
    public static String getFilePathPrefix(){ 
    	if(fileurl=="") getfileurl();
    	return fileurl; 
    }
    
    public String getIMG_URL()
    {
        return  getValue("IMG_URL");
    }
    public String getFILE_URL()
    {
    	return getValue("FILE_URL");
    }
    public String getValue(String nodeName)
    {
        // 方法一
        // ResourceBundle rb = ResourceBundle.getBundle("application");

        // 方法二
        Properties prop = new Properties();
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("application.properties");
        try
        {
            prop.load(is);
            return prop.getProperty(nodeName);
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            if (is != null)
            {
                try
                {
                    is.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    public boolean setValue(String nodeName, String nodeValue)
    {
        boolean flag = false;
        // 得到配置文件路径
        URL url = this.getClass().getClassLoader().getResource("application.properties");
        String filepath = url.getPath().substring(1);

        Properties prop = new Properties();
        InputStream is = null;
        try
        {
            is = new FileInputStream(filepath);
            prop.load(is);
            prop.setProperty(nodeName, nodeValue);
            // 保存文件
            FileOutputStream outputFile = new FileOutputStream(filepath);
            prop.store(outputFile, "application.properties");
            outputFile.close();
            flag = true;

        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            if (is != null)
            {
                try
                {
                    is.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

    public boolean setValue(String[] nodeName, String[] nodeValue)
    {
        boolean flag = false;
        // 得到配置文件路径
        URL url = this.getClass().getClassLoader().getResource("application.properties");
        String filepath = url.getPath().substring(1);

        Properties prop = new Properties();
        InputStream is = null;
        try
        {
            is = new FileInputStream(filepath);
            prop.load(is);
            if (nodeName.length != nodeValue.length)
            {
                return false;
            } else
            {
                for (int i = 0; i < nodeName.length; i++)
                {
                    prop.setProperty(nodeName[i], nodeValue[i]);
                }
                // 保存文件
                FileOutputStream outputFile = new FileOutputStream(filepath);
                prop.store(outputFile, "application.properties");
                outputFile.close();
                flag = true;
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            if (is != null)
            {
                try
                {
                    is.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }
    
    
}
