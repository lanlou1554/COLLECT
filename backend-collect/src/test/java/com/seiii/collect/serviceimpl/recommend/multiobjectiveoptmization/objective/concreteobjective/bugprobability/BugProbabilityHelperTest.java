package com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective.bugprobability;

import com.seiii.collect.mapper.flaw.FlawMapper;
import com.seiii.collect.model.po.flaw.Flaw;
import com.seiii.collect.model.po.flaw.Score;
import com.seiii.collect.model.po.user.worker.WorkerAbility;
import com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective.bugprobability.activeness.ScopeType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class BugProbabilityHelperTest {

    @Transactional
    @Rollback
    @Test
    void getSubmitFlawList() {
        List<Flaw> submitFlawList = BugProbabilityHelper.getSubmitFlawList(3);
        assertNotNull(submitFlawList);
        assertNotEquals(0, submitFlawList.size());
//        assertEquals(6, submitFlawList.size());
    }

    @Transactional
    @Rollback
    @Test
    void getForkingFlawNum() {
        Integer forkedFlawNum = BugProbabilityHelper.getForkingFlawNum(3);
//        assertEquals(2, forkedFlawNum);
    }

    @Transactional
    @Rollback
    @Test
    void getForkingFlawList() {
        List<Flaw> forkingFlawList = BugProbabilityHelper.getForkingFlawList(3);
        assertNotNull(forkingFlawList);
        assertNotEquals(0, forkingFlawList.size());
//        assertEquals(2, forkingFlawList.size());
    }

    @Transactional
    @Rollback
    @Test
    void getRootFlawNum() {
        Integer rootFlawNum = BugProbabilityHelper.getRootFlawNum(3);
//        assertEquals(4, rootFlawNum);
    }

    @Transactional
    @Rollback
    @Test
    void getForkedFlawNum() {
        Integer forkedFlawNum = BugProbabilityHelper.getForkedFlawNum(3);
//        assertEquals(0, forkedFlawNum);
    }

    @Transactional
    @Rollback
    @Test
    void getAllFlawListFromTree() {
        List<Flaw> allFlawListFromTree = BugProbabilityHelper.getAllFlawListFromTree(1);
        assertNotNull(allFlawListFromTree);
        assertNotEquals(0, allFlawListFromTree.size());
//        assertEquals(5, allFlawListFromTree.size());
    }

    @Transactional
    @Rollback
    @Test
    void getAllFlawNumFromTree() {
        Integer allFlawNumFromTree = BugProbabilityHelper.getAllFlawNumFromTree(1);
//        assertEquals(5, allFlawNumFromTree);
    }

    @Transactional
    @Rollback
    @Test
    void getSubmitFlawNum() {
        Integer submitFlawNum = BugProbabilityHelper.getSubmitFlawNum(3);
//        assertEquals(6, submitFlawNum);
    }

    @Transactional
    @Rollback
    @Test
    void getSubmitReportNum() {
        Integer submitReportNum = BugProbabilityHelper.getSubmitReportNum(3);
//        assertEquals(2, submitReportNum);
    }

    @Transactional
    @Rollback
    @Test
    void getParticipateTaskNum() {
        Integer participateTaskNum = BugProbabilityHelper.getParticipateTaskNum(3);
//        assertEquals(3, participateTaskNum);
    }

    @Transactional
    @Rollback
    @Test
    void getSubmitReportNumByTime() {
        Integer submitReportNumByTime = BugProbabilityHelper.getSubmitReportNumByTime(3, ScopeType.LAST_THREE_DAYS);
        Integer submitReportNumByTime1 = BugProbabilityHelper.getSubmitReportNumByTime(3, ScopeType.LAST_TWO_WEEKS);
        Integer submitReportNumByTime2 = BugProbabilityHelper.getSubmitReportNumByTime(3, ScopeType.LAST_ONE_MONTH);
//        assertEquals(0, submitReportNumByTime);
//        assertEquals(0, submitReportNumByTime1);
//        assertEquals(0, submitReportNumByTime2);
    }

    @Transactional
    @Rollback
    @Test
    void getAllFlawNumFromTask() {
        Integer allFlawNumFromTask = BugProbabilityHelper.getAllFlawNumFromTask(8);
//        assertEquals(5, allFlawNumFromTask);
    }

    @Transactional
    @Rollback
    @Test
    void getSubmitFlawNumFromTask() {
        Integer submitFlawNumFromTask = BugProbabilityHelper.getSubmitFlawNumFromTask(3, 8);
//        assertEquals(3, submitFlawNumFromTask);
    }

    @Transactional
    @Rollback
    @Test
    void getAllFlawNum() {
        Integer allFlawNum = BugProbabilityHelper.getAllFlawNum();
//        assertEquals(18, allFlawNum);
    }

    @Transactional
    @Rollback
    @Test
    void getSimilarityList() {
        List<Double> similarityList = BugProbabilityHelper.getSimilarityList(1);
//        assertEquals(11, similarityList.size());
    }

    @Transactional
    @Rollback
    @Test
    void getSubmitTaskIdList() {
        List<Integer> submitTaskIdList = BugProbabilityHelper.getSubmitTaskIdList(3);
        assertNotNull(submitTaskIdList);
        assertNotEquals(0, submitTaskIdList.size());
//        assertEquals(2, submitTaskIdList.size());
    }

    @Transactional
    @Rollback
    @Test
    void getAllEvaInfo1() {
        List<List<Integer>> allEvaInfo = BugProbabilityHelper.getAllEvaInfo(7);
        assertNotNull(allEvaInfo);
//        assertNotEquals(0, allEvaInfo.size());
//        assertEquals(2, allEvaInfo.size());
//        assertEquals(1, allEvaInfo.size());
    }

    @Transactional
    @Rollback
    @Test
    void getAllEvaInfo2() {
        List<List<Integer>> allEvaInfo = BugProbabilityHelper.getAllEvaInfo(9);
        assertNotNull(allEvaInfo);
        assertNotEquals(0, allEvaInfo.size());
//        assertEquals(1, allEvaInfo.size());
    }

    @Transactional
    @Rollback
    @Test
    void getAllScoreList() {
        List<Score> allScoreList = BugProbabilityHelper.getAllScoreList(7);
        assertNotNull(allScoreList);
        assertNotEquals(0, allScoreList.size());
//        assertEquals(2, allScoreList.size());
    }

    @Transactional
    @Rollback
    @Test
    void getScoreFromFlawId() {
        Double scoreFromFlawId = BugProbabilityHelper.getScoreFromFlawId(8);
        assertNotNull(scoreFromFlawId);
//        assertEquals(4.5, scoreFromFlawId);
    }

    @Transactional
    @Rollback
    @Test
    void getLastSubmitTime() {
        Date time = BugProbabilityHelper.getLastSubmitTime(10000);
        assertNull(time);
//        System.err.println(BugProbabilityHelper.getLastSubmitTime(3));
//        System.err.println(BugProbabilityHelper.getLastSubmitTime(5));
    }

    @Transactional
    @Rollback
    @Test
    void getAllUserIds() {
        List<Integer> allUserIds = BugProbabilityHelper.getAllUserIds();
        assertNotNull(allUserIds);
//        assertEquals(9, allUserIds.size());
    }

    @Transactional
    @Rollback
    @Test
    void getWorkerAbilityByUserId() {
        WorkerAbility workerAbilityByUserId = BugProbabilityHelper.getWorkerAbilityByUserId(3);
        assertNotNull(workerAbilityByUserId);
    }

}
