package com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective;

import com.seiii.collect.mapper.task.TaskMapper;
import com.seiii.collect.mapper.task.TaskUserMapper;
import com.seiii.collect.mapper.user.worker.WorkerContextMapper;
import com.seiii.collect.model.po.task.TaskUser;
import com.seiii.collect.model.po.task.taskrecruitstop.TaskRecruitStopRecommendFactor;
import com.seiii.collect.model.po.user.worker.WorkerContext;
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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class WorkerDiversityTest {
    @Resource
    private Util util;
    @MockBean
    private WorkerContextMapper workerContextMapper;
//    @Resource
//    private WorkerDiversity workerDiversity;
    @MockBean
    UserService userService;
    @MockBean
    ReportService reportService;
    @MockBean
    TaskService taskService;

    @Transactional
    @Rollback
    @Test
    void testWorkerDiversity() {
        List<String> descriptions=Arrays.asList("页面没有正常跳转",
                "页面无法打开",
                "无法正常删除评论",
                "发布任务出现闪退",
                "列表底部项不能完全显示",
                "点赞后无变化",
                "登录成功提示信息违背直觉",
                "明文显示密码按键失效",
                "提交报告没有提示信息",
                "“个人中心”页面在电脑设备上显示效果较差",
                "第三方账号授权登录失败",
                "评论搜索不能有效查找",
                "查看正在招募的任务却显示已经结束招募的任务",
                "同一评论重复显示",
                "首页“消息中心”图标一直有小红点提示",
                "“个人账单”页面重复弹出“知情同意”提示",
                "积分兑换后积分余额无变动",
                "注册页面可以手动输入国家和地区",
                "连续点击“积分记录”系统卡死",
                "同一账号多设备登录积分数据不同步",
                "多账号同时兑换仅剩一件的商品均兑换成功",
                "可以对评论既点赞又点踩",
                "已注册账号不能在平板登录",
                "无法正常上传图片",
                "时间排序查看评论按键无效",
                "无法正常加载视频",
                "注册页面实名认证无效",
                "弱网环境不能正常使用",
                "文件下载进度停滞",
                "草稿箱不能保存草稿"
                );
        List<String> steps=Arrays.asList("点击个人中心，页面不发生跳转",
                "点击用户头像，页面跳转到一个无法打开的网页",
                "点击“删除评论”，刷新后评论未被删除",
                "点击“发布任务”，页面跳转后发生闪退",
                "在“查看评论”页面，最底部评论图片和文字显示不完整",
                "对评论点赞后，刷新后点赞个数未发生变化",
                "登录成功后页面发生剧烈抖动进行提示，但用户很容易错认为登录失败",
                "登录页面点击“明文显示密码”图标，密码未能明文显示",
                "报告提交页面点击“提交”按钮后，没有成功或失败信息提示，让用户迷茫",
                "在电脑设备上点击“个人中心”,页面显示扭曲",
                "登录时选择使用“领英”第三方登录，授权后登录失败",
                "在评论搜索中搜索关键词“有道理”，应当至少有3条评论，但没有任何评论显示",
                "在“任务广场”页面选择“正在招募的任务”，显示的任务均为已经结束招募的任务",
                "在id为153246的帖子下，上下滑动查看评论时id为542631的评论重复出现",
                "登录后首页“消息中心”图标有小红点提示且点击查看后返回首页红点未消失",
                "从任意页面进入“个人账单”页面，弹出“知情同意”提示，点击“确定”后，提示消失但在1s内再次弹出，且周而复始",
                "在“积分兑换”页面，选择兑换任一商品并刷新后，积分余额无变化",
                "注册页面地址字段可用下拉框选择国家和地区外，还可手动输入，增加了安全风险",
                "在“个人中心”页面，未跳转时间区间内连续点击“积分记录”系统卡死",
                "手机和电脑同时登录同一账号，手机端通过发布评论增加积分后，刷新电脑端发现积分数据未同步",
                "3个账号同时在“积分兑换”页面选择兑换仅剩一件的商品（id为48657），均提示“兑换成功”",
                "在“查看评论”页面，对任一评论先点赞，再点踩，均成功",
                "在平板登录已注册账号，显示“未知错误登录失败”",
                "发布评论时选择图片上传后，无预览图，发布后也无图片",
                "在“查看评论”页面，选择“时间排序”按键，评论仍未按时间排序",
                "在“查看帖子”页面，视频加载失败并显示“格式解析错误”",
                "在注册页面，姓名和证件号等实名认证字段输入任意内容均可成功注册",
                "在电梯等弱网环境中，其他软件可以正常打开的情况下，打开该软件首页黑屏，不能正常加载",
                "在“查看帖子”页面，点击下载文件图标，出现下载进度但10分钟一直停滞",
                "在“发布帖子”页面，编辑一段内容后选择“保存草稿”，草稿箱查看发现无内容"
                );
        List<List<String>> workerContextsList=Arrays.asList(Arrays.asList("手机","Android","8G","WLAN"),
                Arrays.asList("手机","Android","8G","WLAN"),
                Arrays.asList("手机","Android","4G","WLAN"),
                Arrays.asList("手机","ios","8G","WLAN"),
                Arrays.asList("手机","HarmonyOS","4G","Cellular network"),
                Arrays.asList("电脑","Linux","8G","LAN"),
                Arrays.asList("电脑","Windows","16G","LAN"),
                Arrays.asList("电脑","ios","16G","WLAN"),
                Arrays.asList("平板","ipadOS","4G","Cellular network"),
                Arrays.asList("嵌入设备","Linux","2G","WLAN"));

        int workerIdStart=31;
        int workerNum=10;
        int reportNum=20;
        int flawNum=30;

        List<WorkerContext> workerContexts=new ArrayList<>();
        for(int i=workerIdStart,j=0;i<workerIdStart+workerNum;i++,j++){
            WorkerContext workerContext=new WorkerContext();
            List<String> workerContextString=workerContextsList.get(j);
            workerContext.setWorkerid(i);
            workerContext.setDevicetype(workerContextString.get(0));
            workerContext.setOsinfo(workerContextString.get(1));
            workerContext.setRamsize(workerContextString.get(2));
            workerContext.setNetenv(workerContextString.get(3));
            workerContexts.add(workerContext);
        }

        List<ReportViewVO> reportViewVOs=new ArrayList<>();
        for(int i=1;i<=reportNum;i++){
            ReportViewVO reportViewVO=new ReportViewVO();
            reportViewVO.setId(i);
            reportViewVOs.add(reportViewVO);
        }
//        ReportViewVO reportViewVO1 = new ReportViewVO();
//        reportViewVO1.setId(1);
//        ReportViewVO reportViewVO2 = new ReportViewVO();
//        reportViewVO2.setId(2);

        List<FlawVO> flawVOs=new ArrayList<>();
        for(int i=0;i<flawNum;i++){
            FlawVO flawVO=new FlawVO();
            flawVO.setDescription(descriptions.get(i));
            flawVO.setStepDes(steps.get(i));
            flawVOs.add(flawVO);
        }
//        FlawVO flawVO1 = new FlawVO();
//        FlawVO flawVO2 = new FlawVO();
//        flawVO1.setDescription("主界面中连续快速点击“更改设置”按钮，程序会卡死");
//        flawVO1.setStepDes("首先，进入应用主界面；其次，连续点击右上角的“更改设置”按钮，注意一定要点的飞快。十几次后程序会无法响应，强制退出。");
//        flawVO2.setDescription("内置跳转小程序打不开");
//        flawVO2.setStepDes("在“发现页面”点击左下角“开心一下”，页面发生跳转，不过一直是白屏，无法打开");

        List<ReportVO> reportVOs=new ArrayList<>();
        for(int i=1,j=0;i<=reportNum;i++){
            ReportVO reportVO1=new ReportVO();
            reportVO1.setFlaws(Arrays.asList(flawVOs.get(j),flawVOs.get(j+1)));
            reportVOs.add(reportVO1);
            i++;
            j+=2;
            ReportVO reportVO2=new ReportVO();
            reportVO2.setFlaws(Arrays.asList(flawVOs.get(j++)));
            reportVOs.add(reportVO2);
        }
//        ReportVO report1 = new ReportVO();
//        ReportVO report2 = new ReportVO();
//        report1.setFlaws(Arrays.asList(flawVO1));
//        report2.setFlaws(Arrays.asList(flawVO2));


        for(int i=workerIdStart,j=0;i<workerIdStart+workerNum;i++,j=j+2){
            Mockito.when(userService.viewWorkerAllReports(i,1)).thenReturn(new ResponseVO<>(Constant.REQUEST_SUCCESS,"请求成功",Arrays.asList(reportViewVOs.get(j),reportViewVOs.get(j+1))));
            Mockito.when(userService.viewWorkerAllReports(i,2)).thenReturn(new ResponseVO<>(Constant.REQUEST_FAIL,"请求失败"));
        }
//        Mockito.when(userService.viewWorkerAllReports(1,1)).thenReturn(new ResponseVO<>(Constant.REQUEST_SUCCESS,"请求成功",Arrays.asList(reportViewVO1,reportViewVO2)));
//        Mockito.when(userService.viewWorkerAllReports(1,2)).thenReturn(new ResponseVO<>(Constant.REQUEST_SUCCESS,"请求成功",Collections.emptyList()));

        for(int i=1;i<=reportNum;i=i+2){
            Mockito.when(reportService.viewReportDetails(i)).thenReturn(new ResponseVO<>(Constant.REQUEST_SUCCESS,"请求成功",reportVOs.get(i-1)));
            Mockito.when(reportService.viewReportDetails(i+1)).thenReturn(new ResponseVO<>(Constant.REQUEST_SUCCESS,"请求成功",reportVOs.get(i)));
        }
//        Mockito.when(reportService.viewReportDetails(1)).thenReturn(new ResponseVO<>(Constant.REQUEST_SUCCESS,"请求成功",report1));
//        Mockito.when(reportService.viewReportDetails(2)).thenReturn(new ResponseVO<>(Constant.REQUEST_SUCCESS,"请求成功",report2));

        for(int i=workerIdStart,j=0;i<workerIdStart+workerNum;i++,j++){
            Mockito.when(workerContextMapper.selectByPrimaryKey(i)).thenReturn(workerContexts.get(j));
        }

        WorkerDiversity workerDiversity=new WorkerDiversity(util,workerContextMapper);
        List<Integer> workerIds=new ArrayList<>();
        for(int i=workerIdStart;i<workerIdStart+workerNum;i++){
            workerIds.add(i);
        }

        assert(workerDiversity.calculateValue(workerIds)>0.5);
//        System.out.println(workerDiversity.calculateValue(workerIds));
    }

    @Test
    void fillActual() {
        TaskRecruitStopRecommendFactor factor = new TaskRecruitStopRecommendFactor();
        WorkerDiversity diversity = new WorkerDiversity(null, null);
        diversity.fillActual(factor, 2.0);
        Assertions.assertEquals(factor.getDiversityactual(), 2.0);
    }

    @Test
    void fillExpected() {
        TaskRecruitStopRecommendFactor factor = new TaskRecruitStopRecommendFactor();
        WorkerDiversity diversity = new WorkerDiversity(null, null);
        diversity.fillExpected(factor, 2.0);
        Assertions.assertEquals(factor.getDiversityexpected(), 2.0);
    }
}

