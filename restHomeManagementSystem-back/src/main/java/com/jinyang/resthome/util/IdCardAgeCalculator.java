package com.jinyang.resthome.util;

/**
 * @Description: 根据身份证计算年龄
 * @ClassName: IdCardAgeCalculator
 * @Author: jinyang
 * @Date: 2025/2/13 16:06
 * @Version: 1.0
 */
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public final class IdCardAgeCalculator {

    /**
     * 根据 18 位身份证号计算年龄
     * @param idCard 18 位身份证号
     * @return 年龄
     */
    public static String calculateAge(String idCard) {
        // 验证身份证号长度是否为 18 位
        if (idCard == null || idCard.length() != 18) {
            throw new IllegalArgumentException("输入的身份证号不是 18 位");
        }
        // 提取出生日期部分
        String birthDateStr = idCard.substring(6, 14);
        // 定义日期格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        // 将出生日期字符串转换为 LocalDate 对象
        LocalDate birthDate = LocalDate.parse(birthDateStr, formatter);
        // 获取当前日期
        LocalDate currentDate = LocalDate.now();
        // 计算年龄
        Period period = Period.between(birthDate, currentDate);
        int age = period.getYears();
        return String.valueOf(age);
    }
}
