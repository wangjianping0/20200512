package com.example.firstdemo.test;

import org.apache.commons.lang3.StringUtils;

public class Test9 {
    public static void main(String[] args) {
        String [] strs=new String[]{"1","2","3","4","5","6","7","8","9","10"};
        boolean flag=check("7",strs);
        System.out.println("result:"+flag);
    }

    public static boolean check(String param,String [] strings){
        for(String str:strings){
            System.out.println("-----------:"+str);
            if(StringUtils.equals(str,param)){
                return true;
            }
        }
        return false;
    }
}
