package com.talkweb.xwzcxt.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.XMLConfiguration;

/**
 * <p>文件名称: FileOperate.java </p>
 * <p>文件描述: 对文件及目录(文件夹)的操作封装类</p>
**/

public class FileOperate
{
	/**
	 * 日志文件定义
	 */
    private Logger log = LoggerFactory.getLogger(FileOperate.class);
	
	private static final int BUFFEREDSIZE = 1024; 
	
	/**
	 * 空构造器
	 *
	 */
	public FileOperate() {
	}

	/**
	 * 读取文本文件内容
	 * @param filePathAndName 带有完整绝对路径的文件名
	 * @param encoding 文本文件打开的编码方式
	 * @return 返回文本文件的内容
	 */
	public String readTxt(String filePathAndName, String encoding)
			throws IOException
	{
		encoding = encoding.trim();
		StringBuffer str = new StringBuffer("");
		String st = "";
		try {
			FileInputStream fs = new FileInputStream(filePathAndName);
			InputStreamReader isr;
			if (encoding.equals("")) {
				isr = new InputStreamReader(fs);
			} else {
				isr = new InputStreamReader(fs, encoding);
			}
			BufferedReader br = new BufferedReader(isr);
			try {
				String data = "";
				while ((data = br.readLine()) != null) {
					str.append(data + " ");
				}
			} catch (Exception e) {
				str.append(e.toString());
			}
			st = str.toString();
		} catch (IOException es) {
			st = "";
			log.error("读取文本文件内容 readTxt：",es);
			es.printStackTrace();
		}
		return st;
	}

	/**
	 * 新建目录
	 * @param folderPath 目录
	 * @return 返回目录创建后的路径
	 */
	public String createFolder(String folderPath)
	{
		String txt = folderPath;
		try {
			java.io.File myFilePath = new java.io.File(txt);
			txt = folderPath;
			if (!myFilePath.exists()) {
				myFilePath.mkdirs();
			}
		} catch (Exception e) {
			log.error("创建目录操作出错 createFolder：",e);
			e.printStackTrace();
		}
		return txt;
	}

	/**
	 * 多级目录创建
	 * 
	 * @param folderPath
	 *            准备要在本级目录下创建新目录的目录路径 例如 c:/myf
	 * @param paths
	 *            无限级目录参数，各级目录以单数线区分 例如 a|b|c
	 * @return 返回创建文件后的路径 例如 c:/myf/a/b/c
	 */
	public String createFolders(String folderPath, String paths)
	{
		String txts = folderPath;
		try {
			String txt;
			txts = folderPath;
			StringTokenizer st = new StringTokenizer(paths, "|");
			for (int i = 0; st.hasMoreTokens(); i++) {
				txt = st.nextToken().trim();
				if (txts.lastIndexOf("/") != -1) {
					txts = createFolder(txts + txt);
				} else {
					txts = createFolder(txts + txt + "/");
				}
			}
		} catch (Exception e) {
			log.error("创建目录操作出错 createFolders：",e);
			e.printStackTrace();
		}
		return txts;
	}

	/**
	 * 新建文件
	 * 
	 * @param filePath
	 *            文本文件完整绝对路径及文件名
	 * @param fileContent
	 *            文本文件内容
	 * @return
	 */
	public void createFile(String filePath, String fileContent)
	{
		try {
		    createFile(filePath, fileContent, "UTF-8");
		} catch (Exception e) {
			log.error("创建文件操作出错 createFile：",e);
			e.printStackTrace();
		}
	}

