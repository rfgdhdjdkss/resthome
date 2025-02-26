package com.jinyang.resthome.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description: 验证11位手机号正则表达式工具类
 * @ClassName: PhoneNumberUtils
 * @Author: jinyang
 * @Date: 2025/2/22 17:44
 * @Version: 1.0
 */
public class PhoneNumberUtils {
    // 手机号码正则表达式
    private static final String PHONE_REGEX = "^1[3-9]\\d{9}$";
    private static final Pattern PHONE_PATTERN = Pattern.compile(PHONE_REGEX);

    /**
     * 验证输入的字符串是否为有效的 11 位手机号码
     *
     * @param phoneNumber 待验证的手机号码字符串
     * @return 如果是有效的手机号码返回 true，否则返回 false
     */
    public static boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return false;
        }
        Matcher matcher = PHONE_PATTERN.matcher(phoneNumber);
        return matcher.matches();
    }

    // 私有构造函数，防止实例化工具类
    private PhoneNumberUtils() {
    }
}
