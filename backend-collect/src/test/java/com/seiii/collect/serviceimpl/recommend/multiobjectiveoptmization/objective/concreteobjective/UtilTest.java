package com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective;

import com.seiii.collect.model.vo.ResponseVO;
import com.seiii.collect.model.vo.flaw.FlawVO;
import com.seiii.collect.model.vo.report.ReportVO;
import com.seiii.collect.model.vo.report.ReportViewVO;
import com.seiii.collect.model.vo.task.TaskVO;
import com.seiii.collect.service.python.PythonService;
import com.seiii.collect.service.report.ReportService;
import com.seiii.collect.service.task.TaskService;
import com.seiii.collect.service.user.UserService;
import com.seiii.collect.util.Constant;
import net.sf.ehcache.CacheManager;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UtilTest {
    @Resource
    Util util;
    @MockBean
    UserService userService;
    @MockBean
    ReportService reportService;
    @MockBean
    TaskService taskService;

    @Test
    public void testDomainKnowledge1() {
        ReportViewVO reportViewVO1 = new ReportViewVO();
        reportViewVO1.setId(1);
        ReportViewVO reportViewVO2 = new ReportViewVO();
        reportViewVO2.setId(2);

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

        Mockito.when(userService.viewWorkerAllReports(1,1)).thenReturn(new ResponseVO<>(Constant.REQUEST_SUCCESS,"请求成功",Arrays.asList(reportViewVO1,reportViewVO2)));
        Mockito.when(userService.viewWorkerAllReports(1,2)).thenReturn(new ResponseVO<>(Constant.REQUEST_SUCCESS,"请求成功",Collections.emptyList()));
        Mockito.when(reportService.viewReportDetails(1)).thenReturn(new ResponseVO<>(Constant.REQUEST_SUCCESS,"请求成功",report1));
        Mockito.when(reportService.viewReportDetails(2)).thenReturn(new ResponseVO<>(Constant.REQUEST_SUCCESS,"请求成功",report2));
        assertEquals(util.getDomainKnowledge(true,1).data.get(0),"点击");
    }

    @Test
    public void testDomainKnowledge2(){
        Mockito.when(userService.viewWorkerAllReports(1,1)).thenReturn(new ResponseVO<>(Constant.REQUEST_SUCCESS,"请求成功",Collections.emptyList()));
        assertEquals(util.getDomainKnowledge(true,1).data.size(),0);
    }


    @Test
    public void testDomainKnowledge3(){
        TaskVO taskVO = new TaskVO();
        taskVO.setTitle("点击一下");
        taskVO.setDescription("真的点击，点击后有奇迹");
        Mockito.when(taskService.viewTaskDetails(1)).thenReturn(new ResponseVO<>(Constant.REQUEST_SUCCESS,"请求成功",taskVO));
        assertEquals(util.getDomainKnowledge(false,1).data.get(0),"点击");
    }


    @Test
    public void testDomainKnowledge4(){
        Mockito.when(taskService.viewTaskDetails(1)).thenReturn(new ResponseVO<>(Constant.REQUEST_SUCCESS,"请求成功",new TaskVO()));
        assertEquals(util.getDomainKnowledge(false,1).data,null);
    }


    @Test
    public void testCosineSimilarity() {
        List<String> vector1 = Arrays.asList("手机","应用","声音");
        List<String> vector2 = Arrays.asList("手机","应用","声音");
        List<String> vector3 = Arrays.asList("电脑","爬虫","视频");
        assertTrue(Math.abs(util.cosineSimilarity(vector1,vector2)-1.0)<0.01);
        assertEquals(util.cosineSimilarity(vector1,vector3),0.0);
    }
}

