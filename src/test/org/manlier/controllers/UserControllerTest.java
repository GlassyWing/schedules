package org.manlier.controllers;

import org.junit.Before;
import org.junit.Test;
import org.manlier.TestUtil;
import org.manlier.beans.Preferences;
import org.manlier.beans.User;
import org.manlier.providers.interfaces.IPreferencesService;
import org.manlier.providers.interfaces.IUserService;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import java.time.ZoneId;

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
    private IPreferencesService settingsService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {

        Mockito.reset(userService, settingsService);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    /*测试用户登录*/
    @Test
    public void login() throws Exception {
        User user = new User("140f@mail.com", "4020");

        //language=JSON
        String userStr = "{\"username\":\"14fea\",\"password\":\"feaf4901\"}";

        when(userService.isUserExists(any()))
                .thenReturn(true);
        when(userService.isAccountExists(Matchers.any(String.class)))
                .thenReturn(true);
        when(userService.findUserByAccountAndPassword(anyString(), anyString()))
                .thenReturn(user);

        MvcResult mvcResult = mockMvc.perform(
                post("/api/user/login")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(userStr.getBytes()))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.result", is(true)))
                .andReturn();

        System.out.println("Response body: " + mvcResult.getResponse().getContentAsString());
    }

    /*测试用户注册*/
    @Test
    public void testSignUp() throws Exception {
        User newUser = new User("14684029@gmail.com", "affea04");
        Preferences preferences =new Preferences();

        when(userService.addUser(Mockito.any(User.class)))
                .thenReturn(newUser);

        when(settingsService.getPreferencesForUser(anyString()))
                .thenReturn(new Preferences());

        when(settingsService.updatePreferencesForUser(anyString(), any()))
                .thenReturn(preferences);

        mockMvc.perform(
                post("/api/user/signup")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(newUser)))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.result", is(true)))
                .andDo(mvcResult -> {
                    System.out.println("Response body: " + mvcResult.getResponse().getContentAsString());
                });
    }

    /*测试用户注销*/
    @Test
    public void testSignOut() throws Exception {
        User user = new User("140f@mail.com", "4020");

        mockMvc.perform(
                get("/api/user/signout")
        )
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.result", is(false)))
                .andDo(mvcResult -> {
                    System.out.println("Response body: " + mvcResult.getResponse().getContentAsString());
                });
    }

    @Test
    public void testChangePassword() throws Exception {
        User user = new User("140f@mail.com", "4020");

        when(userService.updateUser(Mockito.any(User.class)))
                .thenReturn(user);

        mockMvc.perform(
                post("/api/user/changePassword")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(user))
                .sessionAttr("userId", "nothing")
        )
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.result", is(true)))
                .andDo(mvcResult -> {
                    System.out.println("Response body: " + mvcResult.getResponse().getContentAsString());
                });
    }

    /*测试变更邮箱*/
    @Test
    public void testChangeEmail() throws Exception {
        User user = new User("140f@mail.com", "4020");
        when(userService.updateUser(Mockito.any(User.class)))
                .thenReturn(user);

        mockMvc.perform(
                post("/api/user/email")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(user))
                .sessionAttr("userId", "nothing")
        )
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.result", is(true)))
                .andDo(mvcResult -> {
                    System.out.println("Response body: " + mvcResult.getResponse().getContentAsString());
                });
    }

    @Test
    public void testGetPreferences() throws Exception {

        Preferences preferences = new Preferences();
        preferences.setUserUuid("97177576174256135");
        preferences.setDailyRemindToggle(true);
        preferences.setTimeZone(ZoneId.systemDefault());

        when(settingsService.getPreferencesForUser(anyString()))
                .thenReturn(preferences);

        mockMvc.perform(
                get("/api/user/preferences/settings")
                .sessionAttr("userId", "nothing")
        )
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.result", is(true)))
                .andDo(this::printResult);
    }

    @Test
    public void testUpdatePreferences() throws Exception {

    }

}
