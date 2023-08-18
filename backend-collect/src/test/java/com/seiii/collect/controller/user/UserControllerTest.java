package com.seiii.collect.controller.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.seiii.collect.model.dto.user.UserDTO;
import com.seiii.collect.model.dto.user.UserFormDTO;
import io.swagger.models.auth.In;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private WebApplicationContext wac;
    @Resource
    private MockMvc mvc;

    @Before
    public void setupMockMvc() {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build(); //初始化MockMvc对象
    }

    @Transactional
    @Rollback
    @Test
    public void register() throws Exception {

        UserDTO userDTO = new UserDTO(0, "test", "test", "test", "test", new ArrayList<>());

        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(userDTO);
        mvc.perform(MockMvcRequestBuilders
                        .post("/api/user/register")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(requestJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Transactional
    @Rollback
    @Test
    public void login() throws Exception {

        UserFormDTO userFormDTO = new UserFormDTO("test", "test");

        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(userFormDTO);
        mvc.perform(MockMvcRequestBuilders
                        .post("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(requestJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Transactional
    @Rollback
    @Test
    public void getUserInfo() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/api/user/info/{userId}", 1)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Transactional
    @Rollback
    @Test
    public void getUserInfoList() throws Exception {

        List<Integer> ids = new ArrayList<>();
        ids.add(3);
        ids.add(5);

        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(ids);

        mvc.perform(MockMvcRequestBuilders
                        .post("/api/user/info/list")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(requestJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Transactional
    @Rollback
    @Test
    public void viewUser1() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/api/user/view/{userId}", 1)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Transactional
    @Rollback
    @Test
    public void viewUser2() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/api/user/view/{userId}", 2)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Transactional
    @Rollback
    @Test
    public void viewFinishedTasks() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/api/user/task/finished/{userId}", 2)
                        .param("pageId", "1")
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Transactional
    @Rollback
    @Test
    public void viewUnFinishedTasks() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/api/user/task/unfinished/{userId}", 2)
                        .param("pageId", "1")
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Transactional
    @Rollback
    @Test
    public void viewExpiredTasks() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/api/user/task/expired/{userId}", 2)
                        .param("pageId", "1")
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Transactional
    @Rollback
    @Test
    public void viewReportsFromTask() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/api/user/task/report/{userId}", 5)
                        .param("taskId", "8")
                        .param("pageId", "1")
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Transactional
    @Rollback
    @Test
    public void viewAllReports() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/api/user/report/{userId}", 2)
                        .param("pageId", "1")
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    //用户查看对某个任务的报告
    @Transactional
    @Rollback
    @Test
    public void viewTaskReport() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/api/user/workerreport/{userId}", 3)
                        .param("taskId", "8")
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    //用户添加用户标签
    @Transactional
    @Rollback
    @Test
    public void addUserTag() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .post("/api/user/tag/add")
                        .param("userId", "3")
                        .param("tag", "3")
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    //用户删除用户标签
    @Transactional
    @Rollback
    @Test
    public void deleteUserTag() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .delete("/api/user/tag/delete")
                        .param("userId", "3")
                        .param("tag", "2")
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}