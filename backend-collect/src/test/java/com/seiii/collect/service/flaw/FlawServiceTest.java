package com.seiii.collect.service.flaw;

import com.seiii.collect.model.dto.flaw.FlawDTO;
import com.seiii.collect.model.dto.flaw.FlawEvaluationDTO;
import com.seiii.collect.model.vo.ResponseVO;
import com.seiii.collect.model.vo.flaw.FlawEvaluationVO;
import com.seiii.collect.model.vo.flaw.FlawMapVO;
import com.seiii.collect.model.vo.flaw.FlawTreeNodeVO;
import com.seiii.collect.model.vo.flaw.SimilarFlawVO;
import com.seiii.collect.util.Constant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class FlawServiceTest {
    @Autowired
    FlawService flawService;

    //获取与该缺陷所在的缺陷树
    @Transactional
    @Rollback
    @Test
    void getFlawTree() {
        ResponseVO<FlawTreeNodeVO> vo = flawService.getFlawTree(3);
        assertEquals(Constant.REQUEST_SUCCESS, vo.getCode());
    }

    //获取缺陷的相似缺陷列表
    @Transactional
    @Rollback
    @Test
    void getSimilarFlaws() {
        ResponseVO<List<SimilarFlawVO>> vo = flawService.getSimilarFlaws(3);
        assertEquals(Constant.REQUEST_SUCCESS, vo.getCode());
    }

    //获取用户对缺陷的评分
    @Transactional
    @Rollback
    @Test
    void getFlawScore() {
        ResponseVO<Integer> vo = flawService.getFlawScore(9, 7);
        assertEquals(Constant.REQUEST_SUCCESS, vo.getCode());
        assertEquals(5, vo.getData());
    }

    //对缺陷进行评分
    @Transactional
    @Rollback
    @Test
    void scoreFlaw() {
        ResponseVO<Object> vo = flawService.scoreFlaw(8, 7, 3);
        assertEquals(Constant.REQUEST_SUCCESS, vo.getCode());
    }

    //获得某个任务的缺陷图
    @Transactional
    @Rollback
    @Test
    void getTaskFlawMap() {
        ResponseVO<FlawMapVO> vo = flawService.getTaskFlawMap(4);
        assertEquals(Constant.REQUEST_SUCCESS, vo.getCode());
    }

//    @Transactional
//    @Rollback
//    @Test
//    void refineFlaw() {
////        ResponseVO<Object> res = flawService.refineFlaw(7, 4, new FlawDTO(new ArrayList<>(), "test", "test", "test"));
////        assertEquals(Constant.REQUEST_SUCCESS, res.getCode());
//    }

    @Transactional
    @Rollback
    @Test
    void noforkFlaw() {
        ResponseVO<Object> res = flawService.noforkFlaw(2);
        assertEquals(Constant.REQUEST_SUCCESS, res.getCode());
    }

    @Transactional
    @Rollback
    @Test
    void getAllEvaluations(){
        ResponseVO<List<FlawEvaluationVO>> res = flawService.getAllEvaluations(5,7);
        assertEquals(Constant.REQUEST_SUCCESS,res.getCode());
    }

    @Transactional
    @Rollback
    @Test
    void addEvaluation(){
        FlawEvaluationDTO flawEvaluationDTO = new FlawEvaluationDTO(3,"test");
        ResponseVO<List<FlawEvaluationVO>> res = flawService.addEvaluation(1, flawEvaluationDTO);
        assertEquals(Constant.REQUEST_SUCCESS,res.getCode());
        assertNotNull(res.getData());
    }

    @Transactional
    @Rollback
    @Test
    void addAppendContent(){
        ResponseVO<Object> res = flawService.addAppendContent(1, "test");
        assertEquals(Constant.REQUEST_SUCCESS,res.getCode());
    }

}
