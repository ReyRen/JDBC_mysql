package company.whoami.BaseDao;

/**
 * @Author Yuan Ren.
 * @Description
 * @Date 2017/6/29 下午4:41
 */
public class Admin {

    private int id;
    private String userName;
    private String pwd;



    public String getPwd() {
        return pwd;
    }
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    @Override
    public String toString() {
        return "Admin [id=" + id + ", pwd=" + pwd + ", userName=" + userName
                + "]";
    }
}
