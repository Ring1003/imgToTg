package com.cherry.img.Controller;

import java.util.ArrayList;
import java.util.List;

public class testWhile {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");

        int number = 0;
        int size = list.size();
        int limit = 0;

        while (number<10){
            String s = list.get(number % size);
            System.out.println(number+":"+s+":"+number % size);

            number++;
        }


    }

}
