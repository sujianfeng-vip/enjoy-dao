package cc.twobears.library.fastdao;

import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author SuJianFeng
 * @date 2020/6/3 10:25
 **/
public class TbSelectSqlTest {

    @Test
    public void test(){
        assertThat("a.abc".contains("."), equalTo(true));
        assertThat("addabc".contains("."), equalTo(false));
    }
}
