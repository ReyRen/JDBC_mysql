package company.whoami.dbUtiles;

import company.whoami.BaseDao.Admin;
import company.whoami.JDBCutil.JDBCutil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @Author Yuan Ren.
 * @Description
 * @Date 2017/6/29 下午5:53
 */
public class App_query {
    private Connection conn;

    // 一、查询， 自定义结果集封装数据
    @Test
    public void testQuery() throws Exception {
        String sql = "select * from admin where id=?";
        // 获取连接
        conn = JDBCutil.getConnection();
        // 创建DbUtils核心工具类对象
        QueryRunner qr = new QueryRunner();
        // 查询
        Admin admin = qr.query(conn, sql, new ResultSetHandler<Admin>() {

            // 如何封装一个Admin对象
            public Admin handle(ResultSet rs) throws SQLException {
                if (rs.next()) {
                    Admin admin = new Admin();
                    admin.setId(rs.getInt("id"));
                    admin.setUserName(rs.getString("userName"));
                    admin.setPwd(rs.getString("pwd"));
                    return admin;
                }
                return null;
            }

        }, 29);

        // 测试
        System.out.println(admin);
        // 关闭
        conn.close();

    }

    // 二、查询， 使用组件提供的结果集对象封装数据

    // 1）BeanHandler: 查询返回单个对象
    @Test
    public void testQueryOne() throws Exception {
        String sql = "select * from admin where id=?";
        // 获取连接
        conn = JDBCutil.getConnection();
        // 创建DbUtils核心工具类对象
        QueryRunner qr = new QueryRunner();
        // 查询返回单个对象
        Admin admin =  qr.query(conn, sql, new BeanHandler<Admin>(Admin.class), 29);
        //new BeanHandler<Admin>(Admin.class)就是创建对象，泛型类，然后带一个构造函数的参数是Admin.class

        System.out.println(admin);
        conn.close();
    }

    // 2）BeanListHandler: 查询返回list集合，集合元素是指定的对象
    @Test
    public void testQueryMany() throws Exception {
        String sql = "select * from admin";
        conn = JDBCutil.getConnection();
        QueryRunner qr = new QueryRunner();
        // 查询全部数据
        List<Admin> list = qr.query(conn, sql, new BeanListHandler<Admin>(Admin.class));

        System.out.println(list);
        conn.close();
    }
    @Test
//	3) ArrayHandler, 查询返回结果记录的第一行，封装对对象数组, 即返回：Object[]
//	4) ArrayListHandler, 把查询的每一行都封装为对象数组，再添加到list集合中
//	5) ScalarHandler 查询返回结果记录的第一行的第一列  (在聚合函数统计的时候用)
//	6) MapHandler  查询返回结果的第一条记录封装为map
    public void testArray() throws Exception {
        String sql = "select * from admin";
        conn = JDBCutil.getConnection();
        QueryRunner qr = new QueryRunner();
        // 查询
        //Object[] obj = qr.query(conn, sql, new ArrayHandler());
        List<Object[]> list = qr.query(conn, sql, new ArrayListHandler());
        //Long num = qr.query(conn, sql, new ScalarHandler<Long>());
        //Map<String, Object> map = qr.query(conn,sql, new MapHandler());

        conn.close();
    }
}
