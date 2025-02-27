package com.jinyang.resthome.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;

/**
 * @Description: 订单号生成工具类
 * @ClassName: OrderNumberGeneratorUtils
 * @Author: jinyang
 * @Date: 2025/2/26 18:04
 * @Version: 1.0
 */
public class OrderNumberGeneratorUtils {
    private static final Random RANDOM = new Random();
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    /**
     * 生成订单号
     *
     * @param uid 用户 ID
     * @return 生成的订单号
     */
    public static String generateOrderNumber(LocalDateTime time, Long uid) {
        // 获取当前时间并格式化
        String dateTimeStr = time.format(DATE_FORMAT);

        // 生成三位随机整数
        int randomNum = RANDOM.nextInt(900) + 100;

        // 组合订单号
        return dateTimeStr  + uid + randomNum;
    }
}
