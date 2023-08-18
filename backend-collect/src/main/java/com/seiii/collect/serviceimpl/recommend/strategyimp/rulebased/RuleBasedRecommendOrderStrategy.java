package com.seiii.collect.serviceimpl.recommend.strategyimp.rulebased;

import com.seiii.collect.mapper.recommend.RecommendRuleFactorMapper;
import com.seiii.collect.model.po.recommend.RecommendRuleFactor;
import com.seiii.collect.model.vo.task.TaskViewVO;
import com.seiii.collect.serviceimpl.recommend.prioritytask.PriorityTask;
import com.seiii.collect.serviceimpl.recommend.strategy.RecommendStrategy;
import com.seiii.collect.serviceimpl.recommend.strategyimp.rulebased.rulefactor.RuleFactor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RuleBasedRecommendOrderStrategy implements RecommendStrategy, ApplicationContextAware {

    private ApplicationContext applicationContext;

    private RecommendRuleFactorMapper ruleFactorMapper;


    private List<RuleFactor> prepareFactors(List<RecommendRuleFactor> recommendRuleFactors) {
        return recommendRuleFactors.stream()
                .map(ruleFactorVO ->
                        applicationContext
                                .getBean(ruleFactorVO.getFactorname(), RuleFactor.class)
                                .setWeight(ruleFactorVO.getFactorweight())
                )
                .collect(Collectors.toList());
    }


    @Override
    public List<TaskViewVO> recommend(Integer targetUserId, List<TaskViewVO> candidateTasks) {
        if (candidateTasks.isEmpty()) return new ArrayList<>();//如果没有待推荐的任务，则直接返回，不进行任何操作
        List<RecommendRuleFactor> factors = ruleFactorMapper.selectByUsingRule();
        List<PriorityTask> priorityTasks = candidateTasks.stream().map(PriorityTask::new).collect(Collectors.toList());
        for (RuleFactor factor : prepareFactors(factors)) {
            List<Double> factorValues = factor.calculateWaitedFactorValues(targetUserId, candidateTasks);
            for (int i = 0; i < candidateTasks.size(); i++) {
                priorityTasks.get(i).addPriorityBy(factorValues.get(i));
            }
        }
        return priorityTasks.stream().sorted().map(PriorityTask::getTask).collect(Collectors.toList());
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Autowired
    public void setRuleFactorMapper(RecommendRuleFactorMapper ruleFactorMapper) {
        this.ruleFactorMapper = ruleFactorMapper;
    }
}
