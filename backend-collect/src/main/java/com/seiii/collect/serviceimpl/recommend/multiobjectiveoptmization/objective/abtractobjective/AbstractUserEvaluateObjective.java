package com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.abtractobjective;

import com.seiii.collect.model.po.recommend.MultiObjectiveRecommendFactor;
import com.seiii.collect.model.po.task.taskrecruitstop.TaskRecruitStopRecommendFactor;
import com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.ObjectiveType;

import java.util.List;

public abstract class AbstractUserEvaluateObjective {
    protected double weight = 1;

    public double evaluate(final List<Integer> userIds){
        ObjectiveType type = getObjectType();
        double result = 0;
        double weight = getWeight();
        double value = calculateValue(userIds);
        if(!Double.isFinite(value)){
            value = 0;
        }
        switch (type){
            case MAXIMIZE:
                result = -value * weight;
                break;
            case MINIMIZE:
                result = value * weight;
                break;
            default:
                throw new RuntimeException("Unknown ObjectiveType: " + type);
        }
        if(!Double.isFinite(result)){
            result = 0;
        }
        return result;

    }
    public double evaluateAndFillActual(final List<Integer> userIds, TaskRecruitStopRecommendFactor factor){
        double result;
        fillActual(factor, result = evaluate(userIds));
        return result;
    }

    public AbstractUserEvaluateObjective setWeight(MultiObjectiveRecommendFactor factor){
        return this;
    }

    public double getWeight() {
        return weight;
    }

    protected abstract double calculateValue(final List<Integer> userIds);
    protected abstract ObjectiveType getObjectType();
    protected void fillActual(TaskRecruitStopRecommendFactor factor, double resultValue){};
    public void fillExpected(TaskRecruitStopRecommendFactor factor, double resultValue){};

    /**
     * 这个方法会在目标初始化之后被调用，用来预先计算一些中间结果，保证calculateValue方法的告诉执行。
     * @param candidateUserIds 所有候选用户的id，之后调用calculateValue方法所传入的工人Id将是该集合的子集。
     */
    public void prepareForAllCandidateUsers(final List<Integer> candidateUserIds){

    }
}
