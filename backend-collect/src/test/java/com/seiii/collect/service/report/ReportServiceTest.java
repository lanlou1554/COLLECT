package com.seiii.collect.service.report;

import com.seiii.collect.model.dto.flaw.FlawDTO;
import com.seiii.collect.model.dto.report.ReportDTO;
import com.seiii.collect.model.vo.ResponseVO;
import com.seiii.collect.model.vo.flaw.FlawVO;
import com.seiii.collect.model.vo.flaw.TBAFlawVO;
import com.seiii.collect.model.vo.report.ReportVO;
import com.seiii.collect.util.Constant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ReportServiceTest {
    @Autowired
    ReportService reportService;

    @Transactional
    @Rollback
    @Test
    void viewReportDetails() {
        ResponseVO<ReportVO> res = reportService.viewReportDetails(1);
        assertEquals(Constant.REQUEST_SUCCESS, res.getCode());
        assertNotNull(res.getData());
    }

    @Transactional
    @Rollback
    @Test
    void createReport() {

        Integer taskId = 4;
        Integer userId = 9;
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

        ResponseVO<ReportVO> res = reportService.createReport(reportDTO);

        assertEquals(Constant.REQUEST_SUCCESS, res.getCode());
        assertNotNull(res.getData());
        assertEquals(taskId, res.getData().getTaskId());
        assertEquals(userId, res.getData().getUserId());
        assertEquals(title, res.getData().getTitle());
        assertNotNull(res.getData().getFlaws());
        List<FlawVO> res_flaws = res.getData().getFlaws();
        assertEquals(3, res_flaws.size());
        assertTrue(compareFlawVO(flaw1, res_flaws.get(0)));
        assertTrue(compareFlawVO(flaw2, res_flaws.get(1)));
        assertTrue(compareFlawVO(flaw3, res_flaws.get(2)));
    }

    boolean compareFlawVO(FlawDTO flawVO1, FlawVO flawVO2) {
        return Objects.equals(flawVO1.getDescription(), flawVO2.getDescription())
                && Objects.equals(flawVO1.getDeviceInfo(), flawVO2.getDeviceInfo())
                && Objects.equals(flawVO1.getStepDes(), flawVO2.getStepDes())
                && Objects.equals(flawVO1.getPictureURLs(), flawVO2.getPictureURLs());
    }

    @Transactional
    @Rollback
    @Test
    void getToBeRefinedFlawLists() {
        ResponseVO<List<TBAFlawVO>> res = reportService.getToBeRefinedFlawLists(1);
        assertEquals(Constant.REQUEST_SUCCESS, res.getCode());
        assertNotNull(res.getData());
    }

}
