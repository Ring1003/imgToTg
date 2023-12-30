package com.cherry.img.Controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class test {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("123");
        list.add("345");

        for (int i = 0; i < 4; i++) {
            int h = new Random().nextInt(list.size());
            System.out.println(h);
        }



    }

    public static void asdfg(){
        List<String> list = new ArrayList<>();
        list.add("123");
        list.add("345");
        list.add("111");
        list.add("222");
        list.add("333");
        list.add("1az");
        list.add("33ddd3");
        list.add("aaaa");

        int as = 0;
        System.out.println("执行前"+list.size());
        remove(list,as);
        System.out.println("执行后"+list.size());
    }


    public static void remove(List<String> code,int a){
        List<String> asdf = code;
        a = a+1;
        if (a > 7){
            return;
        }
            if (a < 4){
                System.out.println("循环");
                test.remove(asdf,a);
            } else{
                asdf.remove(1);
                test.remove(asdf,a);
                System.out.println("执行删除");
            }

    }
}
