package company.whoami.tx;

import company.whoami.JDBCutil.JDBCutil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Savepoint;

/**
 * Created by reyren on 2017/6/27.
 */
public class AccountDao {

    //全局参数
    private Connection con;
    private PreparedStatement pstmt;

    //转账，没有使用事物
    public void trans(){
        String sql_zs = "update account set money=money-1000 where accountName='张三';";
        String sql_ls = "update account set money=money+1000 where accountName='李四';";

        try {
            /*第一次执行sql*/
            con = JDBCutil.getConnection();//创建连接,相当于默认开启了隐士事物，自动提交了
            //创建statement
            pstmt = con.prepareStatement(sql_zs);
            pstmt.executeUpdate();

            /*第二次执行sql*/
            con = JDBCutil.getConnection();//创建连接
            //创建statement
            pstmt = con.prepareStatement(sql_ls);
            pstmt.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCutil.close(con,pstmt);
        }

    }


    //转账，使用事物
    public void trans2(){
        String sql_zs = "update account set money=money-1000 where accountName='张三';";
        String sql_ls = "update account set money=money+1000 where accountName='李四';";

        try {
            /*第一次执行sql*/
            con = JDBCutil.getConnection();//创建连接,相当于默认开启了隐士事物，自动提交了
            //设置事物为手动提交
            con.setAutoCommit(false);

            //创建statement
            pstmt = con.prepareStatement(sql_zs);
            pstmt.executeUpdate();

            /*第二次执行sql*/
            con = JDBCutil.getConnection();//创建连接
            //创建statement
            pstmt = con.prepareStatement(sql_ls);
            pstmt.executeUpdate();
        }catch (Exception e){
            try {
                //出现异常需要回滚事物
                con.rollback();
            } catch (SQLException e1) {
            }
            e.printStackTrace();
        }finally {
            //所有事物成功，提交事物
            try {
                con.commit();
                JDBCutil.close(con,pstmt);
            } catch (SQLException e) {
            }
        }
    }
    //转账，使用事物，回滚到指定的代码段
    public void trans3(){
        //第一次转账
        String sql_zs = "update account set money=money-1000 where accountName='张三';";
        String sql_ls = "update account set money=money+1000 where accountName='李四';";

        //第二次转账
        String sql_zs2 = "update account set money=money-500 where accountName='张三';";
        String sql_ls2 = "update account set money=money+500 where accountName='李四';";

        Savepoint sp = null;
        try {
            /*第一次执行sql*/
            con = JDBCutil.getConnection();//创建连接,相当于默认开启了隐士事物，自动提交了
            con.setAutoCommit(false);

            //创建statement
            //第一次转账
            pstmt = con.prepareStatement(sql_zs);
            pstmt.executeUpdate();
            /*第二次执行sql*/
            con = JDBCutil.getConnection();//创建连接
            //创建statement
            pstmt = con.prepareStatement(sql_ls);
            pstmt.executeUpdate();


            //回滚到这里？
            sp = con.setSavepoint();//定义一个保存点

            //第二次转账
            pstmt = con.prepareStatement(sql_zs2);
            pstmt.executeUpdate();
            /*第二次执行sql*/
            con = JDBCutil.getConnection();//创建连接
            //创建statement
            pstmt = con.prepareStatement(sql_ls2);
            pstmt.executeUpdate();
        }catch (Exception e){
            try {
                con.rollback(sp);//回滚到指定的代码段
            } catch (SQLException e1) {

            }
            e.printStackTrace();
        }finally {
            try {
                con.commit();
                JDBCutil.close(con,pstmt);
            } catch (SQLException e) {

            }
        }

    }
}