	/**
	 * 有编码方式的文件创建
	 * 
	 * @param filePath
	 *            文本文件完整绝对路径及文件名
	 * @param fileContent
	 *            文本文件内容
	 * @param encoding
	 *            编码方式 例如 GBK 或者 UTF-8
	 * @return
	 */
	public void createFile(String filePath, String fileContent, String encoding)
	{

		try {
			File myFilePath = new File(filePath);
			
			//如果此文件的上一级目录不存在，则创建
			createFolder(myFilePath.getParent());
			
			if (!myFilePath.exists()) {
				myFilePath.createNewFile();
			}
            java.io.FileOutputStream fs = new java.io.FileOutputStream(
                    myFilePath);
            String strContent = fileContent;

            Writer out = new BufferedWriter(new OutputStreamWriter(fs, encoding));
			out.write(strContent);
		    out.flush();
		    out.close();



//			PrintWriter myFile = new PrintWriter(fs);
//			String strContent = fileContent;
//			myFile.println(strContent);
//			myFile.close();
		} catch (Exception e) {
			log.error("创建文件操作出错<"+filePath+"> createFile：",e);
			e.printStackTrace();
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param filePathAndName
	 *            文本文件完整绝对路径及文件名
	 * @return Boolean 成功删除返回 true遭遇异常返回false
	 */
	public boolean delFile(String filePathAndName)
	{
		boolean bea = false;
		try {
			String filePath = filePathAndName;
			File myDelFile = new File(filePath);
			if (myDelFile.exists()) {
				 System.gc();
				myDelFile.delete();
				bea = true;
			} else {
				bea = false;
				log.error("删除文件操作出错 delFile <" + filePathAndName + ">");
			}
		} catch (Exception e) {
			log.error("删除文件操作出错 delFile <" + filePathAndName + ">：",e);
			e.printStackTrace();
		}
		return bea;
	}

	/**
	 * 删除文件夹
	 * 
	 * @param folderPath
	 *            文件夹完整绝对路径
	 * @return
	 */
	public void delFolder(String folderPath)
	{
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			log.error("删除文件夹操作出错 delFolder <" + folderPath + ">：",e);
			e.printStackTrace();
		}
	}

	/**
	 * 删除指定文件夹下所有文件
	 * 
	 * @param path
	 *            文件夹完整绝对路径
	 * @return 成功删除返回true 遭遇异常返回false
	 */
	public boolean delAllFile(String path)
	{
		boolean bea = false;
		File file = new File(path);
		if (!file.exists()) {
			return bea;
		}
		if (!file.isDirectory()) {
			return bea;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
				bea = true;
			}
		}
		return bea;
	}

	/**
	 * 复制单个文件 
	 * @param oldPathFile        准备复制的文件源
	 * @param newPathFile        拷贝到新绝对路径带文件名
	 * @return 成功返回true 遭遇异常返回false
	 */
	public boolean copyFile(String oldPathFile, String newPathFile)
	{
		boolean bReturn = false;
		try {
			createFile(newPathFile,"");
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPathFile);
			if (oldfile.exists()) { // 文件存在时
				InputStream inStream = new FileInputStream(oldPathFile); // 读入原文件
				FileOutputStream fs = new FileOutputStream(newPathFile);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 字节数 文件大小
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
				bReturn = true;
			}
		} catch (Exception e) {
			log.error("复制单个文件操作出错 copyFile ：",e);
			e.printStackTrace();
		}
		return bReturn;
	}
	
	/**
	 * 复制文件夹
	 * 
	 * @param oldPath
	 *            准备拷贝的目录
	 * @param newPath
	 *            指定绝对路径的新目录
	 * @return
	 */
	public void copyFolder(String oldPath, String newPath)
	{
		try {
			createFolder(newPath); // 如果文件夹不存在 则建立新文件夹
			File a = new File(oldPath);
			String[] file = a.list();
			File temp = null;
			for (int i = 0; i < file.length; i++) {
				if (oldPath.endsWith(File.separator)) {
					temp = new File(oldPath + file[i]);
				} else {
					temp = new File(oldPath + File.separator + file[i]);
				}
				if (temp.isFile()) {
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(newPath
							+ "/" + (temp.getName()).toString());
					byte[] b = new byte[1024 * 5];
					int len;
					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
					output.close();
					input.close();
				}
				if (temp.isDirectory()) {// 如果是子文件夹
					copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
				}
			}
		} catch (Exception e) {
			log.error("复制文件夹 copyFolder(String oldPath, String newPath) ：",e);
			e.printStackTrace();
		}
	}

	/**
	 * 移动文件
	 * 
	 * @param oldPath 准备移动的文件所在目录
	 * @param newPath 移动文件至此目录
	 * @return
	 */
	public void moveFile(String oldPath, String newPath)
	{
		copyFile(oldPath, newPath);
		delFile(oldPath);
	}

	/**
	 * 移动目录
	 * 
	 * @param oldPath 将要被移动的目录
	 * @param newPath 新的目的目录
	 * @return
	 */
	public void moveFolder(String oldPath, String newPath)
	{
		copyFolder(oldPath, newPath);
		delFolder(oldPath);
	}

	
	/**
	 * 获取文件的大小
	 * 
	 * @param path 本地文件路径
	 * @return long 文件大小(字节)
	 */
	public long getFileSize(String path){
		
		File file = new File(path);
		return file.length();
		
	}
	
}