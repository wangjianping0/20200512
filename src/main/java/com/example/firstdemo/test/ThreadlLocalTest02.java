package com.example.firstdemo.test;

public class ThreadlLocalTest02 {
    private String content;
    private ThreadLocal<String> local=new ThreadLocal<>();

    public String getContent() {
        //返回对应的Threadlocal中的数据
        return local.get();
    }

    public void setContent(String content) {
        local.set(content);
        this.content = content;
    }

    public static void main(String[] args) {
        ThreadlLocalTest02 t2 = new ThreadlLocalTest02();

        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    t2.setContent(Thread.currentThread().getName() + "");
                    System.out.println(Thread.currentThread().getName() + "的数据为：" + t2.getContent());
                }
            });
            thread.start();
        }
    }
}
