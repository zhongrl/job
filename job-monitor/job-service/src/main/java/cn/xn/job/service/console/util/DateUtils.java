package cn.xn.job.service.console.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期工具类 默认使用 "yyyy-MM-dd HH:mm:ss" 格式化日期
 * 
 * @author
 */
public final class DateUtils {

	public static String FORMAT_SHORT = "yyyy-MM-dd";
	public static String FORMAT_LONG = "yyyy-MM-dd HH:mm:ss";
	public static String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss.S";
	public static String FORMAT_SHORT_CN = "yyyy年MM月dd";
	public static String FORMAT_LONG_CN = "yyyy年MM月dd日  HH时mm分ss秒";
	public static String FORMAT_FULL_CN = "yyyy年MM月dd日  HH时mm分ss秒SSS毫秒";

	/**
	 * 获得默认的 date pattern 　　
	 */
	public static String getDatePattern() {
		return FORMAT_LONG;
	}

	/**
	 * 根据预设格式返回当前日期 　　
	 * 
	 * @return 　　
	 */
	public static String getNow() {
		return format(new Date());
	}

	/**
	 * 根据用户格式返回当前日期 　
	 * 
	 * @param format
	 *            　　
	 * @return 　　
	 */
	public static String getNow(String format) {
		return format(new Date(), format);
	}

	/**
	 * 使用预设格式格式化日期 　　
	 * 
	 * @param date
	 *            　　
	 * @return 　　
	 */
	public static String format(Date date) {
		return format(date, getDatePattern());
	}

	/**
	 * 使用用户格式格式化日期 　
	 * 
	 * @param date
	 *            日期 　　
	 * @param pattern
	 *            日期格式 　　
	 * @return 　　
	 */
	public static String format(Date date, String pattern) {
		String returnValue = "";
		if (date != null) {
			SimpleDateFormat df = new SimpleDateFormat(pattern);
			returnValue = df.format(date);
		}
		return (returnValue);
	}

	/**
	 * 使用预设格式提取字符串日期 　　
	 * 
	 * @param strDate
	 *            日期字符串 　　
	 * @return 　　
	 */
	public static Date parse(String strDate) {
		return parse(strDate, getDatePattern());
	}

