package company.whoami.dbUtiles;

import company.whoami.JDBCutil.JDBCutil;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.junit.Test;

import java.sql.Connection;

/**
 * @Author Yuan Ren.
 * @Description
 * @Date 2017/6/29 下午5:45
 */
public class App_update {
    private Connection conn;

    // 1. 更新
    @Test
    public void testUpdate() throws Exception {
        String sql = "delete from admin where id=?";
        // 连接对象
        conn = JDBCutil.getConnection();

        // 创建DbUtils核心工具类对象
        QueryRunner qr = new QueryRunner();
        qr.update(conn, sql, 26);

        // 关闭
        DbUtils.close(conn);
    }

    // 2. 批处理
    @Test
    public void testBatch() throws Exception {
        String sql = "insert into admin (userName, pwd) values(?,?)";
        conn = JDBCutil.getConnection();
        QueryRunner qr = new QueryRunner();
        // 批量删除
        qr.batch(conn, sql, new Object[][]{ {"jack1","888"},{"jack2","999"}  });

        // 关闭
        conn.close();
    }
}
