package com.seiii.collect.controller.report;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.seiii.collect.model.dto.flaw.FlawDTO;
import com.seiii.collect.model.dto.report.ReportDTO;
import com.seiii.collect.model.vo.flaw.FlawVO;
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
public class ReportControllerTest {
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
    public void addReport1() throws Exception {
        Integer taskId = 4;
        Integer userId = 3;
        String title = "title";

        List<FlawDTO> flaws = new ArrayList<>();

        List<String> flaw1_pictureURLs = new ArrayList<>();
        String flaw1_pictureURL1 = "flaw1PictureURL1";
        String flaw1_pictureURL2 = "flaw1PictureURL2";
        flaw1_pictureURLs.add(flaw1_pictureURL1);
        flaw1_pictureURLs.add(flaw1_pictureURL2);
        String flaw1_description = "flaw1Description";
        String flaw1_stepDes = "flaw1StepDes";
        String flaw1_deviceInfo = "flaw1DeviceInfo";
        FlawDTO flaw1 = new FlawDTO(flaw1_pictureURLs, flaw1_description, flaw1_stepDes, flaw1_deviceInfo);

        List<String> flaw2_pictureURLs = new ArrayList<>();
        String flaw2_pictureURL1 = "flaw2PictureURL1";
        String flaw2_pictureURL2 = "flaw2PictureURL2";
        String flaw2_pictureURL3 = "flaw2PictureURL3";
        flaw2_pictureURLs.add(flaw2_pictureURL1);
        flaw2_pictureURLs.add(flaw2_pictureURL2);
        flaw2_pictureURLs.add(flaw2_pictureURL3);
        String flaw2_description = "flaw2Description";
        String flaw2_stepDes = "flaw2StepDes";
        String flaw2_deviceInfo = "flaw2DeviceInfo";
        FlawDTO flaw2 = new FlawDTO(flaw2_pictureURLs, flaw2_description, flaw2_stepDes, flaw2_deviceInfo);

        List<String> flaw3_pictureURLs = new ArrayList<>();
        String flaw3_pictureURL1 = "flaw3PictureURL1";
        flaw3_pictureURLs.add(flaw3_pictureURL1);
        String flaw3_description = "flaw3Description";
        String flaw3_stepDes = "flaw3StepDes";
        String flaw3_deviceInfo = "flaw3DeviceInfo";
        FlawDTO flaw3 = new FlawDTO(flaw3_pictureURLs, flaw3_description, flaw3_stepDes, flaw3_deviceInfo);

        flaws.add(flaw1);
        flaws.add(flaw2);
        flaws.add(flaw3);

        ReportDTO reportDTO = new ReportDTO(taskId, userId, title, flaws);

        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(reportDTO);
        mvc.perform(MockMvcRequestBuilders
                        .post("/api/report/issue")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(requestJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Transactional
    @Rollback
    @Test
    public void addReport2() throws Exception {
        Integer taskId = 4;
        Integer userId = 3;
        String title = "title";
        List<FlawDTO> flaws = new ArrayList<>();
        ReportDTO reportDTO = new ReportDTO(taskId, userId, title, flaws);

        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(reportDTO);
        mvc.perform(MockMvcRequestBuilders
                        .post("/api/report/issue")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(requestJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Transactional
    @Rollback
    @Test
    public void viewReport1() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/api/report/detail/{reportId}", 1)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Transactional
    @Rollback
    @Test
    public void viewReport2() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/api/report/detail/{reportId}", 2)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Transactional
    @Rollback
    @Test
    public void getToBeRefinedFlawLists() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/api/report/toBeRefined")
                        .param("reportId","1")
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
