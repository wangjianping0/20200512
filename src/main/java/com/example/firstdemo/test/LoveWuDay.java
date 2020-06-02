package com.example.firstdemo.test;

import java.util.Scanner;

public class LoveWuDay {
    public static void main(String[] args) {
        int January = 31;
        int February = 28;
        int March = 31;
        int April = 30;
        int May = 31;
        int June = 30;
        int July = 31;
        int August = 31;
        int September = 30;
        int October = 31;
        int November = 30;
        int December = 31;

        for(;;){
            Scanner sc =new Scanner(System.in);
            System.out.println("请输入你们相爱的年份");
            int loveYear = sc.nextInt();

            System.out.println("请输入你们相爱的月份(阳历月份)");
            int loveMonth = sc.nextInt();
            if(loveMonth<1||loveMonth>12){
                System.out.println("输入的月份不合理,自动为您重新录入");
                continue;
            }

            System.out.println("请输入你们相爱的那一天是多少号");
            int loveDay = sc.nextInt();
            if(loveDay<1||loveDay>31){
                System.out.println("输入的日期不合理,自动为您重新录入");
                continue;
            }

            System.out.println("请输入现在的年份");
            int nowYear = sc.nextInt();
            if(nowYear<loveYear){
                System.out.println("输入的现在的年份早于相爱的年份,不合理,自动为您重新录入");
                continue;
            }

            System.out.println("请输入你们现在的月份(阳历月份)");
            int nowMonth = sc.nextInt();
            if(nowMonth<1||nowMonth>12){
                System.out.println("输入的月份不合理,自动为您重新录入");
                continue;
            }

            System.out.println("请输入现在是多少号");
            int nowDay = sc.nextInt();
            if(nowDay<1||nowDay>31){
                System.out.println("输入的日期不合理,自动为您重新录入");
                continue;
            }
            int year = nowYear-loveYear;

  break;
        }
    }
}
