package com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective.bugprobability.workerability.abilities;

import com.seiii.collect.serviceimpl.recommend.multiobjectiveoptmization.objective.concreteobjective.bugprobability.workerability.WorkerAbilityInterface;
import com.seiii.collect.util.Constant;
import org.springframework.stereotype.Component;

@Component
// todo 语言表达能力
public class ExpressionAbility implements WorkerAbilityInterface {
    @Override
    public Double calAbilityValue(Integer userId) {
        return Constant.EXPRESSION_ABILITY_DEFAULT_VALUE;
    }
}
