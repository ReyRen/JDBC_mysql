package company.whoami.dbcp;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

/**
 * @Author Yuan Ren.
 * @Description
 * @Date 2017/6/30 下午6:59
 */
public class App_DBCP {


    //1.硬编码方式实现连接池
    @Test
    public void testDbcp() throws Exception{
        //DBCP连接池核心类
        BasicDataSource dataSource = new BasicDataSource();
        //连接池参数配置：初始化连接数，最大链接数,链接字符串，驱动，用户，密码
        dataSource.setUrl("jdbc:mysql:///jdbc_demo");
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUsername("root");
        dataSource.setPassword("renyuan");

        dataSource.setInitialSize(3);   //初始化链接
        dataSource.setMaxActive(6); //最大链接
        dataSource.setMaxIdle(3000);//最大空闲时间，单位是s--就是达到这个时间没用，就会自动关闭




        //获取链接
        Connection con = dataSource.getConnection();

        con.prepareStatement("delete from admin where id=3").executeUpdate();

        con.close();
    }

    //2.配置方式实现连接池
    @Test
     public void testDbcp2() throws Exception{
         //加载prop配置文件
         Properties prop = new Properties();
         //获取文件流
         InputStream inputStream = App_DBCP.class.getResourceAsStream("db.properties");
         //加载配置属性
         prop.load(inputStream);

         //根据prop配置，直接创建数据源对象
         DataSource dataSource = BasicDataSourceFactory.createDataSource(prop);

         //获取链接
         Connection con = dataSource.getConnection();

         con.prepareStatement("delete from admin where id=3").executeUpdate();

         con.close();

     }


}
