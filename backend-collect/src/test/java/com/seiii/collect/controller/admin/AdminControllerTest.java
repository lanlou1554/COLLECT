package com.seiii.collect.controller.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.seiii.collect.model.dto.recommend.RecommendRuleDTO;
import com.seiii.collect.model.dto.task.TaskDTO;
import com.seiii.collect.model.vo.recommend.RecommendRuleFactorVO;
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
import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class AdminControllerTest {
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
    public void viewTask1() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/api/admin/task/detail/{taskId}", 1)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Transactional
    @Rollback
    @Test
    public void viewTask2() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/api/admin/task/detail/{taskId}", 2)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Transactional
    @Rollback
    @Test
    public void findTasks() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/api/admin/task/all")
                        .param("pageId", "1")
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    //管理员查看推荐规则列表
    @Transactional
    @Rollback
    @Test
    public void viewAllRecommendRules() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/api/admin/recommendRule/list"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    //管理员查看可选的推荐影响因素列表
    @Transactional
    @Rollback
    @Test
    public void viewAllRecommendRuleFactors() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/api/admin/priorityFactor/list"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    //管理员删除推荐规则
    @Transactional
    @Rollback
    @Test
    public void deleteRecommendRule() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .delete("/api/admin/recommendRule/delete/{ruleId}",1))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    //管理员添加推荐规则
    @Transactional
    @Rollback
    @Test
    public void addRecommendRule() throws Exception {
        String name="推荐规则2";
        RecommendRuleFactorVO factor1=new RecommendRuleFactorVO("任务紧迫度",0.2);
        RecommendRuleFactorVO factor2=new RecommendRuleFactorVO("任务相似度",0.3);
        RecommendRuleFactorVO factor3=new RecommendRuleFactorVO("用户相似度",0.5);
        List<RecommendRuleFactorVO> factors=new ArrayList<>();
        factors.add(factor1);
        factors.add(factor2);
        factors.add(factor3);
        RecommendRuleDTO recommendRuleDTO=new RecommendRuleDTO(name,factors);

        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(recommendRuleDTO);
        mvc.perform(MockMvcRequestBuilders
                .post("/api/admin/recommendRule/add")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    //管理员启用推荐规则
    @Transactional
    @Rollback
    @Test
    public void selectRecommendRule() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .post("/api/admin/recommendRule/select/{ruleId}",1))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
