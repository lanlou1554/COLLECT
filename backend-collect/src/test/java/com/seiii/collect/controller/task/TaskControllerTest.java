package com.seiii.collect.controller.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.seiii.collect.model.dto.task.TaskDTO;
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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class TaskControllerTest {
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
    public void addTask() throws Exception {
        Date startTime = new Timestamp(System.currentTimeMillis());
        Integer userId = 2;
        Integer type = 1;
        String title = "title";
        String description = "description";
        Integer workNum = 10;
        String exeFileName = "exeFileName";
        String reqDocName = "reqDocName";
        Date endTime = new Timestamp(System.currentTimeMillis());
        TaskDTO taskDTO = new TaskDTO(userId, type, title, description, startTime, endTime, workNum, exeFileName, reqDocName, new ArrayList<>(),new ArrayList<>());

        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(taskDTO);
        mvc.perform(MockMvcRequestBuilders
                        .post("/api/task/issue")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(requestJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Transactional
    @Rollback
    @Test
    public void viewTask1() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/api/task/detail/{taskId}", 1)
                        .param("userId", "4")
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Transactional
    @Rollback
    @Test
    public void viewTask2() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/api/task/detail/{taskId}", 2)
                        .param("userId", "4")
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Transactional
    @Rollback
    @Test
    public void pickTask() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .post("/api/task/pick/{taskId}", 10)
                        .param("userId", "9")
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Transactional
    @Rollback
    @Test
    public void findTasks() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/api/task/unfinished")
                        .param("pageId", "1")
                        .param("userId", "5")
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Transactional
    @Rollback
    @Test
    public void getReportId() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/api/task/detail/getreport/{taskId}", 8)
                        .param("userId", "5")
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Transactional
    @Rollback
    @Test
    void getCurrentTaskRadar() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/api/task/taskRadar/current/{taskId}", 8)
                .param("userId", "5")
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getTargetTaskRadar() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/api/task/taskRadar/target/{taskId}", 8)
                .param("userId", "5")
        )
                .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
