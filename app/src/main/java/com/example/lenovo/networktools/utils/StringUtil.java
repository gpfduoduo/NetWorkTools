package com.example.lenovo.networktools.utils;


import android.content.Context;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 常用的一些操作
 */
public class StringUtil {

    public static int getIntegerToString(String str) {
        Integer number = 0;
        if (str.trim().equals("") || null == str) {
            number = 0;
        } else {
            number = Integer.parseInt(str.trim());
        }

        return number;
    }

    public static float getFloatToString(String str) {
        float number = 0.0f;
        if (str.trim().equals("") || null == str) {
            number = 0;
        } else {
            number = Float.valueOf(str.trim());
        }

        return number;
    }

    /**
     * 判断字符串是否为空 郭攀峰 10129032 添加
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * 去除空格 郭攀峰 10129032 添加
     */
    public static String trim(String str) {
        return str == null ? "" : str.trim();
    }

    /**
     * 开始用的这种，后来就不限制，调用checkPhone方法。 判断手机格式是否正确，在注册和修改手机号码的时候用到
     *
     * @param mobiles
     * @return true:正确的手机号码
     */
    public static boolean isMobileNO(String mobiles) {
        // Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Pattern p = Pattern.compile("^(1(([358][0-9])|(4[57])))\\d{8}$");
        Matcher m = p.matcher(mobiles);

        return m.matches();
    }

    /**
     * 校验手机号码
     *
     * @param context
     * @param phone
     * @return
     */
    public static boolean checkPhone(Context context, String phone) {
        if (null == phone || "".equals(phone) || "".equals(phone.trim())) {
            toastMessage(context, "请输入手机号码");
            return false;
        } else if (phone.length() != 11 || !phone.startsWith("1")) {
            toastMessage(context, "手机号码格式不对");
            return false;
        }

        return true;
    }

    /**
     * Toast提示语句
     */
    public static void toastMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 是否包含非法字符 注册的时候判断用户名是否包含特殊字符
     *
     * @return true ：包含特殊字符，fasle就是不包含
     */
    public static boolean containInvalidChars(String str) {
        if (str == null || "".equals(str))
            return false;

        String SPECIAL_STR = "#~!@%^&*();'\"?><[]{}\\|,:/=+—“”‘.`$；，。！@#￥%……&*（）——+？";
        for (int i = 0; i < str.length(); i++) {
            if (SPECIAL_STR.indexOf(str.charAt(i)) != -1) {
                return true;
            }
        }

        return false;
    }

    /**
     * 判断是否全是数字
     *
     * @param str
     * @return true :全是数字
     */
    public static boolean isNumer(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }

        return true;
    }

    /**
     * 判断email格式是否正确
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        final String str_email = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";

        Pattern p = Pattern.compile(str_email);
        Matcher m = p.matcher(email);

        return m.matches();
    }

}
