package org.manlier.models;

import org.junit.Assert;
import org.junit.Test;
import org.manlier.beans.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by manlier on 2017/6/4.
 */
public class UserModelTest extends DBConnectTest {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private UserDao userDao;

    @Test
    public void testAddUser() {
        User user = new User("1490224913@qq.com", "afeeafzvvn");
        userDao.addUser(user);
        logger.info("generated uuid: " + user.getUserUuid());
    }

    @Test
    public void testGetUser() {
        User user = new User("1490224913@qq.com", "afeeafzvvn");
        userDao.addUser(user);
        user = userDao.getUserByUserId(user.getUserUuid());
        System.out.println(user);
    }

    @Test
    public void testSelectUser() {
        List<User> users = userDao.selectUsersFrom(0, 2);
        System.out.println(users);
    }

    @Test
    public void updateUser() {
        User user = userDao.getUserByUserId("97147179147198488");
        user.setEmail("21feaafeaf11fea@gmail.com");
        user.setNickname("Manlier");
        user.setPassword("9ffcafe84afacz");
        int affected = userDao.updateUser(user);
        Assert.assertEquals(1, affected);
    }

    @Test
    public void deleteUser() {
        int affected = userDao.deleteUser("97147179147198487");
        Assert.assertEquals(1, affected);
    }

    @Test
    public void findUser() {
        String email = "fjeiao;@femca;.ca";
        String password = "feacceafea";
        User user = userDao.findUserByAccount(email);
        Assert.assertNull(user);

        email = "134105810@qq.com";
        user = userDao.findUserByAccount(email);
        Assert.assertNotNull(user);

        user = userDao.findUserByAccountAndPassword(email, password);
        Assert.assertNull(user);

        password = "14ff3afe";
        user = userDao.findUserByAccountAndPassword(email, password);
        Assert.assertNotNull(user);
    }

}
