package com.seiii.collect.service.admin;

import com.seiii.collect.model.dto.recommend.RecommendRuleDTO;
import com.seiii.collect.model.vo.ResponseVO;
import com.seiii.collect.model.vo.recommend.RecommendRuleFactorVO;
import com.seiii.collect.model.vo.recommend.RecommendRuleVO;
import com.seiii.collect.model.vo.task.TaskVO;
import com.seiii.collect.model.vo.task.TaskViewVO;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class AdminServiceTest {
    @Autowired
    AdminService adminService;

    @Transactional
    @Rollback
    @Test
    void getAllTasks() {
        ResponseVO<List<TaskViewVO>> vos = adminService.getAllTasks(1);
        assertEquals(Constant.REQUEST_SUCCESS, vos.getCode());
//        assertEquals(3, vos.getData().size());
        vos = adminService.getAllTasks(2000);
        assertEquals(Constant.REQUEST_SUCCESS, vos.getCode());
        assertEquals(0, vos.getData().size());
    }

    @Transactional
    @Rollback
    @Test
    void viewTaskDetails() {
        ResponseVO<TaskVO> res = adminService.viewTaskDetails(8);
        assertEquals(Constant.REQUEST_SUCCESS, res.getCode());
        assertNotNull(res.getData());
    }

    //管理员获取推荐规则列表
    @Transactional
    @Rollback
    @Test
    void getAllRecommendRules() {
        ResponseVO<List<RecommendRuleVO>> vos = adminService.getRecommendRules();
        assertEquals(Constant.REQUEST_SUCCESS, vos.getCode());
        assertEquals(1, vos.getData().size());
    }

    //管理员获取推荐因子列表
    @Transactional
    @Rollback
    @Test
    void getAllRecommendFactors() {
        ResponseVO<List<RecommendRuleFactorVO>> vos = adminService.getRecommendRuleFactors();
        assertEquals(Constant.REQUEST_SUCCESS, vos.getCode());
        List<RecommendRuleFactorVO> recommendRuleFactorVOs=new ArrayList<>();
        recommendRuleFactorVOs.add(new RecommendRuleFactorVO(Constant.RECOMMEND_FACTORS.get(0),0.0));
        recommendRuleFactorVOs.add(new RecommendRuleFactorVO(Constant.RECOMMEND_FACTORS.get(1),0.0));
        recommendRuleFactorVOs.add(new RecommendRuleFactorVO(Constant.RECOMMEND_FACTORS.get(2),0.0));
        assertEquals(recommendRuleFactorVOs, vos.getData());
    }

    //管理员删除推荐规则
    @Transactional
    @Rollback
    @Test
    void deleteRecommendRule() {
        ResponseVO<Object> vos = adminService.deleteRecommendRule(2);
        assertEquals(Constant.REQUEST_SUCCESS, vos.getCode());
    }

    //管理员添加推荐规则
    @Transactional
    @Rollback
    @Test
    void addRecommendRule() {
        RecommendRuleFactorVO recommendRuleFactorVO1=new RecommendRuleFactorVO("任务紧迫度",0.3);
        RecommendRuleFactorVO recommendRuleFactorVO2=new RecommendRuleFactorVO("任务相似度",0.4);
        RecommendRuleFactorVO recommendRuleFactorVO3=new RecommendRuleFactorVO("用户相似度",0.2);
        List<RecommendRuleFactorVO> factors=new ArrayList<>();
        factors.add(recommendRuleFactorVO1);
        factors.add(recommendRuleFactorVO2);
        factors.add(recommendRuleFactorVO3);
        RecommendRuleDTO recommendRuleDTO=new RecommendRuleDTO("测试添加规则",factors);
        ResponseVO<List<RecommendRuleVO>> vos = adminService.addRecommendRule(recommendRuleDTO);
        assertEquals(Constant.REQUEST_SUCCESS, vos.getCode());
        assertEquals(2,vos.getData().size());
    }

    //管理员启用推荐规则
    @Transactional
    @Rollback
    @Test
    void selectRecommendRule() {
        ResponseVO<Object> vos = adminService.selectRecommendRule(1);
        assertEquals(Constant.REQUEST_SUCCESS, vos.getCode());
    }
}
