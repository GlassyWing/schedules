package org.manlier.providers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by manlier on 2017/6/6.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-providers.xml"
        , "classpath:spring/spring-model.xml"
        , "classpath:spring/spring-quartz-config.xml"})
public class BaseServiceTest {

    @Test
    public void init() {}
}
