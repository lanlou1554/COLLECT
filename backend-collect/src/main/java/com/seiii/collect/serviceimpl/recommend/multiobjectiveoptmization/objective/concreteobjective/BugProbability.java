package com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective;

import com.seiii.collect.model.po.recommend.MultiObjectiveRecommendFactor;
import com.seiii.collect.model.po.task.taskrecruitstop.TaskRecruitStopRecommendFactor;
import com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.ObjectiveType;
import com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.abtractobjective.AbstractUserEvaluateObjective;
import com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective.bugprobability.activeness.WorkerActiveness;
import com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective.bugprobability.workerability.ComprehensiveAbility;

import java.util.List;

@Deprecated
public class BugProbability extends AbstractUserEvaluateObjective {
    private Double workerAbilityWeight;
    private Double workerActivenessWeight;

    @Override
    public AbstractUserEvaluateObjective setWeight(MultiObjectiveRecommendFactor factor) {
        this.workerAbilityWeight = factor.getWorkerAbility();
        this.workerActivenessWeight = factor.getActiveness();
        this.weight = 1;
        return this;
    }

    //不要使用spring自动注入mapper，请将mapper放在从构造方法传入
    @Override
    protected double calculateValue(List<Integer> userIds) {
        double res = 0.0;
        ComprehensiveAbility comprehensiveAbility = new ComprehensiveAbility();
        WorkerActiveness workerActiveness = new WorkerActiveness();
        for (Integer userId : userIds) {
            Double abilityValue = comprehensiveAbility.getAbilityValue(userId);
            Double activenessVal = workerActiveness.getActivenessValue(userId);
            res += (abilityValue * workerAbilityWeight
                    + activenessVal * workerActivenessWeight);
        }
        return res;
    }

    @Override
    protected ObjectiveType getObjectType() {
        return ObjectiveType.MAXIMIZE;
    }


}
