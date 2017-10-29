package company.whoami.statement;

import company.whoami.JDBCutil.JDBCutil;
import jdk.nashorn.internal.scripts.JD;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by reyren on 2017/6/25.
 */
/*
* JDBC使用statement执行DML语句
*
* 关于乱码问题
*   1：eclipse文件保存的编码方式
    2：数据库的编码方式
    3：jdbc连接的编码方式
* */
public class Demo2 {


    /*
    * 添加
    * */
    @Test
    public void testInsert(){
        Statement stmt = null;
        Connection conn = null;
        try {
            //通过工具类过去链接对象
            JDBCutil.getConnection();

            //获取statement
            stmt = conn.createStatement();

            //sql
            String sql = "insert into student(name,gender) values('李四','女');";

            //执行
            int count = stmt.executeUpdate(sql);
            System.out.println("影响了:" + count + "行");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            JDBCutil.close(conn,stmt);
        }
    }

    /*
    * 更新
    * */
    @Test
    public void testUpdate(){
        Statement stmt = null;
        Connection conn = null;
        //模拟用户输入
        String name = "陈六";
        int id = 1;

        try {
            JDBCutil.getConnection();

            //获取statement
            stmt = conn.createStatement();

            //sql
            String sql = "UPDATE student SET name='"+name+"' where id="+id+"";

            //执行
            int count = stmt.executeUpdate(sql);
            System.out.println("影响了:" + count + "行");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            JDBCutil.close(conn,stmt);
        }
    }


}
