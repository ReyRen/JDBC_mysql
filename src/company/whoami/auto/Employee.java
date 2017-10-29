package company.whoami.auto;

/**
 * Created by reyren on 2017/6/27.
 */
public class Employee {

    private int empId;
    private String empName;
    // 关联的部门
    private Dept dept;


    public Dept getDept() {
        return dept;
    }
    public void setDept(Dept dept) {
        this.dept = dept;
    }
    public int getEmpId() {
        return empId;
    }
    public void setEmpId(int empId) {
        this.empId = empId;
    }
    public String getEmpName() {
        return empName;
    }
    public void setEmpName(String empName) {
        this.empName = empName;
    }




}
