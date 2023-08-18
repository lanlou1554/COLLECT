package com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective.bugprobability.workerability;

import com.seiii.collect.mapper.user.worker.WorkerAbilityMapper;
import com.seiii.collect.model.po.user.worker.WorkerAbility;
import com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective.bugprobability.BugProbabilityHelper;
import com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective.bugprobability.workerability.abilities.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
// 综合能力
public class ComprehensiveAbility implements WorkerAbilityInterface {

    // 报告协作能力
    private Double collabVal;
    // 报告审查能力
    private Double reviewVal;
    // 创新能力
    private Double creatVal;
    // 寻找bug能力
    private Double detVal;

    // 语言表达能力
    private Double expVal;

    // 权重
    private final Double[] weight = {0.2, 0.2, 0.2, 0.2, 0.2};

    public Double getAbilityValue(Integer userId) {
        WorkerAbility workerAbility = BugProbabilityHelper.getWorkerAbilityByUserId(userId);
        return workerAbility.getComprehensiveval();
    }

    // 返回所有能力值列表
    // 浮点数
    // 顺序：协作、审查、创新、bug、语言、综合
    public List<Double> getAllAbilityValue(Integer userId) {
        WorkerAbility workerAbility = BugProbabilityHelper.getWorkerAbilityByUserId(userId);
        List<Double> res = new ArrayList<>();
        res.add(workerAbility.getCollabval());
        res.add(workerAbility.getReviewval());
        res.add(workerAbility.getCreatval());
        res.add(workerAbility.getDetval());
        res.add(workerAbility.getExpval());
        res.add(workerAbility.getComprehensiveval());
        return res;
    }

    @Override
    public Double calAbilityValue(Integer userId) {
        setAllAbilities(userId);
        return collabVal * weight[0] + reviewVal * weight[1]
                + creatVal * weight[2] + detVal * weight[3] + expVal * weight[4];
    }

    // 计算所有能力值，返回列表形式getAllAbilityValue
    public List<Double> calAllAbilityValue(Integer userId) {
        List<Double> res = new ArrayList<>();
        Double val = calAbilityValue(userId);
        res.add(this.collabVal);
        res.add(this.reviewVal);
        res.add(this.creatVal);
        res.add(this.detVal);
        res.add(this.expVal);
        res.add(val);
        return res;
    }

    public void setAllAbilities(Integer userId) {
        setCollabVal(userId);
        setReviewVal(userId);
        setCreatVal(userId);
        setDetVal(userId);
        setExpVal(userId);
    }

    public void setCollabVal(Integer userId) {
        this.collabVal = new CollaborationAbility().calAbilityValue(userId);
    }

    public void setReviewVal(Integer userId) {
        this.reviewVal = new ReportReviewAbility().calAbilityValue(userId);
    }

    public void setCreatVal(Integer userId) {
        this.creatVal = new CreativityAbility().calAbilityValue(userId);
    }

    public void setDetVal(Integer userId) {
        this.detVal = new BugDetectionAbility().calAbilityValue(userId);
    }

    public void setExpVal(Integer userId) {
        this.expVal = new ExpressionAbility().calAbilityValue(userId);
    }

    public Double getCollabVal() {
        return collabVal;
    }

    public Double getReviewVal() {
        return reviewVal;
    }

    public Double getCreatVal() {
        return creatVal;
    }

    public Double getDetVal() {
        return detVal;
    }

    public Double getExpVal() {
        return expVal;
    }

}
