package com.seiii.collect.controller.flaw;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.seiii.collect.model.dto.flaw.FlawAppendDTO;
import com.seiii.collect.model.dto.flaw.FlawDTO;
import com.seiii.collect.model.dto.flaw.FlawEvaluationDTO;
import com.seiii.collect.model.dto.flaw.FlawScoreDTO;
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

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class FlawControllerTest {
    @Autowired
    private WebApplicationContext wac;
    @Resource
    private MockMvc mvc;

    @Before
    public void setupMockMvc() {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build(); //初始化MockMvc对象
    }

    //获得当前缺陷所在的缺陷树
    @Transactional
    @Rollback
    @Test
    public void getFlawTree() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/api/flaw/tree/{flawId}", 8)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    //获得当前缺陷的相似缺陷列表
    @Transactional
    @Rollback
    @Test
    public void getSimilarFlaws() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/api/flaw/similarFlaw/{flawId}", 8)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    //获得某用户对该缺陷的评分
    @Transactional
    @Rollback
    @Test
    public void getUserFlawScore() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/api/flaw/myScore/{flawId}", 8)
                        .param("userId", "12")
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    //用户对缺陷进行评分
    @Transactional
    @Rollback
    @Test
    public void scoreFlaw() throws Exception {
        Integer userId = 5;
        Integer flawId = 6;
        Integer score = 3;
        FlawScoreDTO flawScoreDTO = new FlawScoreDTO(userId, flawId, score);

        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(flawScoreDTO);

        mvc.perform(MockMvcRequestBuilders
                        .post("/api/flaw/score")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(requestJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    //获得任务的缺陷图
    @Transactional
    @Rollback
    @Test
    public void getTaskFlawMap() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/api/flaw/flawMap/{taskId}", 8)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Transactional
    @Rollback
    @Test
    public void refineFlaw() throws Exception {

        FlawDTO flawDTO = new FlawDTO(new ArrayList<>(), "test", "test", "test");

        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(flawDTO);

//        mvc.perform(MockMvcRequestBuilders
//                        .post("/api/flaw/refine/{flawId}", 8)
//                        .param("forkedFlawId", "1")
//                        .contentType(MediaType.APPLICATION_JSON_UTF8)
//                        .content(requestJson))
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Transactional
    @Rollback
    @Test
    public void noforkFlaw() throws Exception {

        mvc.perform(MockMvcRequestBuilders
                        .get("/api/flaw/nofork/{flawId}", 2)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Transactional
    @Rollback
    @Test
    public void getAllEvaluations() throws Exception {

        mvc.perform(MockMvcRequestBuilders
                        .get("/api/flaw/evaluations/{flawId}", 5)
                        .param("userId","7")
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Transactional
    @Rollback
    @Test
    public void addEvaluation() throws Exception {

        FlawEvaluationDTO flawEvaluationDTO = new FlawEvaluationDTO(3, "test");

        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(flawEvaluationDTO);

        mvc.perform(MockMvcRequestBuilders
                        .post("/api/flaw/add/evaluation/{flawId}", 1)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(requestJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Transactional
    @Rollback
    @Test
    public void addAppendContent() throws Exception {

        FlawAppendDTO flawEvaluationDTO = new FlawAppendDTO(1, "test");

        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(flawEvaluationDTO);

        mvc.perform(MockMvcRequestBuilders
                        .post("/api/flaw/add/append")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(requestJson)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
