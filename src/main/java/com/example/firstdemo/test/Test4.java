package com.example.firstdemo.test;


import java.sql.*;

public class Test4 {
    public static void main(String[] args) {
        Connection conn = null;
        String sql;
        // 此处为您的连接地址、端口、账号、密码等信息。
        String url = "jdbc:mysql://rm-uf6c993efkx1p7rp80o.mysql.rds.aliyuncs.com:3306?zeroDateTimeBehavior=convertToNull&"
                + "user=client1&password=Password&useUnicode=true&characterEncoding=UTF8";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            //切换库。
            String sqlusedb="use superuser";
            int result1 = stmt.executeUpdate(sqlusedb);
            //创建表。
            sql = "create table teacher(NO char(20),name varchar(20),primary key(NO))";
            int result = stmt.executeUpdate(sql);
            //插入数据。
            if (result != -1) {
                sql = "insert into teacher(NO,name) values('2016001','wangsan')";
                result = stmt.executeUpdate(sql);
                sql = "insert into teacher(NO,name) values('2016002','zhaosi')";
                result = stmt.executeUpdate(sql);
                //查询数据。
                sql = "select * from teacher";
                ResultSet rs = stmt.executeQuery(sql);
                System.out.println("学号\t姓名");
                while (rs.next()) {
                    System.out
                            .println(rs.getString(1) + "\t" + rs.getString(2));
                }
            }
            //捕捉异常。
        } catch (SQLException e) {
            System.out.println("MySQL操作错误");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
