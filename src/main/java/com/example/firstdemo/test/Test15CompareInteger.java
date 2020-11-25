package com.example.firstdemo.test;

public class Test15CompareInteger {
    public static void main(String[] args) {
        int i1=5;
        int i2=5;
        Integer i3=new Integer(5);
        Integer i4=new Integer(5);
        int i5=128;
        int i6=128;
        Integer i7=new Integer(128);
        Integer i8=new Integer(128);
        Integer i9=128;
        Integer i10=128;
        System.out.println("i1==i2:"+(i1==i2));
        System.out.println("i2==i3:"+(i2==i3));
        System.out.println("i3==i4:"+(i3==i4));
        System.out.println("i5==i6:"+(i5==i6));
        System.out.println("i6==i7:"+(i6==i7));
        System.out.println("i7==i8:"+(i7==i8));
        System.out.println("i8==i9:"+(i8==i9));
        System.out.println("i9==i10:"+(i9==i10));
        System.out.println("-----------------------------------------");
        int a=128;
        int b=128;
        Integer c=127;
        Integer d=127;
        Integer e=128;
        Integer f=128;
        Integer g=new Integer(128);
        Integer h=new Integer(128);
        System.out.println("int基本类型比较a==b："+(a==b));
        System.out.println("自动装箱比较范围在（-128~127）："+(c==d));
        System.out.println("自动装箱比较范围不在（-128~127）："+(e==f));
        System.out.println("new Integer()==new Integer()："+(g==h));
        System.out.println("int==new Integer()："+(a==g));
        System.out.println("new Integer().intValue()==new Integer()："+(g.intValue()==h));
    }
}
