package com.example.firstdemo.test;

/**
 *
 */
public class Test6 {
    public static void main(String[] args) {
        Object o;
        int a=test();
        System.out.println(a);
    }
    public static int test(){
        try{
//            throw new Exception();
            String string="asdf";
            int num=Integer.parseInt(string);
            return 1;
        }catch (Exception e){
            return 2;
        }finally {
            return 3;
        }
    }

}
