package org.manlier.providers;

import org.junit.Assert;
import org.junit.Test;
import org.manlier.beans.User;
import org.manlier.providers.interfaces.IUserService;

import javax.annotation.Resource;

/**
 * Created by manlier on 2017/6/19.
 */
public class UserServiceTest extends BaseServiceTest {

    @Resource
    private IUserService userService;

    private String defaultEmail = "miaomiao@qq.com";

    @Test
    public void testIsUserExist() {
        boolean exist = userService.isAccountExists(defaultEmail);
        Assert.assertTrue(exist);
    }
}
