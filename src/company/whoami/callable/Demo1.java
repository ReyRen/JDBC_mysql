package company.whoami.callable;

import company.whoami.JDBCutil.JDBCutil;
import org.junit.Test;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;

/**
 * Created by reyren on 2017/6/26.
 *//*
 *
 * 使用CablleStatement调用存储过程
 * */
public class Demo1 {

    @Test
    public void test1(){
        Connection conn = null;
        CallableStatement stmt = null;
        ResultSet rs = null;
        try{
            conn = JDBCutil.getConnection();


            String sql = "call pro_findById(?);";

            //预编译
            stmt = conn.prepareCall(sql);

            //设置
            stmt.setInt(1,4);

            //发送参数
            rs = stmt.executeQuery();//所有调用存储过程的语句都使用executeQuery

            while (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String gender = rs.getString("gender");
                System.out.println(id + name + gender);
            }

        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }finally {
            JDBCutil.close(conn,stmt,rs);
        }
    }



    @Test
    public void test2(){
        Connection conn = null;
        CallableStatement stmt = null;
        ResultSet rs = null;
        try{
            conn = JDBCutil.getConnection();


            String sql = "call pro_findById2(?,?);";//第一个是输入参数，第二个是输出参数

            //预编译
            stmt = conn.prepareCall(sql);

            //设置输入参数
            stmt.setInt(1,4);
            //设置输出参数(第一个是表示参数的位置，第二个是存储过程中的输出参数的类型)
            stmt.registerOutParameter(2, Types.VARCHAR);//注册一个输出参数
            //发送这个参数然后执行

            //发送参数
            rs = stmt.executeQuery();//所有调用存储过程的语句都使用executeQuery,这里是没有结果集返回的

            //得到输出参数的值
            /*
            * 注意：索引值即使预编译sql中输出参数的位置，和上面的一致
            * */
            String result = stmt.getString(2);//这里的getXX是用于专门获取存储过程中的输出参数的
            System.out.println(result);


        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }finally {
            JDBCutil.close(conn,stmt,rs);
        }
    }
}
