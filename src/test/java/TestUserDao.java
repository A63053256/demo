import com.bean.User;
import com.dao.NoteBook_TypeDao;
import com.dao.UserDao;
import com.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import util.SHA256Util;

public class TestUserDao {

    private UserDao userDao;
    @Test
    public void test(){
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserService  service = (UserService) ac.getBean("userService");
        User user = new User();
        user.setName("1234");
        user.setNickName("qwq");
        user.setPassword("123456");
        user.setRepassword("123456");
        System.out.println(user);
        System.out.println(user.getPassword().equals(user.getRepassword()));
        System.out.println(service.register(user));
    }
    @Test
    public void logintest(){
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserService  service = (UserService) ac.getBean("userService");
        User user = new User();
        user.setName("1234");
        user.setPassword("12356");
        System.out.println(user);
        System.out.println(service.login(user));
    }
    @Test
    public void SHA256(){
        String a = "12345";
        System.out.println(SHA256Util.sha256(a));
    }
    @Test
    public void selecteNodeTypeDao(){
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        NoteBook_TypeDao notedao = (NoteBook_TypeDao) ac.getBean("noteBook_TypeDao");
        System.out.println(notedao.findAll());
    }
}
