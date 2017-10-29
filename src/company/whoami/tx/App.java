package company.whoami.tx;

import org.junit.Test;

/**
 * Created by reyren on 2017/6/27.
 */
public class App {
    @Test
    public void testname() throws Exception{

        //转账
        AccountDao accountDao = new AccountDao();
        accountDao.trans();
    }
}
