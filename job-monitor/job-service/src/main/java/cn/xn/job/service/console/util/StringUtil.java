package cn.xn.job.service.console.util;

public class StringUtil {

	 public static boolean toBoolean(Object value)
	    {
	        if(value instanceof Boolean)
	            return ((Boolean)value).booleanValue();
	        if(value instanceof Number)
	            return ((Number)value).doubleValue() != 0.0D;
	        if(value instanceof String)
	            return ((String)value).equalsIgnoreCase("true") || ((String)value).equalsIgnoreCase("1");
	        else
	            return false;
	    }
}
