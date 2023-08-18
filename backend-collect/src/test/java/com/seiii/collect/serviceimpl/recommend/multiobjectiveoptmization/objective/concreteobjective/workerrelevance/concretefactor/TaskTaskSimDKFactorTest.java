package com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective.workerrelevance.concretefactor;

import com.seiii.collect.mapper.task.TaskMapper;
import com.seiii.collect.mapper.task.TaskUserMapper;
import com.seiii.collect.model.po.task.TaskUser;
import com.seiii.collect.model.vo.ResponseVO;
import com.seiii.collect.model.vo.task.TaskVO;
import com.seiii.collect.service.task.TaskService;
import com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective.Util;
import com.seiii.collect.util.Constant;
import net.sf.ehcache.CacheManager;
import org.junit.Before;
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
public class TaskTaskSimDKFactorTest {
    @Resource
    private TaskTaskSimDKFactor taskTaskSimDKFactor;
    @Resource
    private Util util;
    @MockBean
    private TaskUserMapper taskUserMapper;
    @MockBean
    private TaskService taskService;


    @Transactional
    @Rollback
    @Test
    public void calculateEmptyHistoryWorker(){
        Mockito.when(taskUserMapper.selectByUserId(1)).thenReturn(Collections.emptyList());
        Mockito.when(taskService.viewTaskDetails(1)).thenReturn(new ResponseVO<>(Constant.REQUEST_SUCCESS,"请求成功",new TaskVO()));
        assertEquals(taskTaskSimDKFactor.calSingleWorkerFactorValues(1,1),0.0);
    }

    @Transactional
    @Rollback
    @Test
    public void calculateTaskTaskSimDK1(){
        TaskVO taskVO1 = new TaskVO();
        taskVO1.setDescription("需要在安卓手机上测试一个游戏，希望能打通到后面的关卡，尤其是屏幕的边角信息。包括游戏画面是否卡顿等等");
        taskVO1.setId(1);
        taskVO1.setTitle("测试安卓游戏");
        TaskVO taskVO2 = new TaskVO();
        taskVO2.setDescription("需要在安卓手机上测试一个游戏，希望能打通到后面的关卡，尤其是屏幕的边角信息。包括游戏声音是否平稳等等");
        taskVO2.setId(2);
        taskVO2.setTitle("测试安卓游戏");
        Mockito.when(taskService.viewTaskDetails(1)).thenReturn(new ResponseVO<>(Constant.REQUEST_SUCCESS,"请求成功",taskVO1));
        Mockito.when(taskService.viewTaskDetails(2)).thenReturn(new ResponseVO<>(Constant.REQUEST_SUCCESS,"请求成功",taskVO2));
        Mockito.when(taskUserMapper.selectByUserId(1)).thenReturn(Arrays.asList(1));
        List<String> temp1 = util.getDomainKnowledge(false,1).data;
        List<String> temp2 = util.getDomainKnowledge(false,2).data;
        assertEquals(taskTaskSimDKFactor.calSingleWorkerFactorValues(1,2),util.cosineSimilarity(temp1,temp2));
    }
}
