package com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective.workerrelevance.concretefactor;

import com.seiii.collect.mapper.task.TaskMapper;
import com.seiii.collect.mapper.task.TaskUserMapper;
import com.seiii.collect.model.po.task.TaskUser;
import com.seiii.collect.model.vo.ResponseVO;
import com.seiii.collect.model.vo.flaw.FlawVO;
import com.seiii.collect.model.vo.report.ReportVO;
import com.seiii.collect.model.vo.report.ReportViewVO;
import com.seiii.collect.model.vo.task.TaskVO;
import com.seiii.collect.service.report.ReportService;
import com.seiii.collect.service.task.TaskService;
import com.seiii.collect.service.user.UserService;
import com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective.Util;
import com.seiii.collect.util.Constant;
import io.swagger.models.auth.In;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WorkerWorkerSimDKFactorTest {
    @Resource
    private WorkerWorkerSimDKFactor workerWorkerSimDKFactor;
    @Resource
    private Util util;
    @MockBean
    private TaskUserMapper taskUserMapper;
    @MockBean
    private TaskService taskService;
    @MockBean
    private UserService userService;
    @MockBean
    private ReportService reportService;

    private Integer currentReportId = 1;


    private void initWorker(Integer id){
        ReportViewVO reportViewVO1 = new ReportViewVO();
        reportViewVO1.setId(currentReportId);
        ReportViewVO reportViewVO2 = new ReportViewVO();
        reportViewVO2.setId(currentReportId+1);

        ReportVO report1 = new ReportVO();
        ReportVO report2 = new ReportVO();
        FlawVO flawVO1 = new FlawVO();
        FlawVO flawVO2 = new FlawVO();
        report1.setFlaws(Arrays.asList(flawVO1));
        report2.setFlaws(Arrays.asList(flawVO2));
        flawVO1.setDescription("主界面中连续快速点击“更改设置”按钮，程序会卡死");
        flawVO1.setStepDes("首先，进入应用主界面；其次，连续点击右上角的“更改设置”按钮，注意一定要点的飞快。十几次后程序会无法响应，强制退出。");
        flawVO2.setDescription("内置跳转小程序打不开");
        flawVO2.setStepDes("在“发现页面”点击左下角“开心一下”，页面发生跳转，不过一直是白屏，无法打开");

        Mockito.when(userService.viewWorkerAllReports(id,1)).thenReturn(new ResponseVO<>(Constant.REQUEST_SUCCESS,"请求成功",Arrays.asList(reportViewVO1,reportViewVO2)));
        Mockito.when(userService.viewWorkerAllReports(id,2)).thenReturn(new ResponseVO<>(Constant.REQUEST_SUCCESS,"请求成功",Collections.emptyList()));
        Mockito.when(reportService.viewReportDetails(currentReportId)).thenReturn(new ResponseVO<>(Constant.REQUEST_SUCCESS,"请求成功",report1));
        Mockito.when(reportService.viewReportDetails(currentReportId+1)).thenReturn(new ResponseVO<>(Constant.REQUEST_SUCCESS,"请求成功",report2));
        currentReportId += 2;
    }

    @Transactional
    @Rollback
    @Test
    public void calculateEmptyHistoryWorker1(){
        Mockito.when(taskUserMapper.selectByTaskId(1)).thenReturn(Collections.emptyList());
        initWorker(1);
        assertEquals(workerWorkerSimDKFactor.calSingleWorkerFactorValues(1,1),0.0);
    }

    @Transactional
    @Rollback
    @Test
    public void calculateEmptyHistoryWorker2(){
        initWorker(2);
        Mockito.when(taskUserMapper.selectByTaskId(1)).thenReturn(Arrays.asList(2));
        Mockito.when(userService.viewWorkerAllReports(1,1)).thenReturn(new ResponseVO<>(Constant.REQUEST_SUCCESS,"请求成功",Collections.emptyList()));
        assertEquals(workerWorkerSimDKFactor.calSingleWorkerFactorValues(1,1),0.0);
    }

    @Transactional
    @Rollback
    @Test
    public void calculateWorkerWorkerSimDK1(){
        initWorker(3);
        initWorker(4);
        Mockito.when(taskUserMapper.selectByTaskId(1)).thenReturn(Arrays.asList(3));
        Assertions.assertTrue(Math.abs(workerWorkerSimDKFactor.calSingleWorkerFactorValues(4, 1)-1.0)<0.01);

    }
}