	/**
	 * 使用用户格式提取字符串日期 　　
	 *
	 * @param strDate
	 *            日期字符串 　　
	 * @param pattern
	 *            日期格式 　　
	 * @return 　　
	 */
	public static Date parse(String strDate, String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		try {
			return df.parse(strDate);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 使用用户格式提取字符串日期 　　
	 *
	 * @param date
	 *            日期 　　
	 * @param pattern
	 *            日期格式 　　
	 * @return 　　
	 * 
	 * @throws ParseException
	 */
	public static Date parse(Date date, String pattern) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		return df.parse(df.format(date));
	}

	/**
	 * 在日期上增加数个整月 　　
	 * 
	 * @param date
	 *            日期 　　
	 * @param n
	 *            要增加的月数 　　
	 * @return 　　
	 */
	public static Date addMonth(Date date, int n) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, n);
		return cal.getTime();
	}

	/**
	 * 在日期上减去天数 　　
	 * 
	 * @param date
	 *            日期 　　
	 * @param n
	 *            要减去的天数 　　
	 * @return 　　
	 */
	public static Date subDay(Date date, int n) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) - n);
		return cal.getTime();
	}

	/**
	 * 在日期上增加天数 　　
	 *
	 * @param date
	 *            日期 　　
	 * @param n
	 *            要增加的天数 　　
	 * @return 　　
	 */
	public static Date addDay(Date date, int n) {
		if(date == null || n == 0){
			return date;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, n);
		return cal.getTime();
	}
	
	/**
	 * 在日期上增加天数 　　
	 *
	 * @param date
	 *            日期 　　
	 * @param n
	 *            要增加的天数 　　
	 * @return 　　
	 */
	public static Date addHour(Date date, int n) {
		if(date == null || n == 0){
			return date;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR, n);
		return cal.getTime();
	}

	/**
	 * 获取时间戳 　　
	 */
	public static String getTimeString() {
		SimpleDateFormat df = new SimpleDateFormat(FORMAT_FULL);
		Calendar calendar = Calendar.getInstance();
		return df.format(calendar.getTime());
	}

	/**
	 * 获取日期年份 　　
	 *
	 * @param date
	 *            日期 　　
	 * @return 　　
	 */
	public static String getYear(Date date) {
		return format(date).substring(0, 4);
	}

	/**
	 * 按默认格式的字符串距离今天的天数 　　
	 * 
	 * @param date
	 *            日期字符串 　　
	 * @return 　　
	 */
	public static int countDays(String date) {
		long t = Calendar.getInstance().getTime().getTime();
		Calendar c = Calendar.getInstance();
		c.setTime(parse(date));
		long t1 = c.getTime().getTime();
		return (int) (t / 1000 - t1 / 1000) / 3600 / 24;
	}

	/**
	 * 　　 按用户格式字符串距离今天的天数 　　
	 * 
	 * @param date
	 *            日期字符串 　　
	 * @param format
	 *            日期格式 　 　
	 * @return 　　
	 */
	public static int countDays(String date, String format) {
		long t = Calendar.getInstance().getTime().getTime();
		Calendar c = Calendar.getInstance();
		c.setTime(parse(date, format));
		long t1 = c.getTime().getTime();
		return (int) (t / 1000 - t1 / 1000) / 3600 / 24;
	}

	/**
	 * 
	 * countDays(计算连个时间点相隔多少天)
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 * 
	 *         author：zhou'sheng
	 */
	public static int countDays(Date date1, Date date2) {

		Date dateA, dateB, temp;

		try {
			dateA = parse(date1, "yyyy-MM-dd");
			dateB = parse(date2, "yyyy-MM-dd");
		} catch (ParseException e) {
			return 0;
		}

		if (dateA.getTime() == dateB.getTime()) {
			return 0;
		}

		if (dateA.getTime() < dateB.getTime()) {
			temp = dateA;
			dateA = dateB;
			dateB = temp;
		}

		Calendar cA = Calendar.getInstance();
		Calendar cB = Calendar.getInstance();
		cA.setTime(dateA);
		cB.setTime(dateB);

		long t1 = dateA.getTime();
		long t2 = dateB.getTime();

		return (int) (t1 / 1000 - t2 / 1000) / 3600 / 24;
	}

	/**
	 * @param date1
	 *            需要比较的时间 不能为空(null),需要正确的日期格式
	 * @param date2
	 *            被比较的时间 为
	 * @param stype
	 *            返回值类型 0为多少天，1为多少个月，2为多少年
	 * @return
	 */
	public static int compareDate(String date1, String date2, int stype) {
		int n = 0;

		String[] u = { "天", "月", "年" };
		String formatStyle = stype == 1 ? "yyyy-MM" : "yyyy-MM-dd";

		DateFormat df = new SimpleDateFormat(formatStyle);
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();

		try {
			c1.setTime(df.parse(date1));
			c2.setTime(df.parse(date2));
		} catch (Exception e3) {
			System.out.println("wrong occured");
		}

		// List list = new ArrayList();
		while (!c1.after(c2)) { // 循环对比，直到相等，n 就是所要的结果
			n++;
			if (stype == 1) {
				c1.add(Calendar.MONTH, 1); // 比较月份，月份+1
			} else {
				c1.add(Calendar.DATE, 1); // 比较天数，日期+1
			}
		}

		n = n - 1;

		if (stype == 2) {
			n = (int) n / 365;
		}

		System.out.println(date1 + " -- " + date2 + " 相差多少" + u[stype] + ":" + n);
		return n;
	}

	/**
	 * 
	 * compareDate(比较时间大小)
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 * 
	 * @author：zhou'sheng
	 */
	public static int compareDate(Date date1, Date date2) {
		if(date1 == null || null == date2){
			return 0;
		}
		try {
			if (date1.getTime() > date2.getTime()) {
				return 1;
			} else if (date1.getTime() < date2.getTime()) {
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}

	/**
	 * 得到当前日期
	 * 
	 * @return
	 */
	public static String getCurrentDate() {
		Calendar c = Calendar.getInstance();
		Date date = c.getTime();
		SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
		return simple.format(date);
	}

	public static void main(String[] args) {
		// System.out.println(getMonths(new Date(),DateUtils.parse("2015-05-23", "yyyy-MM-dd")));
		// System.out.println(compareDate("2015-06-06","2015-07-5",0));
		// System.out.println(getMonths(DateUtils.parse("2015-04-31", "yyyy-MM-dd"), DateUtils.parse("2015-04-01", "yyyy-MM-dd")));

		// Calendar c1 = Calendar.getInstance();
		// Calendar c2 = Calendar.getInstance();
		//
		// c1.setTime(DateUtils.parse("2015-02-21", "yyyy-MM-dd"));
		// c2.setTime(DateUtils.parse("2015-06-21", "yyyy-MM-dd"));
		//
		// System.out.println("year=" + c1.get(Calendar.YEAR) + ",month=" + c1.get(Calendar.MONTH) + ",Day=" + c1.get(Calendar.DATE) + ",dd=" + c1.getActualMaximum(Calendar.DATE));

		// System.out.println(getMonths(DateUtils.parse("2015-02-20", "yyyy-MM-dd"),DateUtils.parse("2015-06-21", "yyyy-MM-dd")));

		double initD = 0.055;// 初始利率
		double d;// 利率
		Date start = null;
		Date system = null;

		try {
			start = DateUtils.parse("2015-08-25", "yyyy-MM-dd");
			system = DateUtils.parse("2015-08-25", "yyyy-MM-dd");
		} catch (Exception e) {

		}

		for (int i = 0; i <= 365; i++) {
			if (i > 0) {
				system = DateUtils.addDay(system, 1);
			}

			int month = DateUtils.getMonths(DateUtils.format(start), DateUtils.format(system));
			// int day = countDays(DateUtils.format(DateUtils.addMonth(start, month + 1)), "yyyy-MM-dd");
			int day = countDays(DateUtils.addMonth(start, month + 1), system);
			d = NumUtil.add(initD, NumUtil.mul2(month, 0.005, 4), 4);
			StringBuffer sb = new StringBuffer();
			sb.append(DateUtils.format(start) + "_" + DateUtils.format(system) + ",间隔" + month + "个月,当前利率=" + d + ",增长利率=" + NumUtil.mul2(month, 0.005, 4) + ",");
			sb.append(DateUtils.format(DateUtils.addMonth(start, month + 1)) + "-" + DateUtils.format(system, "yyyy-MM-dd") + "相隔" + day + "天");
			sb.append("利率增长至=" + (NumUtil.add(initD, NumUtil.mul2(month + 1, 0.005, 4), 4)));
			System.out.println(sb.toString());
		}

	}

	public static int getMonths(String date1, String date2) {
		int year, month, day;
		Date dateA, dateB, temp;

		dateA = parse(date1, "yyyy-MM-dd");
		dateB = parse(date2, "yyyy-MM-dd");

		if (dateA.getTime() == dateB.getTime()) {
			return 0;
		}

		if (dateA.getTime() < dateB.getTime()) {
			temp = dateA;
			dateA = dateB;
			dateB = temp;
		}

		Calendar cA = Calendar.getInstance();
		Calendar cB = Calendar.getInstance();
		cA.setTime(dateA);
		cB.setTime(dateB);

		year = cA.get(Calendar.YEAR) - cB.get(Calendar.YEAR);// 年
		month = cA.get(Calendar.MONTH) - cB.get(Calendar.MONTH);// 月

		if (year > 0) {
			if (month > 0) {
				if (cB.get(Calendar.DATE) > cA.get(Calendar.DATE)) {
					month--;
				} else if (cB.get(Calendar.DATE) == cA.get(Calendar.DATE)) {

				} else {

				}
			} else if (month == 0) {
				if (cB.get(Calendar.DATE) > cA.get(Calendar.DATE)) {
					month = -1;
				} else if (cB.get(Calendar.DATE) == cA.get(Calendar.DATE)) {

				} else {

				}
			} else {
				if (cA.get(Calendar.DATE) > cB.get(Calendar.DATE)) {

				} else if (cA.get(Calendar.DATE) == cB.get(Calendar.DATE)) {

				} else {
					month--;
				}
			}
		} else {
			if (month > 0) {
				if (cB.get(Calendar.DATE) > cA.get(Calendar.DATE)) {
					month--;
				} else if (cB.get(Calendar.DATE) == cA.get(Calendar.DATE)) {

				} else {

				}
			}
		}

		return year * 12 + month;
	}
	
	
	/**
	 *  时间相减获得天数
	 * @return
	 * author：chunlin.li
	 */
	public static int subDay(Date sDate, Date eDate) {
	    Calendar aCalendar = Calendar.getInstance();
	    aCalendar.setTime(sDate);
	    int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
	    int y1 = aCalendar.get(Calendar.YEAR);
	    
	    aCalendar.setTime(eDate);
	    int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
	    int y2 = aCalendar.get(Calendar.YEAR);
	    
	    return (y2-y1)*360+day2-day1;
	}


    /**
     * 获取month个自然月后的日期
     * wg.he 04/29/2015
     * @param currentDateStr
     * @param month
     * @return
     */
    public static String getNaturalMonthsAfterDateStr(String currentDateStr,int month) {
        String returnDateStr="";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate;
        try {
            currentDate = sdf.parse(currentDateStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currentDate);
            calendar.add(Calendar.MONTH, month);
            returnDateStr = sdf.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return returnDateStr;
    }

    /**
     * 获取day个自然日后的日期，包括当前指定的日期
     * wg.he 04/29/2015
     * @param currentDateStr
     * @param day
     * @return
     */
    public static String getNaturalDaysAfterDateStr(String currentDateStr, int day){
        String returnDateStr="";
        Calendar c = new GregorianCalendar();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = null;
        try {
            currentDate = df.parse(currentDateStr);
            c.setTime(currentDate);
            c.add(Calendar.DAY_OF_MONTH, day-1);
            returnDateStr = df.format(c.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return returnDateStr;
    }

    /**
     * 获取day个自然日后的日期，不包括当前指定的日期
     * wg.he 05/12/2015
     * @param currentDateStr
     * @param day
     * @return
     */
    public static String getNaturalDaysAfterDateStr2(String currentDateStr, int day){
        String returnDateStr="";
        Calendar c = new GregorianCalendar();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = null;
        try {
            currentDate = df.parse(currentDateStr);
            c.setTime(currentDate);
            c.add(Calendar.DAY_OF_MONTH, day);
            returnDateStr = df.format(c.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return returnDateStr;
    }


    public static int daysOfTwo(Date fDate, Date oDate) {
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(fDate);
        int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
        int y1 = aCalendar.get(Calendar.YEAR);
        aCalendar.setTime(oDate);

        int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
        int y2 = aCalendar.get(Calendar.YEAR);

        return (y2-y1)*360+day2-day1;

    }
}