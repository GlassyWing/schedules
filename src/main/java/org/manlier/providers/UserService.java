package org.manlier.providers;

import org.manlier.beans.User;
import org.manlier.models.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户服务
 * Created by manlier on 2017/6/6.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
@Transactional
public class UserService implements org.manlier.providers.interfaces.IUserService {

    private UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * 添加用户
     *
     * @param user 用户
     * @return 添加结果
     */
    @Override
    public boolean addUser(User user) {
        return userDao.addUser(user) == 1;
    }

    /**
     * 通过账号和密码查找用户
     *
     * @param email 邮箱账号
     * @param password 密码
     * @return 用户
     */
    @Override
    public User findUserByAccountAndPassword(String email, String password) {
        return userDao.findUserByAccountAndPassword(email, password);
    }

    /**
     * 删除用户
     *
     * @param userId 用户id
     * @return 删除结果
     */
    @Override
    public boolean deleteUser(String userId) {
        return userDao.deleteUser(userId) == 1;
    }

    /**
     * 更新用户
     *
     * @param user 用户
     * @return 更新结果
     */
    @Override
    public boolean updateUser(User user) {
        return userDao.updateUser(user) == 1;
    }

    /**
     * 判断账号是否存在
     *
     * @param email 邮箱
     * @return 存在时为true，否则为false
     */
    @Override
    public boolean isAccountExists(String email) {
        return userDao.findUserByAccount(email) != null;
    }

    /**
     * 判断当前用户是否存在
     *
     * @param user 用户
     * @return 若存在为true，否则为false
     */
    @Override
    public boolean isUserExists(User user) {
        return userDao.findUserByAccountAndPassword(user.getEmail(), user.getPassword()) != null;
    }
}
