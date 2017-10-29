package company.whoami.auto;

import org.junit.Test;

/**
 * Created by reyren on 2017/6/27.
 */
public class App {

    // 保存员工
    @Test
    public void testSave() throws Exception {
        // 模拟数据
        Dept d = new Dept();
        d.setDeptName("应用开发部");
        Employee emp = new Employee();
        emp.setEmpName("李俊杰");
        emp.setDept(d);   // 关联

        // 调用dao保存
        EmpDao empDao = new EmpDao();
        empDao.save(emp);

    }
}
