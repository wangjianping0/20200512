package com.example.firstdemo.test;

import com.alibaba.fastjson.JSONObject;
import lombok.Cleanup;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Component
public class TestJDBCUtil {
    public List selectAllTest(){
        List list=new ArrayList();
        String sql;
        // 此处为您的连接地址、端口、账号、密码等信息。
        String url = "jdbc:mysql://rm-uf6c993efkx1p7rp80o.mysql.rds.aliyuncs.com:3306?zeroDateTimeBehavior=convertToNull&"
                + "user=client1&password=Password&useUnicode=true&characterEncoding=UTF8";
        try {
            @Cleanup Connection conn = null;
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            //切换库。
            String sqlusedb="use superuser";
            int result1 = stmt.executeUpdate(sqlusedb);
                //查询数据。
                sql = "select * from teacher";
                ResultSet rs = stmt.executeQuery(sql);
                System.out.println("学号\t姓名");
                while (rs.next()) {
                    System.out
                            .println(rs.getString(1) + "\t" + rs.getString(2));
                    JSONObject jsonObject=new JSONObject();
                    jsonObject.put("NO",rs.getString(1));
                    jsonObject.put("name",rs.getString(2));
                    list.add(jsonObject);
                }
            //捕捉异常。
        } catch (SQLException e) {
            System.out.println("MySQL操作错误");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
