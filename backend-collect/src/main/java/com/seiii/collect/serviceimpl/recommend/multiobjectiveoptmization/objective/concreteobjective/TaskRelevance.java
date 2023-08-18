package com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective;

import com.seiii.collect.model.po.recommend.MultiObjectiveRecommendFactor;
import com.seiii.collect.model.po.task.taskrecruitstop.TaskRecruitStopRecommendFactor;
import com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.ObjectiveType;
import com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.abtractobjective.AbstractUserEvaluateObjective;
import com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.abtractobjective.TaskRelatedUserEvaluatedObjective;
import com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective.workerrelevance.AbstractSimFactor;
import org.springframework.context.ApplicationContext;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TaskRelevance extends TaskRelatedUserEvaluatedObjective {
    private List<AbstractSimFactor> simFactors = new LinkedList<>();

    //不要使用spring自动注入mapper，请将mapper放在从构造方法传入
    public TaskRelevance(Integer taskId,ApplicationContext applicationContext) {
        super(taskId);
        Map<String, AbstractSimFactor> factors = applicationContext.getBeansOfType(AbstractSimFactor.class);
        for (String key:factors.keySet()){
            simFactors.add(factors.get(key));
        }
    }

    @Override
    public AbstractUserEvaluateObjective setWeight(MultiObjectiveRecommendFactor factor) {
        this.weight = factor.getTaskRelevance();
        return this;
    }

    @Override
    protected ObjectiveType getObjectType() {
        return ObjectiveType.MAXIMIZE;
    }

    @Override
    protected void fillActual(TaskRecruitStopRecommendFactor factor, double resultValue) {
        factor.setRelevanceactual(resultValue);
    }

    @Override
    public void fillExpected(TaskRecruitStopRecommendFactor factor, double resultValue) {
        factor.setRelevanceexpected(resultValue);
    }


    @Override
    // 给定工人集合，和任务id，返回这个工人集合与这个任务的相关性
    /*
    0. 先求出单个工人的相关性衡量，然后再算平均
    1. 单个工人的domain knowledge与此任务任务描述的相似性 —— 权重少
    2. 单个工人的过去任务的tag（或者domain knowledge）和这个任务tag（或者domain knowledge）的相似性
    3. 这个任务已经有的工人的工人tag（或者domain knowledge）和这个工人的工人tag（或者domain knowledge）的相似性
    */
    protected double calculateTaskRelatedValue(List<Integer> userIds, Integer taskId) {
        double res = 0.0;
        for (AbstractSimFactor factor:simFactors){
            res += factor.getWeight() * calRawFactorValues(factor, userIds,taskId);
        }
        return res;
    }

    public Double calRawFactorValues(AbstractSimFactor factor, List<Integer> workerIds,Integer taskId){
        Double total = 0.0;
        for(Integer id:workerIds){
            total += factor.calSingleWorkerFactorValues(id,taskId);
        }
        return total/workerIds.size();
    }
}
