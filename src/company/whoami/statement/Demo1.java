package company.whoami.statement;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by reyren on 2017/6/25.
 */
/*
* 使用Statement对象执行sql语句
* */
public class Demo1 {
    private String url = "jdbc:mysql://localhost:3306/day17";
    private String user = "root";
    private String password = "renyuan";

    @Test
    public void test1(){
        Statement stmt = null;
        Connection conn = null;

        try {
        /*
        * 执行DDL语句:创建学生表
        * */

            //1.注册驱动程序
            Class.forName("com.mysql.jdbc.Driver");
            //2.获取链接对象
            conn = DriverManager.getConnection(url,user,password);
            //3.创建statement对象
            stmt = conn.createStatement();
            //4.准备sql
            String sql = "create table student( id int primary key auto_increment, name varchar(20),gender varchar(20));";
            //5.发送sql,得到返回结果
            int count = stmt.executeUpdate(sql);//返回影响的行数
            System.out.println("影响了：" + count + "行");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }finally {
            //关闭链接,后打开的先关闭
            if (stmt != null){
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
            if (conn != null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }

        }



    }
}
