package org.manlier.models;

import org.apache.ibatis.annotations.Param;
import org.manlier.beans.User;

import java.util.List;

/**
 * Created by manlier on 2017/6/4.
 */
public interface UserDao {

    /**
     * 添加用户
     *
     * @param user 用户
     * @return 添加的数量
     */
    int addUser(@Param("user") User user);

    /**
     * 通过uuid来获取用户
     *
     * @param userId uuid
     * @return 用户对象
     */
    User getUserByUserId(@Param("userId") String userId);

    /**
     * 更新用户
     *
     * @param user 用户
     * @return 更新数量
     */
    int updateUser(@Param("user") User user);

    /**
     * 删除用户
     *
     * @param useId 用户id
     * @return 删除数量
     */
    int deleteUser(@Param("userId") String useId);

    /**
     * 查找数据库中的用户
     *
     * @param offset
     * @param size
     * @return
     */
    List<User> selectUsersFrom(@Param("offset") int offset, @Param("size") int size);

    /**
     * 通过账号查找用户
     *
     * @param email Email
     * @return 用户
     */
    User findUserByAccount(@Param("email") String email);

    /**
     * 通过账号和密码查找用户
     *
     * @param email 邮箱
     * @param password 密码
     * @return 用户
     */
    User findUserByAccountAndPassword(@Param("email") String email, @Param("password") String password);
}
