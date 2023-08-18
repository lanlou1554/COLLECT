package com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.abtractobjective;

import java.util.List;

public abstract class TaskRelatedUserEvaluatedObjective extends AbstractUserEvaluateObjective{
    private final Integer taskId;
    public TaskRelatedUserEvaluatedObjective(Integer taskId){
        this.taskId = taskId;
    }

    @Override
    protected double calculateValue(List<Integer> userIds) {
        return calculateTaskRelatedValue(userIds, taskId);
    }

    protected abstract double calculateTaskRelatedValue(List<Integer> userIds, Integer taskId);
}
