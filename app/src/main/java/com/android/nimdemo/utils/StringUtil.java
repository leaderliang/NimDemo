package com.android.nimdemo.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;


import com.android.nimdemo.MyApplication;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Various string manipulation methods.
 * @author liangyuanyuan
 */
public class StringUtil {

    public static final String REGEX_URL = "(([a-zA-Z0-9+-.]+://)*(([a-zA-Z0-9\\.\\-]+\\.(bb|so|com|cn|net|pro|org|int|info|xxx|biz|coop))" +
            "|(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}))(:\\d+)?(/[a-zA-Z0-9\\.\\-~!@#$%^&*+?:_/=<>]*)?(?=\\b|[^a-zA-Z0-9\\.\\-~!@#$%^&*+?:_/=<>]))";

    public static boolean isNullOrEmpty(CharSequence s) {
        return s == null || s.equals("") || s.toString().trim().equals("") || s.equals("null");
    }

    public static boolean isEmptyOrNull(CharSequence s) {
        return s == null || s.equals("") || s.equals("null");
    }

    // datetime utilize
    // ----------------------------------------
    private static final String TIME_FORMAT = "yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSSSSSS";
    private static final String TIME_FORMAT_TEXT = "yyyy'-'MM'-'dd' 'HH':'mm";
    private static final String SAVE_FORMAT_TEXT = "yyyy'/'MM'/'dd";
    private static final String SAVESEC_FORMAT_TEXT = "HH:mm";

    private static final String LONGDATE_FORMAT_TEXT = "yyyy'-'MM'-'dd";
    // 12:"h:mm";24:"H:mm"
    private static final String DATE_FORMAT_HH_MM = "H:mm";
    private static final String DATE_FORMAT_MM_DD_HH_MM = "MM'-'dd' 'HH':'mm";

