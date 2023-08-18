package com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective;

import com.seiii.collect.model.po.recommend.MultiObjectiveRecommendFactor;
import com.seiii.collect.model.po.task.taskrecruitstop.TaskRecruitStopRecommendFactor;
import com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.ObjectiveType;
import com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.abtractobjective.AbstractUserEvaluateObjective;

import java.util.List;

public class WorkerCost extends AbstractUserEvaluateObjective {
    private Double maxCost;

    @Override
    public AbstractUserEvaluateObjective setWeight(MultiObjectiveRecommendFactor factor) {
        weight = factor.getWorkerCost();
        return this;
    }
    public WorkerCost(double maxCost){
        this.maxCost = maxCost;
        if(maxCost == 0.0)this.maxCost++;
    };

    //不要使用spring自动注入mapper，请将mapper放在从构造方法传入
    @Override
    protected double calculateValue(List<Integer> userIds) {
        return (double) userIds.size() / this.maxCost;
    }

    @Override
    protected ObjectiveType getObjectType() {
        return ObjectiveType.MINIMIZE;
    }

}
