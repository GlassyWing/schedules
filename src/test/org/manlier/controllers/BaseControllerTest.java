package org.manlier.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.manlier.config.TestContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;

/**
 * Created by manlier on 2017/6/8.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class})
@WebAppConfiguration
public class BaseControllerTest {

    @Test
    public void base() {}

    protected void printResult(MvcResult result) throws UnsupportedEncodingException {
        System.out.println("Response body:" + result.getResponse().getContentAsString());
    }
}