    private static final SimpleDateFormat DF_LONG_AGO = new SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault());

    public static String getVideoTimeString(long time) {
        if (time <= 0) {
            return "00:00";
        }
        long second = time % 60;
        long minute = time / 60;
        minute = minute > 59 ? 59 : minute;
        return String.format(Locale.getDefault(), "%02d:%02d", minute, second);
    }

    public static boolean isValidUrl(String url) {
        Pattern pattern = Pattern.compile(REGEX_URL);
        Matcher matcher = pattern.matcher(url);
        if (matcher != null) {
            return matcher.matches();
        }
        return false;
    }

    /**
     * convert local time to UTC string
     *
     * @param l
     * @return
     */
    public static String getUTCDateTimeFromLong(final Long l) {
        try {
            final TimeZone timeZone = TimeZone.getDefault();
            final long offset = timeZone.getOffset(l);
            Date d = new Date(l - offset);
            final SimpleDateFormat df = new SimpleDateFormat(TIME_FORMAT,
                    Locale.getDefault());
            final String sDate = df.format(d) + "Z";
            return sDate;
        } catch (final Exception e) {
            return null;
        }
    }

    /**
     * 设置消息界面 消息数量
     *
     * @param tv
     * @param count
     */
    public static void setViewCount(TextView tv, int count) {
        String showStr = count > 99 ? "99+" : count + "";
        tv.setText(showStr);
        tv.setVisibility(View.VISIBLE);
        if (count <= 0) {
            tv.setVisibility(View.GONE);
        } else if (count > 0 && count < 10) {
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        } else if (count > 0 && count <= 99) {
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
        } else {
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 9);
        }
    }

    public static String getTimeString(final Long l) {
        try {
            Date d = new Date(l);
            final SimpleDateFormat df = new SimpleDateFormat(TIME_FORMAT_TEXT,
                    Locale.getDefault());
            final String sDate = df.format(d);
            return sDate;
        } catch (final Exception e) {
            return null;
        }
    }

    public static String getSaveDateTime(final Long l) {
        try {
            Date d = new Date(l);
            final SimpleDateFormat df = new SimpleDateFormat(SAVE_FORMAT_TEXT,
                    Locale.getDefault());
            final String sDate = df.format(d);
            return sDate;
        } catch (final Exception e) {
            return null;
        }
    }

    public static String getSaveSecTime(final Long l) {
        try {
            Date d = new Date(l);
            final SimpleDateFormat df = new SimpleDateFormat(
                    SAVESEC_FORMAT_TEXT, Locale.getDefault());
            final String sDate = df.format(d);
            return sDate;
        } catch (final Exception e) {
            return null;
        }
    }

    public static String longFormatdateConvertFrom(long time) {
        SimpleDateFormat df = new SimpleDateFormat(LONGDATE_FORMAT_TEXT,
                Locale.getDefault());
        String timeResult = df.format(new Date(time));
        return timeResult;
    }

    public static String dateConvertFrom(long millis) {
        if (millis > 0) {
            return dateConvertFrom(new Date(millis));
        }
        return "";
    }

    public static String dateConvertFrom(final Date dDate) {

        if (dDate == null) {
            return null;
        }
        String timeResult = "";
        long lCur = System.currentTimeMillis();
        long lDiff = (lCur - dDate.getTime()) / 1000;

        // get current calendar
        Calendar calendar = GregorianCalendar.getInstance();
        int hoursOfDayNow = calendar.get(Calendar.HOUR_OF_DAY); // 24
        int minutesNow = calendar.get(Calendar.MINUTE);

        // set old date to calendar object
        // calendar.setTime(dDate);
        // int dayOfWeekOld = calendar.get(Calendar.DAY_OF_WEEK);
        // int hoursOfDayOld = calendar.get(Calendar.HOUR_OF_DAY);

        // 今天
        if (lDiff < hoursOfDayNow * 3600 + minutesNow) {
            SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT_HH_MM,
                    Locale.getDefault());
            timeResult = df.format(dDate);
            // String sDate = df.format(dDate);
            // if (hoursOfDayOld < 12) {
            // timeResult = "上午 " + sDate;
            // } else {
            // timeResult = "下午 " + sDate;
            // }
        } else if (lDiff < hoursOfDayNow * 3600 + minutesNow + 24 * 3600) {
            SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT_HH_MM,
                    Locale.getDefault());
            String sDate = df.format(dDate);
            timeResult = "昨天 " + sDate;
        } else if (lDiff >= hoursOfDayNow * 3600 + minutesNow + 24 * 3600
                && lDiff < hoursOfDayNow * 3600 + minutesNow + 24 * 3600 * 2) {
            SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT_HH_MM,
                    Locale.getDefault());
            String sDate = df.format(dDate);
            timeResult = "前天 " + sDate;
        } else {
            try {
                SimpleDateFormat df = new SimpleDateFormat(
                        DATE_FORMAT_MM_DD_HH_MM, Locale.getDefault());
                timeResult = df.format(dDate);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        return timeResult;
    }

    public static String getWeekName(long date) {
        String timeResult = "";
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(new Date(date));
        int dayOfWeekOld = calendar.get(Calendar.DAY_OF_WEEK);
        switch (dayOfWeekOld) {
            case Calendar.SUNDAY: {
                timeResult = "星期日";
                break;
            }
            case Calendar.MONDAY: {
                timeResult = "星期一 ";
                break;
            }
            case Calendar.TUESDAY: {
                timeResult = "星期二 ";
                break;
            }
            case Calendar.WEDNESDAY: {
                timeResult = "星期三 ";
                break;
            }
            case Calendar.THURSDAY: {
                timeResult = "星期四 ";
                break;
            }
            case Calendar.FRIDAY: {
                timeResult = "星期五 ";
                break;
            }
            case Calendar.SATURDAY: {
                timeResult = "星期六 ";
                break;
            }
            default:
                break;

        }
        return timeResult;
    }

    public static String getSimpleDataTimeText(long lData) {
        return DF_LONG_AGO.format(lData);
    }

    public static long localDateTimeToUTC(long currentDate) {
        final TimeZone timeZone = TimeZone.getDefault();
        final long offset = timeZone.getOffset(currentDate);
        return currentDate - offset;
    }

    public static String urlByAppendQuery(String url, String query) {
        if (query == null || query.length() == 0) {
            return url;
        }

        String urlQuery = String.format("%s%s%s", url,
                url.indexOf('?') > 0 ? "&" : "?", query);

        return urlQuery;
    }

    /**
     * add HTTP schema
     */
    public static String urlAppendSchema(String url) {
        if (StringUtil.isNullOrEmpty(url) || url.startsWith("http")) {
            return url;
        }

        return "http://" + url;
    }

    public static String validUrl(String url) {
        // if (StringUtil.isNullOrEmpty(url))
        // return null;
        //
        // if (!url.startsWith("http"))
        // return url;
        //
        // try {
        // URI myUri = new URI(url);
        // if (myUri.getHost() == null) {
        // return null;
        // }
        // if (myUri.getHost().equals(SAFE_HOST)
        // && myUri.getScheme().equals(SAFE_SCHEME)) {
        // String desUrl = "http://www.zhisland.com";
        // int startIndex = SAFE_HOST.length() + SAFE_SCHEME.length() + 3; // 3
        // // is
        // // for
        // // "://"
        // if (startIndex < url.length()) {
        // desUrl = desUrl + url.substring(startIndex);
        // desUrl = desUrl.replaceFirst(":443", "");
        // }
        //
        // return desUrl;
        // }
        // ;
        // } catch (URISyntaxException e) {
        // e.printStackTrace();
        // }
        return url;

    }

    public static boolean isEquals(String s1, String s2) {
        if (StringUtil.isNullOrEmpty(s1)) {
            return StringUtil.isNullOrEmpty(s2);
        } else {
            return s1.equals(s2);
        }
    }

    /**
     * 将一个数组中的元素根据制定分隔符拼接成一个字符串
     *
     * @param arr
     * @param split
     * @return
     */
    public static String convertFromArr(Collection<String> arr, String split) {
        if (arr == null || StringUtil.isNullOrEmpty(split)) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        for (String str : arr) {
            if (!StringUtil.isNullOrEmpty(str)) {
                sb.append(str + split);
            }
        }
        return sb.toString();
    }

    public static String convertFromArr(String[] arr, String split) {
        if (arr == null || StringUtil.isNullOrEmpty(split)) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        for (String str : arr) {
            if (!StringUtil.isNullOrEmpty(str)) {
                sb.append(str + split);
            }
        }
        return sb.toString();
    }

    /*
     * 将inputStream 转化为String
     */
    public static String readTextFile(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            int fileSize = inputStream.available();
            byte buf[] = new byte[fileSize];
            int len;

            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputStream.toString();
    }

	/*
     * 做作业,查看作业报告，老师批阅作业，点赞四个接口参数
	 */

//	public static String getLinkMsgExtraBody(String classId, String lessonNum,
//			int subType) {
//		if (XESUserInfo.getInstance() == null) {
//			return null;
//		}
//		String userId = XESUserInfo.getInstance().userId;
//		String userName = XESUserInfo.getInstance().name;
//		String avatarurl = XESUserInfo.getInstance().avatarUrl;
//		String format = "\"realFromUserId\":\"%s\",\"realFromUserName\":\"%s\",\"realFromUserImg\":
//                         \"%s\",\"dummyUser\":1,\"classId\":\"%s\",\"lessonNum\":\"%s\",\"subType\":%d";
//
//		return String.format(format, userId, userName, avatarurl, classId,
//				lessonNum, subType);
//	}
//
//	public static String getClassMsgExtraBody() {
//		if (XESUserInfo.getInstance() == null) {
//			return null;
//		}
//
//		String userId = XESUserInfo.getInstance().userId;
//		String userName = XESUserInfo.getInstance().name;
//		String avatarurl = XESUserInfo.getInstance().avatarUrl;
//		String format = "\"realFromUserId\":\"%s\",\"realFromUserName\":\"%s\",\"realFromUserImg\":\"%s\"";
//
//		return String.format(format, userId, userName, avatarurl);
//	}

    public static List<String> extractUrl(String content) {
        List<String> list = new ArrayList<String>();
        Pattern p = Pattern.compile(REGEX_URL);
        Matcher m = p.matcher(content);
        while (m.find()) {
            String str = m.group();
            if (str.length() >= 100) {
                list.add(str);
            }
        }
        return list;
    }

    public static Map<String, String[]> getParamsMap(String queryString, String enc) {
        Map<String, String[]> paramsMap = new HashMap<String, String[]>();
        if (queryString != null && queryString.length() > 0) {
            int ampersandIndex;
            int lastAmpersandIndex = 0;
            String subStr, param, value;
            String[] paramPair, values, newValues;
            do {
                ampersandIndex = queryString.indexOf('&', lastAmpersandIndex) + 1;
                if (ampersandIndex > 0) {
                    subStr = queryString.substring(lastAmpersandIndex,
                            ampersandIndex - 1);
                    lastAmpersandIndex = ampersandIndex;
                } else {
                    subStr = queryString.substring(lastAmpersandIndex);
                }
                paramPair = subStr.split("=");
                param = paramPair[0];
                value = paramPair.length == 1 ? "" : paramPair[1];
                try {
                    value = URLDecoder.decode(value, enc);
                } catch (UnsupportedEncodingException ignored) {
                    ignored.printStackTrace();
                }
                if (paramsMap.containsKey(param)) {
                    values = paramsMap.get(param);
                    int len = values.length;
                    newValues = new String[len + 1];
                    System.arraycopy(values, 0, newValues, 0, len);
                    newValues[len] = value;
                } else {
                    newValues = new String[]{value};
                }
                paramsMap.put(param, newValues);
            } while (ampersandIndex > 0);
        }
        return paramsMap;
    }

    public static String getFiltedNullStr(String value) {
        return isNullOrEmpty(value) ? "" : value;
    }


    public static boolean containsEmoji(String source) {
        if (isNullOrEmpty(source)) {
            return false;
        }
        int len = source.length();
        boolean isEmoji = false;
        for (int i = 0; i < len; i++) {
            char hs = source.charAt(i);
            if (0xd800 <= hs && hs <= 0xdbff) {
                if (source.length() > 1) {
                    char ls = source.charAt(i + 1);
                    int uc = ((hs - 0xd800) * 0x400) + (ls - 0xdc00) + 0x10000;
                    if (0x1d000 <= uc && uc <= 0x1f77f) {
                        return true;
                    }
                }
            } else {
                // non surrogate
                if (0x2100 <= hs && hs <= 0x27ff && hs != 0x263b) {
                    return true;
                } else if (0x2B05 <= hs && hs <= 0x2b07) {
                    return true;
                } else if (0x2934 <= hs && hs <= 0x2935) {
                    return true;
                } else if (0x3297 <= hs && hs <= 0x3299) {
                    return true;
                } else if (hs == 0xa9 || hs == 0xae || hs == 0x303d || hs == 0x3030 || hs == 0x2b55 || hs == 0x2b1c || hs == 0x2b1b || hs == 0x2b50 || hs == 0x231a) {
                    return true;
                }
                if (!isEmoji && source.length() > 1 && i < source.length() - 1) {
                    char ls = source.charAt(i + 1);
                    if (ls == 0x20e3) {
                        return true;
                    }
                }
            }
        }
        return isEmoji;
    }

    public static int counterChars(String str) {
        // return
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            int tmp = (int) str.charAt(i);
            if (tmp > 0 && tmp < 127) {
                count += 1;
            } else {
                count += 2;
            }
        }
        return count;
    }

    /**
     * 删除字符串中的空白符
     *
     * @param content
     * @return String
     */
    public static String removeBlanks(String content) {
        if (content == null) {
            return null;
        }
        StringBuilder buff = new StringBuilder();
        buff.append(content);
        for (int i = buff.length() - 1; i >= 0; i--) {
            if (' ' == buff.charAt(i) || ('\n' == buff.charAt(i)) || ('\t' == buff.charAt(i))
                    || ('\r' == buff.charAt(i))) {
                buff.deleteCharAt(i);
            }
        }
        return buff.toString();
    }

    public static String get32UUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String get36UUID() {
        UUID uuid = UUID.randomUUID();
        String uniqueId = uuid.toString();
        return uniqueId;
    }

    @NonNull
    public static String getPoneInfo() {
        String version = "";

        PackageManager pm = MyApplication.getInstance().getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(MyApplication.getInstance().getPackageName(), 0);
            version = pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        StringBuilder builder = new StringBuilder();
        builder.append("#Android");
        builder.append("，APP版本" + version);
        builder.append("，操作系统版本" + Build.VERSION.SDK_INT);
        builder.append("，手机型号" + Build.MODEL);
        builder.append("，网络" + getNetType(MyApplication.getInstance()));
//        builder.append("，登录账号" + account + "#");
        return builder.toString();
    }

    public static String getNetType(Context context) {
        NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService(Context
                .CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (ConnectivityManager.TYPE_WIFI == networkInfo.getType()) {
                return "WiFi";
            } else {
                return "WWAN";
            }
        }
        return "";
    }

    /**
     * 定义一个是否显示控件的枚举(View.VISIBLE, View.INVISIBLE, View.GONE)
     */
    @IntDef({View.VISIBLE, View.INVISIBLE, View.GONE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Visibility {
    }
}
