import Beans.PersonInfo;
import com.google.gson.Gson;
import org.junit.Test;

/**
 * Created by Allen on 2015/7/28.
 */
public class TestRegister {

    PersonInfo personInfo;

    @Test
    public void testRegister() {
        personInfo = new PersonInfo();

        personInfo.setPerson_id("510199506060432");
        personInfo.setName("Allen");
        personInfo.setGender(0);
        personInfo.setAge(20);
        personInfo.setPhone("13514234234234");
        personInfo.setVip(0);
        personInfo.setBloodType("A");
        personInfo.setPassword("123123");
        personInfo.setGroup_id(1);

        System.out.println(new Gson().toJson(personInfo));
    }

}
