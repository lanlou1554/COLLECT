package com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective;

import com.seiii.collect.model.po.recommend.MultiObjectiveRecommendFactor;
import com.seiii.collect.model.po.task.taskrecruitstop.TaskRecruitStopRecommendFactor;
import com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.ObjectiveType;
import com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.abtractobjective.AbstractUserEvaluateObjective;
import com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective.bugprobability.activeness.WorkerActiveness;
import com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective.bugprobability.workerability.ComprehensiveAbility;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkerAbilityObjective extends AbstractUserEvaluateObjective {
    private Map<Integer, Double> workerAbilityMap = new HashMap<>();

    @Override
    public AbstractUserEvaluateObjective setWeight(MultiObjectiveRecommendFactor factor) {
        this.weight = factor.getWorkerAbility();
        return this;
    }

    //不要使用spring自动注入mapper，请将mapper放在从构造方法传入
    @Override
    protected double calculateValue(List<Integer> userIds) {
        return userIds.stream().mapToDouble(userId -> this.workerAbilityMap.get(userId)).sum();
    }

    @Override
    protected ObjectiveType getObjectType() {
        return ObjectiveType.MAXIMIZE;
    }

    @Override
    protected void fillActual(TaskRecruitStopRecommendFactor factor, double resultValue) {
        factor.setAbilityactual(resultValue);
    }

    @Override
    public void fillExpected(TaskRecruitStopRecommendFactor factor, double resultValue) {
        factor.setAbilityexpected(resultValue);
    }

    @Override
    public void prepareForAllCandidateUsers(List<Integer> candidateUserIds) {
        ComprehensiveAbility comprehensiveAbility = new ComprehensiveAbility();
        candidateUserIds.forEach(
                userId ->
                this.workerAbilityMap.put(
                        userId,
                        comprehensiveAbility.getAbilityValue(userId)));
    }
}
