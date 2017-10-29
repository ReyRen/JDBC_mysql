package company.whoami.meta;

import company.whoami.JDBCutil.JDBCutil;
import org.junit.Test;

import java.sql.*;

/**
 * @Author Yuan Ren.
 * @Description
 * @Date 2017/6/29 下午4:07
 */
public class App {
    //1. 数据库元数据
    @Test
    public void testDB() throws Exception {
        // 获取连接
        Connection conn = JDBCutil.getConnection();
        // 获取数据库元数据
        DatabaseMetaData metaData = conn.getMetaData();// alt + shift + L  快速获取方法返回值

        System.out.println(metaData.getUserName());//root@localhost
        System.out.println(metaData.getURL());//jdbc:mysql://localhost:3306/day17
        System.out.println(metaData.getDatabaseProductName());//MySQL
    }

    //2. 参数元数据
    @Test
    public void testParams() throws Exception {
        // 获取连接
        Connection conn = JDBCutil.getConnection();
        // SQL
        String sql = "select * from admin where id=? and userName=?";
        // Object[] values = {"tom","888"};

        PreparedStatement pstmt = conn.prepareStatement(sql);
        // 参数元数据
        ParameterMetaData p_metaDate = pstmt.getParameterMetaData();
        // 获取参数的个数
        int count = p_metaDate.getParameterCount();


        // 测试
        System.out.println(count);
    }

    // 3. 结果集元数据
    @Test
    public void testRs() throws Exception {
        String sql = "select * from admin ";

        // 获取连接
        Connection conn = JDBCutil.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        // 得到结果集元数据(目标：通过结果集元数据，得到列的名称)
        ResultSetMetaData rs_metaData = rs.getMetaData();

        // 迭代每一行结果
        while (rs.next()) {
            // 1. 获取列的个数
            int count = rs_metaData.getColumnCount();
            // 2. 遍历，获取每一列的列的名称
            for (int i=0; i<count; i++) {
                // 得到列的名称
                String columnName = rs_metaData.getColumnName(i + 1);
                // 获取每一行的每一列的值
                Object columnValue = rs.getObject(columnName);
                // 测试
                System.out.print(columnName + "=" + columnValue + ",");
            }
            System.out.println();
        }

    }
}
