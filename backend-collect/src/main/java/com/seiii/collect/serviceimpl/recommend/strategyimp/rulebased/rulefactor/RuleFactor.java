package com.seiii.collect.serviceimpl.recommend.strategyimp.rulebased.rulefactor;

import com.seiii.collect.model.vo.task.TaskViewVO;

import java.util.List;
import java.util.stream.Collectors;

public abstract class RuleFactor {

    //小于这个容差值以后，推荐规则将不会计算优先数值，而是直接返回0
    protected static final Double TOLERANCE = 0.0001;

    protected double weight;

    public RuleFactor setWeight(double weight) {
        this.weight = weight;
        return this;
    }

    public List<Double> calculateWaitedFactorValues(Integer targetUserId, List<TaskViewVO> candidateTasks) {
        if (this.weight < TOLERANCE) {
            return candidateTasks.stream().map(task -> 0.0).collect(Collectors.toList());
        } else {
            return calculateRawFactorValues(targetUserId, candidateTasks).stream()
                    .map(doubleVal -> doubleVal * weight).collect(Collectors.toList());
        }
    }

    public abstract List<Double> calculateRawFactorValues(Integer targetUserId, List<TaskViewVO> candidateTask);
}
