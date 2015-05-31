package com.lnw.template.util;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DateUtils {
	
    public DateUtils(){
    	
    }

    public static String getCurrentDateString()
    {
        return getCurrentDateString("yyyyMMdd");
    }

    public static String getCurrentTimeString()
    {
        return getCurrentDateString("HHmmss");
    }

    public static String getCurrentDateString(String pattern)
    {
        return convertToString(getCurrentTimeStamp(), pattern);
    }
    
    public static String getYesterdayDateString(String pattern)
    {
        return convertToString(getYesterdayTimeStamp(), pattern);
    }

    public static String convertFormat(String dateData)
    {
        if(dateData == null)
            return "";
        else
            return convertFormat(dateData, "yyyy/MM/dd");
    }

    public static String convertFormat(String dateData, String format)
    {
        if(dateData == null)
            return "";
        else
            return convertToString(convertToTimestamp(dateData), format);
    }
    

    public static String convertFormatExchange(String dateData, String format)
    {
        if(dateData == null)
            return "";
        else
            return convertToString(convertToTimestampHMS(dateData), format);
    }
    
    public static Timestamp getCurrentTimeStamp()
    {
        try
        {
            Calendar cal = new GregorianCalendar();
            Timestamp result = new Timestamp(cal.getTime().getTime());
            return result;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    
    public static Timestamp getYesterdayTimeStamp()
    {
        try
        {
            Calendar cal = new GregorianCalendar();
            cal.add(Calendar.DATE, -1);
            Timestamp result = new Timestamp(cal.getTime().getTime());
            return result;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static String convertToString(Timestamp dateData)
    {
        return convertToString(dateData, "yyyy-MM-dd");
    }

    public static String convertToString(Timestamp dateData, String pattern)
    {
        return convertToString(dateData, pattern, Locale.KOREA);
    }

    public static String convertToString(Timestamp dateData, String pattern, Locale locale)
    {
        try
        {
            if(dateData == null)
            {
                return "";
            }
            else
            {	
                SimpleDateFormat formatter = new SimpleDateFormat(pattern, locale);
                return formatter.format(dateData);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static Timestamp convertToTimestamp(String dateData)
    {
        try
        {
            if(dateData == null)
                return null;
            if(dateData.trim().equals(""))
                return null;
            int dateObjLength = dateData.length();
            String yearString = "2002";
            String monthString = "01";
            String dayString = "01";
            if(dateObjLength >= 4)
                yearString = dateData.substring(0, 4);
            if(dateObjLength >= 6)
                monthString = dateData.substring(4, 6);
            if(dateObjLength >= 8)
                dayString = dateData.substring(6, 8);
            int year = Integer.parseInt(yearString);
            int month = Integer.parseInt(monthString) - 1;
            int day = Integer.parseInt(dayString);
            Calendar cal = new GregorianCalendar();
            cal.set(year, month, day);
            return new Timestamp(cal.getTime().getTime());
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        	return null;
        }
    }

    public static Timestamp convertToTimestampHMS(String dateData)
    {
        try
        {
            if(dateData == null)
                return null;
            if(dateData.trim().equals(""))
            {
                return null;
            }
            else
            {
                String yearString = dateData.substring(0, 4);
                String monthString = dateData.substring(4, 6);
                String dayString = dateData.substring(6, 8);
                String hourString = dateData.substring(8, 10);
                String minString = dateData.substring(10, 12);
                String secString = dateData.substring(12, 14);
                int year = Integer.parseInt(yearString);
                int month = Integer.parseInt(monthString) - 1;
                int day = Integer.parseInt(dayString);
                int hour = Integer.parseInt(hourString);
                int min = Integer.parseInt(minString);
                int sec = Integer.parseInt(secString);
                Calendar cal = new GregorianCalendar();
                cal.set(year, month, day, hour, min, sec);
                return new Timestamp(cal.getTime().getTime());
            }
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        	return null;
        }
    }

    private static Date check(String s)
        throws ParseException
    {
        return check(s, "yyyyMMdd");
    }

    private static Date check(String s, String format)
        throws ParseException
    {
        if(s == null)
            throw new ParseException("date string to check is null", 0);
        if(format == null)
            throw new ParseException("format string to check date is null", 0);
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.KOREA);
        Date date = null;
        try
        {
            date = formatter.parse(s);
        }
        catch(ParseException e)
        {
            throw new ParseException(" wrong date:\"" + s + "\" with format \"" + format + "\"", 0);
        }
        if(!formatter.format(date).equals(s))
            throw new ParseException("Out of bound date:\"" + s + "\" with format \"" + format + "\"", 0);
        else
            return date;
    }

    public static boolean isValid(String s)
    {
        return isValid(s, "yyyyMMdd");
    }

    public static boolean isValid(String s, String format)
    {
        try
        {
            SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.KOREA);
            Date date = null;
            try
            {
                date = formatter.parse(s);
            }
            catch(ParseException e)
            {
                return false;
            }
            return formatter.format(date).equals(s);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public static int whichDay(String s)
    {
        return whichDay(s, "yyyyMMdd");
    }

    public static int whichDay(String s, String format)
    {
        try
        {
            SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.KOREA);
            Date date = check(s, format);
            Calendar calendar = formatter.getCalendar();
            calendar.setTime(date);
            return calendar.get(7);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return -10000000;
        }
    }

    public static int daysBetween(String from, String to)
    {
        return daysBetween(from, to, "yyyyMMdd");
    }

    public static int daysBetween(String from, String to, String format)
    {
        try
        {
            Date d1 = check(from, format);
            Date d2 = check(to, format);
            long duration = d2.getTime() - d1.getTime();
            return (int)(duration / 0x5265c00L);
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        	return -1000000;
        }
    }
    
    public static int yearsBetween(String from, String to)
    {
        return yearsBetween(from, to, "yyyyMMdd");
    }

    public static int yearsBetween(String from, String to, String format)
    {
        return daysBetween(from, to, format) / 365;
    }

    public static String addDays(String s, int day)
    {
        return addDays(s, day, "yyyyMMdd");
    }
    
    public static String addDays2(String s, int day)
    {
        return addDays(s, day, "yyyy-MM-dd HH:mm:ss");
    }
    
    public static String addTimes(String s, int day, String format)
    {
        try
        {
            SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.KOREA);
            Date date = check(s, format);
            long time = date.getTime();
            long time1 = (long)day * 1000L * 60L * 60L;
            date.setTime(date.getTime() + ((long)day) * 1000L * 60L * 60L);
            return formatter.format(date);
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        	return null;
        }
    }
    
    public static long getMillSecond(String s, String format){
    	long time = 0;
        try
        {
            SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.KOREA);
            Date date = check(s, format);
            time = date.getTime();            
        }
        catch(Exception e){
        	e.printStackTrace();
        	
        }
        return time;
    }
    
    public static String addDays(String s, int day, String format)
    {
        try
        {
            SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.KOREA);
            Date date = check(s, format);
            date.setTime(date.getTime() + (long)day * 1000L * 60L * 60L * 24L);
            return formatter.format(date);
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        	return null;
        }
    }

    public static String addMonths(String s, int month)
    {
        return addMonths(s, month, "yyyyMMdd");
    }

    public static String addMonths(String s, int addMonth, String format)
    {
        try
        {
            SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.KOREA);
            Date date = check(s, format);
            SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.KOREA);
            SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.KOREA);
            SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.KOREA);
            int year = Integer.parseInt(yearFormat.format(date));
            int month = Integer.parseInt(monthFormat.format(date));
            int day = Integer.parseInt(dayFormat.format(date));
            month += addMonth;
            if(addMonth > 0)
                while(month > 12) 
                {
                    month -= 12;
                    year++;
                }

            else
                while(month <= 0) 
                {
                    month += 12;
                    year--;
                }

            DecimalFormat fourDf = new DecimalFormat("0000");
            DecimalFormat twoDf = new DecimalFormat("00");
            String tempDate = String.valueOf(fourDf.format(year)) + String.valueOf(twoDf.format(month)) + String.valueOf(twoDf.format(day));
            Date targetDate = null;
            try
            {
                targetDate = check(tempDate, "yyyyMMdd");
            }
            catch(ParseException pe)
            {
                day = lastDay(year, month);
                tempDate = String.valueOf(fourDf.format(year)) + String.valueOf(twoDf.format(month)) + String.valueOf(twoDf.format(day));
                targetDate = check(tempDate, "yyyyMMdd");
            }
            return formatter.format(targetDate);
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        	return null;
        }
    }

    public static String addYears(String s, int year)
    {
        return addYears(s, year, "yyyyMMdd");
    }

    public static String addYears(String s, int year, String format)
    {
        try
        {
            SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.KOREA);
            Date date = check(s, format);
            date.setTime(date.getTime() + (long)year * 1000L * 60L * 60L * 24L * 365L);
            return formatter.format(date);
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        	return null;
        }
    }

    public static int monthsBetween(String from, String to)
    {
        return monthsBetween(from, to, "yyyyMMdd");
    }

    public static int monthsBetween(String from, String to, String format)
    {
        try
        {
            Date fromDate = check(from, format);
            Date toDate = check(to, format);
            if(fromDate.compareTo(toDate) == 0)
                return 0;
            SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.KOREA);
            SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.KOREA);
            SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.KOREA);
            int fromYear = Integer.parseInt(yearFormat.format(fromDate));
            int toYear = Integer.parseInt(yearFormat.format(toDate));
            int fromMonth = Integer.parseInt(monthFormat.format(fromDate));
            int toMonth = Integer.parseInt(monthFormat.format(toDate));
            int fromDay = Integer.parseInt(dayFormat.format(fromDate));
            int toDay = Integer.parseInt(dayFormat.format(toDate));
            int result = 0;
            result += (toYear - fromYear) * 12;
            result += toMonth - fromMonth;
            if(toDay - fromDay > 0)
                result += toDate.compareTo(fromDate);
            return result;
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        	return -10000000;
        }
    }

    public static String lastDayOfMonth(String src)
    {
        return lastDayOfMonth(src, "yyyyMMdd");
    }

    public static String lastDayOfMonth(String src, String format)
    {
        try
        {
            SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.KOREA);
            Date date = check(src, format);
            SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.KOREA);
            SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.KOREA);
            int year = Integer.parseInt(yearFormat.format(date));
            int month = Integer.parseInt(monthFormat.format(date));
            int day = lastDay(year, month);
            DecimalFormat fourDf = new DecimalFormat("0000");
            DecimalFormat twoDf = new DecimalFormat("00");
            String tempDate = String.valueOf(fourDf.format(year)) + String.valueOf(twoDf.format(month)) + String.valueOf(twoDf.format(day));
            Date targetDate = check(tempDate, "yyyyMMdd");
            return formatter.format(targetDate);
        }
        catch(Exception e)
        {
        	e.printStackTrace(); 
        	return null;
        }
    }

    private static int lastDay(int year, int month)
        throws ParseException
    {
        int day = 0;
        switch(month)
        {
        case 1: // '\001'
        case 3: // '\003'
        case 5: // '\005'
        case 7: // '\007'
        case 8: // '\b'
        case 10: // '\n'
        case 12: // '\f'
            day = 31;
            break;

        case 2: // '\002'
            if(year % 4 == 0)
            {
                if(year % 100 == 0 && year % 400 != 0)
                    day = 28;
                else
                    day = 29;
            }
            else
            {
                day = 28;
            }
            break;

        case 4: // '\004'
        case 6: // '\006'
        case 9: // '\t'
        case 11: // '\013'
        default:
            day = 30;
            break;

        }
        return day;
    }

    public static String getYearMonthDateString(String datestr)
    {
        String datestring = "";
        if(datestr == null || datestr.trim().equals(""))
            datestring = "";
        else
        if(datestr.trim().length() < 6)
            datestring = datestr;
        else
        if(datestr.length() >= 6)
            datestring = datestr.substring(0, 4) + "-" + datestr.substring(4, 6);
        return datestring;
    }
    
    public static String CheckDate(String from)
    {
         String[] last_days = {"31","28","31","30","31","30","31","31","30","31","30","31"};
         String month_lastday="";
         int year = Integer.parseInt(from.substring(0, 4));
         String month = from.substring(4,6);
         
         if (month.equals("02"))
         {
             //윤달
             if (((year%4 == 0) && (year%100 != 0)) || (year%400 == 0))
                     last_days[1]="29";
             else
                     last_days[1]="28";
         }
         for(int i=1; i<13; i++)
         {
                 if(i==Integer.parseInt(month))
                   month_lastday=last_days[i-1];

          }
         return month_lastday;

     }

    public static boolean CheckWeekDate(int date)
    {
        String strDay =String.valueOf(date); 
    	boolean flag= false;  
    	if( !((Integer.parseInt(strDay.substring(6,8)) > Integer.parseInt(CheckDate(strDay)) 
    		  || strDay.substring(6,8).equals("00")	))
    	   ){
    		if (!(DateUtils.whichDay(strDay)==1 || DateUtils.whichDay(strDay)==7)) flag=true;
    	}
         

        return flag;

     }

    
    public static int daysSkipWeekDayBetween(String from, String to)
    {
    	  int restDate =0;
    	try
        {
            int fromNumber = Integer.parseInt(from);
            int toNumber = Integer.parseInt(to);

    		if(fromNumber<toNumber){
	            for(int date=fromNumber+1; date<toNumber+1; date++){
	    			if(CheckWeekDate(date)) restDate++;
	            }
    		}
    		else if(fromNumber>toNumber){
	            for(int date=toNumber; date<fromNumber; date++){
	    			if(CheckWeekDate(date)) restDate--;
	            }
    		}
    			
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        	return -1000000;
        }
        return restDate;
    }
    
    /**
     * @param fromDate   날짜 문자열
     * @param format     날짜 포멧
     * @param addYear    가산・감산할연
     * @param addMonth   가산・감산할 월
     * @param addDate    가산・감산할 일
     * @param addHour    가산・감산할 시간
     * @param addMinute  가산・감산할 분
     * @param addSecond  가산・감산할 초
     * @return String    날짜 문자열
     * @throws ParseException
     */
    public static String addDate(String fromDate, String format,
                          int addYear, int addMonth, int addDate,
                          int addHour, int addMinute, int addSecond) throws ParseException {


	     SimpleDateFormat sdf = new SimpleDateFormat(format);
	
	     Date date = null;
	     if(fromDate != ""){
	         date = sdf.parse(fromDate);
	     }else{
	         date = new Date();
	     }
	
	     Calendar cal = new GregorianCalendar();
	
	     cal.setTime(date);
	     cal.add(Calendar.YEAR,         +addYear);
	     cal.add(Calendar.MONTH,        +addMonth);
	     cal.add(Calendar.DATE,         +addDate);
	     cal.add(Calendar.HOUR_OF_DAY,  +addHour);
	     cal.add(Calendar.MINUTE,       +addMinute);
	     cal.add(Calendar.SECOND,       +addSecond);
	
	     SimpleDateFormat sdf2 = new SimpleDateFormat(format);
	
	     String toDate = sdf2.format(cal.getTime());
	
	     return toDate;

    }

    //kenosis add
    public static String getPaymentLimitDatetime(int hour) {
		Calendar today = Calendar.getInstance();
		StringBuffer paymentLimitDatetime = new StringBuffer();
		
		today.add (Calendar.HOUR, hour);

		int year = today.get ( Calendar.YEAR );
		int month = today.get ( Calendar.MONTH ) + 1;
		int day = today.get ( Calendar.DAY_OF_MONTH );
		int hour1 = today.get ( Calendar.HOUR_OF_DAY );
		int minute = today.get ( Calendar.MINUTE );

		paymentLimitDatetime.append(year);
		if (month < 10) {
			paymentLimitDatetime.append("0");
		}
		paymentLimitDatetime.append(month);
		if (day < 10) {
			paymentLimitDatetime.append ("0");
		}
		paymentLimitDatetime.append(day);
		if (hour1 < 10) { 
			paymentLimitDatetime.append("0");
		}
		paymentLimitDatetime.append(hour1);
		if (minute < 10) { 
			paymentLimitDatetime.append("0");
		}
		paymentLimitDatetime.append(minute);

	
    	return paymentLimitDatetime.toString();
	}
	
  //kenosis add
	public static String getPaymentLimitDatetime(int days, String minute) {
		Calendar today = Calendar.getInstance();
		StringBuffer paymentLimitDatetime = new StringBuffer();
		
		today.add (Calendar.DAY_OF_MONTH, days);

		int year = today.get ( Calendar.YEAR );
		int month = today.get ( Calendar.MONTH ) + 1;
		int day = today.get ( Calendar.DAY_OF_MONTH );
		

		paymentLimitDatetime.append(year);
		if (month < 10) {
			paymentLimitDatetime.append("0");
		}
		paymentLimitDatetime.append(month);
		if (day < 10) {
			paymentLimitDatetime.append ("0");
		}
		paymentLimitDatetime.append(day);
		
		paymentLimitDatetime.append(minute);

    	return paymentLimitDatetime.toString();
	}
}
