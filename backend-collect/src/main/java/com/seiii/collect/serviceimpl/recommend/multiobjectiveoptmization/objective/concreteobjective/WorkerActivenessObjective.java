package com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective;

import com.seiii.collect.model.po.recommend.MultiObjectiveRecommendFactor;
import com.seiii.collect.model.po.task.taskrecruitstop.TaskRecruitStopRecommendFactor;
import com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.ObjectiveType;
import com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.abtractobjective.AbstractUserEvaluateObjective;
import com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective.bugprobability.activeness.WorkerActiveness;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkerActivenessObjective extends AbstractUserEvaluateObjective {
    private Map<Integer, Double> workerActivenessMap = new HashMap<>();

    @Override
    public AbstractUserEvaluateObjective setWeight(MultiObjectiveRecommendFactor factor) {
        this.weight = factor.getActiveness();
        return this;
    }

    //不要使用spring自动注入mapper，请将mapper放在从构造方法传入
    @Override
    protected double calculateValue(List<Integer> userIds) {
        return userIds.stream().mapToDouble(userId -> workerActivenessMap.get(userId)).sum();

    }

    @Override
    protected ObjectiveType getObjectType() {
        return ObjectiveType.MAXIMIZE;
    }

    @Override
    protected void fillActual(TaskRecruitStopRecommendFactor factor, double resultValue) {
        factor.setActivenessactual(resultValue);
    }

    @Override
    public void fillExpected(TaskRecruitStopRecommendFactor factor, double resultValue) {
        factor.setActivenessexpected(resultValue);
    }

    @Override
    public void prepareForAllCandidateUsers(List<Integer> candidateUserIds) {
        WorkerActiveness workerActiveness = new WorkerActiveness();
        candidateUserIds.forEach(userId -> workerActivenessMap.put(userId, workerActiveness.getActivenessValue(userId)));
    }
}
