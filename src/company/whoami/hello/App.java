package company.whoami.hello;

import company.whoami.BaseDao.Admin;
import company.whoami.JDBCutil.JDBCutil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.junit.Test;

import java.sql.Connection;
import java.util.List;

/**
 * @Author Yuan Ren.
 * @Description
 * @Date 2017/6/30 下午4:56
 */
public class App {

    //使用DBUtils组件进行更新的方法
    @Test
    public void testUpdate() throws Exception{
        String sql = "delete from admin where id=10;";
        //获取链接
        Connection con = JDBCutil.getConnection();
        //创建核心工具类
        QueryRunner qr = new QueryRunner();
        //更新
        qr.update(con,sql);
        con.close();
    }

    //使用DBUtils组件进行查询的方法
    @Test
    public void testQuery() throws Exception{
        String sql = "select * from admin;";
        //获取链接
        Connection con = JDBCutil.getConnection();
        //创建核心工具类
        QueryRunner qr = new QueryRunner();
        List<Admin> list = qr.query(con,sql,new BeanListHandler<Admin>(Admin.class));
        System.out.println(list);
        con.close();
    }
}
