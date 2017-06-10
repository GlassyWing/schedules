package org.manlier.models;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by manlier on 2017/6/4.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-model.xml")
public class DBConnectTest {

    @Autowired
    private ComboPooledDataSource dataSource;

    @Test
    public void testConnect() throws SQLException {
        Connection connection = dataSource.getConnection();
    }
}
