package company.whoami.statement;

import company.whoami.JDBCutil.JDBCutil;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by reyren on 2017/6/25.
 */
/*
* 使用statement执行DQL语句
* */
public class Demo3 {
    @Test
    public void test1(){
        Connection conn = null;
        Statement stmt = null;
        try{
            conn = JDBCutil.getConnection();
            //创建statement对象
            stmt = conn.createStatement();

            String sql = "select * from student";
            //执行
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()){
                int id = rs.getInt("id");
            }

        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }finally {
            JDBCutil.close(conn,stmt);
        }

    }
}
