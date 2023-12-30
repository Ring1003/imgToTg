package com.cherry.img.Controller;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexDemo {

     /**
     　*  正则表达式,识别出字符串中的数字
     　* @author MengJie
     　* @date 2023-12-14 16:12:02
     　*/
        public static void main(String[] args) {

            String grossAmount = " $27,432.15";
            if (StringUtils.isNotEmpty(grossAmount)){
                grossAmount = grossAmount.replaceAll("\\s+", "");
            }
            System.out.println("原始的金额字符串: " + grossAmount);
            Pattern pattern = Pattern.compile("[$￥]?[,\\d]*(\\.\\d+)?");
            Matcher matcher = pattern.matcher(grossAmount);
            if (matcher.find()) {
                String numberStr = matcher.group().replaceAll("[^0-9.]", "");
                System.out.println("金额: " + numberStr);
            }


//            String str = "hello $12,211,542.75234 world";
//            String str = "$45,111.47";
//            Pattern pattern = Pattern.compile("\\$?\\d{1,3}(,\\d{3})*(\\.\\d+)?");
//            Matcher matcher = pattern.matcher(str);
//            if (matcher.find()) {
//                String numberStr = matcher.group().replaceAll("[^0-9.]", "");
//
//                System.out.println(new BigDecimal(numberStr));
////                System.out.println(new BigDecimal(numberStr));
//            }
////            String str2 = "￥12,211,542.75234";
//            String str2 = " $10,709.00￥12,211,542.75234";
//            Pattern pattern2 = Pattern.compile("(\\p{Sc})");
//            Matcher matcher2 = pattern2.matcher(str2);
//            if (matcher2.find()) {
//                String currencySymbol = matcher2.group(1);
//                System.out.println(currencySymbol);
//            }
        }

}
