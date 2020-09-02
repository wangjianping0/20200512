package com.example.firstdemo.test;

public class Test10 {
    public static void main(String[] args) {
        System.out.println("---------java中跳出多重循环的三种方式：---------");
        System.out.println("---------第一种，使用带有标号的的break语句---------");
        String a1 = "";
        String b1 = "";
        here:
        for (int i = 1; i <= 4; i++) {
            a1 = "外层循环第"+i+"层";
            for (int j = 1; j <= 4; j++) {
                b1 = "内层循环第"+j+"层";
                if (2 == j & 2 == i) {
                    break here;
                }
            }
        }
        System.out.println(a1+b1);
        System.out.println("---------第二种，外层的循环条件收到内层的代码控制限制---------");
        String a2 = "";
        String b2 = "";
        Boolean state =  true;
        for (int i = 1; i <= 4 && state; i++) {
            a2 = "外层循环第"+i+"层";
            for (int j = 1; j <= 4 && state; j++) {
                b2 = "内层循环第"+j+"层";
                if (2 == j & 2 == i) {
                    state = false;
                }
            }
        }
        System.out.println(a2+b2);
        System.out.println("---------第三种，使用try/catch强制跳出循环---------");
        String a3 = "";
        String b3 = "";
        try {
            for (int i = 1; i <= 3; i++) {
                a3 = "外层循环第"+i+"层";
                for (int j = 1; j <= 3; j++) {
                    b3 = "内层循环第"+j+"层";
                    if (2 == j & 2 == i) {
                        throw new Exception();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(a3+b3);
        }
        System.out.println("---------java中跳出多重循环的两种方式---------");
    }
}
