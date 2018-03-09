package tw.sysbase.exception;

/**
 * <p>类说明：</p>
 *    
 *    
 * <p>Copyright: Copyright (c) 2008</p>
 *    
 * @author mark
 * 2007-12-12
 *
 * @version 1.0
 */

import tw.sysbase.pub.Constants;
import tw.sysbase.pub.DateBean;

public class LogException {

	/**
	 * 功能说明： 记录异常日志。 如果是调试模式，异常将被记录到控制台；否则，记录到文件。
	 * 
	 * @param ex：待记录的异常类。
	 */
	public static void logEx(Exception ex) {
		logEx(ex, null);
	}

	public static void logEx(Exception ex,Class clazz) {
		logEx(ex, clazz,null);
	}	
	
	/**
	 * 功能说明： 记录异常日志。
	 * 
	 * @param ex
	 * @param otherMsg:自定义的需要输出的异常信息。
	 */
	public static void logEx(Throwable ex,Class clazz, String otherMsg) {
		if (Constants.DEBUG)
			logToConsole(ex, otherMsg);
		else
			logToFile(ex, otherMsg);
	}

	/**
	 * 功能说明： 将异常记录到控制台。
	 * 
	 * @param ex
	 */
	private static void logToConsole(Throwable ex, String otherMsg) {
		String name = ex.getClass().getName();

		StackTraceElement[] stackTraceElement = ex.getStackTrace();

		System.out.println("[" + DateBean.getToday() + "]");
		System.out.println("异常：" + name);

		if (ex.getMessage() != null) {
			System.out.println("错误信息： " + ex.getMessage());
		}

		if (otherMsg != null) {
			System.out.println("提示信息： " + otherMsg);
		}

		System.out.println("位于：");

		for (StackTraceElement ste : stackTraceElement) {
			String detail = getExceptionDetail(ste);
			if (detail != null) {
				System.out.println(detail);
			}
		}
		System.out.println("");
	}

	/**
	 * 功能说明： 将异常记录到文件。此方法未完成。 对于异常非常多，异常记录并发程度在毫秒级时，可能会有并发冲突。
	 * 
	 * @param ex
	 */
	private static void logToFile(Throwable ex, String otherMsg) {
	}

	/**
	 * 功能说明： 获取异常的详细信息。包括：类名、方法名和错误行。 仅处理应用系统包内抛出的异常。对于服务器自身的异常，此处将过滤掉。
	 * 
	 * @param ste
	 * @return
	 */
	private static String getExceptionDetail(StackTraceElement ste) {
		String ret = null;
		String clazz = ste.getClassName();

		if (clazz.indexOf(Constants.PROJPACKAGE) == -1)
			return ret;

		int line = ste.getLineNumber();
		String method = ste.getMethodName();

		ret = clazz + "." + method + "(" + String.valueOf(line) + ")";

		return ret;
	}
}
