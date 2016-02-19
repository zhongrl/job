/*
 * Copyright (c) 2014 hytz365, Inc. All rights reserved.
 *
 * @author lichunlin https://github.com/springlin2012
 *
 */
package cn.xn.freamwork.support.converter;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.core.convert.converter.Converter;
import java.text.ParseException;
import java.util.Date;

/**
 * Spring RequestMapping 日期类型格式转换处理
 *
 * @author lcl 2014/08/21
 * @version 1.0.0
 */
public class DateConverter  implements Converter<String, Date>
{
    private static final String[] DATE_FMT={"yyyyMMdd","yyyy-MM-dd","yyyy/MM/dd"};

    @Override
    public Date convert(String source) {
        if(StringUtils.isEmpty(source))
            return null;

        try {
            return DateUtils.parseDate(source, DATE_FMT);
        }
        catch (ParseException e) {
            return null;
        }
    }
}
