package company.whoami.pool;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * @Author Yuan Ren.
 * @Description
 * 自定义连接池, 管理连接
 * 代码实现：
        1.  MyPool.java  连接池类，
        2.  指定全局参数：  初始化数目、最大连接数、当前连接、   连接池集合
        3.  构造函数：循环创建3个连接
        4.  写一个创建连接的方法
        5.  获取连接
------>  判断： 池中有连接， 直接拿
------>                池中没有连接，
------>                 判断，是否达到最大连接数； 达到，抛出异常；没有达到最大连接数，
创建新的连接
6. 释放连接
------->  连接放回集合中(..)
 *
 *
 * @Date 2017/6/30 下午5:32
 */
public class MyPool {
    private int init_count = 3;//初始化链接的数目
    private int max_count = 6;//最大链接数
    private int current_count = 0;//记录当前使用的连接数
    private LinkedList<Connection> pool = new LinkedList<Connection>();//用于存放所有的初始化链接

    //1.构造函数中初始化链接，放入连接池
    public MyPool() {
        //初始化链接
        for (int i = 0; i < init_count;i++){
            //记录当前链接数目
            current_count++;
            //创建原始的链接对象
            Connection con = createConnection();



            pool.addLast(con);//把链接加入连接池,放的就是代理对象
        }
    }

    //2.创建一个新的链接的方法
    public Connection createConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //原始的目标对象
            final Connection con =  DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc_demo","root","renyuan");
            /************对con对象代理**************/
            //对con创建代理对象,这样可以在不实现接口的情况，对接口的方法进行扩展，添加额外的用户需要的业务逻辑
            Connection proxy = (Connection) Proxy.newProxyInstance(
                    con.getClass().getClassLoader(),//类加载器
                    //con.getClass().getInterfaces(),//指定目标对象实现的接口（如果是对象的话，就是能通过这个获取这个对象的所有接口）
                    new Class[]{Connection.class},  //如果代理的是接口就这样写
                    new InvocationHandler() {       //当调用con对象方法的时候，会自动触发事件处理器(也就是这个)
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            //方法返回值
                            Object result = null;
                            //当前执行的方法的方法名
                            String methodName = method.getName();
                            //判断当执行了close方法的时候，就链接放入连接池
                            if ("close".equals(methodName)){
                                System.out.println("当前执行close方法开始");
                                //链接放入连接池
                                pool.addLast(con);
                                System.out.println("当前链接已经放入链接池了");

                            }else {//其他方法不需要检测
                                //调用目标对象方法
                                result = method.invoke(con,args);
                            }


                            return result;
                        }
                    }
            );

            return proxy;//这样创建每个对象都是代理对象了
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //3.获取链接
    public Connection getConnection(){
        //3.1判断链接池中是否有链接，如果有链接就直接从链接池取出
        if (pool.size() > 0){
            return pool.removeFirst();//删了第一个并且返回第一个
        }
        //3.2链接池中没有链接，判断如果没有拿到最大连接数，就创建链接
        if (current_count < max_count){
            //记录当前使用的连接数
            current_count++;
            //创建链接
            return createConnection();
        }

        //3.3如果当前已经拿到最大链接数，抛出异常
        throw new RuntimeException("当前链接已经达到最大连接数");
    }

    //4.释放链接
    public void realeaseConnect(Connection con){
        //4.1判断池的数目如果小于初始化连接数，则放入池中
        if (pool.size() < init_count){
            pool.add(con);
        }else {
            //4.2如果池的数目等于最大链接
            try {
                current_count--;
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public static void main(String[] args) {
        MyPool pool = new MyPool();
        System.out.println("当前链接:" + pool.current_count);//3
        System.out.println("链接池中剩余链接:" + pool.pool.size());//3

        //使用链接
        pool.getConnection();
        System.out.println("当前链接:" + pool.current_count);//3,因为是从池中取的
        System.out.println("链接池中剩余链接:" + pool.pool.size());//2
        pool.getConnection();
        pool.getConnection();
        System.out.println("链接池中剩余链接:" + pool.pool.size());//0
        pool.getConnection();
        System.out.println("当前链接:" + pool.current_count);//4
        pool.getConnection();
        Connection con1 = pool.getConnection();
        System.out.println("当前链接:" + pool.current_count);//6
        //释放链接，链接放回连接池中
        //pool.realeaseConnect(con1);
        //使用代理对象
        try {
            con1.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("链接池中剩余链接:" + pool.pool.size());
        //pool.getConnection();//Exception in thread "main" java.lang.RuntimeException: 当前链接已经达到最大连接数
    }

}
