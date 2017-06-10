package org.manlier.controllers;

import org.junit.Before;
import org.junit.Test;
import org.manlier.TestUtil;
import org.manlier.beans.User;
import org.manlier.dto.base.BaseResult;
import org.manlier.providers.interfaces.IUserService;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.hamcrest.Matchers.*;

import static org.mockito.Mockito.*;


/**
 * Created by manlier on 2017/6/8.
 */
public class UserControllerTest extends BaseControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private IUserService userService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {

        Mockito.reset(userService);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void login() throws Exception {
        User user = new User("140f@mail.com", "4020");

        MvcResult mvcResult = mockMvc.perform(
                post("/user/login")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(user)))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.result", is(true)))
                .andReturn();

        System.out.println("Response body: " + mvcResult.getResponse().getContentAsString());

        verifyZeroInteractions(userService);
    }

    @Test
    public void signOut() throws Exception {
        User user = new User("140f@mail.com", "4020");
        mockMvc.perform(
                post("/user/login")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(user))
        );
        mockMvc.perform(
                get("/user/signout/140f@mail.com")
        )
                .andExpect(status().is(200));

        verifyZeroInteractions(userService);
    }

}
