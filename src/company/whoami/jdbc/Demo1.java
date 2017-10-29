package company.whoami.jdbc;

import org.junit.Test;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * Created by reyren on 2017/6/25.
 */
/*
* jdbc连接数据库
* */
public class Demo1 {
    //连接数据库的url
    private String url="jdbc:mysql://localhost:3306/day15";
                    //jdbc协议：数据库的子协议：//主机：端口/具体的需要连接的数据库
    private String user = "root";//用户名
    private String password = "renyuan";

    @Test
    public void test1() throws Exception{

        //创建一个驱动程序的类对象
        Driver driver = new com.mysql.jdbc.Driver();

        //设置用户名和密码
        Properties props = new Properties();
        props.setProperty("user",user);
        props.setProperty("password",password);

        //连接数据库
        Connection conn = driver.connect(url,props);//返回连接的对象，如果有这个对象就说明链接成功
        System.out.println(conn);

    }

    /*
    * 使用驱动管理器类来连接数据库
    * */
    @Test
    public void test2() throws Exception{
        //创建一个驱动程序的类对象
        Driver driver = new com.mysql.jdbc.Driver();
        //Driver driver2 = new com.mysql.jdbc.Driver();
        //注册驱动程序,可以注册多个驱动程序
        DriverManager.registerDriver(driver);
       // DriverManager.registerDriver(driver2);

        //2.连接到具体的数据库
        Connection conn = DriverManager.getConnection(url,user,password);
        System.out.println(conn);
    }
    /*
    * 使用驱动管理器类来连接数据库(优化)
    * */
    @Test
    public void test3() throws Exception{

        /*
        * 因为com.mysql.jdbc.Driver这个类的源代码中有个静态代码快，其中执行的就是DriverManager.registerDriver(driver);
        * 所以没必要再写一遍注册，只需要把com.mysql.jdbc.Driver这个类加载类也就是加载字节码文件的时候静态代码快就执行了
        * */
        Class.forName("com.mysql.jdbc.Driver");

        //2.连接到具体的数据库
        Connection conn = DriverManager.getConnection(url,user,password);
        System.out.println(conn);
    }

}
