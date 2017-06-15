package org.manlier.providers.interfaces;

import org.manlier.beans.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by manlier on 2017/6/7.
 */
public interface IUserService {
    User addUser(User user);

    User findUserByAccountAndPassword(String email, String password);

    boolean deleteUser(String userId);

    User updateUser(User user);

    boolean isAccountExists(String email);

    boolean isUserExists(User user);

    User getCurrentUser();

    User getUserById(String userId);
}
