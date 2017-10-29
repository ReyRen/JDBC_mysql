package company.whoami.batch;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by reyren on 2017/6/27.
 */
public class App {
    // 测试批处理操作
    @Test
    public void testBatch() throws Exception {

        // 模拟数据
        List<Admin> list = new ArrayList<Admin>();
        for (int i=1; i<21; i++) {
            Admin admin = new Admin();
            admin.setUserName("Jack" + i);
            admin.setPwd("888" + i);
            list.add(admin);
        }

        // 保存
        AdminDao dao = new AdminDao();
        dao.save(list);
    }
}
