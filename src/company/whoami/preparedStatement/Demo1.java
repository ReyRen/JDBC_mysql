package company.whoami.preparedStatement;

import company.whoami.JDBCutil.JDBCutil;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by reyren on 2017/6/25.
 */
/*
preparedStatement执行sql语句
*/

public class Demo1 {


    //增加
    @Test
    public void testInsert() {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = JDBCutil.getConnection();

            //预编译的sql
            String sql = "insert into student(name,gender) values(?,?);";//表示参数占位符

            //预编译--只是检查语法
            stmt = conn.prepareStatement(sql);

            //设置参数的值
            stmt.setString(1,"撒");
            stmt.setString(2,"男");

            //执行sql
            int count = stmt.executeUpdate();
            System.out.println("影响了" + count + "行");


        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            JDBCutil.close(conn, stmt);
        }
    }

    //修改
    @Test
    public void testUpdate() {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = JDBCutil.getConnection();

            //预编译的sql
            String sql = "UPDATE student set NAME=? WHERE id=?";//表示参数占位符

            //预编译--只是检查语法
            stmt = conn.prepareStatement(sql);

            //设置参数的值
            stmt.setString(1,"王五");
            stmt.setInt(2,4);

            //执行sql
            int count = stmt.executeUpdate();
            System.out.println("影响了" + count + "行");


        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            JDBCutil.close(conn, stmt);
        }
    }


    //查询
    @Test
    public void testQuery() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = JDBCutil.getConnection();

            //预编译的sql
            String sql = "SELECT * from student;";//表示参数占位符

            //预编译--只是检查语法
            stmt = conn.prepareStatement(sql);

            rs = stmt.executeQuery();
            while (rs.next()){
                int id = rs.getInt("id");
            }

            //执行sql
            int count = stmt.executeUpdate();
            System.out.println("影响了" + count + "行");


        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            JDBCutil.close(conn, stmt);
        }
    }
}
