package cn.xn.freamwork.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogLib {
	
	private static final Logger logger = LoggerFactory.getLogger("LogLib");

	public static void logInfo(String userId, String msg, Integer logId) {
		logger.info(strFormat(logId, userId, msg));
	}

	public static void logInfo(String userId, String msg) {
		logger.info(strFormat(Integer.valueOf(0), userId, msg));
	}

	public static void logDebug(String userId, String msg, Integer logId) {
		logger.debug(strFormat(logId, userId, msg));
	}

	public static void logDebug(String userId, String msg) {
		logger.debug(strFormat(Integer.valueOf(0), userId, msg));
	}

	public static void logError(String userId, String msg, Integer logId) {
		logger.error(strFormat(logId, userId, msg));
	}

	public static void logError(String userId, String msg) {
		logger.error(strFormat(Integer.valueOf(0), userId, msg));
	}

	public static void writeBill(String userId, String bill, Integer billId) {
		logger.warn(strFormat(billId, userId, bill));
	}

	public static void writeBill(String userId, String bill) {
		logger.warn(strFormat(Integer.valueOf(0), userId, bill));
	}

	private static String strFormat(Integer logId, String userId, String msg) {
		String strMethodName = "";
		Integer iLineNumber = Integer.valueOf(-1);
		String strFileName = "";
		Throwable throwable = new Throwable();
		StackTraceElement[] ste = throwable.getStackTrace();

		strMethodName = ste[2].getMethodName();
		iLineNumber = Integer.valueOf(ste[2].getLineNumber());
		strFileName = ste[2].getFileName();

		String msgTempString = null;
		msgTempString = String.format("%d|%s|%s(%s:%d)|%s", new Object[] { logId, userId, strMethodName, strFileName, iLineNumber, msg });

		return msgTempString;
	}
}