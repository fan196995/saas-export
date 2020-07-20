package com.itheima.web.converter;


import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *查StringToBooleanConverter
 *日期格式转化
 * @author fanbo
 * @date 2020/6/28 17:20
 */
public class StringToDateConverter implements Converter<String, Date> {
//    日期转化
    public Date convert(String s) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return simpleDateFormat.parse(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
