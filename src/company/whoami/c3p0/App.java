package company.whoami.c3p0;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * @Author Yuan Ren.
 * @Description
 * @Date 2017/6/30 下午8:20
 */
public class App {
    //1.编码方式，使用c3p0连接池管理
    @Test
    public void testCode() throws Exception{
        //创建链接池核心工具类
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        //连接池参数配置：初始化连接数，最大链接数,链接字符串，驱动，用户，密码
        dataSource.setJdbcUrl("jdbc:mysql:///jdbc_demo");
        dataSource.setDriverClass("com.mysql.jdbc.Driver");
        dataSource.setUser("root");
        dataSource.setPassword("renyuan");
        dataSource.setInitialPoolSize(3);
        dataSource.setMaxPoolSize(6);
        dataSource.setMaxIdleTime(3000);

        //从连接池对象中获取链接对象
        Connection con = dataSource.getConnection();

        con.prepareStatement("delete from admin where id=3").executeUpdate();

        con.close();

    }

    //2.xml配置方式使用c3p0
    @Test
    public void testXML() throws Exception{

        //创建链接池核心工具类,会自动加载src下的c3p0配置文件c3p0-config.xml
        ComboPooledDataSource dataSource = new ComboPooledDataSource();//这里不传字符串是默认配置，传可以是<named-config name="oracle_config">
        PreparedStatement pstm = null;

        //从连接池对象中获取链接对象
        Connection con = dataSource.getConnection();
        for (int i = 1; i < 11;i++){
           String sql = "INSERT INTO admin(userName,pwd) VALUES (?,?)";
            pstm = con.prepareStatement(sql);
            pstm.setString(1,"Ross" + i);
            pstm.setInt(2,1);
            pstm.executeUpdate();
        }

        pstm.close();
        con.close();
    }
}
