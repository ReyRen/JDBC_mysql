package company.whoami.BaseDao;

import org.junit.Test;

import java.util.List;

/**
 * @Author Yuan Ren.
 * @Description
 * @Date 2017/6/29 下午5:10
 */
public class AdminDaoTest {

    @Test
    public void testUpdate() throws Exception {
        AdminDao adminDao = new AdminDao();
        //adminDao.delete(2);
        //adminDao.save(new Admin());

        // 测试查询
        List<Admin> list = adminDao.getAll();
        System.out.println(list.get(0));
    }
}
